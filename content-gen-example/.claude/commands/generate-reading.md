---
description: "Generate the primary reading artifact for a topic via the reading-builder subagent. If a diagram is planned, it must be done first so the reading can embed it inline."
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<topic-id>"
---

# /generate-reading

Dispatch the **reading-builder** subagent to write the topic's primary reading. The reading is what learners actually consume — quality matters more than throughput.

## Refuse if

- `progress.gates.corpus_human_approved` is not true.
- `progress.stage` is not `corpus_approved` and not `reading_critic_failed` (the regenerate-and-revise loop).
- Caveman not active.
- **Diagram precondition.** Read `topic.manifest.json.artifact_plan.diagram`. If it is `true` (the topic was tagged as requiring a diagram), refuse unless `progress.gates.diagram_critic_passed = true`. Tell the user to run `/generate-diagram <id>` and `/critic-diagram <id>` first. (When `artifact_plan.diagram = false`, this precondition does not apply.)

## Pre-flight: context budget

Run `context-budget-advisor` for the `generate_reading` step (main ~6k, subagent ~30k). If yellow/red, prompt for `/save-session`.

## What to do

1. Read inputs:
   - `topic_corpus.md` (the source)
   - `critic-reports/corpus.critic.json` — extract any open warn/soft findings; pass them to the subagent as `corpus_critic_findings` so flagged constructions are resolved (not copied) while condensing
   - `topic.resources.json` (citation pool)
   - `topic.manifest.json` for `artifact_plan` (target word range + minute range) and `week_num`
   - `curriculum/sequence.md` filtered to prior topics — same forward-reference discipline as the corpus
   - If `artifact_plan.diagram = true`, locate `artifacts/diagram.png` (must exist if the diagram critic passed) and pass its relative path to the subagent as `diagram_path`.

2. Dispatch the **reading-builder** subagent with:
   - Target word range (from `artifact_plan.reading.target_word_range`)
   - Target minutes (4–11 depending on profile)
   - The full corpus text
   - Resources to cite (must cite each `primary` resource at least once)
   - List of prior-topic concepts (vocabulary the reading may use without re-defining)
   - `diagram_path` (when applicable — the subagent embeds the image inline; see the skill)
   - Output path: `<topic-dir>/artifacts/reading.md`
   (Do NOT pass `next_assessment` — readings carry no assessment references; that linkage is LMS
   metadata in the manifest.)

3. Reading conventions the subagent must follow (encoded in the skill):
   - **No YAML frontmatter** — `reading.md` is a clean learner-facing file opening at the `# <title>` H1. All metadata goes in the sidecar `reading.meta.json`.
   - **Fixed six sections, every topic:** `Overview` / `Key Concepts` / `Worked Example` / `In Practice` / `Key Takeaways` / `References`. Never rename, reorder, add, or drop — except `References`, which is omitted when zero citations exist (matches the builder skill + reading-critic).
   - Treats the corpus as the backbone and *condenses* it — the reading is a tightened curated pass, not a longer rewrite.
   - Cites external resources inline with footnote-style markers `[1]`, `[2]`; bibliography in `References`. Every `primary` resource cited at least once.
   - No forward references — only concepts in prior topics + this topic's corpus.
   - Embeds the diagram inline within `Key Concepts` (when present), replacing prose rather than adding to it.
   - **Writes for an absolute beginner** (the `audience` baseline in `.claude/config/artifact-mix.json`): defines every term/acronym on first use, one new idea at a time, concrete example before abstraction.
   - **Beginner formatting** (`formatting_standards`): bullets / numbered steps over prose walls, inline examples, bold-term definitions, Q→A where natural — paragraphs ~4 sentences max.
   - **Stays on the topic headline** (`scope_discipline`): no teaching beyond the title; advanced/adjacent concepts are named-and-deferred only.
   - Matches the gold example in `.claude/references/gold-examples/reading/` when one exists.

4. On subagent return:
   - Verify both files exist: `<topic-dir>/artifacts/reading.md` and `<topic-dir>/artifacts/reading.meta.json`.
   - Confirm `reading.md` has **no frontmatter** (first non-empty line is `# `) and carries exactly the six fixed section headings (five when `References` is legitimately omitted at zero citations).
   - Count words (`wc -w` or Python `len(text.split())`); confirm it falls in the target range. If not, flag for the critic.
   - Compute `reading_minutes = ceil(word_count / 200)`. Write it to `topic.manifest.json.expected_delivery_minutes`. **Quizzes do NOT contribute to delivery time** — `expected_delivery_minutes` is reading minutes only (quizzes are question-bank feedstock for module/course-level assessments, not per-topic seat time).
   - `python .claude/scripts/progress_update.py <id> set reading_generated true`
   - `python .claude/scripts/progress_update.py <id> stage awaiting_reading_critic`
   - Tell user to run `/critic-reading <id>`.

## Tone

Be terse. The actual writing happens in the subagent. The main session only orchestrates.
