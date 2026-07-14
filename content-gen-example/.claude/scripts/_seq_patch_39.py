#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.9", "concepts_introduced",
     '["factual hallucination","attributional hallucination","faithful-but-wrong hallucination","RLHF (Reinforcement Learning from Human Feedback)","overconfidence bias","grounding","provenance"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.9", "prerequisites", '["3.2","3.4","3.6"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.9", "cross_refs", '["RAG"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.9", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
