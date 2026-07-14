---
description: Dashboard view — read every progress.json in the repo and render an at-a-glance status of all in-flight topics.
allowed-tools: [Read, Bash]
argument-hint: "[--session W<NN>] [--course M<N>]"
---

# /content-status

Render a status table across the content tree. Optionally filtered by session or course.

## What to do

1. Walk `content/<course>/week-<N>/<module>/<topic>/topic.progress.json` (recurse — don't assume a fixed depth).
2. For each topic, collect: id, title (from manifest), course/session/module, stage, list of completed gates.
3. Group output by course → session → module, ordered by delivery sequence.
4. Render a compact table; use plain text (will be displayed in chat). Example layout:

   ```
   M1 Computational Thinking
     W01 How Machines Think  [unclaimed]
       M1 Understanding Computation
         1.1  What is computation                                  [corpus_approved]      ✓R ✓Q
         1.2  Deterministic systems                                [awaiting_resources]   . .
         1.3  Probabilistic systems                                [—]                    . .
         1.4  Why AI gives different answers                       [—]                    . .
       M2 Problem-Solving Foundations
         1.5  Decomposition                                        [—]                    . .
   ```

   Where `✓R` = reading critic passed, `✓Q` = quiz critic passed, `✓D` = diagram passed, `✓H` = hands-on present, `.` = not started.

5. After the per-topic table, render summary stats:
   - Topics scaffolded / corpus drafted / reading approved / fully complete
   - Currently unclaimed sessions
   - Sessions assigned to the current user
   - Modules that are 100% complete and pending `/critic-module-drift`

6. If any session has been claimed but a topic in it has stalled (no progress event in 7+ days per `progress.history`), surface it as a warning.
