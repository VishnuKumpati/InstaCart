#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.2", "concepts_introduced",
     '["large language model","word embedding","recurrent neural network","Transformer architecture","BERT","GPT","pre-training and fine-tuning","hallucination","few-shot","prompt"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.2", "prerequisites", '["3.1"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.2", "cross_refs",
     '["Word2Vec","BERT","GPT","LSTMs","Google Translate"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.2", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
