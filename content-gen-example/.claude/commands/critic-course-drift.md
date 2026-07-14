---
description: "Multi-week course critic — runs once all modules in a course have passed module-drift. Checks cross-week coherence + weekly time-budget compliance."
allowed-tools: [Read, Write, Bash, Task]
argument-hint: "<course-code>   e.g. /critic-course-drift M1"
---

# /critic-course-drift

Dispatch the **course-drift-critic** subagent across every module in the named course. A course typically spans multiple weeks (e.g., M1 covers W01–W02; M5 covers W11–W14), so this critic is the only place that sees concept flow *across* weeks.

## Refuse if
- Any module in the course has not yet passed `/critic-module-drift`.
- Caveman not active.

## What to check

1. **Cross-week terminology consistency.** A term defined in W01 should be used the same way in W02–W15 of the same course. Flag re-definitions or synonym drift across weeks.

2. **Prerequisite ordering across weeks.** Every topic's `prerequisites` field references topics earlier in the delivery sequence (earlier week or earlier in same week).

3. **Weekly time-budget compliance.** For each week_num in the course:
   - Sum `topic.expected_delivery_minutes` across every topic in that week.
   - Target = 90 min per `artifact-mix.json`.
   - **Soft warn** if total is outside `target ± 15%` (i.e. < 77 min or > 104 min).
   - **Hard fail** if total is outside `target ± 25%` (i.e. < 68 min or > 113 min).
   - Suggest which topics' profiles to downgrade / upgrade to bring the week into range.

4. **No duplicated material across weeks.** Compute Jaccard similarity between Core Concepts of every pair of topics in the course. Flag pairs > 0.55.

5. **Learning arc completeness.** The course's stated `scope` (from `course.manifest.json`) should be covered by the union of all topics' Learning Objectives. Flag uncovered facets.

## Output

`content/<course-slug>/critic-reports/course-drift.critic.json` with this shape:

```json
{
  "course_code": "M1",
  "passed": true|false,
  "checks": {
    "terminology_consistency": { "passed": true, "drift_findings": [] },
    "prerequisite_ordering":   { "passed": true, "violations": [] },
    "weekly_time_budget":      {
      "passed": true,
      "per_week": [
        { "week_num": 1, "total_minutes": 88, "status": "ok"   },
        { "week_num": 2, "total_minutes": 76, "status": "warn" }
      ]
    },
    "no_duplicated_material":  { "passed": true, "high_similarity_pairs": [] },
    "learning_arc":            { "passed": true, "uncovered_scope_facets": [] }
  },
  "verdict": "...",
  "at": "<ISO>"
}
```

## On result
- **Pass:** for every topic in the course, `python .claude/scripts/progress_update.py <id> set course_drift_passed true`. Then **purge each completed week's checkpoints** since the work is final:
  ```
  for w in <weeks in this course>:
      python .claude/scripts/session_checkpoint.py purge-week <w>
  ```
  Tell the user the course is ready for publication.
- **Fail:** surface findings ordered by severity. Time-budget failures get explicit topic recommendations ("downgrade 1.3 atomic → standard to add ~5 min to W01"). User decides which topics to revisit. **Do not purge** — checkpoints stay so the user can resume work.
