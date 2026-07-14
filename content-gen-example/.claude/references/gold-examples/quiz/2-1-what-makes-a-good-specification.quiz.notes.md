# Why this passes — 2.1 quiz (GIFT)

**Provenance:** `origin/week-2` — shipped alongside the team-reviewed reading. Confirm quiz
review status with the content lead before treating as fully load-bearing.

Moves that make it the bar:

- `$CATEGORY:` header scoped `CIT/<course>/week-<N>/<topic>`.
- Every stem is a **plain-language scenario** ("A student writes the specification…"), not a
  definition-recall prompt.
- ⚠️ **Known defect (fixed in this copy, 2026-06-11):** the original file carried stem-level
  `#<feedback>` before `{` — invalid GIFT that leaks the answer into the stem on import. Introduced
  by the external-LLM review pass (our generator and every other shipped quiz use clean stems).
  Stripped here; source fix for the `week-2` branch staged as a PR. Builders: feedback only on
  answer lines and the `####` line — never after the stem.
- **Per-option feedback** on every distractor explains *why it's plausible but wrong* — teaches,
  not just grades.
- Closing `####` rationale cites the corpus section the question tests (`corpus §Testable`).
- Distractors are real misconceptions at the learner's level, never trick wording or vocabulary
  beyond the reading.
