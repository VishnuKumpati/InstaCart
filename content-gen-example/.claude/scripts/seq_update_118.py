import subprocess, sys

concepts = ["golden rule (full definition)", "explain line by line", "silent failure", "loud failure", "four-question test", "code ownership", "plausible-looking wrong code"]
prereqs = ["11.7"]
cross_refs = ["Python"]

import json

subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "11.8", "concepts_introduced", json.dumps(concepts)], check=True)
subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "11.8", "prerequisites", json.dumps(prereqs)], check=True)
subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "11.8", "cross_refs", json.dumps(cross_refs)], check=True)
subprocess.run([sys.executable, ".claude/scripts/sequence_update.py", "11.8", "status", json.dumps("corpus_drafted")], check=True)
print("done")
