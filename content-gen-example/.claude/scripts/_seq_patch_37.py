#!/usr/bin/env python3
import subprocess, sys

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "3.7", "concepts_introduced",
     '["computer vision (medical)","telemedicine","IndiaAI Mission","Kisan e-Mitra","vernacular NLP","multilingual AI","BharatGen","digital divide"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.7", "prerequisites", '["3.1","3.2"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.7", "cross_refs",
     '["Kisan e-Mitra","BharatGen","AIIMS","eSanjeevani","IndiaAI Mission"]'],
    ["python", ".claude/scripts/sequence_update.py", "3.7", "status", '"corpus_drafted"'],
]

for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(f"FAIL: {cmd[3]}\n{r.stderr}", file=sys.stderr)
    else:
        print(f"OK: {cmd[3]}")
