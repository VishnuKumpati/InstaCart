---
description: "Generate the quiz artifact via the quiz-builder subagent. Bias-defensive: shuffles correct-answer positions and rejects positional/length bias."
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<topic-id>"
---

# /generate-quiz

Dispatch the **quiz-builder** subagent. The quiz tests recall and applied judgment against the corpus's Learning Objectives.

## Refuse if

- `gates.corpus_human_approved` is not true.
- Caveman not active.

(Note: quiz can run in parallel with reading and diagram once the corpus is approved. It does NOT require the reading to be done first.)

## Pre-flight: context budget

`generate_quiz` cost: main ~4k, subagent ~12k. Cheap step — usually safe.

## What to do

1. Read:
   - `topic_corpus.md`
   - `topic.manifest.json.artifact_plan.quiz` for `min_questions`, `max_questions`, `include_short_answer`
   - The corpus's Learning Objectives section as the canonical list of what to test

2. Dispatch **quiz-builder** subagent with:
   - The corpus
   - One MCQ-style question per Learning Objective, plus enough additional questions to hit the question count for the depth profile
   - For atomic-profile topics: add short-answer items (factual recall) per `include_short_answer = true`
   - Distractor requirements: each distractor must be plausible to a learner who has read the corpus but not the reading — i.e. they capture common misconceptions, not nonsense.
   - **Beginner-fair + on-headline** (`audience` + `scope_discipline` in `.claude/config/artifact-mix.json`): plain-language stems, acronyms spelled out, every question tests *this* topic only. Distractors are beginner misconceptions about this topic — never trivia requiring un-taught advanced/adjacent material.

3. The subagent writes **two files** to `<topic-dir>/artifacts/`:
   - **`quiz.gift`** — clean importable **GIFT** (Moodle plain-text): a `$CATEGORY` line then the questions. **No metadata comments.** The quiz is question-bank feedstock — these get pooled into module/course-level assessments, and GIFT imports directly into Moodle (and converts to QTI for Canvas/Blackboard). Shape (see the quiz-builder skill for the full primer):

   ```gift
   $CATEGORY: CIT/<course-code>/week-<N>/<topic-id>-<slug>

   ::q1:: <stem> {
   ~<distractor>#<why wrong>
   =<correct answer>#<why correct>
   ~<distractor>#<why wrong>
   ~<distractor>#<why wrong>
   #### <what this tests + corpus section>
   }
   ```
   - `=` = correct answer, `~` = distractor; per-answer `#feedback` carries the rationale; `####` is general feedback. All learner-facing — stays in the `.gift`.
   - **`quiz.meta.json`** — sidecar with the machine metadata: `$bias_defense_applied`, `$shuffle_seed`, `correct_position_distribution`, and the per-question `objective_ref` map. (Mirrors `reading.meta.json` — keeps the `.gift` clean and importable.)

4. **Defensive shuffle (mandatory).** Before writing, the subagent must randomly shuffle the **order of the answer lines** (`=`/`~`) within each MCQ so the correct (`=`) line's position is spread across 0–3 over the quiz. The shuffle eliminates the model's inherent positional bias toward the first option. The skill MUST set `$bias_defense_applied: true` in `quiz.meta.json` only after the shuffle has run.

5. On return:
   - Verify both `quiz.gift` and `quiz.meta.json` exist, and `quiz.meta.json.$bias_defense_applied` is `true`.
   - **Quizzes do not contribute to `expected_delivery_minutes`** — that field is reading minutes only. Do not add quiz time here.
   - `python .claude/scripts/progress_update.py ... set quiz_generated true`
   - `python .claude/scripts/progress_update.py ... stage awaiting_quiz_critic`
   - Tell user to run `/critic-quiz <topic-id>`.
