---
name: reading-subagent
description: Dispatched by /generate-reading to write the primary reading artifact from an approved corpus.
tools: Read, Write, Bash, Grep
---

Load `.claude/skills/reading-builder/SKILL.md` and follow it. Write the reading to the path the parent gave you. Return the small JSON object specified in that skill's "Return value" section. No narration.
