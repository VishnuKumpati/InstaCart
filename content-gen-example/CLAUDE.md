# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A demo / starter scaffold for the **CIT Content-Generation pipeline** — the team's `.claude/`-driven workflow for producing per-topic learning artifacts (reading + quiz + diagram) for the CIT Semester 1 curriculum.

Two things share the "content-gen" name and are easy to confuse:
- **The curriculum** — the 6 modules / 15 weeks / 157 topics of CIT Semester 1, materialized in `content/`. Locked with the client.
- **The pipeline** — the team's `/content-gen` workflow under `.claude/skills/` + `.claude/commands/`. It's how authors *generate* artifacts on top of that curriculum.

This repo ships the **scaffold + pre-ingested content tree**. A teammate clones, opens Claude Code, types `/content-gen week-1`, and starts producing artifacts immediately — no source xlsx/docx needed at runtime.

## Source of truth

| Topic | Authoritative location |
|---|---|
| Pipeline workflow (HITL gates, week loop) | `README.md` + `.claude/commands/content-gen.md` |
| Per-stage behavior (corpus / reading / quiz / diagram) | `.claude/commands/<stage>.md` + `.claude/skills/<role>/SKILL.md` |
| Manifest contract (course / module / topic / sequence) | `.claude/skills/coordinator/SKILL.md` + this file's "Manifest schema" section |
| Curriculum source | `content/curriculum/sequence.md` (human-readable), `content/curriculum/sequence.index.json` (machine) |
| Per-topic state | `content/<course>/week-<N>/<module>/<topic>/topic.progress.json` |

The xlsx + docx that originally produced the `content/` tree are **not in this repo**. They are locked with the client. If you need to rebuild the tree (e.g., curriculum changes), the source project has `tools/curriculum_ingest.py` — ask the eng lead.

## Quickstart

```
# Clone, then in Claude Code:
/content-gen week-1
```

That's the entire entry point. See `README.md` for the per-gate cheatsheet. Caveman mode auto-activates on session start (no setup).

## Pipeline (content-gen) — when and how

`/content-gen week-<NN>` is the per-week orchestrator. It walks every topic in delivery order through three HITL gates plus the per-stage critic loop. Use it when starting any week's content work.

```
/content-gen week-1                    # start
/content-gen week-7 --resume           # pick up at the last gate
/critic-course-drift M2                # end-of-course (manual, once all weeks complete)
/content-status                        # cross-curriculum status view
```

For a typo fix in a corpus or a one-line README tweak, the pipeline is overkill — edit directly. Reserve `/content-gen` for genuine artifact work.

## Hard rules (apply to every pipeline run — no exceptions)

- **40% context budget.** Statusline shows `[cit ctx X%]`. When it crosses 40%, run `/save-session` → `/clear` → `/content-gen week-N --resume`. The hooks soft-warn at 35% (orange band) and hard-stop at 40% (red).
- **Four HITL gates, never auto-bypassed.** HITL #1 (resources) and HITL #2 (corpus review) fire per topic before artifact fan-out. **HITL #3 (per-topic artifact approval) fires per topic after reading + quiz (+ diagram) critics all pass — the cursor never advances to the next topic without it.** HITL #4 (end-of-week rollup) fires once per week after module-drift critic passes. `topic_artifacts_human_approved` only becomes true through HITL #3; `week_human_approved` only through HITL #4. No flag, no `--resume` shortcut, no auto-approve.
- **HITL #3 is the per-topic quality stop, not a rubber stamp.** The author reviews reading + quiz (+ diagram when planned) at the moment they were generated — context fresh, paths in front of them. End-of-week (#4) is a rollup, not a deep review. Catching a problem at HITL #3 is always cheaper than catching it at HITL #4 with 11 baked topics on top.
- **Diagram critic verdicts on the PNG, not the .mmd.** Mermaid that parses can render as a mess. The builder ↔ critic loop renders the PNG and Claude's vision inspects it. After 2 cycles without pass, the gate stays false and HITL #3 surfaces it for human triage with the latest PNG attached.
- **Carry-over critic failures gate topic approval.** If any artifact has `*_critic_passed=false` after 2 builder ↔ critic cycles, **Approve topic artifacts** at HITL #3 is locked behind an explicit "accept with N known failures" override that gets logged to the topic manifest. HITL #4 surfaces the aggregate of these overrides but does not re-litigate them.
- **Mermaid is the default diagram tool.** Code-as-text, renders inline on GitHub + ADO + VS Code preview. Excalidraw is opt-in (`/generate-diagram <id> --excalidraw`) for hand-drawn aesthetic.
- **Never re-read the xlsx/docx.** Every fact comes from the manifests. If a manifest field is wrong, fix it in the manifest (or re-run `/curriculum-ingest` in the source project and copy the result over) — never patch around it in a command.

## Architecture cheatsheet

### Content tree

```
content/
├── curriculum/
│   ├── sequence.md           # human-readable delivery order (157 topics)
│   └── sequence.index.json   # machine index: {id, week_num, course_slug, module_slug, topic_slug, path, ...}
└── m<n>-<course-slug>/
    ├── course.manifest.json
    └── week-<N>/                     # week is a directory level (week_num, no zero-pad)
        └── <n>-<module-slug>/        # module folder: position + slug, no week prefix
            ├── module.manifest.json
            └── <p>-<n>-<topic-slug>/
                ├── topic.manifest.json
                ├── topic.progress.json
                ├── topic.resources.json     # written at HITL #1
                ├── topic_corpus.md          # written at corpus step
                ├── artifacts/               # reading.md (+reading.meta.json), quiz.gift (+quiz.meta.json), diagram.mmd, diagram.png
                └── critic-reports/          # corpus.critic.json, reading.critic.json, ...
```

Three module **types** map onto filesystem levels: `course` (m<n>-…), `module` (<n>-<module-slug>), `topic` (<p>-<n>-…), with a **`week-<N>/` directory grouping the modules of each week** under its course. Week is *also* metadata — every topic + module manifest still carries `week_num`, and the orchestrator filters `sequence.index.json` by `week_num` (it does not parse the folder name). The module folder no longer encodes the week: position resets to 1 inside each `week-<N>/`, so module slugs are just `<position>-<slug>` (e.g. `1-understanding-computation`).

### Manifest schema (read by every command — don't drift)

**`course.manifest.json`** — one per course directory:
```json
{
  "type": "course",
  "code": "M1",
  "title": "Computational Thinking",
  "slug": "m1-computational-thinking",
  "weeks_range": "1–2",
  "total_hours": "6 hrs",
  "scope": "Decomposition, abstraction, pseudocode, specification writing.",
  "week_nums": [1, 2],
  "assessments": [ { "id": "A1", "title": "...", "due_week": 3, "weight": "10%" }, ... ]
}
```

**`module.manifest.json`** — one per module directory (lives under `week-<N>/`):
```json
{
  "type": "module",
  "position": 1,
  "title": "Understanding Computation",
  "slug": "1-understanding-computation",
  "course_code": "M1",
  "course_slug": "m1-computational-thinking",
  "week_nums": [1],
  "session_name_by_week":     { "1": "How Machines Think" },
  "narrative_seed_by_week":   { "1": "What is computation — deterministic vs probabilistic …" },
  "lab_activity_seed_by_week":{ "1": "Draw flowcharts for 2 real-world tasks using Excalidraw …" }
}
```

**`topic.manifest.json`** — one per topic directory:
```json
{
  "type": "topic",
  "id": "1.1",
  "title": "What is computation",
  "slug": "1-1-what-is-computation",
  "position_in_module": 1,
  "week_num": 1,
  "session_name": "How Machines Think",
  "module": { "position": 1, "slug": "...", "title": "..." },
  "course": { "code": "M1", "slug": "...", "title": "..." },
  "next_assessment": { "id": "A1", "due_week": 3, "title": "...", "weight": "10%" } | null,
  "paths": {
    "corpus": "topic_corpus.md",
    "resources": "topic.resources.json",
    "progress": "topic.progress.json",
    "artifacts_dir": "artifacts/",
    "critic_reports_dir": "critic-reports/"
  },
  "artifact_plan": { "diagram": true|false, "diagram_intent": "..." } | null,
  "expected_delivery_minutes": <int> | null,
  "status": "scaffolded" | "corpus_drafted" | "artifacts_complete" | "module_passed"
}
```

**`topic.progress.json`** — the state machine; updated by `python .claude/scripts/progress_update.py`:
```json
{
  "topic_id": "1.1",
  "stage": "awaiting_resources" | "awaiting_corpus" | "awaiting_corpus_critic" |
           "awaiting_corpus_human_review" | "corpus_approved" |
           "awaiting_diagram_critic" | "awaiting_reading_critic" | "awaiting_quiz_critic" |
           "quiz_approved" | "awaiting_topic_artifacts_review" | "topic_artifacts_approved",
  "gates": {
    "resources_captured": false,
    "corpus_generated": false,
    "corpus_critic_passed": false,
    "corpus_human_approved": false,
    "reading_generated": false, "reading_critic_passed": false,
    "quiz_generated": false,    "quiz_critic_passed": false,
    "diagram_generated": null,  "diagram_critic_passed": null,   // null = not in artifact_plan
    "topic_artifacts_human_approved": false,                     // HITL #3
    "module_drift_passed": false,
    "week_human_approved": false,                                // HITL #4
    "course_drift_passed": false
  },
  "history": [ /* append-only stage transitions */ ]
}
```

Pristine post-ingest state: stage `awaiting_resources`, all gates false (or null where N/A).

### Per-stage flow (per topic)

```
awaiting_resources
  └─ HITL #1: /topic-resources <id>     ── teammate pastes 1-5 grounding refs
awaiting_corpus
  └─ auto: /topic-corpus <id>           ── corpus drafted from refs
awaiting_corpus_critic
  └─ auto: /critic-corpus <id>          ── regenerate-and-revise up to 2 tries
awaiting_corpus_human_review
  └─ HITL #2: /topic-corpus-review <id> ── section approval + depth pick (atomic/standard/deep)
corpus_approved
  ├─ (if artifact_plan.diagram=true) auto: /generate-diagram + /critic-diagram (builder↔critic loop, 2 cycles)
  └─ auto: /generate-reading + /generate-quiz (parallel via Task) + their critics
quiz_approved + reading_critic_passed (+ diagram_critic_passed when applicable)
  └─ awaiting_topic_artifacts_review
       └─ HITL #3: /topic-artifacts-review <id> ── per-topic reading + quiz (+ diagram) sign-off
                                                   cursor does NOT move until Approve
topic_artifacts_approved
  └─ next topic, repeat ──────────────────┐
                                          ▼
                              all topics in week at topic_artifacts_approved
                                          ▼
                              auto: /critic-module-drift <module> (per module, parallel)
                                          ▼
                              HITL #4: end-of-week rollup approval (drift findings + HITL #3 overrides)
                                          ▼
                              Week N complete ✓
```

End of multi-week course (manual, once every week complete):
```
/critic-course-drift M<n>
```

## Commands reference

| Command | Surface | Notes |
|---|---|---|
| `/content-gen week-<NN>` | orchestrator | the only command authors type to start work; `--resume` to continue |
| `/content-status` | read-only | cross-curriculum status view |
| `/save-session` | checkpoint | run at 40% ctx, then `/clear`, then `/content-gen week-<NN> --resume` |
| `/restore-session` | checkpoint | manual restore — orchestrator handles this on `--resume` |
| `/topic-resources <id>` | HITL #1 worker | invoked by orchestrator at the resources gate |
| `/topic-corpus <id>` | auto | corpus draft from resources |
| `/critic-corpus <id>` | auto critic | regenerate-and-revise on fail |
| `/topic-corpus-review <id>` | HITL #2 worker | invoked by orchestrator at the corpus review gate |
| `/generate-reading <id>` | auto | reading.md generation |
| `/critic-reading <id>` | auto critic | |
| `/generate-quiz <id>` | auto | quiz.gift (Moodle GIFT) generation |
| `/critic-quiz <id>` | auto critic | |
| `/generate-diagram <id>` | auto | mermaid by default; `--excalidraw` opt-in |
| `/critic-diagram <id>` | auto critic | verdicts on the rendered PNG via Claude vision |
| `/topic-artifacts-review <id>` | HITL #3 worker | invoked by orchestrator before cursor advances; reading + quiz (+ diagram) sign-off |
| `/critic-module-drift <module-slug>` | auto, end-of-week | parallel across all modules in the week |
| `/critic-course-drift M<n>` | manual, end-of-course | the one command authors invoke outside the per-week flow |

Authors should only need `/content-gen` + `/save-session` + `/critic-course-drift` in normal day-to-day. Everything else is auto-fired by the orchestrator.

## `.claude/` scaffold

```
.claude/
├── settings.json              # hooks + statusline + caveman session-start
├── hooks/
│   ├── session_start.py       # caveman auto-activation
│   └── pre_command_router.py  # ctx-budget gate + HITL enforcement
├── commands/                  # 17 commands (above)
├── agents/                    # subagent role definitions
├── skills/                    # 14 skills: coordinator, *-builder, *-critic, hitl-gate-checker, ...
├── scripts/
│   ├── progress_update.py     # the only writer of topic.progress.json
│   ├── caveman_check.py
│   ├── session_checkpoint.py  # checkpoint write / restore / list pending
│   └── sequence_update.py
└── statusline.py              # renders `[cit ctx X%] week-N · stage`
```

## Runtime prerequisite

**Python 3.8+ on PATH as `python`.** All of `.claude/` (hooks, statusline, scripts) is launched as `python .claude/...` from `settings.json`. If `python` is absent (some platforms only ship `python3`) Claude Code does not block — hooks fail **open**, silently. Cosmetic loss: caveman auto-activation + statusline. Real risk: `pre_command_router.py` stops enforcing HITL gates with no warning. Floor is ~3.7 (scripts use `from __future__ import annotations`, so `str | None` hints never evaluate); no upper bound. There is no `python3`/`py` fallback wired — if your machine lacks `python`, alias/symlink it.

## Secrets & env

Nothing in this repo requires runtime secrets (no LLM key beyond what Claude Code provides). If you wire a personal API key, keep it in `~/.config/...` not in this tree — `.gitignore` blocks `.env*` defensively.

## Skill discovery

Tier-3 skills live at `.claude/skills/<name>/SKILL.md`. Available in this repo:
`coordinator`, `context-budget-advisor`, `corpus-critic`, `course-drift-critic`, `diagram-builder`, `diagram-critic`, `drift-critic`, `hitl-gate-checker`, `quiz-builder`, `quiz-critic`, `reading-builder`, `reading-critic`, `sequence-reader`, `topic-corpus-builder`.

Authors don't `cd` to a special subdir — Claude Code walks up from CWD until it finds `.claude/skills/`.

## Status

- **Pipeline:** demo-ready scaffold. Diagram builder + critic flipped to mermaid-first. Four HITL gates: #1 resources, #2 corpus review, #3 per-topic artifact approval (cursor-blocking), #4 end-of-week rollup.
- **Content tree:** 157 topics pre-ingested, all at pristine `awaiting_resources` state.
- **Source xlsx/docx:** intentionally absent from this repo — locked with the client; available in the source project.
