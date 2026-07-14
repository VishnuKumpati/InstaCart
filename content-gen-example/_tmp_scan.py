import json, os

with open('content/curriculum/sequence.index.json') as f:
    data = json.load(f)

idx = data['topics'] if isinstance(data, dict) else data
week5 = [t for t in idx if t.get('week_num') == 5]
print(f'Topics found: {len(week5)}')
for t in week5:
    prog_path = os.path.join('content', t['path'], 'topic.progress.json')
    if os.path.exists(prog_path):
        with open(prog_path) as f:
            prog = json.load(f)
        stage = prog.get('stage','?')
        gates = prog.get('gates', {})
        print(f"{t['id']:6} {t['title'][:50]:50} [{stage}]")
        print(f"       ta_approved={gates.get('topic_artifacts_human_approved')} drift={gates.get('module_drift_passed')} week_ok={gates.get('week_human_approved')}")
    else:
        print(f"{t['id']:6} {t['title'][:50]:50} [NO PROGRESS FILE]")
