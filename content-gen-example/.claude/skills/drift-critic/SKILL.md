---
name: drift-critic
description: "End-of-module coherence pass — terminology consistency, prerequisite ordering, learning-arc completeness, no duplicated material, lab/activity alignment. Runs in parallel across modules."
---

# drift-critic

## Inputs
- `module_dir` — `content/<course>/week-<N>/<module>/` (walk from the module dir down to its topics)
- `sequence_index_path` — `content/curriculum/sequence.index.json`
- `output_path` — `<module-dir>/critic-reports/module-drift.critic.json`

## Checks (across all topics in the module)

1. **Terminology consistency.** For each concept introduced in topic N, check whether topics N+1, N+2, ... refer to the same concept by the same name. Flag synonym drift.
2. **Prerequisites respected.** Every topic's `prerequisites` field references only topics with earlier positions in the sequence.
3. **Learning arc.** The union of all topics' Learning Objectives should cover the module's stated outcome (the `module.title` + the topic titles together). Flag uncovered facets.
4. **No duplicated Core Concepts.** No two topics in the module should have substantively overlapping Core Concepts. Compute Jaccard similarity of the set of nouns in each pair's section 5; flag pairs > 0.6 similarity.
5. **Lab/Activity alignment.** The sprint's `lab_activity_seed` must be supported by at least one topic's hands-on artifact, OR (when Phase 4 lands) the module-level review wrapper.

6. **Audience-level consistency (no upward drift).** Per the `audience` baseline in `.claude/config/artifact-mix.json`, every topic targets the same absolute-beginner level. Across the module's topics in delivery order, flag where a later topic **assumes a concept as known that no earlier topic introduced** (a term used cold that isn't in any prior topic's `concepts_introduced` and isn't defined in that topic) — this is the cross-topic form of the "jumps into advanced material out of nowhere" complaint. Also flag a topic whose register/depth spikes well above its neighbors for its depth profile. Findings are per-topic, soft; they surface at HITL #4.

## Output
Standard critic-report shape, with a per-topic findings list nested inside `checks.terminology_consistency` etc.

## Parallel-safe
This skill is read-only on the source artifacts. Multiple instances can run simultaneously across modules without coordination.
