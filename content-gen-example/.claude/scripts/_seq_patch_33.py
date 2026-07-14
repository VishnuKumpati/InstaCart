#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.3", "concepts_introduced",
     '["AI agent","agent loop","goal (agent)","planning","tool use","memory (short-term and long-term)","feedback loop","orchestration"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.3", "prerequisites", '["3.1","3.2"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.3", "cross_refs",
     '["LangChain","AutoGen","CrewAI"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.3", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
