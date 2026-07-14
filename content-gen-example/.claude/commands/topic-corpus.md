---
description: Build the 11-section topic corpus by dispatching the topic-corpus-builder subagent. Grounded in topic.resources.json and prior topics in curriculum/sequence.md.
allowed-tools: [Read, Write, Bash, Task, AskUserQuestion]
argument-hint: "<topic-id>   e.g. /topic-corpus 1.4"
---

# /topic-corpus

You are dispatching the **topic-corpus-builder** subagent to produce a topic's 11-section markdown corpus. The corpus is the source of truth that every downstream artifact (reading, quiz, diagram, hands-on) is generated from.

## Refuse if

- Caveman is not active (`scripts/python .claude/scripts/caveman_check.py require`).
- `topic.progress.json.gates.resources_captured` is not true.
- `topic.progress.json.stage` is not `awaiting_corpus`.
- An earlier topic in the same module has `stage != "module_passed"` and its position is `< current_topic.position_in_module`. Intra-module ordering is strict (the team agreed: no parallel topics within a module).

## Pre-flight: context budget check

Before dispatching the subagent, run the **context-budget-advisor** skill. It reads:
- Current context % (from the Claude Code statusline env)
- The `topic_corpus_build` step cost in `.claude/config/context-budget.json` (main ~8k tokens; subagent ~35k)

If the advisor returns `soft_checkpoint` or `hard_checkpoint`, prompt the user via AskUserQuestion to run `/save-session` before continuing. If they decline, proceed and log the decision in `topic.progress.json` via `python .claude/scripts/progress_update.py ... log "Proceeded past <band> band without checkpoint."`.

## What to do

1. Load the topic context:
   - `topic.manifest.json` for IDs, title, position
   - `topic.resources.json` for the user-provided grounding
   - `curriculum/sequence.md` filtered to topics with position **before** this topic's delivery order (this is what the subagent uses to avoid forward references and to reuse vocabulary already introduced)
   - The parent session's `module.manifest.json (week metadata lives here)` for `narrative_seed` and `lab_activity_seed`
   - The parent module's `module.manifest.json` for `scope`

2. Dispatch the **topic-corpus-builder** subagent via the Task tool, passing:
   - The topic ID, title, and source line
   - The full list of resources with their roles
   - The list of prior topics (id, title, concepts_introduced)
   - The session narrative + lab seeds
   - The course scope
   - The target output path: `<topic-dir>/topic_corpus.md`
   - The depth-classification request: "After writing the corpus, return your assessment of the topic's depth profile (atomic / standard / deep) per `.claude/config/artifact-mix.json`, including the signals that drove your pick."
   - The diagram-classification request: "Judge `requires_diagram` against `classification_signals.diagram_signals` in the same config — if ANY signal holds (structure, flow, branch, contrast, cycle, 3+ held-in-head steps), return `requires_diagram: true` with a one-line `diagram_intent`. Do not default to false because the topic is conceptual; conceptual structure is exactly what diagrams carry."
   - **Audience + scope reminder** (the builder skill enforces this; restate so it's front-of-mind): write for an absolute beginner per the `audience` baseline, and **stay on the topic headline** per `scope_discipline` — resources that dive deeper than the title are sources for this topic's slice only; advanced/adjacent concepts are named-and-deferred, never taught. Return any `scope_flags` (sequencing problems where the topic can't be explained without an un-introduced concept).

3. When the subagent returns, parse its structured response:
   - Path to written corpus
   - List of concepts_introduced (3–7 short noun phrases)
   - List of prerequisites (topic IDs from the prior list)
   - cross_refs (technology spans, e.g. ["Python", "Embeddings"] if the topic touches both)
   - Suggested depth profile + signals
   - `requires_diagram` boolean

4. Patch `curriculum/sequence.md` and `sequence.index.json` for this topic:
   ```
   python .claude/scripts/sequence_update.py <topic-id> concepts_introduced '<json-array>'
   python .claude/scripts/sequence_update.py <topic-id> prerequisites       '<json-array>'
   python .claude/scripts/sequence_update.py <topic-id> cross_refs          '<json-array>'
   python .claude/scripts/sequence_update.py <topic-id> status              '"corpus_drafted"'
   ```

5. Write the depth-profile suggestion + `requires_diagram` flag into `topic.manifest.json` under a new `artifact_plan` field.

6. Update progress:
   ```
   python .claude/scripts/progress_update.py <progress-path> set corpus_generated true
   python .claude/scripts/progress_update.py <progress-path> stage awaiting_corpus_critic
   ```

7. Tell the user to run `/critic-corpus <topic-id>` next.

## Tone

This is a long-running step (subagent generation). Keep the main-session response terse — caveman should already enforce this. Pass detailed prompts to the subagent, not to the user.
