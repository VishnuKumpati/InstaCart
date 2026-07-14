#!/usr/bin/env python3
"""Save / restore session state files for /save-session, /restore-session,
and /content-gen --resume.

A checkpoint is a single markdown file under .claude/checkpoints/ capturing
enough state for a fresh Claude session to resume cleanly: which topic, which
week, which gate, the latest manifest + progress snapshot, and any free-form
notes the agent wrote.

Frontmatter:
    checkpoint_at: <UTC ISO>
    week_num:      <int>
    topic_id:      <str>
    status:        PENDING   (default — set by /save-session)
                   CONSUMED  (set by /restore-session or /content-gen --resume
                              once the session has been loaded back in)

Cleanup model:
    - Within an active week, PENDING checkpoints are the resume signal. Never
      delete them automatically.
    - CONSUMED checkpoints can be purged at any time (they're just audit trail).
    - When a whole week is complete (every topic at module_passed), the week's
      checkpoints — both PENDING and CONSUMED — are purged by /critic-course-drift
      on pass, or manually via `purge-week`.

Usage:
    python .claude/scripts/session_checkpoint.py save           <topic-id> [notes...]
    python .claude/scripts/session_checkpoint.py list           [--week N] [--status PENDING|CONSUMED]
    python .claude/scripts/session_checkpoint.py restore        <checkpoint-filename>
    python .claude/scripts/session_checkpoint.py mark-consumed  <checkpoint-filename>
    python .claude/scripts/session_checkpoint.py purge-week     <week-num>
    python .claude/scripts/session_checkpoint.py purge-consumed [--week N]
    python .claude/scripts/session_checkpoint.py latest-pending <week-num>     # for --resume
"""
from __future__ import annotations

import argparse
import datetime
import json
import os
import pathlib
import re
import sys

sys.path.insert(0, str(pathlib.Path(__file__).parent))
from _lib import find_topic_dir  # noqa: E402


CHECKPOINTS_DIR = pathlib.Path(os.environ.get("CIT_CHECKPOINTS_DIR", ".claude/checkpoints"))

FRONTMATTER_RE = re.compile(r"^---\n(.*?)\n---\n", re.DOTALL)
STATUS_LINE_RE = re.compile(r"^status:\s*(\w+)\s*$", re.MULTILINE)
WEEK_LINE_RE   = re.compile(r"^week_num:\s*(\d+)\s*$", re.MULTILINE)


def parse_frontmatter(text: str) -> dict:
    m = FRONTMATTER_RE.match(text)
    if not m:
        return {}
    block = m.group(1)
    out: dict = {}
    for line in block.splitlines():
        if ":" in line:
            k, v = line.split(":", 1)
            out[k.strip()] = v.strip()
    return out


def cmd_save(topic_id: str, notes: str) -> int:
    CHECKPOINTS_DIR.mkdir(parents=True, exist_ok=True)
    ts = datetime.datetime.utcnow().strftime("%Y%m%dT%H%M%SZ")
    safe = topic_id.replace(".", "-")
    out = CHECKPOINTS_DIR / f"{ts}-topic-{safe}.md"

    sub_dir = find_topic_dir(topic_id)
    manifest_block = "_(manifest not found — topic may not be ingested yet)_"
    progress_block = "_(progress file not found)_"
    week_num = 0
    if sub_dir:
        mp = sub_dir / "topic.manifest.json"
        pp = sub_dir / "topic.progress.json"
        if mp.exists():
            mtext = mp.read_text(encoding="utf-8")
            manifest_block = "```json\n" + mtext + "\n```"
            try:
                week_num = json.loads(mtext).get("week_num", 0)
            except Exception:
                pass
        if pp.exists():
            progress_block = "```json\n" + pp.read_text(encoding="utf-8") + "\n```"

    body = f"""---
checkpoint_at: {ts}
week_num: {week_num}
topic_id: {topic_id}
status: PENDING
notes: {notes[:500]}
---

# Session checkpoint — Topic {topic_id}  (week {week_num})

## How to resume

Open a fresh Claude Code session in this repo. Either start with:

    /content-gen week-{week_num} --resume

or, for a single-topic restart:

    /restore-session {out.name}

## Topic manifest

{manifest_block}

## Topic progress

{progress_block}

## Notes

{notes}
"""
    out.write_text(body, encoding="utf-8")
    print(str(out))
    return 0


def cmd_list(args) -> int:
    CHECKPOINTS_DIR.mkdir(parents=True, exist_ok=True)
    files = sorted(CHECKPOINTS_DIR.glob("*.md"),
                   key=lambda p: p.stat().st_mtime, reverse=True)
    for f in files:
        text = f.read_text(encoding="utf-8")
        fm = parse_frontmatter(text)
        if args.week is not None and str(fm.get("week_num", "")) != str(args.week):
            continue
        if args.status and fm.get("status", "PENDING") != args.status:
            continue
        print(f"{fm.get('status','PENDING'):8}  week={fm.get('week_num','?'):>2}  topic={fm.get('topic_id','?'):>6}  {f.name}")
    return 0


def cmd_restore(filename: str) -> int:
    path = CHECKPOINTS_DIR / filename
    if not path.exists():
        print(f"restore: not found: {path}", file=sys.stderr)
        return 2
    text = path.read_text(encoding="utf-8")
    print(text)
    # Auto-mark CONSUMED so we know this session has been resumed
    _mark(path, "CONSUMED")
    return 0


def _mark(path: pathlib.Path, new_status: str) -> None:
    text = path.read_text(encoding="utf-8")
    if STATUS_LINE_RE.search(text):
        text = STATUS_LINE_RE.sub(f"status: {new_status}", text, count=1)
    path.write_text(text, encoding="utf-8")


def cmd_mark_consumed(filename: str) -> int:
    path = CHECKPOINTS_DIR / filename
    if not path.exists():
        print(f"mark-consumed: not found: {path}", file=sys.stderr)
        return 2
    _mark(path, "CONSUMED")
    print(f"marked CONSUMED: {filename}")
    return 0


def cmd_purge_week(week_num: int) -> int:
    if not CHECKPOINTS_DIR.exists():
        return 0
    n = 0
    for f in CHECKPOINTS_DIR.glob("*.md"):
        fm = parse_frontmatter(f.read_text(encoding="utf-8"))
        if str(fm.get("week_num")) == str(week_num):
            f.unlink()
            n += 1
    print(f"purged {n} checkpoint(s) for week {week_num}")
    return 0


def cmd_purge_consumed(week_num) -> int:
    if not CHECKPOINTS_DIR.exists():
        return 0
    n = 0
    for f in CHECKPOINTS_DIR.glob("*.md"):
        fm = parse_frontmatter(f.read_text(encoding="utf-8"))
        if fm.get("status") != "CONSUMED":
            continue
        if week_num is not None and str(fm.get("week_num")) != str(week_num):
            continue
        f.unlink()
        n += 1
    print(f"purged {n} CONSUMED checkpoint(s){' for week ' + str(week_num) if week_num else ''}")
    return 0


def cmd_latest_pending(week_num: int) -> int:
    if not CHECKPOINTS_DIR.exists():
        return 1
    candidates = []
    for f in CHECKPOINTS_DIR.glob("*.md"):
        fm = parse_frontmatter(f.read_text(encoding="utf-8"))
        if fm.get("status", "PENDING") != "PENDING":
            continue
        if str(fm.get("week_num")) != str(week_num):
            continue
        candidates.append(f)
    if not candidates:
        print("")
        return 1
    latest = max(candidates, key=lambda p: p.stat().st_mtime)
    print(latest.name)
    return 0


def main() -> int:
    p = argparse.ArgumentParser()
    sub = p.add_subparsers(dest="cmd", required=True)

    s_save = sub.add_parser("save")
    s_save.add_argument("topic_id")
    s_save.add_argument("notes", nargs="*")

    s_list = sub.add_parser("list")
    s_list.add_argument("--week", type=int, default=None)
    s_list.add_argument("--status", choices=["PENDING", "CONSUMED"], default=None)

    s_restore = sub.add_parser("restore")
    s_restore.add_argument("filename")

    s_mark = sub.add_parser("mark-consumed")
    s_mark.add_argument("filename")

    s_purge_week = sub.add_parser("purge-week")
    s_purge_week.add_argument("week_num", type=int)

    s_purge_c = sub.add_parser("purge-consumed")
    s_purge_c.add_argument("--week", type=int, default=None)

    s_latest = sub.add_parser("latest-pending")
    s_latest.add_argument("week_num", type=int)

    args = p.parse_args()

    if args.cmd == "save":
        return cmd_save(args.topic_id, " ".join(args.notes) or "(no notes)")
    if args.cmd == "list":
        return cmd_list(args)
    if args.cmd == "restore":
        return cmd_restore(args.filename)
    if args.cmd == "mark-consumed":
        return cmd_mark_consumed(args.filename)
    if args.cmd == "purge-week":
        return cmd_purge_week(args.week_num)
    if args.cmd == "purge-consumed":
        return cmd_purge_consumed(args.week)
    if args.cmd == "latest-pending":
        return cmd_latest_pending(args.week_num)
    return 2


if __name__ == "__main__":
    sys.exit(main())
