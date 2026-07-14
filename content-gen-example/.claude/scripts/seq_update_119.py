import subprocess, sys, os
os.chdir(r"C:\Users\BhuvaneswariMohan\Desktop\content\Foundations-of-Responsible-AI-Engineering\content-gen-example")

steps = [
    ["python", ".claude/scripts/sequence_update.py", "11.9", "concepts_introduced",
     '["edge case","boundary value","equivalence class","normal input","empty input","zero input","off-by-one error","test prediction","minimum valid input","maximum valid input"]'],
    ["python", ".claude/scripts/sequence_update.py", "11.9", "prerequisites",
     '["11.6","11.7","11.8"]'],
    ["python", ".claude/scripts/sequence_update.py", "11.9", "cross_refs",
     '["Python"]'],
    ["python", ".claude/scripts/sequence_update.py", "11.9", "status",
     '"corpus_drafted"'],
]

for cmd in steps:
    r = subprocess.run(cmd, capture_output=True, text=True)
    print("CMD:", " ".join(cmd[2:]))
    if r.stdout: print("STDOUT:", r.stdout.strip())
    if r.stderr: print("STDERR:", r.stderr.strip())
    if r.returncode != 0:
        print("FAILED — exit", r.returncode)
        sys.exit(r.returncode)
    print("OK")
print("All done.")
