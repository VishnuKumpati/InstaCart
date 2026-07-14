# CIT Content Generation — `.claude/` Bundle

This `.claude/` folder ships the slash commands, skills, hooks, subagents, and tooling the team uses to generate content (corpus → reading → quiz → diagram) from the finalized CIT curriculum sources.

> **On import:** this folder is named `dot-claude/` in the prototyping repo because the prototyping environment reserves `.claude/`. When you import into the real content repo, rename `dot-claude/` to `.claude/`.

## Hierarchy — course / week / module / topic

The content tree groups modules under a `week-<N>/` directory. Week is both a directory level **and** metadata (`week_num` lives on every module + topic manifest):

```
content/
  <course-slug>/                                  # e.g. m1-computational-thinking  (M1–M6)
    course.manifest.json
    week-<N>/                                     # e.g. week-1, week-2  (week_num, no zero-pad)
      <module-slug>/                              # e.g. 1-understanding-computation  (position + slug)
        module.manifest.json                      (carries week_num + lab seeds)
        <topic-slug>/                             # e.g. 1-1-what-is-computation
          topic.manifest.json                     (carries week_num, expected_delivery_minutes)
          topic.progress.json
          topic.resources.json                    (created at HITL gate #1)
          topic_corpus.md                         (filled by /topic-corpus)
          artifacts/
            reading.md + reading.meta.json
            quiz.gift + quiz.meta.json
            diagram.mmd + diagram.png
          critic-reports/
            corpus.critic.json
            reading.critic.json
            quiz.critic.json
            diagram.critic.json
```

Plus the curriculum-wide sequence file:

```
content/curriculum/
  sequence.md            # human-readable, frontmatter per topic
  sequence.index.json    # machine-readable mirror
```

**Term mapping** (so the team and the docs stay aligned):

| In our content tree | In the source docs (xlsx Overview) | Notes |
|---|---|---|
| `<course>/`        | "Module" column (M1–M6)             | Multi-week. Has `course.manifest.json` with scope + week range. |
| `week-<N>/`        | "Week" sheet                        | Groups the week's modules. The week lives here in the path **and** in `week_num` on each manifest. |
| `<module>/`        | "Topic Group" rows in the Week sheet | Folder slug is `<position>-<name>` (no week prefix); position resets to 1 per week. |
| `<topic>/`         | "Sub-topic" rows in the Week sheet  | The atomic unit — corpus + artifacts live here. |

A course spans multiple weeks (e.g. `m1-computational-thinking/` holds `week-1/` and `week-2/`), and each week directory holds that week's modules.

## Time budget — 90 min per week

College-audience constraint: the **sum of every topic's delivery time in a week is 90 minutes**.

- Reading WPM target: **200** (lower-end college reader).
- Reading minutes = `ceil(word_count / 200)`.
- Quiz minutes = `ceil((mcq*60 + short_answer*90) / 60)`.
- Topic's `expected_delivery_minutes` = reading + quiz, written into `topic.manifest.json` after the reading + quiz critics pass.

Depth profiles (from `config/artifact-mix.json`):

| Profile  | Reading      | Quiz                  | Total est. |
|---       |---           |---                    |---         |
| atomic   | 4–6 min      | 5–7 Q + short-answers | ~8 min     |
| standard | 6–8 min      | 5–6 MCQs              | ~10 min    |
| deep     | 9–11 min     | 5–7 Q + short-answers | ~14 min    |

The **course-drift critic** enforces the 90-min target per week: soft-warn at ±15%, hard-fail at ±25%. When a week is over- or under-budget, it suggests specific topics to downgrade or upgrade.

## Workflow

The entry point is **`/content-gen week-N`**. It loads everything a teammate needs from the manifests (course scope, lab seed, next assessment, the ordered topic list) and walks them through the per-topic loop without re-reading the source docs. Use `--resume` to pick up from the latest PENDING session checkpoint for that week.

Inside the loop, per topic, in source-delivery order:

1. **/topic-resources `<id>`** — HITL gate #1: capture ≥1 grounding resource (up to 5; at least one must be `primary`).
2. **/topic-corpus `<id>`** — corpus-subagent writes the 11-section markdown grounded in resources + prior topics from `curriculum/sequence.md`.
3. **/critic-corpus `<id>`** — corpus-critic hard-gates (required sections, citation density, forward references, length sanity).
4. **/topic-corpus-review `<id>`** — HITL gate #2: section-by-section human approval + confirm depth profile (drives the time budget).
5. **Artifact generation:**
   - When `artifact_plan.diagram = true`: **/generate-diagram `<id>` → /critic-diagram `<id>`** runs FIRST so the reading can embed the diagram inline.
   - **/generate-reading `<id>` + /generate-quiz `<id>`** then fan out in parallel.
6. **Per-artifact critics** — `/critic-reading`, `/critic-quiz`. Hard-block on fail; regenerate-and-revise.
7. **/critic-module-drift `<module-slug>`** — end-of-module within-week coherence (parallel across modules).
8. **/critic-course-drift `<course-code>`** — multi-week coherence + **weekly time-budget compliance** (90 min target per week, ±15% soft / ±25% hard). On pass, purges every checkpoint for the course's weeks.

The course has zero remaining ADO integration — the repo lives on ADO but no task tracking is wired in. Teammates pick a week and run `/content-gen week-N`.

## Setup (per teammate, Windows + macOS)

```bash
# 1. Clone the content repo
git clone <repo>
cd <repo>

# 2. Install caveman (output compression — main session lasts longer).
# macOS / Linux / Git Bash:
curl -fsSL https://raw.githubusercontent.com/JuliusBrussee/caveman/main/install.sh | bash
# Windows PowerShell:
# irm https://raw.githubusercontent.com/JuliusBrussee/caveman/main/install.ps1 | iex
# Then in any Claude Code session: /caveman full

# 3. Connect the Excalidraw MCP (https://mcp.excalidraw.com/mcp) in Claude settings.

# 4. Install Python deps for the ingester
python -m pip install openpyxl python-docx
# (If your system only has `python3`, alias it: alias python=python3.)

# 5. Open the repo in Claude Code. The CIT statusline activates automatically.

# 6. Run /curriculum-ingest once if content/ does not yet exist.
```

**All helper scripts are Python.** No Bash dependency. Works identically on Windows and macOS.

## HITL gates — hard block, no override

Every gate is enforced by `hooks/pre_command_router.py` reading `topic.progress.json`. If a required prior step has not been completed (or its critic has not passed), the command is refused and the user is pointed at the missing step. This is by design

## Two context strategies: caveman + session checkpoint

These are complementary, not the same thing.

**Caveman** is an output-compression skill that makes every Claude reply 60–75% shorter without losing technical accuracy. Always-on once installed (`/caveman full` per session). It delays how fast the main context fills up. It does not save state.

**Session checkpoint** is the save/restore mechanism this bundle adds on top. At every HITL gate, the `context-budget-advisor` skill:

1. Reads the current context % (Claude Code statusline env).
2. Projects the next step's main-context cost from `config/context-budget.json` (subagent-dispatched steps only count their dispatch + return-summary cost against main).
3. If the projected total crosses the band, prompts via AskUserQuestion to `/save-session` before continuing.

Bands (configurable):

- **< 25%** — green, safe to continue.
- **25–40%** — yellow, recommend `/save-session` at next HITL gate.
- **> 60%** — red, strongly recommend `/save-session` *before* next subagent dispatch.

The statusline shows both signals: caveman's own `[CAVEMAN] ⛏ Nk` badge plus the CIT bundle's `[cit ctx X% | cave on | t:N.N/stage | next +N% | band]`.

### Checkpoint lifecycle

Checkpoints live at `.claude/checkpoints/<ts>-topic-<id>.md` with YAML frontmatter:

| Field | Values | Set by |
|---|---|---|
| `status` | `PENDING` (default) → `CONSUMED` | `/save-session` writes PENDING; `/restore-session` or `/content-gen --resume` flips to CONSUMED |
| `week_num` | 1–15 | Auto from topic manifest |
| `topic_id` | e.g. `1.4` | Argument to /save-session |

`/content-gen week-N --resume` looks up the latest PENDING checkpoint for the week and loads it (marking it CONSUMED). When `/critic-course-drift` passes for a course, every checkpoint for the course's weeks is automatically purged.

Manual cleanup:
```
python .claude/scripts/session_checkpoint.py list --status PENDING --week 7
python .claude/scripts/session_checkpoint.py purge-consumed --week 7
python .claude/scripts/session_checkpoint.py purge-week 7
```

### Assessment context baked in

Every topic carries a `next_assessment` field in its manifest — populated at ingest from docx Table 2 (the 5 Set-A assessments). When a reading is generated and `next_assessment.due_week` is close to this topic's `week_num`, the reading's "Why this matters" section lands a 1-sentence "this contributes to A<n> — <title> (due W<NN>, <weight>)" hook. No re-reading the docx required.


## Layout

```
.claude/
  settings.json                       # statusline + hooks + env
  README.md                           # this file
  statusline.py                       # cross-platform statusline
  config/
    context-budget.json               # token estimates + bands
    artifact-mix.json                 # atomic/standard/deep + 90-min/week budget
  scripts/
    _lib.py                           # find_topic_dir, atomic_write_json
    caveman_check.py
    session_checkpoint.py             # /save-session and /restore-session
    progress_update.py                # atomic gate/stage/log updates
    sequence_update.py                # patches one topic in sequence.md
  checkpoints/                        # PENDING/CONSUMED markdown checkpoints (auto-purged)
  commands/                           # 17 slash commands (entry point: /content-gen week-N)
  skills/                             # 14 model-invoked specialists
  hooks/
    pre_command_router.py             # gate enforcement
  agents/                             # subagent definitions

tools/
  curriculum_ingest.py                # called via /curriculum-ingest
  render_architecture_diagram.py      # regenerates docs/cit-content-gen-architecture.png
```

## Where to start

Read in this order: `commands/curriculum-ingest.md`, then `commands/topic-resources.md`, then `commands/topic-corpus.md`. Those three cover everything before artifacts get generated.
