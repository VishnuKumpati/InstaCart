#!/usr/bin/env python3
"""Atomic updates to a topic's progress.json.

Usage:
    python .claude/scripts/progress_update.py <topic-id> set <gate> <true|false|null>
    python .claude/scripts/progress_update.py <topic-id> stage <new_stage>
    python .claude/scripts/progress_update.py <topic-id> log <message...>
"""
from __future__ import annotations

import datetime
import pathlib
import sys

sys.path.insert(0, str(pathlib.Path(__file__).parent))
from _lib import find_progress, read_json, atomic_write_json  # noqa: E402


def main() -> int:
    if len(sys.argv) < 3:
        print("usage: progress_update.py <topic-id> {set|stage|log} ...", file=sys.stderr)
        return 2
    topic_id, op, *rest = sys.argv[1:]
    path = find_progress(topic_id)
    if not path:
        print(f"progress file for {topic_id} not found", file=sys.stderr)
        return 2

    data = read_json(path)
    now = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%SZ")

    if op == "set":
        if len(rest) != 2:
            print("set: <gate> <true|false|null>", file=sys.stderr)
            return 2
        gate, raw = rest
        val = {"true": True, "false": False, "null": None}.get(raw)
        if val == "_NOT_SET" or raw not in ("true", "false", "null"):
            print("set: value must be true|false|null", file=sys.stderr)
            return 2
        # Gate schema is owned by CLAUDE.md + the orchestrator, not this writer.
        # If the orchestrator references a gate that does not yet exist in the
        # file (e.g. a new gate added when the pipeline grew a new HITL phase),
        # auto-bootstrap it instead of rejecting. Typo safety is the
        # orchestrator's job, not the state-writer's.
        data.setdefault("gates", {})[gate] = val
        data.setdefault("history", []).append({"at": now, "op": "set", "gate": gate, "value": val})

    elif op == "stage":
        if not rest:
            print("stage: <new_stage>", file=sys.stderr)
            return 2
        data["stage"] = rest[0]
        data.setdefault("history", []).append({"at": now, "op": "stage", "to": rest[0]})

    elif op == "log":
        msg = " ".join(rest)
        data.setdefault("history", []).append({"at": now, "op": "log", "msg": msg})

    else:
        print(f"unknown op: {op}", file=sys.stderr)
        return 2

    atomic_write_json(path, data)
    return 0


if __name__ == "__main__":
    sys.exit(main())
