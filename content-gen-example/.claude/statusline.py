#!/usr/bin/env python3
"""
CIT content-generation statusline. Cross-platform (Windows + macOS).

Renders next to caveman's own [CAVEMAN] badge. Shows context %, caveman state,
current topic + gate (from the most-recently-touched topic.progress.json),
projected next-step main cost, and a band recommendation.

Claude Code passes the session JSON on stdin.

Env:
  CIT_CONTEXT_BUDGET_CONFIG  default .claude/config/context-budget.json
  CIT_CONTENT_ROOT           default content
"""
from __future__ import annotations
import json
import os
import sys
import pathlib
from pathlib import Path

sys.path.insert(0, str(pathlib.Path(__file__).parent / "scripts"))
from _lib import caveman_active  # noqa: E402

BUDGET_PATH = Path(os.environ.get("CIT_CONTEXT_BUDGET_CONFIG", ".claude/config/context-budget.json"))
CONTENT_ROOT = Path(os.environ.get("CIT_CONTENT_ROOT", "content"))


def read_stdin_json() -> dict:
    try:
        raw = sys.stdin.read()
        return json.loads(raw) if raw else {}
    except Exception:
        return {}


def ctx_pct(d: dict) -> int:
    try:
        used = (d.get("context", {}).get("tokens_used")
                or d.get("session", {}).get("tokens_used")
                or d.get("tokens_used") or 0)
        total = (d.get("context", {}).get("tokens_total")
                 or d.get("session", {}).get("tokens_total")
                 or d.get("tokens_total") or 200000)
        if total <= 0:
            total = 200000
        return int(used * 100 / total)
    except Exception:
        return 0


def current_gate() -> tuple[str, str]:
    if not CONTENT_ROOT.exists():
        return "—", "—"
    candidates = list(CONTENT_ROOT.rglob("topic.progress.json"))
    if not candidates:
        return "—", "—"
    latest = max(candidates, key=lambda p: p.stat().st_mtime)
    try:
        d = json.loads(latest.read_text(encoding="utf-8"))
        return d.get("topic_id", "—"), d.get("stage", "—")
    except Exception:
        return "—", "—"


def next_step_cost_pct(budget: dict, stage: str) -> int:
    nxt = {
        "awaiting_resources":            "topic_resources_capture",
        "awaiting_corpus":               "topic_corpus_build",
        "awaiting_corpus_critic":        "corpus_critic",
        "awaiting_corpus_human_review":  "topic_corpus_review",
        "corpus_approved":               "generate_reading",
        "awaiting_reading_critic":       "reading_critic",
        "reading_approved":              "generate_quiz",
        "awaiting_quiz_critic":          "quiz_critic",
        "artifacts_complete":            "module_drift_critic",
    }.get(stage)
    if not nxt:
        return 0
    s = budget.get("steps", {}).get(nxt, {})
    total = budget.get("context_window_total_tokens", 200000)
    return int(s.get("main", 0) * 100 / total)


def caveman_state(session_id: str | None) -> str:
    return "on" if caveman_active(session_id) else "off"


def band(budget: dict, current: int, nxt: int) -> str:
    total = current + nxt
    t = budget.get("thresholds", {})
    g = t.get("green_max_pct", 25)
    y = t.get("yellow_max_pct", 40)
    r = t.get("red_min_pct", 60)
    if total >= r:
        return "RED ⚠"
    if total >= y:
        return "YEL ⚠"
    if total >= g:
        return "amb"
    return "ok"


def main() -> int:
    sj = read_stdin_json()
    try:
        budget = json.loads(BUDGET_PATH.read_text(encoding="utf-8"))
    except Exception:
        budget = {}
    pct = ctx_pct(sj)
    topic, stage = current_gate()
    nxt = next_step_cost_pct(budget, stage)
    cm = caveman_state(sj.get("session_id"))
    bd = band(budget, pct, nxt)
    print(f"[cit ctx {pct}% | cave {cm} | t:{topic}/{stage} | next +{nxt}% | {bd}]", end="")
    return 0


if __name__ == "__main__":
    sys.exit(main())
