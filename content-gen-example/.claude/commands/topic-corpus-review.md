---
description: HITL gate #2 — section-by-section human approval of the topic corpus. Confirms or revises the auto-classified depth profile before artifact fan-out.
allowed-tools: [Read, Edit, Bash, AskUserQuestion]
argument-hint: "<topic-id>"
---

# /topic-corpus-review

You are running HITL gate #2. The user reviews the corpus section by section. Required sections must be approved (Learning Objectives, Introduction, Core Concepts, Key Takeaways); optional sections (Prerequisites, Implementation, Real-World Patterns, Best Practices, Hands-On Exercise) can be approved, revised, or dropped.

## Refuse if

- `progress.json.gates.corpus_critic_passed` is not true.
- `progress.json.stage` is not `awaiting_corpus_human_review`.

## What to do

1. Read `topic_corpus.md` and split it into its sections (each `## N. <Section Name>` header). Also read `critic-reports/corpus.critic.json` and **surface its `topic_scope` (scope_creep) and `beginner_readability` findings at the top of the review** — these are the team's two priority concerns, and the reviewer should see them before approving any section.

2. For each section in order, show the user a compact preview (first 200 chars + word count) and AskUserQuestion. Frame the review around the same bar the critic uses (`audience`, `scope_discipline` in `.claude/config/artifact-mix.json`) — prompt the reviewer to check:
   - **On the headline?** Does the section teach only this topic's concept, or has it drifted into more advanced/adjacent material (scope-creep)?
   - **Beginner-clear?** Are terms/acronyms defined on first use, one idea at a time, concrete-before-abstract — readable by someone with zero background?

   Options:
   - "Approve as written" — proceeds.
   - "Approve with note" — captures a note for the writer.
   - "Reject — regenerate this section" — flags for re-run (use for scope-creep or beginner-clarity misses the critic surfaced).
   - "Drop section (optional sections only)" — only available for non-required sections.

3. Compute the **week-aware recommended depth** for this topic:
   - Start from the corpus-builder's intrinsic suggestion (`topic.manifest.json.artifact_plan.suggested_profile`).
   - Read every sibling `topic.manifest.json` in the same `week_num`. Sum `expected_delivery_minutes` for siblings that are already past corpus approval.
   - Count siblings that still have a `null` `expected_delivery_minutes` (haven't been profiled yet) — they'll consume budget after this topic.
   - Compute `remaining_for_after = 90 - already_spent - this_topic_estimate`, divide by `count_of_yet_to_profile_siblings`.
   - If that per-future-topic average falls below ~6 min, the recommendation downgrades the current topic by one step (deep → standard, standard → atomic). If it climbs above ~14 min, the recommendation upgrades by one step.
   - Cap the recommendation at the corpus-builder's signal direction — never recommend `deep` for a topic whose intrinsic signals are `atomic`, and vice versa. Only swap between adjacent profiles for budget reasons.

4. Surface the recommendation via AskUserQuestion using `hitl_confirmation` in `.claude/config/artifact-mix.json`:

   ```
   Recommended: standard (~10 min)
   Week running total would be 76/90 min after this topic.
   Accept?
     • Accept standard
     • Override → atomic (~8 min)
     • Override → deep (~14 min)
   ```

   There is no "pick from scratch" path — every prompt is accept-or-override. The user always gets a sensible default.

5. If any sections were rejected, summarize them and rerun `/topic-corpus <id> --revise=<section-numbers>` (the corpus-builder accepts a `--revise` flag for partial rebuild). The orchestrator handles this automatically when invoked via `/content-gen`.

6. If all sections approved:
   - Write final `artifact_plan` into `topic.manifest.json` (confirmed profile + the mix decisions: reading length range, quiz size range, diagram yes/no — all derived from `artifact-mix.json`).
   - `python .claude/scripts/progress_update.py <id> set corpus_human_approved true`
   - `python .claude/scripts/progress_update.py <id> stage corpus_approved`
   - The orchestrator then auto-fires `/generate-diagram` (if applicable) followed by `/generate-reading` + `/generate-quiz` in parallel.

## Tone

This is the most important HITL gate for content quality. Be thorough but not chatty — show one section at a time, capture the verdict, move on.
