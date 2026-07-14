#!/usr/bin/env python3
"""SessionStart hook — auto-activates caveman if it's installed.

Claude Code fires this when a session begins. We:
  1. Detect whether caveman is installed (binary on PATH, or its skill file).
  2. If installed: touch the session flag so subsequent commands see caveman as
     active. The team agreed: caveman is always-on for this workflow.
  3. If NOT installed: print a single friendly line pointing at the installer.
     Do NOT block the session — teammates can still work; their output just
     won't be compressed.

Cross-platform: works on Windows + macOS.
"""
from __future__ import annotations

import shutil
import sys
import pathlib
from pathlib import Path

sys.path.insert(0, str(pathlib.Path(__file__).parent.parent / "scripts"))
from _lib import write_caveman_flag, session_id_from_stdin  # noqa: E402


def project_skill() -> Path:
    """The caveman SKILL.md vendored into this repo's .claude/skills/.
    Resolved relative to this hook file, so it works on any clone regardless
    of CWD or where the repo lives."""
    return Path(__file__).resolve().parent.parent / "skills" / "caveman" / "SKILL.md"


def caveman_installed() -> bool:
    """Best-effort detection — checks the repo-vendored skill first (so a fresh
    clone is caveman-ready with no per-machine install), then PATH binary and
    global skill locations."""
    if project_skill().exists():
        return True
    if shutil.which("caveman"):
        return True
    home = Path.home()
    candidates = [
        home / ".claude" / "skills" / "caveman" / "SKILL.md",
        home / ".claude" / "agents" / "caveman.md",
        home / ".codex" / "skills" / "caveman",
        home / ".caveman",  # any caveman-managed state dir
    ]
    return any(p.exists() for p in candidates)


# Directive emitted into the session. We INVOKE the skill rather than paste a
# ruleset: a pasted blob is unreliable (the model may not adopt it — that was
# the old false positive), whereas invoking the skill loads its persistent
# "ACTIVE EVERY RESPONSE" contract. Always fired when installed, so an off
# session self-heals instead of trusting a possibly-stale flag.
ACTIVATION_DIRECTIVE = (
    "caveman is the team default for this repo. ACTION REQUIRED: if caveman is "
    "not already shaping your output this session, invoke the `caveman` skill "
    "now via the Skill tool (skill name: \"caveman\") before responding to the "
    "user. Do not merely imitate the style from this note — actually invoke the "
    "skill so its persistent ruleset loads. Off later with 'stop caveman'."
)


def main() -> int:
    try:
        session_id = session_id_from_stdin(sys.stdin.read())
        if caveman_installed():
            # Flag is session-scoped: records THIS session's id so a leftover
            # flag from a prior session can't masquerade as active.
            write_caveman_flag(session_id or "")
            print("caveman activated for this session")
            print(ACTIVATION_DIRECTIVE)
            return 0
        else:
            print(
                "caveman is not installed — output won't be compressed. "
                "The repo vendors it at .claude/skills/caveman/; if this message "
                "appears, that file is missing. Run /caveman to invoke manually."
            )
            return 0  # never block the session
    except Exception as e:
        # If anything goes wrong, don't block the session.
        print(f"session_start hook: non-fatal error: {e}", file=sys.stderr)
        return 0


if __name__ == "__main__":
    sys.exit(main())
