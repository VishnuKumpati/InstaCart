#!/usr/bin/env python3
"""UserPromptSubmit hook — gate-checks CIT slash commands.

Claude Code passes the prompt as JSON on stdin:
    { "session_id": "...", "prompt": "..." }

Exit 0 = allow. Exit 2 = block with stderr shown to the user.
The team agreed: hard-block, no override.
"""
from __future__ import annotations

import json
import pathlib
import subprocess
import sys

sys.path.insert(0, str(pathlib.Path(__file__).parent.parent / "scripts"))
from _lib import find_progress, read_json  # noqa: E402


GATED_COMMANDS = {
    "/topic-corpus":          ["resources_captured"],
    "/critic-corpus":         ["corpus_generated"],
    "/topic-corpus-review":   ["corpus_critic_passed"],
    "/generate-reading":      ["corpus_human_approved"],
    "/generate-quiz":         ["corpus_human_approved"],
    "/generate-diagram":      ["corpus_human_approved"],
    # /generate-reading is conditionally gated on diagram in the command body
    # (only when artifact_plan.diagram == true). The router enforces only the
    # universal corpus-approved precondition; the command does the conditional.
    "/critic-reading":        ["reading_generated"],
    "/critic-quiz":           ["quiz_generated"],
    "/critic-diagram":        ["diagram_generated"],
    # Per-topic artifact approval — HITL #3. The diagram-critic gate is
    # conditional (only required when artifact_plan.diagram=true); the command
    # body handles the diagram conditional itself.
    "/topic-artifacts-review": ["reading_critic_passed", "quiz_critic_passed"],
    # module-level / course-level / admin commands run their own internal checks
    "/critic-module-drift":   [],
    "/critic-course-drift":   [],
}


def block(msg: str) -> int:
    sys.stderr.write(f"BLOCKED: {msg}\n")
    return 2


def main() -> int:
    raw = sys.stdin.read()
    try:
        payload = json.loads(raw) if raw else {}
    except Exception:
        return 0  # not JSON we understand; let it through
    prompt = (payload.get("prompt") or "").strip()
    if not prompt:
        return 0

    parts = prompt.split()
    cmd = parts[0]
    if cmd not in GATED_COMMANDS:
        return 0

    # Caveman is auto-activated by the SessionStart hook when installed.
    # If it's still off we surface a warning to stderr but do NOT block —
    # the workflow remains usable without compression.
    try:
        cmd_args = [sys.executable, ".claude/scripts/caveman_check.py", "require"]
        sid = payload.get("session_id")
        if sid:
            cmd_args += ["--session", sid]
        r = subprocess.run(cmd_args, capture_output=True, text=True)
        if r.returncode == 0 and r.stderr.strip():
            sys.stderr.write(r.stderr)  # pass the warn-line through
    except Exception:
        pass  # never block on caveman detection errors

    required = GATED_COMMANDS[cmd]
    if not required:
        return 0

    arg1 = parts[1] if len(parts) > 1 else ""
    if not arg1:
        return block(f"{cmd} requires a topic id as the first argument.")

    pf = find_progress(arg1)
    if not pf:
        return block(f"no topic found for id '{arg1}'. Did you run /curriculum-ingest?")

    try:
        data = read_json(pf)
    except Exception as e:
        return block(f"could not read progress file for {arg1}: {e}")

    gates = data.get("gates", {})
    for g in required:
        if gates.get(g) is not True:
            return block(
                f"gate '{g}' for topic {arg1} is not satisfied. "
                f"Current stage: {data.get('stage','?')}. Run the prior step first."
            )

    return 0


if __name__ == "__main__":
    sys.exit(main())
