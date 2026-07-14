import subprocess, json, sys

topic_id = "5.1"
progress_path = "content/m2-introduction-to-ai-systems/week-5/1-ai-failures-and-why-they-happen/5-1-real-ai-failure-cases-healthcare-misdiagnosis-hiring-bias-de/topic.progress.json"

# Run sequence updates
updates = [
    ("concepts_introduced", json.dumps(["AI failure","training data bias","diagnostic AI","algorithmic bias","deepfake","deep learning"])),
    ("prerequisites",       json.dumps(["1.3","2.6","3.8","3.9"])),
    ("cross_refs",          json.dumps([])),
    ("status",              json.dumps("corpus_drafted")),
]

for field, val in updates:
    r = subprocess.run(
        ["python", ".claude/scripts/sequence_update.py", topic_id, field, val],
        capture_output=True, text=True
    )
    print(f"sequence_update {field}: exit={r.returncode} {r.stdout.strip()} {r.stderr.strip()}")

# Run progress updates
prog_updates = [
    ["set", "corpus_generated", "true"],
    ["stage", "awaiting_corpus_critic"],
]

for args in prog_updates:
    r = subprocess.run(
        ["python", ".claude/scripts/progress_update.py", progress_path] + args,
        capture_output=True, text=True
    )
    print(f"progress_update {' '.join(args)}: exit={r.returncode} {r.stdout.strip()} {r.stderr.strip()}")
