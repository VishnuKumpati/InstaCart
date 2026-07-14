import subprocess, json

subprocess.run(["python",".claude/scripts/sequence_update.py","2.8","concepts_introduced",
    json.dumps(["testing a specification","three-question verification check","full pass","partial pass","full fail","pass/fail table","format drift","constraint overflow","task drift"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.8","prerequisites",
    json.dumps(["2.1","2.3","2.7"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.8","cross_refs", json.dumps([])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.8","status", json.dumps("corpus_drafted")])
print("done")
