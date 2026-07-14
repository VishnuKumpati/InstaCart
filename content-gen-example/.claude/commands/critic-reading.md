---
description: Run the reading critic — length, citations, prereq alignment, forward-reference discipline.
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<topic-id>"
---

# /critic-reading

Dispatch the **reading-critic** subagent.

## Refuse if
- `gates.reading_generated` is not true.
- Caveman not active.

## What to check
0. **Format discipline (hard fail).** `reading.md` has NO frontmatter (first non-empty line is the `# <title>` H1); the sidecar `reading.meta.json` exists + parses; the reading carries exactly the six fixed sections in order (`Overview` / `Key Concepts` / `Worked Example` / `In Practice` / `Key Takeaways` / `References`, References optional only when zero citations).
1. **Length budget compliance.** Word count must fall in `artifact_plan.reading.target_word_range`. Reading-aloud-time estimate (≈230 wpm) must fall in the minute range. Both under and over → flag.
2. **Citation discipline.** Every `primary` resource cited at least once. Each citation is footnote-style with a bibliography at the bottom.
3. **No forward references.** Same rule as the corpus critic: only concepts from prior topics + this topic's corpus.
4. **Beginner readability + formatting + scope** (the bar the team raised — see the reading-critic skill, checks 5–7, driven by `audience`, `formatting_standards`, `scope_discipline` in `.claude/config/artifact-mix.json`):
   - **Readability** — terms/acronyms defined on first use; one new idea at a time (no jargon dumps); concrete example before abstraction; plain register, no hedging.
   - **Formatting** — no walls of text (paragraphs ~4 sentences); sequences as bullet/numbered lists; inline examples where concepts are introduced; bold-term/plain-meaning definitions.
   - **Scope** — nothing taught beyond the topic headline; advanced/adjacent concepts are named-and-deferred only.
   - Soft individually; **hard fail** on any `scope_creep`, or when readability+formatting produce ≥3 findings.
5. **Faithfulness to corpus.** No new claims introduced by the reading that aren't in the corpus. The reading is curated walkthrough, not new content.

## Output `<topic-dir>/critic-reports/reading.critic.json` with the same shape as corpus.critic.json.

## On result
- Pass: `set reading_critic_passed true`, stage `reading_approved`, suggest next command.
- Fail: log the verdict, ask whether to auto-rerun `/generate-reading` with the critic's notes attached.
