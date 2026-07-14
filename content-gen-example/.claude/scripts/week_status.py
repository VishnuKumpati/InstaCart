#!/usr/bin/env python3
import json, pathlib, sys

week_num = int(sys.argv[1]) if len(sys.argv) > 1 else 1
data = json.loads(pathlib.Path("content/curriculum/sequence.index.json").read_text(encoding="utf-8"))
idx = data["topics"] if isinstance(data, dict) else data
week1 = [t for t in idx if t.get("week_num") == week_num]
for t in week1:
    prog_path = pathlib.Path("content") / t["path"] / "topic.progress.json"
    stage = "N/A"
    if prog_path.exists():
        prog = json.loads(prog_path.read_text(encoding="utf-8"))
        stage = prog.get("stage", "N/A")
    print(t["id"] + "\t" + t["title"] + "\t[" + stage + "]")
