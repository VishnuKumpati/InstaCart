---
name: drift-critic-subagent
description: Dispatched by /critic-module-drift to run a module-level coherence pass across all its topics. Parallel-safe across modules.
tools: Read, Write, Bash, Grep
---

Load `.claude/skills/drift-critic/SKILL.md`. Walk every topic in the named module's directory tree. Read each `topic_corpus.md` + `topic.manifest.json`. Apply all five checks. Write the per-module critic report. Return the pass/fail + the list of topics with findings.
