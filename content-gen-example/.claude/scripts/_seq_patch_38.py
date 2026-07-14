#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.8", "concepts_introduced",
     '["jagged frontier","benchmark (AI evaluation)","capability cliff","reasoning task (AI)","spatial reasoning","calibration (AI confidence)"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.8", "prerequisites", '["3.2","3.5"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.8", "cross_refs",
     '["AlphaFold","AlphaGo","o3","Gemini 2.5"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.8", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
