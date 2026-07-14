#!/usr/bin/env python3
"""Print week-N topic status."""
import json, pathlib, sys

week_num = int(sys.argv[1]) if len(sys.argv) > 1 else 3
data = json.loads(pathlib.Path("content/curriculum/sequence.index.json").read_text(encoding="utf-8"))
week = [t for t in data["topics"] if t.get("week_num") == week_num]
base = pathlib.Path("content")

for t in week:
    tp = base / t["path"] / "topic.progress.json"
    tm = base / t["path"] / "topic.manifest.json"
    prog = json.loads(tp.read_text(encoding="utf-8")) if tp.exists() else {}
    mani = json.loads(tm.read_text(encoding="utf-8")) if tm.exists() else {}
    plan = mani.get("artifact_plan") or {}
    mins = mani.get("expected_delivery_minutes") or "-"
    print(f"{t['id']}|{prog.get('stage','?')}|{plan.get('diagram')}|{mins}|{mani.get('title','?')}")
