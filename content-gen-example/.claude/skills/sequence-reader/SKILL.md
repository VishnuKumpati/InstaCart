---
name: sequence-reader
description: "Shared utility — read curriculum/sequence.index.json, filter by delivery position, return the concept inventory available to a given topic."
---

# sequence-reader

You are a read-only utility skill that other skills delegate to when they need to know "what concepts have already been introduced before topic X?" Centralizing this here means corpus-builder, reading-builder, and the critics all see the same answer.

## Inputs
- `topic_id` — the topic asking
- Optional: `mode` = `prior_only` (default) | `all` | `after`

## What you do
1. Load `curriculum/sequence.index.json`.
2. Find the entry for `topic_id` and its index in the flat ordered list.
3. Filter:
   - `prior_only`: entries at indices `< self_index`
   - `all`: every entry
   - `after`: entries at indices `> self_index`
4. For each, project: `{id, title, course_id, sprint_id, module_id, concepts_introduced, cross_refs, status}`.
5. Return as a JSON list.

## Special handling
- If a prior topic has `status: "scaffolded"` (no corpus yet), its `concepts_introduced` is empty. Callers should treat such gaps as "this concept space isn't established yet" — flag in their own output.
- If the caller is the corpus-builder for a topic that depends on a not-yet-built prior topic's concept set, the corpus-builder may need to coordinate intra-module ordering or escalate to the user.
