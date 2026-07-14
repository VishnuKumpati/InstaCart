"""One-shot patch: write 4.3 concepts_introduced, prerequisites, cross_refs, status."""
import json, os, pathlib, re, sys, tempfile

ALLOWED = {"concepts_introduced", "prerequisites", "cross_refs", "status"}

UPDATES = {
    "concepts_introduced": [
        "RAG (Retrieval-Augmented Generation)",
        "knowledge cutoff",
        "knowledge source",
        "query (user question)",
        "prompt augmentation",
        "retrieve-augment-generate pipeline"
    ],
    "prerequisites": ["4.1", "4.2"],
    "cross_refs": ["RAG pipeline", "vector database", "embeddings", "similarity search", "chunking"],
    "status": "corpus_drafted",
}

TOPIC_ID = "4.3"
seq_json = pathlib.Path("content/curriculum/sequence.index.json")
seq_md   = pathlib.Path("content/curriculum/sequence.md")

# --- patch JSON index ---
idx = json.loads(seq_json.read_text(encoding="utf-8"))
found = False
for t in idx["topics"]:
    if t["id"] == TOPIC_ID:
        for field, val in UPDATES.items():
            t[field] = val
        found = True
        break
if not found:
    sys.exit(f"topic {TOPIC_ID} not found in index")

fd, tmp = tempfile.mkstemp(dir=str(seq_json.parent), prefix=".seq.", suffix=".json")
with os.fdopen(fd, "w", encoding="utf-8") as f:
    json.dump(idx, f, indent=2, ensure_ascii=False)
    f.write("\n")
os.replace(tmp, seq_json)

# --- patch markdown ---
md = seq_md.read_text(encoding="utf-8")
heading_re = re.compile(rf"^## {re.escape(TOPIC_ID)} — .*$", re.M)
m = heading_re.search(md)
if not m:
    sys.exit(f"heading for {TOPIC_ID} not found in sequence.md")

yaml_start = md.find("```yaml", m.end())
yaml_end   = md.find("```", yaml_start + 7) if yaml_start != -1 else -1
if yaml_start == -1 or yaml_end == -1:
    sys.exit("yaml block not found")

block = md[yaml_start:yaml_end]
for field, val in UPDATES.items():
    new_value = json.dumps(val, ensure_ascii=False)
    field_re = re.compile(rf"^{re.escape(field)}: .*$", re.M)
    if field_re.search(block):
        block = field_re.sub(f"{field}: {new_value}", block, count=1)
    else:
        block = block.rstrip() + f"\n{field}: {new_value}\n"

new_md = md[:yaml_start] + block + md[yaml_end:]
fd, tmp = tempfile.mkstemp(dir=str(seq_md.parent), prefix=".seq.", suffix=".md")
with os.fdopen(fd, "w", encoding="utf-8") as f:
    f.write(new_md)
os.replace(tmp, seq_md)

print(f"patched {TOPIC_ID}: concepts_introduced, prerequisites, cross_refs, status=corpus_drafted")
