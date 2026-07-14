---
description: "Week-level orchestrator. Loads every topic in the named week with all docs context (course scope, narrative seed, lab seed, assessment ref) and walks the teammate through the per-topic loop. Use --resume to pick up from the latest PENDING checkpoint."
allowed-tools: [Read, Bash, Task, AskUserQuestion]
argument-hint: "week-<NN>   [--resume]   (e.g. /content-gen week-1, /content-gen week-7 --resume)"
---

# /content-gen

Entry point for a week's content-generation work. Parses the week argument, loads every topic in delivery order with **all the context baked in at ingest** (course scope, module title, narrative seed, lab seed, next assessment) — so the teammate never needs to re-read the xlsx or docx.

## What it does

1. **Parse the week argument, then check out the week branch.** Accept `week-1`, `week-01`, `W1`, or `W01`. Normalize to a `week_num` integer 1–15.

   Then isolate this week's work on its own branch — `week-<week_num>` (e.g. `week-1`). This runs on **both** the fresh and `--resume` paths, before any content work, so two teammates on different weeks never collide on `main`:
   - `git rev-parse --abbrev-ref HEAD` — if already on `week-<week_num>`, nothing to do; continue.
   - Otherwise check `git status --porcelain`. **If the working tree is dirty,** do not switch — pause and ask the user (AskUserQuestion: `Commit/stash first` / `Switch anyway` / `Stay here`), since switching could carry or clobber in-progress work. Only proceed once it's resolved.
   - If `git rev-parse --verify --quiet refs/heads/week-<week_num>` succeeds, the branch exists: `git checkout week-<week_num>`.
   - Otherwise create it off the main branch so weeks don't stack on each other: `git checkout -b week-<week_num> main`.
   - Narrate the result terse-style (`on branch week-1 (created off main)` / `on branch week-1 (existing)`).

2. **`--resume` path.** If `--resume` is in `$ARGUMENTS`:
   ```
   python .claude/scripts/session_checkpoint.py latest-pending <week_num>
   ```
   - If a filename is printed (non-empty stdout, exit 0): cat the checkpoint via `python .claude/scripts/session_checkpoint.py restore <filename>` (this also marks it CONSUMED). Surface the topic id + stage to the user, then skip to step 5 with that topic as the starting cursor.
   - If empty (exit 1): no per-topic PENDING checkpoint. Before declaring a fresh start, check whether the week is parked at the **end-of-week HITL #4** gate — scan every topic in the week, and if all are at `topic_artifacts_approved` with `module_drift_passed=true` but any has `week_human_approved=false`, jump straight to step 11. If any topic is at `awaiting_topic_artifacts_review` (HITL #3 stopped mid-week), drop the user back at that topic's HITL #3 instead. Otherwise proceed as a fresh start (step 3).

3. **Caveman check.** `python .claude/scripts/caveman_check.py require` — refuse if off.

4. **Load week context.** Look up every topic where `topic.manifest.json.week_num == week_num` via `content/curriculum/sequence.index.json` filter. For each, read its `topic.manifest.json` to get the full delivery-order list. From the first topic, jump to:
   - `course.manifest.json` (parent `course.slug`) → pull `scope`, `weeks_range`, `total_hours`, and the `assessments` array (so you can show which A* is due during/after this week).
   - `module.manifest.json` (each topic's `module.slug`) → pull `narrative_seed_by_week[week_num]` and `lab_activity_seed_by_week[week_num]`.

5. **Show the week summary.** Render a compact preview the user can react to:

   ```
   Week N — <session_name>  [<course_code> <course_title>]

   Course scope: <one paragraph from course.manifest.json.scope>
   Lab activity: <lab_activity_seed[week_num]>
   Next assessment: <A<n> — <title>, due W<NN>, <weight>>  (or "none in scope")

   Topics in delivery order:
     1.1  <title>                     [<stage>]   est <expected_delivery_minutes or '—'> min
     1.2  <title>                     [<stage>]   est <expected_delivery_minutes or '—'> min
     ...
   ──────────────────────────────────
   Week budget: <Σ expected_delivery_minutes or '—'> / 90 min
   ```

6. **Pick the cursor.** Find the first topic in delivery order whose `progress.stage` is not at a terminal post-quiz state. That's where the teammate picks up.

7. **Drive the per-topic loop end-to-end.** For the current cursor topic, walk every stage automatically until you hit a HITL gate, then PAUSE for human input:
   - `awaiting_resources` → **PAUSE — HITL gate #1.** Run `/topic-resources <id>` (which uses AskUserQuestion to capture the resources). After the user finishes, continue automatically.
   - `awaiting_corpus` → auto-fire `/topic-corpus <id>`. Wait for return.
   - `awaiting_corpus_critic` → auto-fire `/critic-corpus <id>`. If fail, regenerate-and-revise loop runs without user intervention (max 2 tries before asking the user).
   - `awaiting_corpus_human_review` → **PAUSE — HITL gate #2.** Run `/topic-corpus-review <id>` (section-by-section approval + depth profile pick via AskUserQuestion). After the user finishes, continue automatically.
   - `corpus_approved` with `artifact_plan.diagram=true` → auto-fire `/generate-diagram <id>` then `/critic-diagram <id>` sequentially.
   - `corpus_approved` (diagram done or not required) → auto-fire `/generate-reading <id>` and `/generate-quiz <id>` in parallel via Task tool. Then their critics.
   - `awaiting_*_critic` — auto-fire the matching critic. Regenerate-and-revise on fail (max 2 tries before asking the user).
   - `quiz_approved` (and `reading_critic_passed=true`, and `diagram_critic_passed=true` when applicable) → set stage `awaiting_topic_artifacts_review` and **PAUSE — HITL gate #3.** Run `/topic-artifacts-review <id>` to surface reading + quiz (+ diagram) for human sign-off. **The cursor does not advance until the gate returns Approve.** On **Request changes**, the gate walks the matching `*_critic_passed` gate back to false + stage back to `awaiting_*_critic`; the orchestrator then re-enters this step. On **Defer**, exit cleanly — `/content-gen week-N --resume` drops the user back at this gate.
   - `topic_artifacts_approved` → topic complete. **Move cursor to next topic** in delivery order, repeat from step 7.

   The user sees prompts at the per-topic HITL gates (resources #1, corpus review #2, **artifact approval #3**) plus context-budget warnings. Everything else is automatic; the orchestrator narrates terse-style ("1.1 corpus drafted", "1.1 reading + quiz generated", "1.1 artifacts up for review", "1.1 approved — moving to 1.2"). HITL #4 (end-of-week approval) is a rollup of HITL #3 approvals plus drift critic verdicts; it fires once per week in step 11.

8. **Watch context every transition.** Run `context-budget-advisor` between every stage. If it returns `soft_checkpoint` or `hard_checkpoint`, pause and prompt the user via AskUserQuestion: "Context at X%. Run /save-session before continuing?" with `Save now` / `Continue without saving` options.

9. **After each artifact passes its critic.** Sum `topic.manifest.json.expected_delivery_minutes` across the week's completed topics. Surface the running total against 90 min in the orchestrator's narration. If currently tracking >113 min or <68 min, flag early so the teammate can adjust the depth profile at the *next* HITL gate.

10. **End-of-week module-drift critic (automatic).** When every topic in the week reaches `topic_artifacts_approved` (HITL #3 passed for every topic):
    - Auto-fire `/critic-module-drift <module-slug>` for each module in the week in parallel (via Task tool fan-out).
    - On fail: surface the drift critic's findings and stop. The user fixes flagged topics and reruns `/content-gen week-N --resume` to retry. **Skip step 11** — the gate only opens once drift passes.
    - On pass: continue to step 11.

11. **HITL gate #4 — End-of-week rollup approval (PAUSE).** Drift critic passed; every topic was already approved at HITL #3. This gate is the rollup — confirm the *week as a unit* is shippable (drift findings included), not re-relitigate every artifact. If HITL #3 was honored, this step is a quick scan, not a deep review.

    a. **Build the rollup packet.** Render a compact summary scoped to *week-level* signals — per-artifact review already happened at HITL #3, so list only what's new at the week level:

       ```
       Week N — <session_name>  [<course_code>]  rollup approval

       Topics (delivery order):
         1.1  <title>     HITL#3 ✓ approved   <YYYY-MM-DD>
         1.2  <title>     HITL#3 ✓ approved (1 known failure accepted: diagram)   <YYYY-MM-DD>
         ...
       Module-drift critics: M<n> ✓   M<n+1> ✓   (any findings surfaced here)
       Week budget: <Σ expected_delivery_minutes> / 90 min   (soft band ±15%, hard ±25%)
       Per-topic overrides accepted at HITL #3: <list — e.g. "1.2 diagram critic-fail accepted">
       ```

    b. **Ask the teammate via `AskUserQuestion`.** Single prompt, three options:

       | Option | Effect |
       |---|---|
       | **Approve week** | `python .claude/scripts/progress_update.py ... set week_human_approved true` for every topic in the week. Continue to step 12. |
       | **Request changes** | Capture which topic(s) + which artifact(s) need rework via a follow-up `AskUserQuestion` (multi-select of `<topic-id>:reading`, `<topic-id>:quiz`, `<topic-id>:diagram`). For each selection, walk the cursor back: set the matching `*_critic_passed` gate to `false` AND `topic_artifacts_human_approved` to `false`, stage `awaiting_*_critic`. The orchestrator re-enters the per-topic loop from step 7 — which means HITL #3 will re-fire for the reworked topic before drift re-runs. After rework, re-run module-drift critic for affected modules, then re-enter this gate. |
       | **Defer** | Leave gates as-is and exit. `/content-gen week-N --resume` re-enters this gate. |

    c. **Never auto-approve.** Even if every HITL #3 returned approve, this gate is the only place `week_human_approved` becomes true. No `--yes` flag. No auto-approve on `--resume`.

    d. **Per-topic carry-over overrides surface here for visibility, not re-litigation.** Carry-over critic failures were already accepted (or rejected) at HITL #3. Re-listing them at HITL #4 is informational — the reviewer should confirm the *aggregate* tolerance is acceptable. To revoke a per-topic override, pick **Request changes** with that topic+artifact and run the loop again.

12. **Mark the week complete, then offer to ship the branch.** Once HITL #4 returns **Approve week** (with or without override):
    - For every module in the week: `python .claude/scripts/progress_update.py ... set module_passed true`.
    - Print `Week N complete ✓` to the user.
    - **Offer to push + open a PR (PAUSE).** The week's work lives on `week-<week_num>`. Ask via `AskUserQuestion` — single prompt, three options:

      | Option | Effect |
      |---|---|
      | **Push & open PR** | `git push -u origin week-<week_num>`, then `gh pr create --base main --head week-<week_num> --title "Week <week_num> content — <session_name>" --body "<summary of topics + artifacts produced>"`. Surface the PR URL. |
      | **Push only** | `git push -u origin week-<week_num>`. No PR. |
      | **Not now** | Leave the branch local. Done — the user can push later. |

    - Never push or open a PR without this explicit choice. If `git push` fails (no remote, auth), report the error and leave the branch local — do not retry destructively. Done.

13. **End-of-course (the user runs this manually).** When the user notices every week in a course has shown complete, they run `/critic-course-drift M<n>` themselves. That's the one command outside the per-week flow.

## What this command never does

- Does not skip HITL gates (resources #1 / corpus review #2 / **per-topic artifact approval #3** / **end-of-week rollup #4**). Even on `--resume`, the user is dropped at the gate the previous session was at.
- Does not auto-approve a topic. `topic_artifacts_human_approved` only becomes true via HITL #3 — and the cursor never advances to the next topic without it.
- Does not auto-approve the week. `week_human_approved` only becomes true via HITL #4, never via flag or `--resume`.
- Does not re-read the xlsx/docx — every fact comes from the manifests written at `/curriculum-ingest`.
- Does not run `/critic-course-drift`. That's the only command the user invokes manually at end-of-course (multi-week boundary).
- Does not switch branches over a dirty working tree without asking, and never `git push` / opens a PR without the explicit choice at step 12.

## What this command DOES auto-fire

- Checks out (or creates off `main`) the `week-<week_num>` branch at startup — both fresh and `--resume`.
- Every per-topic generation + critic step between the four HITL gates (corpus, corpus-critic, reading, reading-critic, quiz, quiz-critic, diagram, diagram-critic when applicable).
- Regenerate-and-revise loop on critic failures (up to 2 retries before asking the user).
- HITL #3 (`/topic-artifacts-review`) is auto-invoked when the per-topic critics all pass — but the gate itself ALWAYS pauses for human input; it never auto-approves.
- `/critic-module-drift` for every module in the week, once every topic in the week is at `topic_artifacts_approved`.
- Surfaces the end-of-week rollup packet for HITL #4 — never auto-approves.

## Examples

```
/content-gen week-1
/content-gen week-7 --resume
/content-gen W14
```

## Behavior when the week doesn't exist

If `week_num` is < 1 or > 15 (or no topics match), refuse with an explicit message: "No topics found for week N. Did you mean weeks 1–15? Run /content-status to see what's claimed."
