import subprocess, sys, json

topic_id = sys.argv[1]
field = sys.argv[2]
payload = sys.argv[3]

# Validate JSON before passing
json.loads(payload)

result = subprocess.run(
    ["python", ".claude/scripts/sequence_update.py", topic_id, field, payload],
    capture_output=True, text=True
)
print(result.stdout, end="")
if result.stderr:
    print(result.stderr, file=sys.stderr, end="")
sys.exit(result.returncode)
