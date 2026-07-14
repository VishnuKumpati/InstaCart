#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.5", "concepts_introduced",
     '["parameter (weight)","weight matrix","bias","scaling law","quantisation","model size (GB)","parameter count","emergent capability","fp16","int8","int4"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.5", "prerequisites", '["3.1","3.2","3.4"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.5", "cross_refs",
     '["GPT-3","GPT-4","LLaMA","Ollama"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.5", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
