import json
d=json.load(open('content/curriculum/sequence.index.json'))
topics=d['topics']
pre=[t for t in topics if t.get('week_num',99) < 5]
print(f'Prior topics count: {len(pre)}')
for t in pre:
    print(f"{t['id']}: {t['title']} | concepts: {t.get('concepts_introduced',[])} | prereqs: {t.get('prerequisites',[])}")
