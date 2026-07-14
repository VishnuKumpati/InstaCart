---
description: "Write a session checkpoint to .claude/checkpoints/ so a fresh Claude session can resume the current work. Default status PENDING; auto-consumed on restore."
allowed-tools: [Bash, Read]
argument-hint: "[<topic-id>] [<one-line note>]"
---

# /save-session

Capture the current session state as a markdown checkpoint under `.claude/checkpoints/`. The checkpoint includes the topic manifest, progress, week number, and a short summary of recent activity. Default status is **PENDING**; it auto-flips to **CONSUMED** the moment a future session restores from it.

## What to do

1. Determine the topic: explicit argument first, else the most-recently-touched `topic.progress.json`. Stop with an error if neither is available.

2. Write a short summary of the current session's notable activity. Things to include if relevant:
   - Which sections of the corpus were revised
   - Which critic findings were resolved
   - Outstanding decisions the user has not made yet
   - Any non-default classification choices (e.g., overrode auto-suggested depth profile)

3. Call:
   ```
   python .claude/scripts/session_checkpoint.py save <topic-id> "<notes>"
   ```

4. Tell the user the checkpoint filename and the three commands they run next, in order, in the same window:

   ```
   /clear                                ← wipes the conversation, frees context
   /content-gen week-<N> --resume        ← drops you back at the same HITL prompt
   ```

   No need to close and reopen the window — `/clear` is the Claude Code built-in that resets the session.

   For a single-topic restart instead of a week resume, replace the third command with `/restore-session <filename>`.

## Lifecycle of a checkpoint

| Status | Meaning | When | Safe to delete? |
|---|---|---|---|
| `PENDING`  | Written, not yet resumed | After `/save-session` | No — only auto signal a fresh session has to resume |
| `CONSUMED` | Already resumed | After `/restore-session` or `/content-gen --resume` | Yes — audit trail only |

When a week is complete (course-drift passes), all its checkpoints — PENDING or CONSUMED — are purged automatically. Manual cleanup:

```
python .claude/scripts/session_checkpoint.py purge-consumed --week 1
python .claude/scripts/session_checkpoint.py purge-week 1
```

## Tone
Terse. Caveman is presumed active.
