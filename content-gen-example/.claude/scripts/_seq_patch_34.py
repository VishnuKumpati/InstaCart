#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.4", "concepts_introduced",
     '["token","tokenisation","subword tokenisation","Byte-Pair Encoding (BPE)","embedding table","next-token prediction","parameters (model weights)","backpropagation","attention mechanism","inference","prefill phase","decode phase","autoregressive decoding","context window"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.4", "prerequisites", '["3.1","3.2"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.4", "cross_refs",
     '["tiktoken","Hugging Face","BPE","GPT-3","bert-base-uncased"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.4", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
