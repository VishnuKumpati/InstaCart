---
description: Run the diagram critic. Cheap source-level pre-flight (parse, node count, label discipline, concept fidelity), then VISUAL inspection of the rendered PNG — the load-bearing check. Code that looks fine often renders messy.
allowed-tools: [Read, Write, Bash, Task, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__read_checkpoint]
argument-hint: "<topic-id>"
---

# /critic-diagram

Dispatch the **diagram-critic** subagent. The PNG is the source of truth for the verdict — see the skill's full visual-checks table.

## Refuse if
- `gates.diagram_generated` is not true.
- Caveman not active.

## Order of operations

1. **Source-level pre-flight (cheap, fail fast).** If any of these fail, return verdict=fail without rendering:
   - **Mermaid parses.** `mmdc -i <topic-dir>/artifacts/diagram.mmd -o /tmp/_validate.png` must exit 0. Non-zero → hard fail.
   - **Node count.** 3 ≤ nodes ≤ 8.
   - **Label discipline.** Every label ≤ 4 words inside `<b>...</b>`. No sentences.
   - **Concept fidelity + on-headline.** Every node maps to a Core Concepts / Implementation phrase in the corpus; a label tracing to a more advanced/adjacent concept beyond the topic headline is scope-creep (`concept_drift: <label> (out of headline scope)`).
   - **Label plainness.** No bare unexplained acronym / undefined jargon as a standalone label (`jargon_label`, soft) — per the `audience` baseline.

2. **Render at viewer scale.** If pre-flight passes and the PNG isn't already at `<topic-dir>/artifacts/diagram.png`, run `mmdc -i <topic-dir>/artifacts/diagram.mmd -o <topic-dir>/artifacts/diagram.png -w 1400 -b transparent`. 1400px = LMS reader display width.

3. **Visual inspection — the verdict.** The subagent uses **Read** on `diagram.png` so Claude's vision sees the rendered image, then walks the visual-checks table in `diagram-critic` SKILL.md:
   - `nodes_overlap` — bounding boxes intersect
   - `label_truncated` — text spills / ellipsizes / wraps awkwardly
   - `crossings_illegible` — edges merge, weave through subgraphs, or cross more than once
   - `layout_wrong_for_intent` — vertical when intent says flow / pipeline (or vice versa)
   - `missing_side_panel_pattern` — single column when intent says main + side concerns
   - `side_panels_hollow` — grid stretched side subgraphs to match main height, leaving big empty interiors
   - `whitespace_imbalance` — >40% empty canvas or columns wildly unbalanced
   - `subtitle_illegible` — 11px italic subtitles unreadable at viewer scale
   - `color_tier_violation` — palette tier mismatch (e.g. HITL gate not yellow)
   - `edge_label_collision` — "if needed" / "if last week" labels overlapping nodes

   Each finding must include a concrete suggested fix the builder can apply on the next cycle.

## Excalidraw path (`--excalidraw` opt-in)

Skip step 1's mermaid checks; jump to step 3 directly on the Excalidraw-exported PNG. Same visual checks apply. `mcp__...__read_checkpoint` is available for inspecting the scene JSON if a fix needs scene-level coordinates.

## Output

`<topic-dir>/critic-reports/diagram.critic.json` — schema in the skill. Findings include `code`, `where`, `detail` (with the suggested fix), plus `stats` (node/edge counts, layout, png path + dimensions).

## On result
- Pass: `set diagram_critic_passed true`.
- Fail: builder reads findings, revises the `.mmd`, re-renders the PNG, re-invokes critic. After 2 builder ↔ critic cycles without pass, leave the gate FALSE — the per-topic HITL #3 gate surfaces it for human triage **with the latest PNG attached** so the human sees what the critic was complaining about. (HITL #4 just rolls up whatever override the human accepted at HITL #3.)
