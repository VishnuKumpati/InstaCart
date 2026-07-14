---
description: "Restore a single PENDING checkpoint. Typically you don't run this directly — /content-gen week-N --resume does the lookup for you after /clear."
allowed-tools: [Bash, Read]
argument-hint: "<checkpoint-filename>   (or omit to pick the most recent PENDING)"
---

# /restore-session

Pick up where a previous Claude session left off, for one topic. (For a normal whole-week resume, you usually don't run this directly — the sequence is `/save-session` → `/clear` → `/content-gen week-N --resume`, which finds the checkpoint for you. Use this command only if you specifically want to resume one topic outside the week orchestrator.)

## What to do

1. If no argument: pick the most recent PENDING checkpoint:
   ```
   python .claude/scripts/session_checkpoint.py list --status PENDING
   ```
   Then take the first row's filename.

2. Cat the checkpoint (this also marks it CONSUMED — only do it when you're actually resuming):
   ```
   python .claude/scripts/session_checkpoint.py restore <filename>
   ```

3. Parse the YAML frontmatter to read `topic_id` and `week_num`.

4. Read the **current** `topic.progress.json` for that topic (not the snapshot in the checkpoint — the file on disk is authoritative). Diff against the checkpoint's snapshot and tell the user what (if anything) has advanced since the save.

5. Suggest the next command based on the current stage:
   - `awaiting_resources` → `/topic-resources <id>`
   - `awaiting_corpus` → `/topic-corpus <id>`
   - `awaiting_corpus_critic` → `/critic-corpus <id>`
   - `awaiting_corpus_human_review` → `/topic-corpus-review <id>`
   - `corpus_approved` → fan out: `/generate-reading`, `/generate-quiz`, optionally `/generate-diagram`
   - `awaiting_reading_critic` → `/critic-reading <id>`
   - `awaiting_quiz_critic` → `/critic-quiz <id>`
   - `awaiting_diagram_critic` → `/critic-diagram <id>`

6. Stop and wait for the user's next instruction. Do not auto-run the next command.
