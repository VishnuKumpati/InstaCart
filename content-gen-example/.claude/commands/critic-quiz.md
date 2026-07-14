---
description: Run the quiz critic — positional bias, length bias, distractor plausibility, objective coverage.
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<topic-id>"
---

# /critic-quiz

Dispatch the **quiz-critic** subagent. This critic enforces the two known LLM quiz-generation failure modes the team flagged.

## Refuse if
- `gates.quiz_generated` is not true.
- `quiz.meta.json` is missing/invalid, or its `$bias_defense_applied` is not `true` — the builder skipped the mandatory shuffle. Surface this and refuse.
- Caveman not active.

## What to check

The quiz is two files: **`quiz.gift`** (clean GIFT — `$CATEGORY` + questions) and **`quiz.meta.json`** (sidecar metadata). Parse `=` (correct) / `~` (distractor) answer lines per question from the `.gift`; the correct-answer position is the 0-based index of the `=` line within each MCQ's answer block (confirm it matches the sidecar's `correct_position_distribution`); `objective_ref` comes from the sidecar's per-question map. Skip short-answer items (only `=` lines) in the positional/length-bias checks.

1. **Positional bias.** For MCQs only:
   - Count distribution of the correct-answer position (index of the `=` line) across all questions.
   - **Fail if any single position holds more than 40% of correct answers.** (e.g., in a 10-Q quiz, no single position should be correct 5+ times. Statistical noise allows 4.)
   - If failed, the critic re-shuffles the answer-line order automatically (rewrites `quiz.gift`) and re-checks. If still failed (extremely unlikely after random shuffle), regenerate questions.

2. **Length bias.** For each MCQ:
   - Compute character length of each answer's text (strip the `#feedback`).
   - Flag any question where the correct answer is the longest by more than 20% of mean answer length.
   - **Fail if more than 1/(num_options) of questions show this pattern** — i.e. baseline rate for a 4-option MCQ is 25%; anything above 40% is a real bias signal.

3. **Distractor plausibility.** Spot-check: for each MCQ, do the distractors capture plausible misconceptions? A distractor that's clearly absurd is a free point. Flag any quiz with > 25% absurd distractors.

4. **Objective coverage.** Cross-reference questions against the corpus's Learning Objectives:
   - Every Learning Objective must be tested by ≥1 question.
   - No question may test material outside the Learning Objectives unless the rationale explicitly cites a corpus section.

5. **Beginner-fairness + on-headline scope** (per `audience` + `scope_discipline` in `.claude/config/artifact-mix.json`):
   - Plain-language stems, acronyms spelled out (`jargon_in_stem`, soft).
   - Every question tests *this* topic, not a more advanced/adjacent concept — a question needing un-taught knowledge is `out_of_scope_question` (**hard fail**, the quiz form of scope-creep).
   - Distractors are plausible *beginner* misconceptions about this topic, not advanced-topic trivia (`unfair_distractor`, soft).

## Output `<topic-dir>/critic-reports/quiz.critic.json`:

```json
{
  "passed": true|false,
  "checks": {
    "positional_bias": { "passed": true, "distribution": [3, 2, 3, 2], "max_share": 0.30 },
    "length_bias":     { "passed": true, "longest_correct_rate": 0.20, "baseline": 0.25 },
    "distractor_plausibility": { "passed": true, "absurd_rate": 0.05 },
    "objective_coverage":      { "passed": true, "uncovered_objectives": [] }
  },
  "shuffled_at": "<ISO timestamp>",
  "verdict": "...",
  "at": "<ISO timestamp>"
}
```

## On result

- Pass: `set quiz_critic_passed true`, stage `quiz_approved`.
- Fail: log verdict; offer auto-rerun.
