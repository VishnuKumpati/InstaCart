#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.1", "concepts_introduced",
     '["symbolic AI","expert system","AI Winter","machine learning","training data","deep learning","artificial neural network"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.1", "prerequisites",
     '["1.1","1.4","2.5"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.1", "cross_refs",
     '["neural networks","LLMs","GPUs","Transformer architecture"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.1", "status",
     '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {' '.join(cmd)}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
