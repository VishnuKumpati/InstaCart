---
description: "Generate an architecture diagram for a topic as Mermaid source (default). Only runs when artifact_plan.diagram = true. Pass --excalidraw to opt into the hand-drawn fallback."
allowed-tools: [Read, Write, Bash, Task, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__create_view, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__export_to_excalidraw, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__save_checkpoint]
argument-hint: "<topic-id>   [--excalidraw]"
---

# /generate-diagram

Dispatch the **diagram-builder** subagent. Mermaid is the default output — code-as-text, renders inline on GitHub + ADO, and the diagram-critic reads the source directly. Excalidraw is opt-in.

## Refuse if

- `gates.corpus_human_approved` is not true.
- `topic.manifest.json.artifact_plan.diagram != true` — diagram is not part of this topic's mix. Tell the user and exit. (If they want one anyway, they edit the artifact_plan and re-run.)
- `--excalidraw` is passed AND the Excalidraw MCP is not connected — list its UUID (`934bf25a-e0d1-43ff-a488-b2c5567ec956`) and direct the user to connect it.

## Pre-flight

`generate_diagram` cost: main ~3k, subagent ~9k (mermaid path is lighter than Excalidraw).

## What to do

1. Read:
   - The corpus, specifically `## 5. Core Concepts`, `## 6. Implementation`, and `## 7. Real-World Patterns` (the spatial/relational sections)
   - `topic.manifest.json.artifact_plan.diagram` intent (e.g. "architecture or pipeline diagram showing the full flow")

2. Dispatch **diagram-builder** subagent with:
   - The relevant corpus sections
   - The intent
   - Output paths:
     - Default mode: `{ mmd: "<topic-dir>/artifacts/diagram.mmd", png: "<topic-dir>/artifacts/diagram.png" }`
     - `--excalidraw` mode: `{ scene: "<topic-dir>/artifacts/diagram.excalidraw", png: "<topic-dir>/artifacts/diagram.png" }`
   - Instructions to:
     - Identify 3–8 nodes (concepts, components, or stages) — **plain-language labels** (no bare unexplained acronyms) and **only this topic's concepts** (no advanced/adjacent nodes — that's visual scope-creep), per the `audience` baseline + `scope_discipline` in `.claude/config/artifact-mix.json`
     - Identify the edges (arrows showing flow or dependency)
     - Pick the layout from `diagram-builder` SKILL.md (pipeline LR, layered TB, side-by-side panels, state machine, constellation)
     - Default: emit a Mermaid `.mmd` file with the project palette (see classDef block in the skill) and render a transparent-background PNG preview via `mmdc -i <mmd> -o <png> -w 1400 -b transparent`
     - `--excalidraw`: call `create_view` → `save_checkpoint` → export PNG → save scene file
     - Run the critic loop (call **diagram-critic** subagent, revise on findings, max 2 cycles)

3. On return:
   - Verify `diagram.mmd` (and `diagram.png` preview) exist at the expected paths. For `--excalidraw`, verify `.excalidraw` + `.png`.
   - `python .claude/scripts/progress_update.py ... set diagram_generated true`
   - `python .claude/scripts/progress_update.py ... set diagram_critic_passed <true|false>` based on the final critic cycle (the builder is responsible for the inner loop)
   - If `diagram_critic_passed=false` after 2 cycles, do NOT advance the stage — leave at `awaiting_diagram_critic` so the end-of-week HITL gate surfaces it for human triage.
   - On pass: `python .claude/scripts/progress_update.py ... stage awaiting_diagram_critic` (no-op transition, but keeps the manifest tidy) then the orchestrator's loop moves on to reading + quiz generation.

## Notes

- The diagram-critic reads the `.mmd` directly — much cleaner than parsing Excalidraw scene JSON.
- Mermaid renders inline in the README, in PR diffs, in ADO Wiki pages, and in VS Code preview. No PNG broken-link risk.
- Diagrams are most valuable for **deep** profile topics (RAG pipeline, agent loop, 2026 AI stack) and for any topic where `cross_refs` is non-empty. The corpus-builder flags `artifact_plan.diagram = true` accordingly.
- Reach for `--excalidraw` only when the topic explicitly calls for a hand-drawn / classroom-whiteboard aesthetic. The Excalidraw MCP tool UUIDs stay in `allowed-tools` so they remain available for that opt-in path.
