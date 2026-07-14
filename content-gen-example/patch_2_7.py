import subprocess, json

subprocess.run(["python",".claude/scripts/sequence_update.py","2.7","concepts_introduced",
    json.dumps(["domain-specific specification","four-part spec structure","domain context","domain-specific constraint","domain-specific output format","explicit exclusion"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.7","prerequisites",
    json.dumps(["2.1","2.2","2.3","2.5"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.7","cross_refs", json.dumps([])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.7","status", json.dumps("corpus_drafted")])
print("done")
