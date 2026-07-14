---
name: diagram-builder
description: "Generate an architecture / pipeline diagram as Mermaid source. Mermaid is the default — renders inline on GitHub + ADO, code-as-text so the critic can read it directly, and edits diff cleanly in PRs."
---

# diagram-builder

You generate a single visual artifact — a **Mermaid** diagram — that captures the topic's structural insight (architecture, pipeline, mental model). The corpus's Core Concepts / Implementation / Real-World Patterns sections are your source.

## Beginner-readable, on-topic labels

Obey the **`audience` baseline** and **`scope_discipline`** in `.claude/config/artifact-mix.json`. The diagram is read by someone seeing this concept for the first time:

- **Plain-language node labels.** Use the everyday term the corpus uses; no unexplained acronym as a bare node label (spell it out or pair it with a plain word). If a label needs jargon the corpus defined, that is fine — but it must trace to a Core Concepts phrase.
- **Only this topic's concepts.** Every node maps to a Core Concepts / Implementation phrase for *this* topic (the critic enforces `concept_drift`). Do not add nodes for more advanced or downstream concepts to look complete — that is scope-creep in visual form.
- Match the gold example in `.claude/references/gold-examples/diagram/` when present; else follow these standards.

Mermaid is the default because:
- GitHub + ADO Wiki + ADO Repo + VS Code preview all render ```` ```mermaid ```` fenced blocks natively (no PNG to maintain, no broken-image risk).
- The source is plain text, so the diagram-critic reads node / edge / label counts directly without parsing scene JSON.
- Edits show as line diffs in PR review — easy critique loop.

## Inputs
- `corpus_sections` (the relevant sections of `topic_corpus.md`)
- `intent` (from `artifact_plan.diagram.intent`)
- `output_paths`: `{ mmd: ".../diagram.mmd" }`  *(plus optional `png` for offline preview only)*

## What to do

1. **Identify nodes (3–8).** Each node is a concept, component, or stage named in the corpus. Label each ≤ 4 words.

   **Title discipline (load-bearing).** If the topic has one *governing* concept or property that holds over the whole flow (e.g. "well-defined steps" governing an input→process→output computation), make it the diagram **title**, not a node. Set it via the `---` config block `title:` field so it renders as a banner above everything. Do **not** hang it off a single stage as a dangling `ghost` annotation — a property that governs the whole diagram must sit above the whole diagram, and a property that qualifies one stage belongs in that stage's italic subtitle. Reserve `ghost` nodes for true side-notes, never for the central organizing idea.

   **Single-path discipline.** When the corpus describes a linear flow (input → process → output, or stage → stage → stage), emit it as **one straight path** — every node on the same rank line, one arrow to the next, no branches off to the side. Branching/hub layouts are only for genuinely non-linear relationships (constellation, layered, side-by-side concerns). A linear concept rendered as a branchy graph reads as confusing; keep it a clean single line under the title banner.

2. **Identify edges.** What are the directional relationships? Common shapes:
   - **Pipeline** (linear): inputs/outputs flow — use `flowchart LR` with `-->` arrows
   - **Constellation** (hub + spokes): a centered concept and its facets — use `flowchart TD` with the hub at top
   - **Layered** (top → bottom abstraction): use `flowchart TB` with subgraphs per layer
   - **State machine** (nodes + back-arrows): use `stateDiagram-v2`
   - **Side-by-side panels** (main flow + side concerns like context-watchdog, end-of-X): use `flowchart LR` with a top-level `MAIN` subgraph (`direction TB`) and a `SIDE` subgraph (`direction TB`) — proven layout, see `docs/cit-content-gen-quickstart.mmd` in this repo for the canonical pattern.

3. **Choose layout** by best fit. If the corpus mentions explicit flow ("query → embed → retrieve → inject"), use pipeline. If it mentions explicit layers, use layered. If it mentions a main flow plus side concerns / escape hatches, use side-by-side panels.

4. **Emit mermaid source.** Write `output_paths.mmd` with:
   - A `---` config block at the top setting `title:` (the governing concept per the Title discipline above, when one exists), `theme: base`, `themeVariables` (use the project's palette — primaryColor `#a5d8ff`, primaryBorderColor `#4a9eed`, lineColor `#555`), and `flowchart: { htmlLabels: true, curve: basis }`.
   - The `flowchart <DIR>` declaration with the layout chosen in step 3.
   - The nodes + edges from steps 1–2, with bold node labels via `<b>...</b>` and small italic subtitles via `<span style='font-size:11px;color:#6d28d9'>...</span>`.
   - `classDef` rules using the project palette tiers:
     - `start` (entry): fill `#a5d8ff`, stroke `#4a9eed`
     - `hitl` (human gate): fill `#fff3bf`, stroke `#f59e0b`, stroke-width 3
     - `auto` (automated step): fill `#d0bfff`, stroke `#8b5cf6`
     - `act` (sub-action / command): fill `#a5d8ff`, stroke `#4a9eed`
     - `done` (terminal): fill `#b2f2bb`, stroke `#22c55e`
     - `ghost` (true side-note only, no border): `fill:none, stroke:none, color:#6d28d9` — never the diagram's central organizing concept (that goes in the `title:`)

5. **Render the PNG (mandatory before critic).** Run `mmdc -i <output_paths.mmd> -o <output_paths.png> -w 1400 -b transparent`. **The critic verdicts on the PNG via Claude's vision** — mermaid that parses can still render as a mess (overlaps, truncation, hollow subgraphs). 1400px width matches the LMS reader display size, so what the critic sees is what the student sees.

6. **Iterate with the critic.** Call `diagram-critic` and read its findings. Source-level findings (`mermaid_parse_error`, `node_overflow`, `label_too_long`, `concept_drift`) fix in the `.mmd`; visual findings (`nodes_overlap`, `label_truncated`, `crossings_illegible`, `layout_wrong_for_intent`, `missing_side_panel_pattern`, `side_panels_hollow`, `whitespace_imbalance`, `subtitle_illegible`, `color_tier_violation`, `edge_label_collision`) fix by adjusting layout direction, subgraph nesting, `nodeSpacing`/`rankSpacing` in the config block, subtitle font sizes, or `:::classDef` suffixes — then **re-render the PNG** before re-invoking the critic. Stop when critic returns pass OR after 2 critic-revise cycles (then surface to the orchestrator with the latest PNG so the per-topic HITL #3 sees what the critic was complaining about).

## When to use Excalidraw instead

Mermaid for ~95% of diagrams. Reach for **Excalidraw MCP** only when:
- The topic explicitly calls for a hand-drawn, classroom-whiteboard aesthetic (intro-level "intuition" diagrams).
- Mermaid's layout engine genuinely can't express the relationship (rare — usually the issue is layout-fight, fix by picking the right top-level direction).
- The orchestrator passes `--excalidraw` to `/generate-diagram`.

In Excalidraw mode, follow the Excalidraw element format from the MCP's `read_me`, and still emit a sidecar `.mmd` if possible so future edits remain diffable.

## Return value

```json
{
  "mmd_path": "...",
  "png_path": "..." | null,
  "title": "Well-defined steps" | null,
  "node_count": 5,
  "edge_count": 6,
  "layout": "flowchart LR (side-by-side)",
  "labels": ["Query", "Embed", "Vector DB", "Top-k", "Generator"],
  "concepts_mapped": ["query", "embedding", "vector store", "retrieval", "generation"],
  "critic_cycles": 1
}
```
