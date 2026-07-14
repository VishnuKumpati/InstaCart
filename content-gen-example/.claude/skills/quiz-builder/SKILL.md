---
name: quiz-builder
description: "Generate a bias-defensive MCQ quiz from an approved topic corpus. Mandatorily shuffles correct-answer positions; balances option lengths; tests every Learning Objective."
---

# quiz-builder

You are generating the quiz that will gate a learner's progression past this topic in the LMS. Quality matters more than question count, and **bias defense is mandatory** — see the team's known failure modes below.

## Write for an absolute beginner

Obey the **`audience` baseline in `.claude/config/artifact-mix.json`**. The learner has no background in programming, CS, AI, or LLMs. The quiz tests whether they understood *this topic at its level* — not whether they can decode jargon:

- **Plain-language stems.** A stem must be answerable by someone who read this topic's reading and nothing else. No unexplained acronyms; spell them out as the corpus does.
- **Stay on the headline.** Obey `scope_discipline` — every question maps to a corpus Learning Objective for *this* topic. Never test a more advanced or downstream concept (that is the quiz form of scope-creep).
- **Beginner-level distractors.** Distractors are plausible *beginner* misconceptions about this topic — not trivia from adjacent advanced topics. A distractor that requires knowing material this topic never taught is unfair, not a "hard" question.
- **Match the gold example** in `.claude/references/gold-examples/quiz/` when present (path from `gold_examples`); else follow these standards. Never fabricate one.

## Inputs

- `corpus_path` — `topic_corpus.md`
- `min_questions`, `max_questions` (from `artifact_plan.quiz`)
- `include_short_answer` (boolean; true for atomic-profile topics)
- `output_path` — `<topic-dir>/artifacts/quiz.gift`

## Two files out (mirrors the reading pattern)

1. **`quiz.gift`** — a **clean, importable** GIFT file: a single `$CATEGORY` directive followed by the questions. **No `//` metadata header, no `// objective_ref` comments** — the `.gift` is what gets imported into the LMS, so it carries only GIFT content (and `$CATEGORY`, which is a functional bank-targeting directive, not descriptive metadata).
2. **`quiz.meta.json`** — a sidecar beside `quiz.gift` carrying all machine metadata the critic and pipeline need: the bias-defense marker, shuffle seed, correct-answer positions, and the per-question `objective_ref` mapping (see "Sidecar metadata").

GIFT is plain text, git-diffable, imports directly into Moodle, and converts to QTI for Canvas/Blackboard. The quiz is question-bank feedstock — these questions get pooled to build module/course-level assessments, so the `.gift` must be a self-contained, importable bank with nothing extraneous.

### GIFT primer (what `quiz.gift` contains)

```gift
$CATEGORY: CIT/M1/week-1/1.1-what-is-computation

::q1:: A computation is best described as ... {
~a process that requires an electronic computer to run#No — computation is defined by its structure, not the device.
=the transformation of an input into an output by a fixed set of steps#Correct — input -> process -> output by a well-defined procedure.
~any task a person finds difficult to do by hand#No — difficulty is irrelevant to whether something is a computation.
~a list of facts stored for later retrieval#No — storage is not transformation.
#### Tests the core definition of computation (corpus §5).
}
```

Rules:
- **`$CATEGORY`** (first line) targets the import bank: `CIT/<course-code>/week-<N>/<topic-id>-<slug>`. It is the only directive in the file.
- **MCQ**: `=` marks the single correct answer, `~` marks each distractor. The **written order of the answer lines is what the learner sees and what the critic checks for positional bias** — so the shuffle (below) reorders these lines.
- **Per-answer feedback**: append `#<one-sentence explanation>` to each answer line — why correct / why this distractor is tempting-but-wrong. This is learner-facing GIFT content and stays in the `.gift`.
- **General feedback**: a `####` line before the closing `}` summarizes what the question tests + the corpus section. Also learner-facing — stays in the `.gift`.
- **No machine metadata in the `.gift`.** `objective_ref`, `$bias_defense_applied`, `$shuffle_seed`, question count, etc. all live in `quiz.meta.json`, keyed by question id.
- **Escaping**: inside question/answer text, escape GIFT control chars with a backslash — `\~ \= \# \{ \} \:`. A literal colon in a stem must be `\:`.
- **Nothing between the stem and `{`.** GIFT has NO stem-level feedback — an unescaped `#<text>` after the question text is invalid GIFT and leaks the answer into the stem on import. Feedback lives only on answer lines (`=...#` / `~...#`) and in the `####` general-feedback line. One gold example shows a stem-level `#` — that is a flagged defect in the example (see its `.notes.md`); do NOT replicate it.

### Short-answer items (atomic profile only)

```gift
::q6:: The fixed sequence of steps in a computation must be ____ (one word). {
=well-defined
=well defined
}
```

All `=` lines are accepted answers (canonical + paraphrases); no `~` lines.

### Sidecar metadata (`quiz.meta.json`)

```json
{
  "topic_id": "1.1",
  "generated_at": "<ISO>",
  "format": "gift",
  "question_count": 5,
  "$bias_defense_applied": true,
  "$shuffle_seed": "<hash of topic id>",
  "correct_position_distribution": [0, 1, 2, 3, 2],
  "questions": [
    { "id": "q1", "type": "mcq", "objective_ref": "<text from corpus Learning Objectives>", "correct_position": 0 }
  ]
}
```

`correct_position` / `correct_position_distribution` is the 0-based index of the `=` line within each MCQ's answer block in the `.gift`. The critic re-derives positions from the `.gift` and confirms they match the sidecar.

## Known failure modes (the quiz-critic will catch these — pre-pass yourself)

### Positional bias (the "A bias")

LLMs trained on quiz data tend to put the correct answer in position A (or wherever the model's prior placed it during training). Across the same 10-question quiz, an unguarded model may put `correct_index = 0` 60–70% of the time.

**Mandatory defense:** After drafting each MCQ's four answers, you must run a **uniform random shuffle** of the order in which the `=`/`~` answer lines are written for that question. The position of the `=` (correct) line among the answer lines is what the critic checks, so the shuffle must spread the `=` across positions 0–3 over the quiz. Use Python's `random.shuffle` (seed with the topic ID hash so runs are reproducible while the distribution stays uniform across topics).

**Post-shuffle constraint check (mandatory — a raw shuffle can still cluster):** after shuffling, derive the quiz-wide correct-position distribution and verify **max_share ≤ 0.40** (no single position holds more than 40% of the correct answers) and, when the quiz has ≥6 MCQs, **all 4 positions are used at least once**. If either fails, redraw deterministically (e.g. XOR the seed with an incrementing salt) until both hold. Only then write the `.gift`. The quiz-critic hard-fails on max_share > 0.40 — burning a critic cycle on a fixable shuffle is a defect.

Set `$bias_defense_applied: true` in `quiz.meta.json` only after the shuffle AND the constraint check have run. The quiz-critic refuses any quiz where the sidecar's marker is missing or false.

### Length bias (long-answer-is-correct)

LLMs tend to produce longer, more qualified prose for the correct answer than for distractors. A learner can game this without reading.

**Mandatory defense:** For each MCQ, equalize option lengths within ±20% of mean length. Distractors should be roughly the same word count as the correct answer. If you write a long, hedged correct answer, you must write equally long distractors.

## Coverage rules

1. **One question per Learning Objective minimum.** If the corpus has 5 LOs and `min_questions` is 5, that's your floor. If `max_questions` allows more, add applied / scenario questions that test the same LOs in less obvious ways.
2. **No questions outside the LOs.** If you find yourself writing a question whose `objective_ref` doesn't map to a corpus LO, drop it.
3. **Distractor plausibility.** Each distractor should be something a learner who has read the corpus but not the reading might pick. Common misconceptions, terminology confusion, off-by-one in a process — these are good distractors. Random wrong facts are not.

## Output

**Exactly two files** in `<topic-dir>/artifacts/` — nothing else:
- **`quiz.gift`** — clean importable GIFT: a `$CATEGORY` line then the questions (per the format above). No metadata comments.
- **`quiz.meta.json`** — the sidecar (per "Sidecar metadata"): `$bias_defense_applied`, `$shuffle_seed`, `correct_position_distribution`, and the per-question `objective_ref` map.

**No helper scripts left behind.** If you write a script to run the shuffle (`build_quiz.py`,
`gen_quiz.py`, etc.), run it from a temp location and **delete it before returning** — the
artifacts dir is learner/LMS-facing content only. Reproducibility lives in the sidecar's
`$shuffle_seed`, not in a script file.

## Return value to parent

```json
{
  "path": "<output_path>",
  "meta_path": "<artifacts/quiz.meta.json>",
  "question_count": 8,
  "objective_coverage": ["<LO 1>", "<LO 2>", ...],
  "correct_position_distribution": [0, 2, 1, 3, 2, 0, 1, 3],
  "mean_option_length_chars": 84,
  "longest_option_correct_count": 2,
  "bias_defense_applied": true
}
```

`correct_position_distribution` is the 0-based position of the `=` line within each MCQ's answer block, in question order — the critic re-derives this from the `.gift` to confirm the shuffle.
