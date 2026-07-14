---
description: "HITL gate #1 — capture grounding resources for ONE topic before any corpus is built. Author can paste up to 5 manual resources OR delegate to WebSearch when they have none on hand. Hard-block: nothing downstream runs without this."
allowed-tools: [Read, Write, Bash, AskUserQuestion, WebSearch]
argument-hint: "<topic-id>   e.g. /topic-resources 1.4"
---

# /topic-resources

You are running HITL gate #1 for **a single topic** — the cursor topic the orchestrator just paused on. The topic-corpus-builder uses these resources as the grounding for the 11-section corpus.

## Refuse if

- Caveman is not active. Run `python .claude/scripts/caveman_check.py require` and stop on non-zero exit.
- The topic's `topic.progress.json` shows `gates.resources_captured = true`. This gate runs once. If the user genuinely needs to revise resources, they edit `topic.resources.json` by hand and re-run `/topic-corpus`.

## What to do

1. Parse `$ARGUMENTS` as the topic ID (e.g. `1.4`). Locate the topic directory via the `path` field in `content/curriculum/sequence.index.json` for that ID. Refuse if not found.

2. Read `topic.manifest.json` for the topic title, course code, module title, and `narrative_seed_by_week[week_num]` from the parent module manifest. You need these to (a) show the user a clear "what topic is this" header and (b) seed the WebSearch query if they delegate.

3. **Show the topic header before the first prompt.** One block, terse:

   ```
   ── HITL #1 — Resources ──
   Topic <id>: <title>   ( <course_code> / <module_title> / week <week_num> )
   Narrative seed: <narrative_seed_by_week[week_num]>
   ```

4. **Ask the author how to ground this topic.** Single `AskUserQuestion`:

   > "Resources for **<title>** — paste your own or let me search?"

   Options:
   - **Paste a resource** — manual entry path (step 5)
   - **Search the web for me** — WebSearch fallback path (step 6)
   - **Skip — use the curriculum narrative seed only** — degraded path, still allowed but flagged in the manifest (step 7)

5. **Manual path.** For each resource, `AskUserQuestion` captures:
   - **value** — URL, file path, or short citation (use the "Other" freeform path).
   - **role** — `primary` (anchor concept), `supporting` (example/counter-example), `historical` (background), `applied` (real-world case).
   - Optional **notes** — one-line context on why.

   After each resource, ask "Add another?" with `Add another` / `Done — proceed`. Cap at 5. Validation: at least one resource must be tagged `role = primary`. If none is, prompt the user to elevate one.

6. **WebSearch fallback path.** When the author delegates:

   a. Build the query string from the topic header you already showed: `"<topic title> <key concept from narrative seed>"`. Keep it under ~10 words. Example for topic 1.4 "Why AI gives different answers to the same question": `"why AI gives different answers to same question probabilistic LLM"`.

   b. Call `WebSearch` with that query. Pull the top 3–5 results.

   c. Filter the raw results:
      - Drop social/forum-only links (reddit, quora, twitter/x) unless nothing else surfaced.
      - Prefer canonical / first-party sources (official docs, university course pages, primary research, well-known textbook authors). Demote SEO listicles.
      - Keep at most 3.

   d. Show the kept list to the author via `AskUserQuestion` (multi-select if 2–3 results) so they can confirm. Title each option with `<source domain> — <one-line snippet>`. Add an explicit "None of these — search again with a different query" option that loops back to step 6a with a different query.

   e. Tag the selected results: first one `role = primary`, remainder `role = supporting`. Set `source = "web_search"` on every web-searched resource. If the author selected zero, fall through to step 7 (degraded skip).

7. **Degraded skip path** (no resource, no web search). Allowed but flagged. Set `resources` to an empty array AND write `resources_source = "narrative_seed_only"` at the top level of `topic.resources.json`. The corpus-critic surfaces this as a soft finding shown at HITL #3 (per-topic artifact approval) — and rolled up at HITL #4 — but does not hard-fail.

8. **Write `topic.resources.json`** in the topic directory:

   ```json
   {
     "topic_id": "<id>",
     "captured_at": "<ISO timestamp>",
     "captured_by": "<user email>",
     "resources_source": "human" | "web_search" | "narrative_seed_only",
     "resources": [
       {
         "type": "url|file|citation",
         "value": "<url or path or citation>",
         "role": "primary|supporting|historical|applied",
         "source": "human" | "web_search",
         "notes": "<optional short note>"
       }
     ],
     "web_search_query": "<the query string used>"   // present only when resources_source = "web_search"
   }
   ```

   Validation:
   - `resources_source = "human"` → at least one `role = primary` required.
   - `resources_source = "web_search"` → at least one `role = primary` required (you assigned it in 6e).
   - `resources_source = "narrative_seed_only"` → empty `resources` array allowed; no primary required.

9. **Update progress:**

   ```
   python .claude/scripts/progress_update.py <topic-id> set resources_captured true
   python .claude/scripts/progress_update.py <topic-id> stage awaiting_corpus
   python .claude/scripts/progress_update.py <topic-id> log "Captured N resources (source=<resources_source>)"
   ```

10. Return control to the orchestrator with a one-line summary the orchestrator can narrate ("1.4 resources captured — 2 web_search, primary: nature.com/article/…"). The orchestrator immediately fires `/topic-corpus <id>`.

## Scope reminder

- **One topic at a time.** This command runs for the cursor topic the orchestrator just paused on. Do not loop here over multiple topics — the orchestrator drives that loop in `content-gen.md` step 7.
- The author sees the topic title and narrative seed in the header so they know exactly which topic they're grounding.

## Tone

The gate is mandatory but the path is flexible — the author can always delegate to WebSearch if they don't have a resource on hand. Be brief and direct. Don't lecture about why grounding matters; show the topic header and ask.
