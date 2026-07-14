---
name: reading-critic
description: "Validate the reading artifact: length budget, citations, no forward references, faithfulness to corpus, tone fit."
---

# reading-critic

## Inputs
- `reading_path` — `<topic-dir>/artifacts/reading.md`
- `meta_path` — `<topic-dir>/artifacts/reading.meta.json`
- `corpus_path` — for faithfulness check
- `resources_path`, `sequence_index_path`, `target_word_range`, `target_minute_range`

## Checks

0. **Format discipline (hard fail).**
   - **No frontmatter.** `reading.md`'s first non-empty line must be the `# <title>` H1 — a leading `---` YAML block is a fail (`frontmatter_present`). Learner-facing file must be clean; metadata lives in the sidecar.
   - **Sidecar present.** `reading.meta.json` must exist beside the reading and parse, carrying `word_count`, `estimated_read_minutes`, `citations_used`, `sections`. Missing/invalid → `meta_sidecar_missing`.
   - **Fixed six sections.** The reading must contain exactly these `##` headings, in this order, none added/renamed/dropped: `Overview`, `Key Concepts`, `Worked Example`, `In Practice`, `Key Takeaways`, `References` (References may be absent ONLY if zero citations exist). Deviation → `section_template_violation` with the diff.

1. **Length.** Word count in `target_word_range`; reading-aloud minutes (`words / 230`) in `target_minute_range`. Fail both under and over.

2. **Citations.** Every `primary` resource cited at least once. Bibliography at end matches inline markers. No orphan markers; no orphan bibliography entries.

3. **No forward references.** Same rule as corpus critic.

4. **Faithfulness.** Every assertion in the reading must trace to the corpus. Sample 8–12 assertions across the reading and verify they appear (verbatim or paraphrased) in the corpus. **Fail if > 2 unsupported assertions.**

5. **Beginner readability (the bar the team raised).** Read the `audience` baseline in `.claude/config/artifact-mix.json`. The reading is for a learner with **zero** background in programming, CS, AI, or LLMs. Check:
   - **Define before use.** Each non-everyday term / acronym has a plain-language meaning at first appearance (acronyms spelled out). Fail (`undefined_terms`, list them + line) if > 2 are used cold.
   - **One idea at a time.** No paragraph introduces 3+ brand-new terms at once → `jargon_dump` with line range.
   - **Concrete-first.** Concepts are introduced with an example/analogy before the abstraction, not after. Pervasive abstract-first explanation → `abstract_first`.
   - **Plain register.** Average sentence length under ~25 words; hedging ("it is generally considered", "many experts believe", "various studies have shown") < 3 total → else `academic_register`.

6. **Formatting / scannability.** Read `formatting_standards` in the same config. Beginners bounce off walls of prose. Check:
   - No paragraph longer than ~6 sentences (target 4) → `wall_of_text` with line range.
   - Procedures / sequences / multi-item lists are rendered as bullet or numbered lists, not run-on prose → `unlisted_sequence`.
   - At least one concrete inline example appears where concepts are introduced (the Worked Example section alone does not satisfy this) → `no_inline_examples`.
   - Definitions use the bold-term / plain-meaning shape where the reading defines a term.

6b. **No assessment references.** The reading must not mention assessment IDs, due weeks, or grade
   weights ("contributes to A1…", "due W3, 10%") — that is LMS metadata, not learner prose. Any
   occurrence → `assessment_reference` (soft; counts toward the checks 5–6 escalation). Gold
   examples that carry such a line are a flagged defect, not the bar.

7. **Scope — on the headline.** Read `scope_discipline`. The reading must not teach material beyond the topic title. Anything more advanced/adjacent must be a one-sentence named-and-deferred pointer, not taught. Teaching beyond the headline → `scope_creep` with the concept + line range. (This catches drift the corpus critic missed and drift the reading introduced while expanding corpus prose.)

   Checks 5–7 are **soft findings** individually (they surface at HITL #3 and roll up). Escalate to a **hard fail** if: any single `scope_creep` is found (the team's top complaint), OR checks 5–6 together produce ≥3 distinct findings (the reading is not beginner-usable).

## Output
Same shape as `corpus.critic.json`. Verdict cites specific paragraphs by line range when something fails. Include `checks.beginner_readability`, `checks.formatting`, and `checks.scope` in the per-check block. If a gold example exists in `.claude/references/gold-examples/reading/`, note in the verdict whether the reading meets that bar.
