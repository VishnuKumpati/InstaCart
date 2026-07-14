import subprocess, json

concepts = ["70/30 rule","implementation (AI role)","specification (human role)","verification (human role)","verification loop","human oversight","accountability split"]
subprocess.run(["python",".claude/scripts/sequence_update.py","2.5","concepts_introduced", json.dumps(concepts)])
subprocess.run(["python",".claude/scripts/sequence_update.py","2.5","status", json.dumps("corpus_drafted")])
print("done")
