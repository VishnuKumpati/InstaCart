# Gold Examples — author-provided "this is what PASSING looks like"

This directory holds **real, approved artifacts** that define the quality bar for the
content-generation pipeline. The content lead drops examples here over time. Builders match
them; critics measure against them.

> **These slots are intentionally empty until you provide examples.**
> The pipeline does **not** fabricate examples to fill a slot. When a slot is empty, builders and
> critics fall back to the written standards in `.claude/config/artifact-mix.json`
> (`audience`, `formatting_standards`, `scope_discipline`) plus each skill's own rules.

## How the pipeline uses these

`artifact-mix.json → gold_examples` points every builder and critic here. The contract:

- **Builders** (`*-builder` skills): before generating, check the matching subdirectory below.
  If an example file exists, treat it as the gold standard for that artifact type — **match its
  sectioning, beginner tone, formatting density (bullets / numbered steps / inline examples /
  Q→A), and depth.** Do not exceed its scope.
- **Critics** (`*-critic` skills): when a gold example exists for the artifact type, use it as the
  concrete pass bar in addition to the written checks. A generated artifact that is markedly
  denser, more jargon-heavy, or wider in scope than the gold example should be flagged.

## Layout

```
gold-examples/
├── corpus/      # approved topic_corpus.md examples
├── reading/     # approved reading.md examples (the learner-facing artifact — highest priority)
├── quiz/        # approved quiz.gift examples
└── diagram/     # approved diagram.mmd (+ rendered .png) examples
```

## How to add an example (for the content lead)

1. Take an artifact that **passed the human quality gate** (HITL #3) and that the team agrees
   represents the target quality for a beginner audience.
2. Copy it into the matching subdirectory. Keep the original filename style
   (`reading.md`, `quiz.gift`, `topic_corpus.md`, `diagram.mmd`).
3. Prefix with the topic id for traceability, e.g. `1-4-why-ai-gives-different-answers.reading.md`.
4. Optionally add a sibling `<name>.notes.md` calling out *why* it passes — the specific moves
   that make it beginner-clear (e.g. "defines 'token' with a one-line analogy before first use",
   "breaks the 3-step flow into a numbered list", "stays on the headline, defers RAG to a pointer").
   Critics weight these notes when present.

## Why this exists

The team observed generated content reading at too high a level for freshers, drifting into
advanced concepts, and arriving as walls of prose. The written standards in `artifact-mix.json`
encode the *rules*; these examples encode the *taste*. One concrete passing artifact aligns the
generator faster than any amount of prose instruction — that is why this slot is first-class.
