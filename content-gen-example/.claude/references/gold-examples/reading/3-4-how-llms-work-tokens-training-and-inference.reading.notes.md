# Why this passes — 3.4 reading (deep depth, technical topic)

**Provenance:** `origin/week-3` — team-reviewed content (ChatGPT review pass). Treat as the
quality bar for **deep-depth technical topics** — NOT the bar for atomic/standard topics
(an atomic topic at this density would be scope creep).

Moves that make it beginner-clear despite technical depth:

- Every technical term is **bolded and defined at first use** (token, tokenizer, embedding,
  context window, pre-training, weights, KV Cache, autoregressive generation).
- Overview gives the whole pipeline in three plain sentences before any detail.
- Worked Example walks ONE prompt end-to-end in numbered steps — the reader follows a single
  concrete thread through tokenization → prefill → decode → detokenization.
- Multi-step processes always rendered as numbered lists, never prose.
- Blockquotes for example prompts/outputs so they stand apart from explanation.
- "In Practice" section grounds abstractions in deployment reality (cloud vs self-hosted vs edge).
- Key Takeaways restate each concept in one self-contained bullet.

Word count ≈ 1900. The breadth here (KV cache, batching, TTFT) is justified by the topic
headline "tokens, training, and inference" — it is the topic, not drift.
