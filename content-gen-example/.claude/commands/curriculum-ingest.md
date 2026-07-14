---
description: Parse the finalized CIT curriculum sources (xlsx + docx) into the content/ hierarchy and curriculum/sequence.md. Idempotent — safe to re-run.
allowed-tools: [Bash, Read]
argument-hint: "[--xlsx PATH] [--docx PATH] [--out PATH]   (defaults to the sources in the repo root)"
---

# /curriculum-ingest

You are running the curriculum ingester. This command parses the canonical curriculum sources into the per-topic directory tree that the rest of the workflow operates on. The source materials are **locked** with the client — do not re-derive structure from anything else.

## What to do

1. Locate the source files. By default:
   - `CIT_Sem1_FullCourseStructure.xlsx` in the repo root
   - `CIT_Semester_1_15weeks_V2 3.docx` in the repo root
   If arguments are provided, prefer them.

2. Run the ingester:

   ```
   python3 tools/curriculum_ingest.py \
     --xlsx "<xlsx-path>" \
     --docx "<docx-path>" \
     --out  content
   ```

3. Read the summary from stdout (counts of courses / sessions / modules / topics) and surface them to the user.

4. Verify `content/curriculum/sequence.md` exists and contains 157 topics. If the count is off, stop and report the discrepancy — do not silently continue.

5. Tell the user the next steps: claim a session with `/claim-session W<NN>` and then start a topic with `/topic-resources <topic-id>`.

## Important

- **Layout contract:** the ingester must emit `content/<course>/week-<N>/<position>-<module-slug>/<topic-slug>/` — week is its own directory level and the module slug has **no** `w<NN>-` prefix (position resets to 1 per week). `week_num` is still written onto every module + topic manifest. The `tools/curriculum_ingest.py` in the source project must match this layout; if it still emits the old `w<NN>-<n>-<slug>/` folders, re-running it will regress the tree — update the source ingester before re-ingesting.
- The ingester is **idempotent**. Re-running it does not destroy in-flight artifacts because it only writes the scaffold files (`*.manifest.json`, `*.progress.json` when missing, the `.gitkeep` placeholders, and the sequence doc).
- The progress file is NEVER overwritten if it already exists for a topic. (If you need to verify this, read the script.)
- Once a session has been claimed in ADO, do not re-run ingest without coordinating with the assigned teammate.
