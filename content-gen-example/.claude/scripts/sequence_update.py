#!/usr/bin/env python3
"""Patches one topic's frontmatter block in curriculum/sequence.md (and the
sequence.index.json mirror) once a corpus is approved.

Usage:
    python .claude/scripts/sequence_update.py <topic-id> <field> <json-array>

Examples:
    python .claude/scripts/sequence_update.py 1.4 concepts_introduced '["temperature","sampling"]'
    python .claude/scripts/sequence_update.py 1.4 prerequisites       '["1.1","1.2","1.3"]'
"""
from __future__ import annotations

import json
import os
import pathlib
import re
import sys
import tempfile

ALLOWED_FIELDS = {"concepts_introduced", "prerequisites", "cross_refs", "status"}


def main() -> int:
    if len(sys.argv) != 4:
        print("usage: sequence_update.py <topic-id> <field> <json>", file=sys.stderr)
        return 2
    topic_id, field, payload = sys.argv[1:]
    if field not in ALLOWED_FIELDS:
        print(f"field must be one of: {sorted(ALLOWED_FIELDS)}", file=sys.stderr)
        return 2
    try:
        parsed = json.loads(payload)
    except Exception as e:
        print(f"payload must be valid JSON: {e}", file=sys.stderr)
        return 2

    seq_md = pathlib.Path(os.environ.get("CIT_CURRICULUM_SEQUENCE", "content/curriculum/sequence.md"))
    seq_json = pathlib.Path(os.environ.get("CIT_CURRICULUM_INDEX", "content/curriculum/sequence.index.json"))

    # Update JSON
    idx = json.loads(seq_json.read_text(encoding="utf-8"))
    for t in idx["topics"]:
        if t["id"] == topic_id:
            t[field] = parsed
            break
    else:
        print(f"topic {topic_id!r} not found in {seq_json}", file=sys.stderr)
        return 2

    fd, tmp = tempfile.mkstemp(dir=str(seq_json.parent), prefix=".seq.", suffix=".json")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        json.dump(idx, f, indent=2, ensure_ascii=False)
        f.write("\n")
    os.replace(tmp, seq_json)

    # Update markdown
    md = seq_md.read_text(encoding="utf-8")
    heading_re = re.compile(rf"^## {re.escape(topic_id)} — .*$", re.M)
    m = heading_re.search(md)
    if not m:
        print(f"topic {topic_id!r} heading not found in {seq_md}", file=sys.stderr)
        return 2

    yaml_start = md.find("```yaml", m.end())
    yaml_end = md.find("```", yaml_start + 7) if yaml_start != -1 else -1
    if yaml_start == -1 or yaml_end == -1:
        print("yaml block not found", file=sys.stderr)
        return 2

    block = md[yaml_start:yaml_end]
    new_value = json.dumps(parsed, ensure_ascii=False)
    field_re = re.compile(rf"^{re.escape(field)}: .*$", re.M)
    if field_re.search(block):
        new_block = field_re.sub(f"{field}: {new_value}", block, count=1)
    else:
        new_block = block.rstrip() + f"\n{field}: {new_value}\n"

    new_md = md[:yaml_start] + new_block + md[yaml_end:]
    fd, tmp = tempfile.mkstemp(dir=str(seq_md.parent), prefix=".seq.", suffix=".md")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        f.write(new_md)
    os.replace(tmp, seq_md)
    print(f"updated {topic_id}.{field}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
