import subprocess, json

subprocess.run(["python",".claude/scripts/sequence_update.py","2.6","concepts_introduced",
    json.dumps(["sensitive data","privacy risk (AI data exposure)","exact precision requirement","legal accountability","three-question check (privacy / precision / accountability)"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.6","prerequisites",
    json.dumps(["2.1","2.3","2.4","2.5"])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.6","cross_refs", json.dumps([])])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.6","status", json.dumps("corpus_drafted")])
print("done")
