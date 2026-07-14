import json, os, sys

week_num = int(sys.argv[1]) if len(sys.argv) > 1 else 11

with open('content/curriculum/sequence.index.json') as f:
    data = json.load(f)

idx = data['topics'] if isinstance(data, dict) and 'topics' in data else data
topics = [t for t in idx if t.get('week_num') == week_num]

for t in topics:
    prog_path = os.path.join('content', t['path'], 'topic.progress.json')
    try:
        with open(prog_path) as pf:
            prog = json.load(pf)
        stage = prog.get('stage', '?')
        gates = prog.get('gates', {})
    except Exception as e:
        stage = 'NOT_FOUND'
        gates = {}
    h3 = gates.get('topic_artifacts_human_approved', '?')
    drift = gates.get('module_drift_passed', '?')
    week_ok = gates.get('week_human_approved', '?')
    title = t['title'][:50]
    print(f"{t['id']} | {stage} | H3={h3} | drift={drift} | week={week_ok} | {title}")
