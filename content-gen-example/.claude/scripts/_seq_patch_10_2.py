import subprocess, sys, json

fields = [
    ("concepts_introduced", json.dumps(["failure mode","systematic under-scoring","sub-group performance audit","deterioration-contradiction alert","evaluation gap","override accountability","post-market surveillance analogy"])),
    ("prerequisites", json.dumps(["9.5","9.7","9.8","9.9","9.11","10.1"])),
    ("cross_refs", json.dumps([])),
    ("status", json.dumps("corpus_drafted")),
]
for field, val in fields:
    r = subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "10.2", field, val], capture_output=True, text=True)
    print(field + ": exit=" + str(r.returncode) + " " + (r.stderr.strip() or "ok"))
