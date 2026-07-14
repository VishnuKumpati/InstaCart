import json, os, sys
from pathlib import Path
base = Path("content/m5-python-for-ai-pe-vc-and-rag/week-14")
topics = [
  ("14.1","1-retrieval-augmented-generation-rag/14-1-vector-databases-storing-embeddings-and-enabling-similarity"),
  ("14.2","1-retrieval-augmented-generation-rag/14-2-the-rag-retrieval-pipeline-query-embed-similarity-search-top"),
  ("14.3","1-retrieval-augmented-generation-rag/14-3-why-rag-reduces-hallucination-ai-answers-from-retrieved-evid"),
  ("14.4","1-retrieval-augmented-generation-rag/14-4-when-to-use-rag-use-it-when-the-answer-depends-on-data-the-m"),
  ("14.5","2-ai-agents/14-5-agent-anatomy-llm-plus-memory-tools-and-a-planning-loop"),
  ("14.6","2-ai-agents/14-6-the-react-pattern-reason-act-observe-repeat"),
  ("14.7","2-ai-agents/14-7-agent-vs-simpler-flow-decision-matrix-direct-call-chained-ca"),
  ("14.8","2-ai-agents/14-8-when-not-to-use-agents-high-stakes-or-irreversible-actions-r"),
  ("14.9","3-production-ai-patterns/14-9-production-ai-patterns-cost-latency-and-reliability-trade-of"),
  ("14.10","3-production-ai-patterns/14-10-writing-an-architectural-decision-choosing-the-right-pattern"),
]
for tid, path in topics:
    full = base / path
    with open(full / "topic.manifest.json", encoding="utf-8") as f:
        m = json.load(f)
    with open(full / "topic.progress.json", encoding="utf-8") as f:
        p = json.load(f)
    edm = m.get("expected_delivery_minutes") or "null"
    print(tid + "|" + m["title"] + "|" + p["stage"] + "|" + str(edm))
