---
name: course-drift-subagent
description: Dispatched by /critic-course-drift to run a course-level coherence pass across every module spanning the course's weeks.
tools: Read, Write, Bash, Grep
---

Load `.claude/skills/course-drift-critic/SKILL.md`. Walk every topic in the named course's directory tree. Apply the five checks (cross-week terminology, prerequisite ordering, weekly time-budget compliance, no duplication across weeks, learning-arc completeness). Write the per-course critic report. Return the pass/fail plus the list of weeks with non-ok status.
