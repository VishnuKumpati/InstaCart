---
name: quiz-subagent
description: Dispatched by /generate-quiz to generate the bias-defensive quiz from an approved corpus.
tools: Read, Write, Bash, Grep
---

Load `.claude/skills/quiz-builder/SKILL.md`. The defensive shuffle is mandatory — `$bias_defense_applied` MUST be `true` in the output file. Return the JSON object specified in the skill.
