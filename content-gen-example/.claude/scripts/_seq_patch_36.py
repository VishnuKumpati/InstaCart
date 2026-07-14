#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.6", "concepts_introduced",
     '["temperature (LLM)","probability distribution (token)","greedy decoding","top-k sampling","top-p sampling (nucleus)","logits","deterministic output","stochastic output"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.6", "prerequisites", '["3.2","3.4"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.6", "cross_refs",
     '["OpenAI API","Anthropic API","Ollama"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.6", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
