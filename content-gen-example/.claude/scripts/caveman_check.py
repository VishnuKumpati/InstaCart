#!/usr/bin/env python3
"""Verify caveman is installed and currently active for THIS session.

Active state is session-scoped (see _lib.caveman_active): the flag must record
the current session_id. Pass it so a stale flag from a previous session can't
report a false positive.

Usage:
    python .claude/scripts/caveman_check.py [--session <id>]            # 'on'/'off', exit 0
    python .claude/scripts/caveman_check.py require [--session <id>]    # soft-warn if off
"""
from __future__ import annotations

import pathlib
import sys

sys.path.insert(0, str(pathlib.Path(__file__).parent))
from _lib import caveman_active  # noqa: E402


def parse_args(argv: list[str]) -> tuple[str, str | None]:
    mode = "check"
    session_id = None
    rest = argv[1:]
    i = 0
    while i < len(rest):
        a = rest[i]
        if a == "--session" and i + 1 < len(rest):
            session_id = rest[i + 1]
            i += 2
            continue
        if not a.startswith("--"):
            mode = a
        i += 1
    return mode, session_id


def main() -> int:
    mode, session_id = parse_args(sys.argv)
    active = caveman_active(session_id)

    # `require` soft-warns (never blocks) so a missing/off caveman never stops a
    # teammate. The SessionStart hook invokes the skill; if that didn't land, we
    # still let the user proceed — just with a stderr nudge.
    if mode == "require" and not active:
        sys.stderr.write(
            "caveman is not active for this session — output will be verbose, "
            "context will fill faster.\n"
            "The SessionStart hook should invoke it automatically; if it didn't, "
            "run /caveman to invoke the skill manually.\n"
        )
        print("off")
        return 0  # soft warn — do not block the workflow

    print("on" if active else "off")
    return 0


if __name__ == "__main__":
    sys.exit(main())
