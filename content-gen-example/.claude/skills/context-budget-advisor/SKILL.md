---
name: context-budget-advisor
description: "At HITL gates and before subagent dispatch, evaluate current context % against the next step's main-context cost and recommend a /save-session if needed."
---

# context-budget-advisor

You are the brain behind the statusline's recommendation and the prompt-at-HITL-gate behavior. Caveman handles compression; you handle when to recommend a session transfer.

## Inputs
- `current_pct` — the context usage from the Claude Code statusline env (passed by the caller)
- `next_step` — the name of the upcoming step (e.g. `topic_corpus_build`, `generate_reading`)
- `config_path` — `.claude/config/context-budget.json`

## What you do

1. Load the config and look up `steps[next_step]`. If the step is unknown, return `{action: "continue", reason: "no estimate available"}`.

2. Compute the projected total: `current_pct + (steps[next_step].main / context_window_total_tokens) * 100`.

3. Compare against thresholds:
   - If `projected_total < green_max_pct`: `action: "continue"`.
   - If `projected_total < yellow_max_pct`: `action: "soft_checkpoint"`.
   - If `projected_total >= red_min_pct` (or current already past red): `action: "hard_checkpoint"`.
   - Else: `action: "soft_checkpoint"` (default for the middle band).

4. Return:
   ```json
   {
     "action": "soft_checkpoint",
     "current_pct": 38,
     "next_step": "topic_corpus_build",
     "projected_pct": 42,
     "message": "Approaching 40%. /save-session here would let you resume cleanly in a new session if needed.",
     "subagent_cost_excluded": 35000,
     "note": "Subagent context cost (35000 tokens) is not counted against main."
   }
   ```

## When the caller is the parent command (not a hook)

The parent command:
- For `soft_checkpoint`: prompts the user via AskUserQuestion ("Save before continuing?") with `Save now` / `Continue without saving` options.
- For `hard_checkpoint`: prompts more firmly, mentions the next step may push past where the main session can recover.
- Logs the user's choice to `topic.progress.json.history` via `python .claude/scripts/progress_update.py ... log "Bypassed hard_checkpoint at corpus_build"`.

## When the caller is the statusline
The statusline just colors the recommendation band — no prompt, no logging. The active prompt always happens at the gate, not in the statusline.
