"""Shared helpers for .claude/scripts/*.py — topic lookup + JSON atomic write.

Cross-platform. Importable by any other script in this directory:

    import sys, pathlib
    sys.path.insert(0, str(pathlib.Path(__file__).parent))
    from _lib import find_topic_dir, atomic_write_json
"""
from __future__ import annotations

import json
import os
import tempfile
from pathlib import Path
from typing import Iterable, Optional


CONTENT_ROOT = Path(os.environ.get("CIT_CONTENT_ROOT", "content"))

# --- caveman session flag -------------------------------------------------
# Single source of truth for caveman activation state. The flag is SESSION-
# SCOPED: it stores the session_id that activated caveman, not a bare boolean.
# A leftover flag from a previous session therefore won't read as active —
# that stale-flag-reads-on bug was the original false positive.
CAVEMAN_FLAG = Path.home() / ".caveman" / "session.flag"


def write_caveman_flag(session_id: str) -> Path:
    """Record that THIS session activated caveman. Overwrites any prior id."""
    CAVEMAN_FLAG.parent.mkdir(parents=True, exist_ok=True)
    CAVEMAN_FLAG.write_text((session_id or "").strip() + "\n", encoding="utf-8")
    return CAVEMAN_FLAG


def caveman_active(session_id: Optional[str]) -> bool:
    """True only if the flag was written by the current session.

    Env CAVEMAN_ACTIVE=1 is an explicit override. With no session_id we cannot
    confirm the live session is caveman, so we report False rather than trust a
    possibly-stale flag — honest-off beats false-on.
    """
    if os.environ.get("CAVEMAN_ACTIVE") == "1":
        return True
    if not session_id:
        return False
    try:
        return CAVEMAN_FLAG.read_text(encoding="utf-8").strip() == session_id.strip()
    except Exception:
        return False


def session_id_from_stdin(raw: str) -> Optional[str]:
    """Pull session_id out of the JSON Claude Code pipes to hooks/statusline."""
    try:
        return (json.loads(raw) if raw else {}).get("session_id")
    except Exception:
        return None


def find_topic_dir(topic_id: str, content_root: Optional[Path] = None) -> Optional[Path]:
    """Locate a topic directory by ID (e.g. '1.1') under the content tree.

    Topic directory names follow the pattern '<N>-<N>-<slug>/' where the
    leading 'N-N-' is the topic_id with the dot replaced by a hyphen.
    """
    root = content_root or CONTENT_ROOT
    if not root.exists():
        return None
    prefix = topic_id.replace(".", "-") + "-"
    # Search every dir whose name starts with that prefix
    for p in root.rglob(f"{prefix}*"):
        if p.is_dir() and (p / "topic.manifest.json").exists():
            return p
    return None


def find_progress(topic_id: str) -> Optional[Path]:
    d = find_topic_dir(topic_id)
    return d / "topic.progress.json" if d else None


def find_manifest(topic_id: str) -> Optional[Path]:
    d = find_topic_dir(topic_id)
    return d / "topic.manifest.json" if d else None


def atomic_write_json(path: Path, data: dict) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    fd, tmp = tempfile.mkstemp(dir=str(path.parent), prefix=".tmp.", suffix=".json")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
        f.write("\n")
    os.replace(tmp, path)


def read_json(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8-sig"))


def all_progress_files(content_root: Optional[Path] = None) -> Iterable[Path]:
    root = content_root or CONTENT_ROOT
    if not root.exists():
        return []
    return list(root.rglob("topic.progress.json"))
