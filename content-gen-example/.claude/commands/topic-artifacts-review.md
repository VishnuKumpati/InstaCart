---
description: HITL gate #3 — per-topic artifact approval. Surfaces reading + quiz (+ diagram) for human sign-off before the cursor advances to the next topic.
allowed-tools: [Read, Bash, AskUserQuestion]
argument-hint: "<topic-id>"
---

# /topic-artifacts-review

You are running **HITL gate #3** — the per-topic artifact approval gate. The cursor does not move to the next topic until this gate returns **Approve**. The end-of-week gate (HITL #4) is a *rollup* of these per-topic approvals; if HITL #3 was bypassed the rollup is meaningless.

## Refuse if

- Caveman not active.
- `progress.json.gates.reading_critic_passed` is not true.
- `progress.json.gates.quiz_critic_passed` is not true.
- `topic.manifest.json.artifact_plan.diagram == true` AND `progress.json.gates.diagram_critic_passed` is not true.
- `progress.json.gates.topic_artifacts_human_approved` is already true. This gate runs once per artifact cycle; if the user needs to revise after approval, the orchestrator walks the cursor back to the matching `awaiting_*_critic` stage and re-enters this gate after the rebuild.

## Why this gate exists

Without it, a problem in topic 1.1's reading or quiz is invisible until HITL #4 — by then 11 more topics may be baked on top of bad output, and rework cascades. HITL #3 is the *closest* gate to the work, where the author still has full context.

## What to do

1. **Build the per-topic approval packet.** Read:
   - `topic.manifest.json` for title, depth profile, artifact_plan
   - `progress.json` for gate states + history (esp. revise-cycle log lines)
   - `artifacts/reading.md` + `artifacts/reading.meta.json` for word_count + reading_minutes
   - `artifacts/quiz.gift` + `artifacts/quiz.meta.json` for question_count + position distribution + `$bias_defense_applied`
   - `artifacts/diagram.mmd` + `artifacts/diagram.png` (if `artifact_plan.diagram == true`)
   - `critic-reports/reading.critic.json` + `critic-reports/quiz.critic.json` (+ `diagram.critic.json` when applicable) for the latest verdicts and any soft findings

   Render terse:

   ```
   ── HITL #3 — Topic <id> artifacts ──
   <id>: <title>   (M<n> / <module> / week <N>)   depth: <profile>

   Reading     artifacts/reading.md            <words>w / <minutes> min      critic ✓ PASS (<rev-cycles>)
                artifacts/reading.meta.json     sidecar
   Quiz        artifacts/quiz.gift              <q-count>q (<mcq> MCQ + <sa> SA)   critic ✓ PASS (<rev-cycles>)   positions <dist>   bias_defense ✓
                artifacts/quiz.meta.json        sidecar
   Diagram     artifacts/diagram.mmd            critic ✓ PASS                       (or "— skipped (artifact_plan.diagram=false)")
                artifacts/diagram.png

   Carry-over critic failures: <list of *_critic_passed=false after 2 cycles, with the latest verdict>   (or "none")
   Soft findings (from critics, deferred): <list — INCLUDE any beginner-readability / formatting / scope_creep findings from the reading + corpus critics>   (or "none")
   Notes (from history): <e.g. "depth override → atomic at HITL #2", "quiz revise 1/2">
   ```

   **Surface the three priority lenses explicitly.** Pull these from the reading/quiz/diagram critic reports so the reviewer sees them at a glance — they are the team's top concerns and the reason most rework happened:
   - **Beginner-clear?** Terms/acronyms defined on first use, one idea at a time, concrete-before-abstract (any `undefined_terms` / `jargon_dump` / `abstract_first` findings).
   - **Well-formatted?** Bullets/numbered steps over prose walls, inline examples, no walls of text (any `wall_of_text` / `unlisted_sequence` / `no_inline_examples` findings).
   - **On the headline?** Nothing taught beyond the topic title (any `scope_creep` / `out_of_scope_question` findings — these are hard fails and should already have blocked the critic, but flag if a human override let one through).

2. **If `artifact_plan.diagram == true`, surface the diagram PNG path explicitly.** The reviewer should open it in the editor's preview pane before clicking Approve — the diagram critic's verdict is on the rendered PNG, not the `.mmd`, so the human's eyes matter here.

3. **Ask the teammate via `AskUserQuestion`.** Three options, mirroring the end-of-week shape but scoped to one topic:

   | Option | Effect |
   |---|---|
   | **Approve topic artifacts** | `python .claude/scripts/progress_update.py <id> set topic_artifacts_human_approved true`. Stage → `topic_artifacts_approved`. Orchestrator advances cursor. |
   | **Request changes** | Follow-up `AskUserQuestion` (multi-select): `reading` / `quiz` / `diagram` (only show diagram if applicable). For each selection, walk the cursor back: reading → set `reading_critic_passed=false`, stage `awaiting_reading_critic`; quiz → set `quiz_critic_passed=false`, stage `awaiting_quiz_critic`; diagram → set `diagram_critic_passed=false`, stage `awaiting_diagram_critic`. The orchestrator re-fires the matching `/generate-*` + `/critic-*` pair, then re-enters this gate. Capture the author's rework notes in the regen prompt; do not just re-run blindly. |
   | **Defer** | Leave gates as-is and exit. Reentering via `/content-gen week-N --resume` finds the topic at `awaiting_topic_artifacts_review` and drops the user back here. |

4. **Carry-over failures must be acknowledged.** If any artifact has `*_critic_passed=false` after the orchestrator's 2 builder ↔ critic cycles, the **Approve topic artifacts** option is grayed out — the teammate must either pick **Request changes** to drive it to pass OR explicitly override via a follow-up `AskUserQuestion` ("Approve with N known critic failure(s)? [Yes, accept as-is / Cancel]"). The override is logged to `topic.progress.json.history` with the per-failure rationale captured as the log message, so it bubbles up to HITL #4 (end-of-week rollup) and `/content-status`.

5. **Never auto-approve.** Even when every critic returned pass on the first try, this gate is the only place `topic_artifacts_human_approved` becomes true. No `--yes` flag. No auto-approve on `--resume`.

6. **On Approve.** Set the gate, advance the stage to `topic_artifacts_approved`, log a one-line summary. Return control to the orchestrator with a terse one-liner the orchestrator can narrate (`"<id> approved — moving to <next-id>"`). The orchestrator advances the cursor.

7. **On Request changes.** Walk back the affected gates + stage as described in (3); log the rework reason; return control. The orchestrator re-fires the matching generation step with the user's rework notes attached.

## Tone

This is the per-topic quality stop. Keep it terse — show the packet, ask the question, capture the verdict. No editorial. The reviewer wants to see paths, counts, and critic verdicts at a glance.

## Refuse-message templates

- "HITL #3 cannot fire — reading_critic_passed is not true yet. Run /critic-reading <id> (the orchestrator handles this in step 7)."
- "HITL #3 cannot fire — diagram is required by artifact_plan but diagram_critic_passed is not true. Run /generate-diagram <id> + /critic-diagram <id> first."
- "HITL #3 already approved for <id>. To revise after approval, re-enter the loop manually by setting the relevant *_critic_passed gate to false."
