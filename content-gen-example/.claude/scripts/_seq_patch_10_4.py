import subprocess, sys
calls = [
    ("concepts_introduced", '["automation complacency","vigilance","vigilance decay","accuracy paradox","trust calibration","out-of-the-loop performance decrement"]'),
    ("prerequisites",       '["10.1","10.2","10.3"]'),
    ("cross_refs",          '["aviation","healthcare","content moderation"]'),
    ("status",              '"corpus_drafted"'),
]
for field, payload in calls:
    r = subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "10.4", field, payload])
    if r.returncode != 0:
        sys.exit(r.returncode)
print("done")
