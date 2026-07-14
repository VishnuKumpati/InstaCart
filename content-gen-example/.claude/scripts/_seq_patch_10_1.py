import subprocess, sys, json

fields = [
    ("concepts_introduced", json.dumps(["holistic review","pipeline","human override point","calibration","holistic review collapse","training data","override as theater"])),
    ("prerequisites", json.dumps(["9.5","9.7","9.8","9.9","9.10","9.11"])),
    ("cross_refs", json.dumps(["9.6"])),
    ("status", json.dumps("corpus_drafted")),
]
for field, val in fields:
    r = subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "10.1", field, val], capture_output=True, text=True)
    print(field + ": exit=" + str(r.returncode) + " " + (r.stderr.strip() or "ok"))
