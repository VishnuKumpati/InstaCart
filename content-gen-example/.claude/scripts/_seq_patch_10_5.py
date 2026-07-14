import subprocess

cmds = [
    ["python", ".claude/scripts/sequence_update.py", "10.5", "concepts_introduced",
     '["human-in-the-loop (HITL)","human-on-the-loop (HOTL)","human-out-of-the-loop (HOOTL)","checkpoint","confidence routing","escalation trigger","checkpoint matrix"]'],
    ["python", ".claude/scripts/sequence_update.py", "10.5", "prerequisites",
     '["9.7","9.8","9.9","9.10","9.11","10.1","10.2","10.3","10.4"]'],
    ["python", ".claude/scripts/sequence_update.py", "10.5", "cross_refs",
     '["college admissions pipeline","medical triage AI","loan approval AI","content moderation AI"]'],
    ["python", ".claude/scripts/sequence_update.py", "10.5", "status", '"corpus_drafted"'],
]
for cmd in cmds:
    r = subprocess.run(cmd, capture_output=True, text=True)
    print(cmd[3], "->", r.returncode, r.stdout.strip(), r.stderr.strip())
