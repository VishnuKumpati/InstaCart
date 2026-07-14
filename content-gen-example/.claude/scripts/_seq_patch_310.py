#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.10", "concepts_introduced",
     '["evaluation rubric (AI output)","task type (AI evaluation)","creative output evaluation","factual output evaluation","logical output evaluation","ethical output evaluation","coding output evaluation"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.10", "prerequisites", '["3.2","3.6","3.8","3.9"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.10", "cross_refs", '["BLEU/ROUGE"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.10", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
