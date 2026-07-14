---
description: Run the corpus critic — required sections, citation density, drift vs sequence.md. Hard-block on fail; regenerate-and-revise loop.
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<topic-id>"
---

# /critic-corpus

Dispatch the **corpus-critic** subagent. The critic gates whether the corpus is good enough to ground every downstream artifact.

## Refuse if

- `gates.corpus_generated` is not true.
- Caveman not active.

## What to check (the subagent enforces these)

1. **Required sections present + non-trivial.** Each of these must exist with substance (≥100 words for Core Concepts; ≥50 words for the others):
   - 3. Learning Objectives
   - 4. Introduction
   - 5. Core Concepts
   - 10. Key Takeaways

2. **Citation density** (mode-aware via `topic.resources.json.resources_source`):
   - `human` or `web_search` → strict: every `primary` resource cited at least once, ≥60% of key claims in Core Concepts / Implementation / Real-World Patterns carry a citation marker. Hard fail on miss.
   - `narrative_seed_only` → degraded path. Skip the citation-density hard check; emit a single soft finding `low_grounding` that surfaces at HITL #3 (per-topic artifact approval) and rolls up to HITL #4. Fabricated `[N]` markers (referencing nonexistent resources) are still a hard fail.

3. **Drift vs sequence.md.** No forward references — concepts referenced must appear in either:
   - This topic's `concepts_introduced` (will be set by the corpus-builder), OR
   - A prior topic's `concepts_introduced` per `sequence.index.json`

   Flag any unknown technical term that has not been introduced yet.

4. **Length sanity.** Corpus total should be 1500–6000 words depending on depth profile. Wildly off → flag.

5. **Topic scope — on the headline (`scope_creep`).** Per `scope_discipline` in `.claude/config/artifact-mix.json`: the topic title is the boundary. Material that *teaches* a more advanced / adjacent concept beyond the headline (vs. naming-and-deferring it in one sentence) is the root cause of artifacts that "jump into advanced topics out of nowhere" — **hard fail**, listing each off-topic concept + its section. Distinct from check 3: a term can be defined here yet still be off-headline.

6. **Beginner readability.** Per the `audience` baseline: terms/acronyms defined on first use, no 3+-new-terms-per-paragraph jargon dumps, plain register. Soft findings individually; escalate to a hard fail if ≥3 distinct readability findings.

## Output

`<topic-dir>/critic-reports/corpus.critic.json`:

```json
{
  "passed": true|false,
  "checks": {
    "required_sections": { "passed": true, "notes": "..." },
    "citation_density": { "passed": true, "primary_cited": true, "claim_citation_rate": 0.78 },
    "sequence_drift":   { "passed": false, "forward_refs": ["RAG", "vector database"] },
    "length_sanity":    { "passed": true, "word_count": 3200, "profile": "standard" }
  },
  "verdict": "regenerate sections 5 and 6 — forward references to RAG / vector database; either introduce them here or move the topic later in the sequence",
  "at": "<ISO timestamp>"
}
```

## On result

- If `passed = true`:
  - `python .claude/scripts/progress_update.py ... set corpus_critic_passed true`
  - `python .claude/scripts/progress_update.py ... stage awaiting_corpus_human_review`
  - Tell user to run `/topic-corpus-review <topic-id>`.

- If `passed = false`:
  - `python .claude/scripts/progress_update.py ... set corpus_critic_passed false`
  - `python .claude/scripts/progress_update.py ... log "Critic failed: <one-line summary>"`
  - Surface the critic's verdict to the user and ask whether to rerun `/topic-corpus <topic-id> --revise=<sections>` automatically or stop and let them inspect first (AskUserQuestion).
