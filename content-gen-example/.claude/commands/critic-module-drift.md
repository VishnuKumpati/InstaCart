---
description: "End-of-module parallel critic — runs once all topics in a module are artifact-complete. Checks cross-topic coherence within the module."
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<session-id> <module-id>   e.g. /critic-module-drift W01 M1"
---

# /critic-module-drift

Dispatch the **drift-critic** subagent across all topics in the specified module. This runs in parallel with other modules' drift critics.

## Refuse if
- Any topic in the module has `progress.stage != "quiz_approved"` (or its equivalent terminal pre-drift state). The module is not ready.
- Caveman not active.

## What to check
1. **Terminology consistency.** A concept introduced in topic N is named the same way in topics N+1, N+2, ... within the module. Flag synonym drift (e.g., "embedding" in 6.3, "vector" in 6.4 when they refer to the same thing without a defined equivalence).
2. **Prerequisites respected.** Every topic's `prerequisites` field references only topics with earlier positions.
3. **Module learning arc.** The module-level outcome (from `module.manifest.json.title` + the topic titles) should be supported by the union of all topics' Learning Objectives. Flag gaps.
4. **No duplicated material.** Two topics in the same module should not have substantively overlapping Core Concepts sections.
5. **Lab/Activity alignment.** The session's `lab_activity_seed` should be supported by at least one topic's hands-on artifact OR the module-level review wrapper (Phase 4 — not yet implemented).

## Output

`content/<course>/week-<N>/<module>/critic-reports/module-drift.critic.json` — same shape as topic-level reports but with a per-topic findings list.

## On result
- Pass: for every topic in the module, `python .claude/scripts/progress_update.py ... set module_drift_passed true`. Set each topic's stage to `module_passed`. Tell user the module is ready for ADO sync.
- Fail: surface findings, let user decide which topics to revisit.
