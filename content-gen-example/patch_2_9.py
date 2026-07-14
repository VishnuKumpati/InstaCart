import subprocess, json

subprocess.run(["python",".claude/scripts/sequence_update.py","2.9","concepts_introduced",
    json.dumps(["iterating a specification","gap","iteration cycle","gap-to-fix mapping","minimum viable patch","accept with known limitation"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.9","prerequisites",
    json.dumps(["2.8","2.1","2.2","2.3","2.7"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.9","cross_refs", json.dumps([])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.9","status", json.dumps("corpus_drafted")])
print("done")
