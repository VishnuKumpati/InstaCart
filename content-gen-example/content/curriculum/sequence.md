# Curriculum Delivery Sequence

_Authoritative ordered list of every topic in delivery order._
_Concepts introduced, prerequisites, and expected_delivery_minutes are filled in by the corpus builder + reading critic._
_Do not hand-edit `concepts_introduced` / `prerequisites` once a topic's corpus is approved._

**Per-week budget:** 90 minutes total across all topics in the week.
**Reading WPM target:** 200 (college audience, lower end).

---

## 1.1 — What is computation

```yaml
id: 1.1
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 1 — Understanding Computation
path:   m1-computational-thinking/week-1/1-understanding-computation/1-1-what-is-computation
one_liner: "What is computation"
concepts_introduced: ["computation", "input / process / output", "computable", "computational thinking", "defined steps"]
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.2 — Deterministic systems — same input always gives the same output

```yaml
id: 1.2
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 1 — Understanding Computation
path:   m1-computational-thinking/week-1/1-understanding-computation/1-2-deterministic-systems-same-input-always-gives-the-same-outpu
one_liner: "Deterministic systems — same input always gives the same output"
concepts_introduced: ["deterministic system", "determinism", "predictability", "reproducibility", "hidden inputs"]
prerequisites: [1.1]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.3 — Probabilistic systems — same input can give different outputs

```yaml
id: 1.3
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 1 — Understanding Computation
path:   m1-computational-thinking/week-1/1-understanding-computation/1-3-probabilistic-systems-same-input-can-give-different-outputs
one_liner: "Probabilistic systems — same input can give different outputs"
concepts_introduced: ["probabilistic system", "probability", "uncertainty", "randomness", "sampling", "output variation", "temperature (as a control on randomness)"]
prerequisites: [1.1, 1.2]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.4 — Why AI gives different answers to the same question

```yaml
id: 1.4
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 1 — Understanding Computation
path:   m1-computational-thinking/week-1/1-understanding-computation/1-4-why-ai-gives-different-answers-to-the-same-question
one_liner: "Why AI gives different answers to the same question"
concepts_introduced: ["LLM (Large Language Model)", "token", "token sampling", "probability distribution"]
prerequisites: [1.1, 1.2, 1.3]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.5 — Decomposition — breaking a big problem into smaller solvable parts

```yaml
id: 1.5
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 2 — Problem-Solving Foundations
path:   m1-computational-thinking/week-1/2-problem-solving-foundations/1-5-decomposition-breaking-a-big-problem-into-smaller-solvable-p
one_liner: "Decomposition — breaking a big problem into smaller solvable parts"
concepts_introduced: ["decomposition", "task tree", "root (task tree)", "sub-task", "leaf (task tree)", "iterative refinement"]
prerequisites: ["1.1"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.6 — Abstraction — hiding complexity at the right level

```yaml
id: 1.6
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 2 — Problem-Solving Foundations
path:   m1-computational-thinking/week-1/2-problem-solving-foundations/1-6-abstraction-hiding-complexity-at-the-right-level
one_liner: "Abstraction — hiding complexity at the right level"
concepts_introduced: ["abstraction", "layer (abstraction layer)", "right level of abstraction", "model (as abstraction output)", "leaky abstraction"]
prerequisites: [1.1, 1.5]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.7 — Pseudocode — writing logic in plain English before writing code

```yaml
id: 1.7
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 3 — Expressing Logic
path:   m1-computational-thinking/week-1/3-expressing-logic/1-7-pseudocode-writing-logic-in-plain-english-before-writing-cod
one_liner: "Pseudocode — writing logic in plain English before writing code"
concepts_introduced: ["pseudocode", "sequence", "decision (conditional / branch)", "condition", "repetition", "building blocks of logic"]
prerequisites: [1.1, 1.5]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.8 — Flowcharts — visualising logic with standard shapes and arrows

```yaml
id: 1.8
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 3 — Expressing Logic
path:   m1-computational-thinking/week-1/3-expressing-logic/1-8-flowcharts-visualising-logic-with-standard-shapes-and-arrows
one_liner: "Flowcharts — visualising logic with standard shapes and arrows"
concepts_introduced: ["flowchart", "terminator (oval)", "process box (rectangle)", "decision diamond", "input/output parallelogram", "flowline (arrow)", "loop (in a flowchart)"]
prerequisites: ["1.7"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.9 — Algorithmic thinking — what makes a set of steps an algorithm

```yaml
id: 1.9
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 4 — Algorithmic Thinking
path:   m1-computational-thinking/week-1/4-algorithmic-thinking/1-9-algorithmic-thinking-what-makes-a-set-of-steps-an-algorithm
one_liner: "Algorithmic thinking — what makes a set of steps an algorithm"
concepts_introduced: ["algorithm", "algorithmic thinking", "unambiguous steps", "incomplete instructions", "precision in instructions"]
prerequisites: [1.1, 1.5, 1.7, 1.8]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.10 — Algorithms in everyday life — recipes, GPS routes, sorting queues

```yaml
id: 1.10
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 4 — Algorithmic Thinking
path:   m1-computational-thinking/week-1/4-algorithmic-thinking/1-10-algorithms-in-everyday-life-recipes-gps-routes-sorting-queue
one_liner: "Algorithms in everyday life — recipes, GPS routes, sorting queues"
concepts_introduced: ["recipe as algorithm", "route-finding", "sorting algorithm", "comparison rule", "stopping condition", "tie-breaking rule"]
prerequisites: [1.9]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.11 — The four properties of a good algorithm — finite, definite, input, output

```yaml
id: 1.11
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 4 — Algorithmic Thinking
path:   m1-computational-thinking/week-1/4-algorithmic-thinking/1-11-the-four-properties-of-a-good-algorithm-finite-definite-inpu
one_liner: "The four properties of a good algorithm — finite, definite, input, output"
concepts_introduced: ["finiteness", "definiteness", "input (algorithm property)", "output (algorithm property)", "infinite loop", "termination", "well-defined algorithm"]
prerequisites: ["1.9", "1.10"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 1.12 — Choosing your domain — writing a 3-sentence problem statement

```yaml
id: 1.12
week: W01 (How Machines Think)
course: M1 Computational Thinking
module: 5 — Applying It to Your Domain
path:   m1-computational-thinking/week-1/5-applying-it-to-your-domain/1-12-choosing-your-domain-writing-a-3-sentence-problem-statement
one_liner: "Choosing your domain — writing a 3-sentence problem statement"
concepts_introduced: ["problem domain", "problem statement", "3-sentence format", "context (problem statement component)", "impact (problem statement component)"]
prerequisites: ["1.5", "1.6"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 2.1 — What makes a good specification — testable, bounded, observable, actionable

```yaml
id: 2.1
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 1 — What Makes a Good Specification
path:   m1-computational-thinking/week-2/1-what-makes-a-good-specification/2-1-what-makes-a-good-specification-testable-bounded-observable
one_liner: "What makes a good specification — testable, bounded, observable, actionable"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 2.2 — Bad spec vs good spec — 'make it better' vs 'rewrite at Grade 8 level, max 80 words'

```yaml
id: 2.2
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 1 — What Makes a Good Specification
path:   m1-computational-thinking/week-2/1-what-makes-a-good-specification/2-2-bad-spec-vs-good-spec-make-it-better-vs-rewrite-at-grade-8-l
one_liner: "Bad spec vs good spec — 'make it better' vs 'rewrite at Grade 8 level, max 80 words'"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 2.3 — How to identify the inputs, expected outputs, and failure conditions of a task

```yaml
id: 2.3
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 1 — What Makes a Good Specification
path:   m1-computational-thinking/week-2/1-what-makes-a-good-specification/2-3-how-to-identify-the-inputs-expected-outputs-and-failure-cond
one_liner: "How to identify the inputs, expected outputs, and failure conditions of a task"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 2.4 — Pattern recognition — how machines find rules in repeated data

```yaml
id: 2.4
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 2 — How Machines Recognise Patterns
path:   m1-computational-thinking/week-2/2-how-machines-recognise-patterns/2-4-pattern-recognition-how-machines-find-rules-in-repeated-data
one_liner: "Pattern recognition — how machines find rules in repeated data"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 2.5 — The 70/30 rule — AI implements, you specify and verify

```yaml
id: 2.5
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 3 — The Professional Rules of AI Use
path:   m1-computational-thinking/week-2/3-the-professional-rules-of-ai-use/2-5-the-70-30-rule-ai-implements-you-specify-and-verify
one_liner: "The 70/30 rule — AI implements, you specify and verify"
concepts_introduced: ["70/30 rule", "implementation (AI role)", "specification (human role)", "verification (human role)", "verification loop", "human oversight", "accountability split"]
prerequisites: [1.1, 1.3, 2.1, 2.3]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 2.6 — When NOT to use AI — privacy, precision, legal accountability

```yaml
id: 2.6
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 3 — The Professional Rules of AI Use
path:   m1-computational-thinking/week-2/3-the-professional-rules-of-ai-use/2-6-when-not-to-use-ai-privacy-precision-legal-accountability
one_liner: "When NOT to use AI — privacy, precision, legal accountability"
concepts_introduced: ["sensitive data", "privacy risk (AI data exposure)", "exact precision requirement", "legal accountability", "three-question check (privacy / precision / accountability)"]
prerequisites: ["2.1", "2.3", "2.4", "2.5"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 2.7 — Writing specifications across five domains — health, transport, education, food, scheduling

```yaml
id: 2.7
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 4 — Writing and Testing Specifications
path:   m1-computational-thinking/week-2/4-writing-and-testing-specifications/2-7-writing-specifications-across-five-domains-health-transport
one_liner: "Writing specifications across five domains — health, transport, education, food, scheduling"
concepts_introduced: ["domain-specific specification", "four-part spec structure", "domain context", "domain-specific constraint", "domain-specific output format", "explicit exclusion"]
prerequisites: ["2.1", "2.2", "2.3", "2.5"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 2.8 — Testing a specification — how to verify the AI did exactly what you asked

```yaml
id: 2.8
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 4 — Writing and Testing Specifications
path:   m1-computational-thinking/week-2/4-writing-and-testing-specifications/2-8-testing-a-specification-how-to-verify-the-ai-did-exactly-wha
one_liner: "Testing a specification — how to verify the AI did exactly what you asked"
concepts_introduced: ["testing a specification", "three-question verification check", "full pass", "partial pass", "full fail", "pass/fail table", "format drift", "constraint overflow", "task drift"]
prerequisites: ["2.1", "2.3", "2.7"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 2.9 — Iterating a specification based on output gaps

```yaml
id: 2.9
week: W02 (Specifying for AI)
course: M1 Computational Thinking
module: 4 — Writing and Testing Specifications
path:   m1-computational-thinking/week-2/4-writing-and-testing-specifications/2-9-iterating-a-specification-based-on-output-gaps
one_liner: "Iterating a specification based on output gaps"
concepts_introduced: ["iterating a specification", "gap", "iteration cycle", "gap-to-fix mapping", "minimum viable patch", "accept with known limitation"]
prerequisites: ["2.8", "2.1", "2.2", "2.3", "2.7"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.1 — History of AI — symbolic AI to machine learning to deep learning

```yaml
id: 3.1
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 1 — A Brief History of AI
path:   m2-introduction-to-ai-systems/week-3/1-a-brief-history-of-ai/3-1-history-of-ai-symbolic-ai-to-machine-learning-to-deep-learni
one_liner: "History of AI — symbolic AI to machine learning to deep learning"
concepts_introduced: ["symbolic AI", "expert system", "AI Winter", "machine learning", "training data", "deep learning", "artificial neural network"]
prerequisites: ["1.1", "1.4", "2.5"]
cross_refs: ["neural networks", "LLMs", "GPUs", "Transformer architecture"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.2 — The rise of large language models (LLMs)

```yaml
id: 3.2
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 1 — A Brief History of AI
path:   m2-introduction-to-ai-systems/week-3/1-a-brief-history-of-ai/3-2-the-rise-of-large-language-models-llms
one_liner: "The rise of large language models (LLMs)"
concepts_introduced: ["large language model", "word embedding", "recurrent neural network", "Transformer architecture", "BERT", "GPT", "pre-training and fine-tuning", "hallucination", "few-shot", "prompt"]
prerequisites: ["3.1"]
cross_refs: ["Word2Vec", "BERT", "GPT", "LSTMs", "Google Translate"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.3 — The move to AI agents

```yaml
id: 3.3
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 1 — A Brief History of AI
path:   m2-introduction-to-ai-systems/week-3/1-a-brief-history-of-ai/3-3-the-move-to-ai-agents
one_liner: "The move to AI agents"
concepts_introduced: ["AI agent", "agent loop", "goal (agent)", "planning", "tool use", "memory (short-term and long-term)", "feedback loop", "orchestration"]
prerequisites: ["3.1", "3.2"]
cross_refs: ["LangChain", "AutoGen", "CrewAI"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.4 — How LLMs work — tokens, training, and inference

```yaml
id: 3.4
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 2 — How LLMs Work
path:   m2-introduction-to-ai-systems/week-3/2-how-llms-work/3-4-how-llms-work-tokens-training-and-inference
one_liner: "How LLMs work — tokens, training, and inference"
concepts_introduced: ["token", "tokenisation", "subword tokenisation", "Byte-Pair Encoding (BPE)", "embedding table", "next-token prediction", "parameters (model weights)", "backpropagation", "attention mechanism", "inference", "prefill phase", "decode phase", "autoregressive decoding", "context window"]
prerequisites: ["3.1", "3.2"]
cross_refs: ["tiktoken", "Hugging Face", "BPE", "GPT-3", "bert-base-uncased"]
expected_delivery_minutes: null
status: "module_passed"
```

## 3.5 — What parameters are and why they matter

```yaml
id: 3.5
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 2 — How LLMs Work
path:   m2-introduction-to-ai-systems/week-3/2-how-llms-work/3-5-what-parameters-are-and-why-they-matter
one_liner: "What parameters are and why they matter"
concepts_introduced: ["parameter (weight)", "weight matrix", "bias", "scaling law", "quantisation", "model size (GB)", "parameter count", "emergent capability", "fp16", "int8", "int4"]
prerequisites: ["3.1", "3.2", "3.4"]
cross_refs: ["GPT-3", "GPT-4", "LLaMA", "Ollama"]
expected_delivery_minutes: null
status: "module_passed"
```

## 3.6 — Temperature and sampling — why the same question gives different answers

```yaml
id: 3.6
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 2 — How LLMs Work
path:   m2-introduction-to-ai-systems/week-3/2-how-llms-work/3-6-temperature-and-sampling-why-the-same-question-gives-differe
one_liner: "Temperature and sampling — why the same question gives different answers"
concepts_introduced: ["temperature (LLM)", "probability distribution (token)", "greedy decoding", "top-k sampling", "top-p sampling (nucleus)", "logits", "deterministic output", "stochastic output"]
prerequisites: ["3.2", "3.4"]
cross_refs: ["OpenAI API", "Anthropic API", "Ollama"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.7 — AI in India today — healthcare, agriculture, vernacular translation

```yaml
id: 3.7
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 3 — AI in Context
path:   m2-introduction-to-ai-systems/week-3/3-ai-in-context/3-7-ai-in-india-today-healthcare-agriculture-vernacular-translat
one_liner: "AI in India today — healthcare, agriculture, vernacular translation"
concepts_introduced: ["computer vision (medical)", "telemedicine", "IndiaAI Mission", "Kisan e-Mitra", "vernacular NLP", "multilingual AI", "BharatGen", "digital divide"]
prerequisites: ["3.1", "3.2"]
cross_refs: ["Kisan e-Mitra", "BharatGen", "AIIMS", "eSanjeevani", "IndiaAI Mission"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.8 — The jagged frontier — tasks AI is superhuman at vs tasks where it is unreliable

```yaml
id: 3.8
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 3 — AI in Context
path:   m2-introduction-to-ai-systems/week-3/3-ai-in-context/3-8-the-jagged-frontier-tasks-ai-is-superhuman-at-vs-tasks-where
one_liner: "The jagged frontier — tasks AI is superhuman at vs tasks where it is unreliable"
concepts_introduced: ["jagged frontier", "benchmark (AI evaluation)", "capability cliff", "reasoning task (AI)", "spatial reasoning", "calibration (AI confidence)"]
prerequisites: ["3.2", "3.5"]
cross_refs: ["AlphaFold", "AlphaGo", "o3", "Gemini 2.5"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.9 — Hallucination — what it is and why it happens

```yaml
id: 3.9
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 3 — AI in Context
path:   m2-introduction-to-ai-systems/week-3/3-ai-in-context/3-9-hallucination-what-it-is-and-why-it-happens
one_liner: "Hallucination — what it is and why it happens"
concepts_introduced: ["factual hallucination", "attributional hallucination", "faithful-but-wrong hallucination", "RLHF (Reinforcement Learning from Human Feedback)", "overconfidence bias", "grounding", "provenance"]
prerequisites: ["3.2", "3.4", "3.6"]
cross_refs: ["RAG"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 3.10 — How to evaluate AI output across five task types — creative, factual, logical, ethical, coding

```yaml
id: 3.10
week: W03 (What AI Is — and Isn't)
course: M2 Introduction to AI Systems
module: 4 — Evaluating AI Output
path:   m2-introduction-to-ai-systems/week-3/4-evaluating-ai-output/3-10-how-to-evaluate-ai-output-across-five-task-types-creative-fa
one_liner: "How to evaluate AI output across five task types — creative, factual, logical, ethical, coding"
concepts_introduced: ["evaluation rubric (AI output)", "task type (AI evaluation)", "creative output evaluation", "factual output evaluation", "logical output evaluation", "ethical output evaluation", "coding output evaluation"]
prerequisites: ["3.2", "3.6", "3.8", "3.9"]
cross_refs: ["BLEU/ROUGE"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.1 — Foundation models — trained once at scale, usable for many tasks

```yaml
id: 4.1
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 1 — Foundation Models and Adaptation
path:   m2-introduction-to-ai-systems/week-4/1-foundation-models-and-adaptation/4-1-foundation-models-trained-once-at-scale-usable-for-many-task
one_liner: "Foundation models — trained once at scale, usable for many tasks"
concepts_introduced: ["foundation model", "multimodal model", "trained once at scale", "generalisation from broad training", "adaptation", "narrow AI vs foundation model"]
prerequisites: [3.2, 3.4, 3.5]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.2 — Fine-tuning — adapting a foundation model on domain-specific data

```yaml
id: 4.2
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 1 — Foundation Models and Adaptation
path:   m2-introduction-to-ai-systems/week-4/1-foundation-models-and-adaptation/4-2-fine-tuning-adapting-a-foundation-model-on-domain-specific-d
one_liner: "Fine-tuning — adapting a foundation model on domain-specific data"
concepts_introduced: ["fine-tuning", "pre-trained model", "domain-specific data", "labelled data", "zero-shot use", "catastrophic forgetting", "hallucination", "overfitting", "epoch"]
prerequisites: [4.1]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.3 — Retrieval-Augmented Generation (RAG) — giving AI access to external knowledge at query time

```yaml
id: 4.3
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 2 — Retrieval, Agents and Tools
path:   m2-introduction-to-ai-systems/week-4/2-retrieval-agents-and-tools/4-3-retrieval-augmented-generation-rag-giving-ai-access-to-exter
one_liner: "Retrieval-Augmented Generation (RAG) — giving AI access to external knowledge at query time"
concepts_introduced: ["RAG (Retrieval-Augmented Generation)", "knowledge cutoff", "knowledge source", "query (user question)", "prompt augmentation", "retrieve-augment-generate pipeline"]
prerequisites: ["4.1", "4.2"]
cross_refs: ["RAG pipeline", "vector database", "embeddings", "similarity search", "chunking"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.4 — Agents — LLM plus memory, tools, and a planning loop (conceptual introduction)

```yaml
id: 4.4
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 2 — Retrieval, Agents and Tools
path:   m2-introduction-to-ai-systems/week-4/2-retrieval-agents-and-tools/4-4-agents-llm-plus-memory-tools-and-a-planning-loop-conceptual
one_liner: "Agents — LLM plus memory, tools, and a planning loop (conceptual introduction)"
concepts_introduced: ["agent", "LLM core (the reasoning engine)", "memory (short-term and long-term)", "tool", "planning loop", "observe-think-act cycle", "agentic workflow"]
prerequisites: [4.1, 4.2, 4.3]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.5 — Tool use — AI calling search, calculator, and code runner

```yaml
id: 4.5
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 2 — Retrieval, Agents and Tools
path:   m2-introduction-to-ai-systems/week-4/2-retrieval-agents-and-tools/4-5-tool-use-ai-calling-search-calculator-and-code-runner
one_liner: "Tool use — AI calling search, calculator, and code runner"
concepts_introduced: ["tool-use cycle", "tool request", "search tool", "calculator tool", "code runner", "routing software layer", "tool availability scope"]
prerequisites: ["4.4"]
cross_refs: ["4.3"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.6 — Multimodal AI — working with text, image, audio, and video in one system

```yaml
id: 4.6
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 3 — Multimodal AI
path:   m2-introduction-to-ai-systems/week-4/3-multimodal-ai/4-6-multimodal-ai-working-with-text-image-audio-and-video-in-one
one_liner: "Multimodal AI — working with text, image, audio, and video in one system"
concepts_introduced: ["modality", "unimodal", "shared representation space", "encoder", "video understanding", "multimodal AI system"]
prerequisites: [4.1, 4.2, 4.3, 4.4, 4.5]
cross_refs: ["shared representation space", "encoder", "modality fusion", "GPT-4o", "document intelligence", "accessibility AI"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.7 — How to compare two AI tools — designing a measurable evaluation rubric

```yaml
id: 4.7
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 4 — Comparing and Evaluating AI Tools
path:   m2-introduction-to-ai-systems/week-4/4-comparing-and-evaluating-ai-tools/4-7-how-to-compare-two-ai-tools-designing-a-measurable-evaluatio
one_liner: "How to compare two AI tools — designing a measurable evaluation rubric"
concepts_introduced: ["evaluation rubric", "criteria", "performance levels", "descriptors", "weighting", "test protocol"]
prerequisites: [3.9, 3.1, 4.1, 4.4, 4.5, 4.6]
cross_refs: ["rubric", "criteria", "descriptors", "weighting", "test protocol", "hallucination"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 4.8 — Presenting a findings-based recommendation — evidence, not opinion

```yaml
id: 4.8
week: W04 (The 2026 AI Stack)
course: M2 Introduction to AI Systems
module: 4 — Comparing and Evaluating AI Tools
path:   m2-introduction-to-ai-systems/week-4/4-comparing-and-evaluating-ai-tools/4-8-presenting-a-findings-based-recommendation-evidence-not-opin
one_liner: "Presenting a findings-based recommendation — evidence, not opinion"
concepts_introduced: ["evidence-based recommendation", "findings summary", "evidence anchor", "recommendation statement", "trade-off (in evaluation context)", "evaluation limitations"]
prerequisites: [4.7]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.1 — Real AI failure cases — healthcare misdiagnosis, hiring bias, deepfake harm

```yaml
id: 5.1
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 1 — AI Failures and Why They Happen
path:   m2-introduction-to-ai-systems/week-5/1-ai-failures-and-why-they-happen/5-1-real-ai-failure-cases-healthcare-misdiagnosis-hiring-bias-de
one_liner: "Real AI failure cases — healthcare misdiagnosis, hiring bias, deepfake harm"
concepts_introduced: ["AI failure", "training data bias", "diagnostic AI", "algorithmic bias", "deepfake", "deep learning"]
prerequisites: ["1.3", "2.6", "3.8", "3.9"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.2 — Hallucination — why AI states falsehoods confidently

```yaml
id: 5.2
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 1 — AI Failures and Why They Happen
path:   m2-introduction-to-ai-systems/week-5/1-ai-failures-and-why-they-happen/5-2-hallucination-why-ai-states-falsehoods-confidently
one_liner: "Hallucination — why AI states falsehoods confidently"
concepts_introduced: ["next-token prediction", "intrinsic hallucination", "extrinsic hallucination", "calibration", "confidence problem"]
prerequisites: [3.9, 5.1]
cross_refs: ["LLM"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.3 — Data bias — how biased training data produces biased model output

```yaml
id: 5.3
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 1 — AI Failures and Why They Happen
path:   m2-introduction-to-ai-systems/week-5/1-ai-failures-and-why-they-happen/5-3-data-bias-how-biased-training-data-produces-biased-model-out
one_liner: "Data bias — how biased training data produces biased model output"
concepts_introduced: ["data bias", "historical bias", "representation bias", "measurement bias", "GIGO", "objectivity illusion", "disaggregated metrics"]
prerequisites: ["5.1", "5.2"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.4 — The four pillars — fairness, transparency, accountability, harm prevention

```yaml
id: 5.4
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 2 — Ethical Principles
path:   m2-introduction-to-ai-systems/week-5/2-ethical-principles/5-4-the-four-pillars-fairness-transparency-accountability-harm-p
one_liner: "The four pillars — fairness, transparency, accountability, harm prevention"
concepts_introduced: ["fairness in AI", "proxy discrimination", "transparency in AI", "explainability", "discoverability", "black box problem", "XAI (Explainable AI)", "accountability in AI", "accountability gap", "harm prevention", "risk assessment", "categories of AI harm"]
prerequisites: [5.1, 5.2, 5.3]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.5 — Red-teaming — systematically testing your own system for failure before deployment

```yaml
id: 5.5
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 3 — Safety Practices
path:   m2-introduction-to-ai-systems/week-5/3-safety-practices/5-5-red-teaming-systematically-testing-your-own-system-for-failu
one_liner: "Red-teaming — systematically testing your own system for failure before deployment"
concepts_introduced: ["red-teaming", "adversarial testing", "red team vs blue team", "jailbreak", "data leakage", "data poisoning", "testing timeline", "test-find-fix-regression loop"]
prerequisites: ["5.1", "5.2", "5.3", "5.4"]
cross_refs: ["LLM"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.6 — Prompt injection — how attackers manipulate AI through crafted inputs

```yaml
id: 5.6
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 3 — Safety Practices
path:   m2-introduction-to-ai-systems/week-5/3-safety-practices/5-6-prompt-injection-how-attackers-manipulate-ai-through-crafted
one_liner: "Prompt injection — how attackers manipulate AI through crafted inputs"
concepts_introduced: ["prompt injection", "system prompt vs user input", "shared context window", "direct vs indirect prompt injection", "ignore-previous-instructions attack", "injection impacts (unauthorized output, data leakage, unintended actions)"]
prerequisites: ["3.2", "3.4", "5.4", "5.5"]
cross_refs: ["LLM"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.7 — EU AI Act — risk tiers (unacceptable / high / limited / minimal risk)

```yaml
id: 5.7
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-7-eu-ai-act-risk-tiers-unacceptable-high-limited-minimal-risk
one_liner: "EU AI Act — risk tiers (unacceptable / high / limited / minimal risk)"
concepts_introduced: ["EU AI Act", "risk-based regulation", "unacceptable-risk tier (banned uses)", "high-risk systems", "conformity assessment", "limited-risk transparency obligation", "minimal-risk tier"]
prerequisites: ["5.1", "5.4", "5.5"]
cross_refs: ["EU AI Act"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.8 — EU AI Act — obligations for high-risk systems (documentation, testing, human oversight)

```yaml
id: 5.8
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-8-eu-ai-act-obligations-for-high-risk-systems-documentation-te
one_liner: "EU AI Act — obligations for high-risk systems (documentation, testing, human oversight)"
concepts_introduced: ["high-risk obligations (documentation, testing, human oversight, etc.)", "provider vs deployer", "conformity assessment", "technical documentation", "robustness", "data governance", "record-keeping / logging"]
prerequisites: ["5.7", "5.4", "5.5", "5.3", "5.6", "5.1"]
cross_refs: ["5.7", "5.4", "5.5", "5.3", "5.6", "5.9", "5.10", "5.11", "5.12"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.9 — EU AI Act — prohibited uses including social scoring and real-time biometric surveillance

```yaml
id: 5.9
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-9-eu-ai-act-prohibited-uses-including-social-scoring-and-real
one_liner: "EU AI Act — prohibited uses including social scoring and real-time biometric surveillance"
concepts_introduced: ["prohibited uses (Article 5)", "social scoring by governments", "real-time biometric surveillance", "narrow biometric-ID exceptions", "other Article 5 bans"]
prerequisites: ["5.7", "5.4", "5.1"]
cross_refs: ["EU AI Act risk tiers (5.7)", "high-risk obligations (5.8)", "four pillars (5.4)", "AI failure cases (5.1)"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.10 — NIST AI Risk Management Framework — four functions: Govern, Map, Measure, Manage

```yaml
id: 5.10
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-10-nist-ai-risk-management-framework-four-functions-govern-map
one_liner: "NIST AI Risk Management Framework — four functions: Govern, Map, Measure, Manage"
concepts_introduced: ["NIST AI Risk Management Framework", "AI RMF", "voluntary framework", "Govern function", "Map function", "Measure function", "Manage function", "risk as chance times severity"]
prerequisites: ["5.7", "5.4", "5.1"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 5.11 — White House Executive Order on AI (2023) — key provisions

```yaml
id: 5.11
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-11-white-house-executive-order-on-ai-2023-key-provisions
one_liner: "White House Executive Order on AI (2023) — key provisions"
concepts_introduced: ["executive order (EO 14110)", "dual-use foundation models", "mandatory safety testing", "civil-rights protections in AI", "federal-agency AI governance guidance", "revocability of executive action"]
prerequisites: ["5.4", "5.5", "5.7", "5.10"]
cross_refs: ["EU AI Act", "NIST AI RMF", "red-teaming", "four pillars"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.12 — India AI governance — MEITY advisory guidelines

```yaml
id: 5.12
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-12-india-ai-governance-meity-advisory-guidelines
one_liner: "India AI governance — MEITY advisory guidelines"
concepts_introduced: ["MEITY (Ministry of Electronics and Information Technology)", "government advisory (soft law vs statute)", "intermediary/online platform", "labeling of unreliable/AI-generated output", "platform due diligence", "directionally aligned with global frameworks"]
prerequisites: ["5.4", "5.7", "5.10", "5.11"]
cross_refs: ["EU AI Act", "NIST AI RMF", "EO 14110", "four pillars"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 5.13 — Knowing which governance framework applies to your system

```yaml
id: 5.13
week: W05 (AI Ethics, Safety and Governance)
course: M2 Introduction to AI Systems
module: 4 — Governance Frameworks
path:   m2-introduction-to-ai-systems/week-5/4-governance-frameworks/5-13-knowing-which-governance-framework-applies-to-your-system
one_liner: "Knowing which governance framework applies to your system"
concepts_introduced: ["governance as a design-time engineering decision", "jurisdiction-driven applicability", "EU AI Act extraterritorial reach (output used in EU)", "risk-tier classification as the second filter", "frameworks are non-exclusive (multiple apply at once)"]
prerequisites: ["5.4", "5.7", "5.10", "5.11", "5.12"]
cross_refs: ["5.4", "5.5", "5.7", "5.8", "5.10", "5.11", "5.12"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.1 — Why every AI system is built on numbers

```yaml
id: 6.1
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 1 — Why Numbers Matter in AI
path:   m3-applied-maths-for-ai/week-6/1-why-numbers-matter-in-ai/6-1-why-every-ai-system-is-built-on-numbers
one_liner: "Why every AI system is built on numbers"
concepts_introduced: ["binary representation", "data-to-number conversion", "pixels as numbers", "parameters", "number as universal data language", "pattern-finding through arithmetic"]
prerequisites: ["1.1", "1.2", "2.1", "3.1", "5.1"]
cross_refs: ["TensorFlow Embedding Projector", "LLM", "AI Systems Essay A2"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.2 — Scalars — a single number representing one property

```yaml
id: 6.2
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 2 — Scalars, Vectors and Matrices
path:   m3-applied-maths-for-ai/week-6/2-scalars-vectors-and-matrices/6-2-scalars-a-single-number-representing-one-property
one_liner: "Scalars — a single number representing one property"
concepts_introduced: ["scalar", "magnitude", "loss value", "learning rate", "hyperparameter", "pixel brightness as scalar"]
prerequisites: ["6.1"]
cross_refs: ["vectors (6.3)", "matrices (6.4)"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.3 — Vectors — a list of numbers representing multiple properties at once

```yaml
id: 6.3
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 2 — Scalars, Vectors and Matrices
path:   m3-applied-maths-for-ai/week-6/2-scalars-vectors-and-matrices/6-3-vectors-a-list-of-numbers-representing-multiple-properties-a
one_liner: "Vectors — a list of numbers representing multiple properties at once"
concepts_introduced: ["vector", "dimension", "feature", "feature vector", "consistent position-to-property mapping", "ordered list of scalars"]
prerequisites: ["6.1", "6.2"]
cross_refs: ["6.4 matrices", "6.5 geometric view of vectors", "6.6 dot product"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.4 — Matrices — grids of numbers and how AI uses them

```yaml
id: 6.4
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 2 — Scalars, Vectors and Matrices
path:   m3-applied-maths-for-ai/week-6/2-scalars-vectors-and-matrices/6-4-matrices-grids-of-numbers-and-how-ai-uses-them
one_liner: "Matrices — grids of numbers and how AI uses them"
concepts_introduced: ["matrix", "row", "column", "cell", "weight_matrix", "image_pixel_grid", "rows_x_columns_notation"]
prerequisites: ["6.1", "6.2", "6.3"]
cross_refs: ["neural_networks", "linear_algebra"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.5 — A vector as a point in space — music taste described as three numbers

```yaml
id: 6.5
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 2 — Scalars, Vectors and Matrices
path:   m3-applied-maths-for-ai/week-6/2-scalars-vectors-and-matrices/6-5-a-vector-as-a-point-in-space-music-taste-described-as-three
one_liner: "A vector as a point in space — music taste described as three numbers"
concepts_introduced: ["coordinate", "axis", "origin", "coordinate_plane", "point_in_space", "3D_coordinate_space", "taste_space", "distance_as_similarity", "coordinate_space", "n_dimensional_space", "neighbourhood"]
prerequisites: ["6.1", "6.2", "6.3", "6.4"]
cross_refs: ["recommendation_systems", "vector_search", "embedding_spaces"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.6 — Dot product as similarity — vectors pointing the same direction score high

```yaml
id: 6.6
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 3 — Similarity and Meaning
path:   m3-applied-maths-for-ai/week-6/3-similarity-and-meaning/6-6-dot-product-as-similarity-vectors-pointing-the-same-directio
one_liner: "Dot product as similarity — vectors pointing the same direction score high"
concepts_introduced: ["dot_product", "position_multiply_sum", "same_direction_score", "opposite_direction_negative", "perpendicular_zero", "similarity_score", "magnitude_sensitivity"]
prerequisites: ["6.2", "6.3", "6.4", "6.5"]
cross_refs: ["recommendation_systems", "vector_search"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.7 — Embeddings — turning words into vectors that capture meaning

```yaml
id: 6.7
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 3 — Similarity and Meaning
path:   m3-applied-maths-for-ai/week-6/3-similarity-and-meaning/6-7-embeddings-turning-words-into-vectors-that-capture-meaning
one_liner: "Embeddings — turning words into vectors that capture meaning"
concepts_introduced: ["embedding", "one-hot_encoding", "distributional_hypothesis", "Word2Vec", "learned_representation", "embedding_space", "dense_vector", "pre-trained_embedding"]
prerequisites: ["6.3", "6.5", "6.6"]
cross_refs: ["Word2Vec", "GloVe", "FastText", "TensorFlow Embedding Projector"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.8 — Why 'king − man + woman ≈ queen' works

```yaml
id: 6.8
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 3 — Similarity and Meaning
path:   m3-applied-maths-for-ai/week-6/3-similarity-and-meaning/6-8-why-king-man-woman-queen-works
one_liner: "Why 'king − man + woman ≈ queen' works"
concepts_introduced: ["analogy_direction", "gender_direction", "vector_arithmetic", "approximate_equality", "nearest_neighbour_answer", "embedding_offset"]
prerequisites: ["6.3", "6.5", "6.6", "6.7"]
cross_refs: ["Word2Vec", "GloVe", "TensorFlow Embedding Projector"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.9 — Cosine similarity — measuring the angle between two meaning-vectors

```yaml
id: 6.9
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 3 — Similarity and Meaning
path:   m3-applied-maths-for-ai/week-6/3-similarity-and-meaning/6-9-cosine-similarity-measuring-the-angle-between-two-meaning-ve
one_liner: "Cosine similarity — measuring the angle between two meaning-vectors"
concepts_introduced: ["cosine_similarity", "angle_between_vectors", "theta", "vector_magnitude", "normalisation", "cosine_formula", "perpendicular_vectors", "cosine_distance"]
prerequisites: ["6.6", "6.5", "6.3"]
cross_refs: ["Word2Vec", "scikit-learn", "Keras"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 6.10 — Using the TensorFlow Embedding Projector to explore word clusters

```yaml
id: 6.10
week: W06 (Numbers, Vectors and Meaning)
course: M3 Applied Maths for AI
module: 4 — Hands-On Exploration
path:   m3-applied-maths-for-ai/week-6/4-hands-on-exploration/6-10-using-the-tensorflow-embedding-projector-to-explore-word-clu
one_liner: "Using the TensorFlow Embedding Projector to explore word clusters"
concepts_introduced: ["TensorFlow Embedding Projector", "cluster of word-vectors", "dimensionality reduction as squashing to 2D/3D", "reading nearest neighbours in the Projector", "search-and-highlight interaction"]
prerequisites: ["6.3", "6.5", "6.6", "6.7", "6.8", "6.9"]
cross_refs: ["Embeddings", "Vectors", "Cosine similarity", "Nearest neighbour", "PCA/t-SNE (deferred)"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 7.1 — Probability basics — likelihood, events, outcomes

```yaml
id: 7.1
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 1 — Probability Foundations
path:   m3-applied-maths-for-ai/week-7/1-probability-foundations/7-1-probability-basics-likelihood-events-outcomes
one_liner: "Probability basics — likelihood, events, outcomes"
concepts_introduced: ["probability", "outcome", "sample space", "event", "complement", "equally-likely outcomes"]
prerequisites: [1.3, 1.4, 3.4]
cross_refs: ["LLMs"]
expected_delivery_minutes: null
status: "module_passed"
```

## 7.2 — Conditional probability — P(A given B)

```yaml
id: 7.2
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 1 — Probability Foundations
path:   m3-applied-maths-for-ai/week-7/1-probability-foundations/7-2-conditional-probability-p-a-given-b
one_liner: "Conditional probability — P(A given B)"
concepts_introduced: ["conditional probability", "P(A|B) notation", "reduced sample space", "joint probability", "conditional probability formula"]
prerequisites: ["7.1"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.3 — Bayes' theorem intuition — updating belief with new evidence

```yaml
id: 7.3
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 1 — Probability Foundations
path:   m3-applied-maths-for-ai/week-7/1-probability-foundations/7-3-bayes-theorem-intuition-updating-belief-with-new-evidence
one_liner: "Bayes' theorem intuition — updating belief with new evidence"
concepts_introduced: ["prior", "posterior", "likelihood", "Bayes' theorem", "Bayesian updating"]
prerequisites: ["7.1", "7.2"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.4 — Why LLMs output a probability distribution, not a single fixed answer

```yaml
id: 7.4
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 2 — How LLMs Use Probability
path:   m3-applied-maths-for-ai/week-7/2-how-llms-use-probability/7-4-why-llms-output-a-probability-distribution-not-a-single-fixe
one_liner: "Why LLMs output a probability distribution, not a single fixed answer"
concepts_introduced: ["token", "vocabulary", "probability distribution over vocabulary", "softmax", "sampling", "stochastic system", "deterministic system", "generation loop"]
prerequisites: ["7.1", "7.2", "7.3"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.5 — Temperature — low picks the most likely token, high introduces variation

```yaml
id: 7.5
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 2 — How LLMs Use Probability
path:   m3-applied-maths-for-ai/week-7/2-how-llms-use-probability/7-5-temperature-low-picks-the-most-likely-token-high-introduces
one_liner: "Temperature — low picks the most likely token, high introduces variation"
concepts_introduced: ["temperature", "sharp distribution", "flat distribution", "temperature 0", "reliability vs variety trade-off"]
prerequisites: [7.4, 7.1]
cross_refs: ["LLM API parameters", "ChatGPT", "temperature setting in developer tools"]
expected_delivery_minutes: null
status: "module_passed"
```

## 7.6 — Mean, median, mode — measuring consistency across AI runs

```yaml
id: 7.6
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-6-mean-median-mode-measuring-consistency-across-ai-runs
one_liner: "Mean, median, mode — measuring consistency across AI runs"
concepts_introduced: ["mean", "median", "mode", "measures of central tendency", "descriptive statistics", "outlier", "mode as categorical measure"]
prerequisites: ["7.4", "7.5"]
cross_refs: ["AI evaluation", "temperature 0 vs temperature 1", "chatbot consistency testing"]
expected_delivery_minutes: null
status: "module_passed"
```

## 7.7 — Accuracy — what proportion of outputs were correct

```yaml
id: 7.7
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-7-accuracy-what-proportion-of-outputs-were-correct
one_liner: "Accuracy — what proportion of outputs were correct"
concepts_introduced: ["accuracy", "correct prediction", "incorrect prediction", "accuracy formula", "classification task", "labelled dataset", "test set", "accuracy limitations", "imbalanced dataset", "dummy baseline"]
prerequisites: ["7.1", "7.6"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.8 — Precision — of the things the model flagged, how many were actually correct

```yaml
id: 7.8
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-8-precision-of-the-things-the-model-flagged-how-many-were-actu
one_liner: "Precision — of the things the model flagged, how many were actually correct"
concepts_introduced: ["precision", "true positive", "false positive", "positive prediction", "negative prediction", "flagged", "false alarm", "precision formula"]
prerequisites: ["7.7"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.9 — Recall — of all the things that were correct, how many did the model find

```yaml
id: 7.9
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-9-recall-of-all-the-things-that-were-correct-how-many-did-the
one_liner: "Recall — of all the things that were correct, how many did the model find"
concepts_introduced: ["recall", "false negative", "precision-recall trade-off", "coverage of true positives"]
prerequisites: [7.7, 7.8]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.10 — Confusion matrix — why 95% accuracy can still mean frequent failure

```yaml
id: 7.10
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-10-confusion-matrix-why-95-accuracy-can-still-mean-frequent-fai
one_liner: "Confusion matrix — why 95% accuracy can still mean frequent failure"
concepts_introduced: ["confusion matrix", "true negative", "accuracy paradox", "off-diagonal cells", "ground truth", "positive class"]
prerequisites: ["7.7", "7.8", "7.9"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 7.11 — Interpreting AI output variation — what it tells you about reliability

```yaml
id: 7.11
week: W07 (Probability, Statistics and AI Confidence)
course: M3 Applied Maths for AI
module: 3 — Measuring AI Performance
path:   m3-applied-maths-for-ai/week-7/3-measuring-ai-performance/7-11-interpreting-ai-output-variation-what-it-tells-you-about-rel
one_liner: "Interpreting AI output variation — what it tells you about reliability"
concepts_introduced: ["output variation", "temperature-adjusted variation reading", "four-quadrant reliability framework", "deterministic task", "open-ended task", "variation-based reliability measurement", "consistency vs correctness distinction"]
prerequisites: ["7.4", "7.5", "7.6", "7.7", "7.8", "7.9", "7.10"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 8.1 — Embedding explorer — comparing domain-specific word clusters in 2D

```yaml
id: 8.1
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 1 — Embeddings in Practice
path:   m3-applied-maths-for-ai/week-8/1-embeddings-in-practice/8-1-embedding-explorer-comparing-domain-specific-word-clusters-i
one_liner: "Embedding explorer — comparing domain-specific word clusters in 2D"
concepts_introduced: ["domain-specific words", "2D projection", "cluster", "outlier", "comparing word clusters across domains", "nearest neighbors (in the Projector)"]
prerequisites: ["6.7", "6.5", "6.10"]
cross_refs: ["Embeddings", "Visualization"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.2 — Similarity scoring — computing cosine similarity between sentence pairs

```yaml
id: 8.2
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 1 — Embeddings in Practice
path:   m3-applied-maths-for-ai/week-8/1-embeddings-in-practice/8-2-similarity-scoring-computing-cosine-similarity-between-sente
one_liner: "Similarity scoring — computing cosine similarity between sentence pairs"
concepts_introduced: ["sentence embedding", "cosine similarity score (0–1 scale)", "similarity by angle/direction not length", "pre-computed vectors", "same meaning vs same words", "similarity ranking of sentence pairs"]
prerequisites: ["6.5", "6.6", "6.7", "6.9", "8.1"]
cross_refs: ["Embeddings", "Similarity"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.3 — Precision vs recall trade-off — when does recall matter more than precision?

```yaml
id: 8.3
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 2 — Evaluation Metrics in Depth
path:   m3-applied-maths-for-ai/week-8/2-evaluation-metrics-in-depth/8-3-precision-vs-recall-trade-off-when-does-recall-matter-more-t
one_liner: "Precision vs recall trade-off — when does recall matter more than precision?"
concepts_introduced: ["precision", "recall", "false positive", "false negative", "precision-recall trade-off", "threshold", "cost-of-error framing"]
prerequisites: []
cross_refs: ["Evaluation", "Classification metrics"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.4 — F1 score — balancing precision and recall into one metric

```yaml
id: 8.4
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 2 — Evaluation Metrics in Depth
path:   m3-applied-maths-for-ai/week-8/2-evaluation-metrics-in-depth/8-4-f1-score-balancing-precision-and-recall-into-one-metric
one_liner: "F1 score — balancing precision and recall into one metric"
concepts_introduced: ["F1 score", "harmonic mean", "arithmetic mean (plain average) as contrast", "single-number metric vs reporting precision/recall separately", "balance-vs-imbalance penalty"]
prerequisites: ["8.3"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.5 — Computing accuracy, precision, recall, and F1 by hand from a confusion matrix

```yaml
id: 8.5
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 2 — Evaluation Metrics in Depth
path:   m3-applied-maths-for-ai/week-8/2-evaluation-metrics-in-depth/8-5-computing-accuracy-precision-recall-and-f1-by-hand-from-a-co
one_liner: "Computing accuracy, precision, recall, and F1 by hand from a confusion matrix"
concepts_introduced: ["confusion matrix (2x2 TP/FP/FN/TN grid)", "true negative", "accuracy", "accuracy formula (TP+TN)/(TP+TN+FP+FN)", "by-hand metric computation from a confusion matrix", "all-cells-sum sanity check"]
prerequisites: ["8.3", "8.4"]
cross_refs: ["Evaluation", "Classification metrics"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.6 — Calibration — does the model's stated confidence match its actual accuracy?

```yaml
id: 8.6
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 3 — Model Calibration
path:   m3-applied-maths-for-ai/week-8/3-model-calibration/8-6-calibration-does-the-models-stated-confidence-match-its-actu
one_liner: "Calibration — does the model's stated confidence match its actual accuracy?"
concepts_introduced: ["confidence (model's stated certainty per prediction)", "calibration / well-calibrated", "grouping predictions by confidence (bucketing)", "reliability diagram (confidence-vs-accuracy plot)", "overconfidence (below diagonal)", "underconfidence (above diagonal)", "accuracy-vs-calibration distinction"]
prerequisites: ["8.5", "8.3"]
cross_refs: ["Evaluation", "Model trust / confidence scores"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 8.7 — Designing a domain evaluation plan — metric, test set size, pass/fail threshold

```yaml
id: 8.7
week: W08 (Applied Maths Lab)
course: M3 Applied Maths for AI
module: 4 — Planning Your Capstone Evaluation
path:   m3-applied-maths-for-ai/week-8/4-planning-your-capstone-evaluation/8-7-designing-a-domain-evaluation-plan-metric-test-set-size-pass
one_liner: "Designing a domain evaluation plan — metric, test set size, pass/fail threshold"
concepts_introduced: ["evaluation plan", "matching metric to cost of errors in your domain", "test set size for a trustworthy (non-noisy) score", "held-out / representative test set", "pass/fail threshold set in advance", "tying the threshold to task stakes"]
prerequisites: ["8.3", "8.4", "8.5", "8.6"]
cross_refs: ["8.1", "8.2"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 9.1 — System 1 thinking — fast, instinctive, automatic

```yaml
id: 9.1
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 1 — How Humans Think
path:   m4-human-cognition-and-ai-oversight/week-9/1-how-humans-think/9-1-system-1-thinking-fast-instinctive-automatic
one_liner: "System 1 thinking — fast, instinctive, automatic"
concepts_introduced: ["System 1 thinking", "cognitive heuristics", "automatic processing", "fast thinking", "pattern matching"]
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.2 — System 2 thinking — slow, deliberate, effortful

```yaml
id: 9.2
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 1 — How Humans Think
path:   m4-human-cognition-and-ai-oversight/week-9/1-how-humans-think/9-2-system-2-thinking-slow-deliberate-effortful
one_liner: "System 2 thinking — slow, deliberate, effortful"
concepts_introduced: ["System 2 thinking", "working memory", "cognitive load", "cognitive fatigue", "serial processing", "deliberate processing", "cognitive capacity"]
prerequisites: [9.1]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.3 — Confirmation bias — seeking information that confirms existing beliefs

```yaml
id: 9.3
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 2 — Cognitive Biases and AI
path:   m4-human-cognition-and-ai-oversight/week-9/2-cognitive-biases-and-ai/9-3-confirmation-bias-seeking-information-that-confirms-existing
one_liner: "Confirmation bias — seeking information that confirms existing beliefs"
concepts_introduced: ["confirmation bias", "selective search", "biased interpretation", "selective memory", "motivated reasoning", "cognitive economy", "Wason 2-4-6 task"]
prerequisites: ["9.1", "9.2"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.4 — Anchoring bias — over-relying on the first piece of information received

```yaml
id: 9.4
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 2 — Cognitive Biases and AI
path:   m4-human-cognition-and-ai-oversight/week-9/2-cognitive-biases-and-ai/9-4-anchoring-bias-over-relying-on-the-first-piece-of-informatio
one_liner: "Anchoring bias — over-relying on the first piece of information received"
concepts_introduced: ["anchoring bias", "anchor", "adjustment heuristic", "insufficient adjustment", "primacy effect", "first-pass anchor"]
prerequisites: ["9.1", "9.2", "9.3"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.5 — Automation bias — trusting automated systems over human judgment

```yaml
id: 9.5
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 2 — Cognitive Biases and AI
path:   m4-human-cognition-and-ai-oversight/week-9/2-cognitive-biases-and-ai/9-5-automation-bias-trusting-automated-systems-over-human-judgme
one_liner: "Automation bias — trusting automated systems over human judgment"
concepts_introduced: ["automation bias", "omission error", "commission error", "cognitive offloading", "complacency", "situational awareness", "calibrated trust", "alert fatigue"]
prerequisites: ["9.1", "9.2", "9.3", "9.4"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.6 — How human biases get encoded into AI training data

```yaml
id: 9.6
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 2 — Cognitive Biases and AI
path:   m4-human-cognition-and-ai-oversight/week-9/2-cognitive-biases-and-ai/9-6-how-human-biases-get-encoded-into-ai-training-data
one_liner: "How human biases get encoded into AI training data"
concepts_introduced: ["encoding (bias into data)", "training data", "labelling", "labelling bias", "historical bias", "selection bias", "representation bias", "feedback loop", "feedback loop amplification", "aggregation bias"]
prerequisites: ["9.3", "9.4", "9.5"]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.7 — The Judgment Framework — Q1: What is the cost of this being wrong?

```yaml
id: 9.7
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 3 — The Judgment Framework
path:   m4-human-cognition-and-ai-oversight/week-9/3-the-judgment-framework/9-7-the-judgment-framework-q1-what-is-the-cost-of-this-being-wro
one_liner: "The Judgment Framework — Q1: What is the cost of this being wrong?"
concepts_introduced: []
prerequisites: [9.1, 9.2, 9.5, 9.6]
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.8 — The Judgment Framework — Q2: Can I verify this without the AI?

```yaml
id: 9.8
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 3 — The Judgment Framework
path:   m4-human-cognition-and-ai-oversight/week-9/3-the-judgment-framework/9-8-the-judgment-framework-q2-can-i-verify-this-without-the-ai
one_liner: "The Judgment Framework — Q2: Can I verify this without the AI?"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.9 — The Judgment Framework — Q3: Who is accountable if this fails?

```yaml
id: 9.9
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 3 — The Judgment Framework
path:   m4-human-cognition-and-ai-oversight/week-9/3-the-judgment-framework/9-9-the-judgment-framework-q3-who-is-accountable-if-this-fails
one_liner: "The Judgment Framework — Q3: Who is accountable if this fails?"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.10 — Acceptable error — defining the failure threshold tolerable for a use case

```yaml
id: 9.10
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 4 — Defining Safe Boundaries
path:   m4-human-cognition-and-ai-oversight/week-9/4-defining-safe-boundaries/9-10-acceptable-error-defining-the-failure-threshold-tolerable-fo
one_liner: "Acceptable error — defining the failure threshold tolerable for a use case"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "module_passed"
```

## 9.11 — High-stakes domains where AI must not have the final word — medical, legal, safety

```yaml
id: 9.11
week: W09 (Human Judgment and AI Oversight)
course: M4 Human Cognition and AI Oversight
module: 4 — Defining Safe Boundaries
path:   m4-human-cognition-and-ai-oversight/week-9/4-defining-safe-boundaries/9-11-high-stakes-domains-where-ai-must-not-have-the-final-word-me
one_liner: "High-stakes domains where AI must not have the final word — medical, legal, safety"
concepts_introduced: ["high-stakes domain", "final word (human final authority)", "decision support vs decision-maker", "meaningful oversight vs nominal oversight", "accountability gap", "high-risk AI (EU AI Act classification)", "explainability / XAI"]
prerequisites: ["9.7", "9.9", "9.10"]
cross_refs: ["EU AI Act", "HITL (human-in-the-loop)", "COMPAS", "Boeing 737 MAX MCAS"]
expected_delivery_minutes: null
status: "module_passed"
```

## 10.1 — Case study: AI in college admissions — where was the human override point missed?

```yaml
id: 10.1
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 1 — Case Studies
path:   m4-human-cognition-and-ai-oversight/week-10/1-case-studies/10-1-case-study-ai-in-college-admissions-where-was-the-human-over
one_liner: "Case study: AI in college admissions — where was the human override point missed?"
concepts_introduced: ["holistic review", "pipeline", "human override point", "calibration", "holistic review collapse", "training data", "override as theater"]
prerequisites: ["9.5", "9.7", "9.8", "9.9", "9.10", "9.11"]
cross_refs: ["9.6"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 10.2 — Case study: Automated medical triage — what failure mode was tolerated?

```yaml
id: 10.2
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 1 — Case Studies
path:   m4-human-cognition-and-ai-oversight/week-10/1-case-studies/10-2-case-study-automated-medical-triage-what-failure-mode-was-to
one_liner: "Case study: Automated medical triage — what failure mode was tolerated?"
concepts_introduced: ["failure mode", "systematic under-scoring", "sub-group performance audit", "deterioration-contradiction alert", "evaluation gap", "override accountability", "post-market surveillance analogy"]
prerequisites: ["9.5", "9.7", "9.8", "9.9", "9.11", "10.1"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 10.3 — Case study: AI loan approval at scale — who was accountable?

```yaml
id: 10.3
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 1 — Case Studies
path:   m4-human-cognition-and-ai-oversight/week-10/1-case-studies/10-3-case-study-ai-loan-approval-at-scale-who-was-accountable
one_liner: "Case study: AI loan approval at scale — who was accountable?"
concepts_introduced: []
prerequisites: [9.5, 9.7, 9.9, 9.11, 10.1, 10.2]
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 10.4 — Automation complacency — how high accuracy makes humans less vigilant

```yaml
id: 10.4
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 2 — Automation Complacency
path:   m4-human-cognition-and-ai-oversight/week-10/2-automation-complacency/10-4-automation-complacency-how-high-accuracy-makes-humans-less-v
one_liner: "Automation complacency — how high accuracy makes humans less vigilant"
concepts_introduced: ["automation complacency", "vigilance", "vigilance decay", "accuracy paradox", "trust calibration", "out-of-the-loop performance decrement"]
prerequisites: ["10.1", "10.2", "10.3"]
cross_refs: ["aviation", "healthcare", "content moderation"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 10.5 — Designing human-in-the-loop checkpoints — when to require sign-off vs allow autonomous action

```yaml
id: 10.5
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 3 — Designing Human Oversight
path:   m4-human-cognition-and-ai-oversight/week-10/3-designing-human-oversight/10-5-designing-human-in-the-loop-checkpoints-when-to-require-sign
one_liner: "Designing human-in-the-loop checkpoints — when to require sign-off vs allow autonomous action"
concepts_introduced: ["human-in-the-loop (HITL)", "human-on-the-loop (HOTL)", "human-out-of-the-loop (HOOTL)", "checkpoint", "confidence routing", "escalation trigger", "checkpoint matrix"]
prerequisites: ["9.7", "9.8", "9.9", "9.10", "9.11", "10.1", "10.2", "10.3", "10.4"]
cross_refs: ["college admissions pipeline", "medical triage AI", "loan approval AI", "content moderation AI"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 10.6 — Identifying which components in your system need a mandatory human checkpoint

```yaml
id: 10.6
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 3 — Designing Human Oversight
path:   m4-human-cognition-and-ai-oversight/week-10/3-designing-human-oversight/10-6-identifying-which-components-in-your-system-need-a-mandatory
one_liner: "Identifying which components in your system need a mandatory human checkpoint"
concepts_introduced: ["component", "risk-tiered component mapping", "mandatory checkpoint", "optional checkpoint", "error budget", "baseline oversight assignment", "triggered oversight assignment"]
prerequisites: ["9.5", "9.7", "9.8", "9.9", "9.10", "9.11", "10.1", "10.3", "10.4", "10.5"]
cross_refs: ["medical triage AI", "AI loan approval pipeline", "EU AI Act Article 14"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 10.7 — Writing a post-mortem — what failed, why, who was accountable, what governance would have prevented it

```yaml
id: 10.7
week: W10 (Cognition in Practice — Case Studies)
course: M4 Human Cognition and AI Oversight
module: 4 — Post-Mortem Writing
path:   m4-human-cognition-and-ai-oversight/week-10/4-post-mortem-writing/10-7-writing-a-post-mortem-what-failed-why-who-was-accountable-wh
one_liner: "Writing a post-mortem — what failed, why, who was accountable, what governance would have prevented it"
concepts_introduced: ["post-mortem", "no-blame post-mortem", "proximate cause", "root cause", "post-incident review"]
prerequisites: [9.7, 9.9, 9.11, 10.1, 10.2, 10.3, 10.4, 10.5, 10.6]
cross_refs: [10.1, 10.2, 10.3]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 11.1 — Python's role — the orchestration layer connecting your specification to an AI system

```yaml
id: 11.1
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Getting Started with Python
path:   m5-python-for-ai-pe-vc-and-rag/week-11/1-getting-started-with-python/11-1-pythons-role-the-orchestration-layer-connecting-your-specifi
one_liner: "Python's role — the orchestration layer connecting your specification to an AI system"
concepts_introduced: ["orchestration layer", "four-layer model", "specification", "spec-first discipline", "API", "Python interpreted language", "golden rule"]
prerequisites: []
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 11.2 — Setting up Google Colab — no installation, runs in the browser

```yaml
id: 11.2
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Getting Started with Python
path:   m5-python-for-ai-pe-vc-and-rag/week-11/1-getting-started-with-python/11-2-setting-up-google-colab-no-installation-runs-in-the-browser
one_liner: "Setting up Google Colab — no installation, runs in the browser"
concepts_introduced: []
prerequisites: [11.1]
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.3 — Variables — naming and storing values

```yaml
id: 11.3
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Core Python Concepts
path:   m5-python-for-ai-pe-vc-and-rag/week-11/2-core-python-concepts/11-3-variables-naming-and-storing-values
one_liner: "Variables — naming and storing values"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.4 — Data types — string, integer, float, boolean

```yaml
id: 11.4
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Core Python Concepts
path:   m5-python-for-ai-pe-vc-and-rag/week-11/2-core-python-concepts/11-4-data-types-string-integer-float-boolean
one_liner: "Data types — string, integer, float, boolean"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.5 — If / else — writing decision logic in code

```yaml
id: 11.5
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Core Python Concepts
path:   m5-python-for-ai-pe-vc-and-rag/week-11/2-core-python-concepts/11-5-if-else-writing-decision-logic-in-code
one_liner: "If / else — writing decision logic in code"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.6 — For loops — repeating an action across a list

```yaml
id: 11.6
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Core Python Concepts
path:   m5-python-for-ai-pe-vc-and-rag/week-11/2-core-python-concepts/11-6-for-loops-repeating-an-action-across-a-list
one_liner: "For loops — repeating an action across a list"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.7 — Spec-first discipline — writing the plain-English specification before writing or prompting code

```yaml
id: 11.7
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Professional Coding Discipline
path:   m5-python-for-ai-pe-vc-and-rag/week-11/3-professional-coding-discipline/11-7-spec-first-discipline-writing-the-plain-english-specificatio
one_liner: "Spec-first discipline — writing the plain-English specification before writing or prompting code"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 11.8 — The golden rule — never run code you cannot explain line by line

```yaml
id: 11.8
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Professional Coding Discipline
path:   m5-python-for-ai-pe-vc-and-rag/week-11/3-professional-coding-discipline/11-8-the-golden-rule-never-run-code-you-cannot-explain-line-by-li
one_liner: "The golden rule — never run code you cannot explain line by line"
concepts_introduced: ["golden rule (full definition)", "explain line by line", "silent failure", "loud failure", "four-question test", "code ownership", "plausible-looking wrong code"]
prerequisites: ["11.7"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 11.9 — Edge cases — testing what happens at the boundary of expected input

```yaml
id: 11.9
week: W11 (Python Foundations I)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Professional Coding Discipline
path:   m5-python-for-ai-pe-vc-and-rag/week-11/3-professional-coding-discipline/11-9-edge-cases-testing-what-happens-at-the-boundary-of-expected
one_liner: "Edge cases — testing what happens at the boundary of expected input"
concepts_introduced: ["edge case", "boundary value", "equivalence class", "normal input", "empty input", "zero input", "off-by-one error", "test prediction", "minimum valid input", "maximum valid input"]
prerequisites: ["11.6", "11.7", "11.8"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.1 — Functions — named, reusable blocks of logic

```yaml
id: 12.1
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Functions and Data Structures
path:   m5-python-for-ai-pe-vc-and-rag/week-12/1-functions-and-data-structures/12-1-functions-named-reusable-blocks-of-logic
one_liner: "Functions — named, reusable blocks of logic"
concepts_introduced: ["Python function", "def keyword", "function call", "code reuse", "function naming", "indentation", "definition vs call"]
prerequisites: ["11.3", "11.4", "11.5", "11.6"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.2 — Parameters and return values — defining what goes in and what comes out

```yaml
id: 12.2
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Functions and Data Structures
path:   m5-python-for-ai-pe-vc-and-rag/week-12/1-functions-and-data-structures/12-2-parameters-and-return-values-defining-what-goes-in-and-what
one_liner: "Parameters and return values — defining what goes in and what comes out"
concepts_introduced: ["parameter", "argument", "positional parameter", "return value", "return keyword", "implicit None"]
prerequisites: [12.1, 11.3, 11.4]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.3 — One function = one job — the testable unit principle

```yaml
id: 12.3
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Functions and Data Structures
path:   m5-python-for-ai-pe-vc-and-rag/week-12/1-functions-and-data-structures/12-3-one-function-one-job-the-testable-unit-principle
one_liner: "One function = one job — the testable unit principle"
concepts_introduced: ["single responsibility principle", "one job per function", "code smell", "testable unit", "refactoring", "and naming heuristic"]
prerequisites: [12.1, 12.2]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.4 — Lists — storing and iterating over multiple values

```yaml
id: 12.4
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Functions and Data Structures
path:   m5-python-for-ai-pe-vc-and-rag/week-12/1-functions-and-data-structures/12-4-lists-storing-and-iterating-over-multiple-values
one_liner: "Lists — storing and iterating over multiple values"
concepts_introduced: ["list", "item", "element", "index", "square brackets", "len()", "append()", "method", "iterating", "for item in list pattern", "range(len()) pattern"]
prerequisites: ["12.1", "12.2", "12.3"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.5 — Dictionaries — key-value pairs for structured data

```yaml
id: 12.5
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Functions and Data Structures
path:   m5-python-for-ai-pe-vc-and-rag/week-12/1-functions-and-data-structures/12-5-dictionaries-key-value-pairs-for-structured-data
one_liner: "Dictionaries — key-value pairs for structured data"
concepts_introduced: ["dictionary", "key", "value", "key-value pair", "curly braces", "KeyError", "in keyword (membership test)", "dict.get()", "dict.values()", "dict.items()", "immutable", "TypeError"]
prerequisites: ["12.1", "12.2", "12.3", "12.4"]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.6 — Reading from a file — open, read lines, close

```yaml
id: 12.6
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Working with Files
path:   m5-python-for-ai-pe-vc-and-rag/week-12/2-working-with-files/12-6-reading-from-a-file-open-read-lines-close
one_liner: "Reading from a file — open, read lines, close"
concepts_introduced: ["file object", "open() function", "read mode", ".read()", ".readlines()", ".readline()", "with block", "FileNotFoundError"]
prerequisites: ["12.1", "12.4", "12.5"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.7 — Writing to a file — open, write, close

```yaml
id: 12.7
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Working with Files
path:   m5-python-for-ai-pe-vc-and-rag/week-12/2-working-with-files/12-7-writing-to-a-file-open-write-close
one_liner: "Writing to a file — open, write, close"
concepts_introduced: ["write mode ('w')", "append mode ('a')", ".write() method", ".writelines() method", "newline requirement in .write()", "overwrite behavior", "append behavior"]
prerequisites: ["12.6", "12.4", "12.1"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.8 — try / except — handling errors gracefully without crashing

```yaml
id: 12.8
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 2 — Working with Files
path:   m5-python-for-ai-pe-vc-and-rag/week-12/2-working-with-files/12-8-try-except-handling-errors-gracefully-without-crashing
one_liner: "try / except — handling errors gracefully without crashing"
concepts_introduced: ["exception", "runtime error", "raises (an exception)", "try block", "except block", "FileNotFoundError", "ValueError", "TypeError", "ZeroDivisionError", "PermissionError", "bare except", "exception object (as e)", "else clause (try/except)", "specific exception catching"]
prerequisites: ["12.6", "12.7"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.9 — What an API is — a door into another system; request and response

```yaml
id: 12.9
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-9-what-an-api-is-a-door-into-another-system-request-and-respon
one_liner: "What an API is — a door into another system; request and response"
concepts_introduced: ["API", "client", "server", "request-response cycle", "endpoint", "HTTP method", "GET", "POST", "headers", "body (payload)", "JSON", "status code", "API key", "rate limit", "abstraction layer"]
prerequisites: ["12.8"]
cross_refs: ["Python", "Anthropic API"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.10 — The Anthropic API — model, messages array, system prompt, user prompt, authentication

```yaml
id: 12.10
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-10-the-anthropic-api-model-messages-array-system-prompt-user-pr
one_liner: "The Anthropic API — model, messages array, system prompt, user prompt, authentication"
concepts_introduced: ["Messages API", "model parameter", "messages array", "role field", "content field", "system prompt", "user prompt", "authentication (API key)"]
prerequisites: ["12.9", "12.1", "12.4", "12.5"]
cross_refs: ["Python", "Anthropic API"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.11 — Making the first API call — install the library, write the call, run in Colab

```yaml
id: 12.11
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-11-making-the-first-api-call-install-the-library-write-the-call
one_liner: "Making the first API call — install the library, write the call, run in Colab"
concepts_introduced: ["!pip install anthropic", "pip", "package", "library", "SDK", "import anthropic", "anthropic.Anthropic()", "client object", "max_tokens", "client.messages.create()", "response object", "message.content[0].text", "content block"]
prerequisites: ["12.9", "12.10"]
cross_refs: ["Python", "Anthropic SDK"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.12 — Parsing the JSON response — extracting the text you need

```yaml
id: 12.12
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-12-parsing-the-json-response-extracting-the-text-you-need
one_liner: "Parsing the JSON response — extracting the text you need"
concepts_introduced: ["response object fields", "message.id", "message.stop_reason", "message.usage", "input_tokens", "output_tokens", "content block type field", "for-loop content block iteration", "truncation detection", "token count logging"]
prerequisites: ["12.11"]
cross_refs: ["Python", "Anthropic SDK"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.13 — Handling API errors — what to do when the call fails

```yaml
id: 12.13
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-13-handling-api-errors-what-to-do-when-the-call-fails
one_liner: "Handling API errors — what to do when the call fails"
concepts_introduced: ["AnthropicError", "APIError", "AuthenticationError", "RateLimitError", "BadRequestError", "APIConnectionError", "APIStatusError", "InternalServerError", "exception hierarchy", "time.sleep", "retry pattern", "status_code attribute"]
prerequisites: ["12.8", "12.11"]
cross_refs: ["Python", "Anthropic SDK"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 12.14 — Every API call is a design decision — latency, cost, and reliability

```yaml
id: 12.14
week: W12 (Python Foundations II and Calling an LLM)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Calling an LLM from Python
path:   m5-python-for-ai-pe-vc-and-rag/week-12/3-calling-an-llm-from-python/12-14-every-api-call-is-a-design-decision-latency-cost-and-reliabi
one_liner: "Every API call is a design decision — latency, cost, and reliability"
concepts_introduced: ["latency", "token pricing", "input tokens", "output tokens", "cost calculation", "model selection trade-off", "Haiku model tier", "Sonnet model tier", "Opus model tier", "three-question design checklist", "cost-and-latency note", "time.time() latency measurement"]
prerequisites: ["12.11", "12.13"]
cross_refs: ["Python", "Anthropic SDK"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.1 — System prompt vs user prompt — context-setting vs the live request

```yaml
id: 13.1
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-1-system-prompt-vs-user-prompt-context-setting-vs-the-live-req
one_liner: "System prompt vs user prompt — context-setting vs the live request"
concepts_introduced: ["system prompt", "user prompt", "static context", "authority", "precedence", "static vs dynamic split", "persistent context"]
prerequisites: ["12.10", "12.11"]
cross_refs: ["Python", "Anthropic API"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.2 — Role assignment — telling the model who it is

```yaml
id: 13.2
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-2-role-assignment-telling-the-model-who-it-is
one_liner: "Role assignment — telling the model who it is"
concepts_introduced: ["role assignment", "role statement", "persona", "specificity", "calibration tool", "tone register", "vocabulary level"]
prerequisites: ["12.10", "12.11", "13.1"]
cross_refs: ["Anthropic Python SDK"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.3 — Few-shot examples — showing the model what good output looks like

```yaml
id: 13.3
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-3-few-shot-examples-showing-the-model-what-good-output-looks-l
one_liner: "Few-shot examples — showing the model what good output looks like"
concepts_introduced: ["few-shot prompting", "zero-shot prompting", "one-shot prompting", "example pair", "in-context learning", "positive example", "negative example"]
prerequisites: [13.1, 13.2, 12.1]
cross_refs: ["Python", "Anthropic API"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.4 — Chain-of-thought prompting — asking the model to reason step by step

```yaml
id: 13.4
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-4-chain-of-thought-prompting-asking-the-model-to-reason-step-b
one_liner: "Chain-of-thought prompting — asking the model to reason step by step"
concepts_introduced: ["chain-of-thought prompting", "chain of thought", "zero-shot CoT", "few-shot CoT", "reasoning triple", "final-answer marker", "multi-step reasoning"]
prerequisites: ["13.1", "13.2", "13.3"]
cross_refs: ["Python", "Anthropic API"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.5 — Constraints — telling AI what NOT to do

```yaml
id: 13.5
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-5-constraints-telling-ai-what-not-to-do
one_liner: "Constraints — telling AI what NOT to do"
concepts_introduced: ["constraint", "negative instruction", "positive reframe", "guardrail", "content exclusion", "scope limit"]
prerequisites: [13.1, 13.2, 13.3]
cross_refs: ["Prompt Engineering", "Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.6 — Output format control — getting JSON, numbered lists, or fixed structure through the prompt

```yaml
id: 13.6
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Prompt Engineering Fundamentals
path:   m5-python-for-ai-pe-vc-and-rag/week-13/1-prompt-engineering-fundamentals/13-6-output-format-control-getting-json-numbered-lists-or-fixed-s
one_liner: "Output format control — getting JSON, numbered lists, or fixed structure through the prompt"
concepts_introduced: ["format mismatch problem", "format specification", "inline declaration", "format example", "template echo", "format drift", "output validator pattern"]
prerequisites: ["13.1", "13.2", "13.5"]
cross_refs: ["Python", "json.loads", "JSON"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.7 — The 5-role context framework — Authority, Exemplar, Constraint, Rubric, Metadata

```yaml
id: 13.7
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 2 — The 5-Role Context Framework
path:   m5-python-for-ai-pe-vc-and-rag/week-13/2-the-5-role-context-framework/13-7-the-5-role-context-framework-authority-exemplar-constraint-r
one_liner: "The 5-role context framework — Authority, Exemplar, Constraint, Rubric, Metadata"
concepts_introduced: ["5-role context framework", "context package", "authority role", "exemplar role", "constraint role", "rubric role", "metadata role", "priority stack"]
prerequisites: ["13.1", "13.2", "13.3", "13.5", "13.6"]
cross_refs: ["Python", "Prompt Engineering"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.8 — Output validation — checking the AI's response meets the required format

```yaml
id: 13.8
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Output Validation and Retry
path:   m5-python-for-ai-pe-vc-and-rag/week-13/3-output-validation-and-retry/13-8-output-validation-checking-the-ais-response-meets-the-requir
one_liner: "Output validation — checking the AI's response meets the required format"
concepts_introduced: ["output validation", "format check", "validation function", "schema check", "validation error"]
prerequisites: ["13.6", "13.7"]
cross_refs: ["Python", "Anthropic SDK", "JSON"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.9 — Retry logic — what to do when validation fails

```yaml
id: 13.9
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Output Validation and Retry
path:   m5-python-for-ai-pe-vc-and-rag/week-13/3-output-validation-and-retry/13-9-retry-logic-what-to-do-when-validation-fails
one_liner: "Retry logic — what to do when validation fails"
concepts_introduced: ["retry loop", "corrective message", "retry counter", "max retries", "fallback response"]
prerequisites: ["13.8"]
cross_refs: ["Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.10 — Why version control matters — never lose working code, track what changed and why

```yaml
id: 13.10
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-10-why-version-control-matters-never-lose-working-code-track-wh
one_liner: "Why version control matters — never lose working code, track what changed and why"
concepts_introduced: ["version control system", "version history", "manual versioning", "working code safety", "change log", "distributed version control"]
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.11 — Git fundamentals — repository, commit, branch, merge

```yaml
id: 13.11
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-11-git-fundamentals-repository-commit-branch-merge
one_liner: "Git fundamentals — repository, commit, branch, merge"
concepts_introduced: ["repository", "working directory", "commit", "snapshot", "commit message", "commit history", "HEAD", "branch", "main branch", "merge", "merge commit"]
prerequisites: ["13.10"]
cross_refs: ["Git"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.12 — GitHub workflow — clone, add, commit, push

```yaml
id: 13.12
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-12-github-workflow-clone-add-commit-push
one_liner: "GitHub workflow — clone, add, commit, push"
concepts_introduced: ["local repository", "remote repository", "GitHub", "git clone", "origin", "staging area", "index", "git add", "commit hash", "SHA", "git commit", "git push"]
prerequisites: [13.1, 13.11]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.13 — Folder structure and README — how to organise a professional codebase

```yaml
id: 13.13
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-13-folder-structure-and-readme-how-to-organise-a-professional-c
one_liner: "Folder structure and README — how to organise a professional codebase"
concepts_introduced: ["folder structure", "project layout", "README", "Markdown", "API key", ".gitignore", "requirements.txt"]
prerequisites: ["13.10", "13.11", "13.12"]
cross_refs: ["Git", "GitHub", "Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.14 — .gitignore — keeping API keys and secrets out of public repositories

```yaml
id: 13.14
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-14-gitignore-keeping-api-keys-and-secrets-out-of-public-reposit
one_liner: ".gitignore — keeping API keys and secrets out of public repositories"
concepts_introduced: ["secret", "environment variable", ".env file", "python-dotenv", "os.getenv()", "key rotation", ".env.example"]
prerequisites: ["13.11", "13.12", "13.13"]
cross_refs: ["Git", "GitHub", "Python"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 13.15 — Commit discipline — one logical change per commit, meaningful commit messages

```yaml
id: 13.15
week: W13 (Prompt Engineering and Version Control)
course: M5 Python for AI, PE, VC and RAG
module: 4 — Version Control with Git and GitHub
path:   m5-python-for-ai-pe-vc-and-rag/week-13/4-version-control-with-git-and-github/13-15-commit-discipline-one-logical-change-per-commit-meaningful-c
one_liner: "Commit discipline — one logical change per commit, meaningful commit messages"
concepts_introduced: ["atomic commit", "commit message subject line", "imperative mood in commit messages", "50-character subject rule", "72-character body-wrap rule", "commit message body"]
prerequisites: [13.1, 13.11, 13.12]
cross_refs: ["Git", "GitHub"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 14.1 — Vector databases — storing embeddings and enabling similarity search at scale

```yaml
id: 14.1
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Retrieval-Augmented Generation (RAG)
path:   m5-python-for-ai-pe-vc-and-rag/week-14/1-retrieval-augmented-generation-rag/14-1-vector-databases-storing-embeddings-and-enabling-similarity
one_liner: "Vector databases — storing embeddings and enabling similarity search at scale"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 14.2 — The RAG retrieval pipeline — query → embed → similarity search → top-k → inject into prompt

```yaml
id: 14.2
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Retrieval-Augmented Generation (RAG)
path:   m5-python-for-ai-pe-vc-and-rag/week-14/1-retrieval-augmented-generation-rag/14-2-the-rag-retrieval-pipeline-query-embed-similarity-search-top
one_liner: "The RAG retrieval pipeline — query → embed → similarity search → top-k → inject into prompt"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 14.3 — Why RAG reduces hallucination — AI answers from retrieved evidence, not from memory

```yaml
id: 14.3
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Retrieval-Augmented Generation (RAG)
path:   m5-python-for-ai-pe-vc-and-rag/week-14/1-retrieval-augmented-generation-rag/14-3-why-rag-reduces-hallucination-ai-answers-from-retrieved-evid
one_liner: "Why RAG reduces hallucination — AI answers from retrieved evidence, not from memory"
concepts_introduced: ["hallucination", "parametric memory", "evidence-grounded generation", "factual grounding", "grounded responses", "graceful degradation", "auditability", "context window"]
prerequisites: ["14.1", "14.2"]
cross_refs: ["RAG", "LLMs"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 14.4 — When to use RAG — use it when the answer depends on data the model was not trained on

```yaml
id: 14.4
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 1 — Retrieval-Augmented Generation (RAG)
path:   m5-python-for-ai-pe-vc-and-rag/week-14/1-retrieval-augmented-generation-rag/14-4-when-to-use-rag-use-it-when-the-answer-depends-on-data-the-m
one_liner: "When to use RAG — use it when the answer depends on data the model was not trained on"
concepts_introduced: ["RAG decision rule","freshness signal","private data signal","precision signal","cost-benefit trade-off","fine-tuning","RAG vs fine-tuning distinction"]
prerequisites: [14.1, 14.2, 14.3]
cross_refs: []
expected_delivery_minutes: null
status: corpus_drafted
```

## 14.5 — Agent anatomy — LLM plus memory, tools, and a planning loop

```yaml
id: 14.5
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 2 — AI Agents
path:   m5-python-for-ai-pe-vc-and-rag/week-14/2-ai-agents/14-5-agent-anatomy-llm-plus-memory-tools-and-a-planning-loop
one_liner: "Agent anatomy — LLM plus memory, tools, and a planning loop"
concepts_introduced: ["AI agent","LLM core","short-term memory","long-term memory","tool (agent tool)","planning loop","ReAct pattern"]
prerequisites: ["14.1","14.2","14.3","14.4"]
cross_refs: []
expected_delivery_minutes: null
status: corpus_drafted
```

## 14.6 — The ReAct pattern — Reason, Act, Observe, Repeat

```yaml
id: 14.6
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 2 — AI Agents
path:   m5-python-for-ai-pe-vc-and-rag/week-14/2-ai-agents/14-6-the-react-pattern-reason-act-observe-repeat
one_liner: "The ReAct pattern — Reason, Act, Observe, Repeat"
concepts_introduced: ["ReAct pattern", "Thought step", "Action step", "Observation step", "chain-of-thought reasoning", "grounded reasoning trace", "Final Answer"]
prerequisites: [14.5]
cross_refs: ["LangChain", "multi-hop question answering"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 14.7 — Agent vs simpler flow — decision matrix: direct call → chained calls → RAG → agent

```yaml
id: 14.7
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 2 — AI Agents
path:   m5-python-for-ai-pe-vc-and-rag/week-14/2-ai-agents/14-7-agent-vs-simpler-flow-decision-matrix-direct-call-chained-ca
one_liner: "Agent vs simpler flow — decision matrix: direct call → chained calls → RAG → agent"
concepts_introduced: ["direct call", "chained calls", "decision matrix", "tier (AI system complexity)", "over-engineering", "under-engineering"]
prerequisites: ["14.4", "14.5", "14.6"]
cross_refs: ["LangChain", "Anthropic tool use"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 14.8 — When NOT to use agents — high-stakes or irreversible actions require human oversight

```yaml
id: 14.8
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 2 — AI Agents
path:   m5-python-for-ai-pe-vc-and-rag/week-14/2-ai-agents/14-8-when-not-to-use-agents-high-stakes-or-irreversible-actions-r
one_liner: "When NOT to use agents — high-stakes or irreversible actions require human oversight"
concepts_introduced: ["irreversible action", "high-stakes action", "human-in-the-loop", "human oversight", "autonomy spectrum", "approval gate"]
prerequisites: ["14.5", "14.6", "14.7"]
cross_refs: ["EU AI Act", "NIST AI RMF"]
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 14.9 — Production AI patterns — cost, latency, and reliability trade-offs

```yaml
id: 14.9
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Production AI Patterns
path:   m5-python-for-ai-pe-vc-and-rag/week-14/3-production-ai-patterns/14-9-production-ai-patterns-cost-latency-and-reliability-trade-of
one_liner: "Production AI patterns — cost, latency, and reliability trade-offs"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 14.10 — Writing an architectural decision — choosing the right pattern for your system

```yaml
id: 14.10
week: W14 (RAG, Vectors, Agents and Production AI)
course: M5 Python for AI, PE, VC and RAG
module: 3 — Production AI Patterns
path:   m5-python-for-ai-pe-vc-and-rag/week-14/3-production-ai-patterns/14-10-writing-an-architectural-decision-choosing-the-right-pattern
one_liner: "Writing an architectural decision — choosing the right pattern for your system"
concepts_introduced: ["architectural decision record", "ADR Status field", "ADR Context section", "ADR Decision section", "ADR Consequences section", "append-only decision log"]
prerequisites: [14.4, 14.7, 14.9]
cross_refs: []
expected_delivery_minutes: null
status: "corpus_drafted"
```

## 15.1 — Writing a formal problem statement and 1-page specification

```yaml
id: 15.1
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 1 — Specification and Build
path:   m6-capstone-project/week-15/1-specification-and-build/15-1-writing-a-formal-problem-statement-and-1-page-specification
one_liner: "Writing a formal problem statement and 1-page specification"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.2 — Building the Python orchestration layer

```yaml
id: 15.2
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 1 — Specification and Build
path:   m6-capstone-project/week-15/1-specification-and-build/15-2-building-the-python-orchestration-layer
one_liner: "Building the Python orchestration layer"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.3 — Writing a 5-role context-engineered system prompt

```yaml
id: 15.3
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 1 — Specification and Build
path:   m6-capstone-project/week-15/1-specification-and-build/15-3-writing-a-5-role-context-engineered-system-prompt
one_liner: "Writing a 5-role context-engineered system prompt"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.4 — Producing structured JSON output and validating it

```yaml
id: 15.4
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 1 — Specification and Build
path:   m6-capstone-project/week-15/1-specification-and-build/15-4-producing-structured-json-output-and-validating-it
one_liner: "Producing structured JSON output and validating it"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.5 — Building a 5-case evaluation harness

```yaml
id: 15.5
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 2 — Testing and Evaluation
path:   m6-capstone-project/week-15/2-testing-and-evaluation/15-5-building-a-5-case-evaluation-harness
one_liner: "Building a 5-case evaluation harness"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.6 — Applying the Judgment Framework — documenting the human override point per component

```yaml
id: 15.6
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 2 — Testing and Evaluation
path:   m6-capstone-project/week-15/2-testing-and-evaluation/15-6-applying-the-judgment-framework-documenting-the-human-overri
one_liner: "Applying the Judgment Framework — documenting the human override point per component"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.7 — Estimating and documenting API cost and latency

```yaml
id: 15.7
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 2 — Testing and Evaluation
path:   m6-capstone-project/week-15/2-testing-and-evaluation/15-7-estimating-and-documenting-api-cost-and-latency
one_liner: "Estimating and documenting API cost and latency"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.8 — Red-teaming your own system — 3 adversarial inputs, results, and mitigations

```yaml
id: 15.8
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 2 — Testing and Evaluation
path:   m6-capstone-project/week-15/2-testing-and-evaluation/15-8-red-teaming-your-own-system-3-adversarial-inputs-results-and
one_liner: "Red-teaming your own system — 3 adversarial inputs, results, and mitigations"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.9 — Failure handling — what the system returns when the AI call or validation fails

```yaml
id: 15.9
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 2 — Testing and Evaluation
path:   m6-capstone-project/week-15/2-testing-and-evaluation/15-9-failure-handling-what-the-system-returns-when-the-ai-call-or
one_liner: "Failure handling — what the system returns when the AI call or validation fails"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.10 — Presenting: Problem → Spec → Live Demo → Eval Results → Judgment Framework → Cost → 3 Learnings

```yaml
id: 15.10
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 3 — Showcase and Portfolio
path:   m6-capstone-project/week-15/3-showcase-and-portfolio/15-10-presenting-problem-spec-live-demo-eval-results-judgment-fram
one_liner: "Presenting: Problem → Spec → Live Demo → Eval Results → Judgment Framework → Cost → 3 Learnings"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```

## 15.11 — Submitting the GitHub portfolio — notebooks, specs, eval harness, AI Decision Journal, reflection

```yaml
id: 15.11
week: W15 (Capstone Build Sprint and Showcase)
course: M6 Capstone Project
module: 3 — Showcase and Portfolio
path:   m6-capstone-project/week-15/3-showcase-and-portfolio/15-11-submitting-the-github-portfolio-notebooks-specs-eval-harness
one_liner: "Submitting the GitHub portfolio — notebooks, specs, eval harness, AI Decision Journal, reflection"
concepts_introduced: []
prerequisites: []
cross_refs: []
expected_delivery_minutes: null
status: scaffolded
```
concepts_introduced: ["diffuse accountability", "accountability gap", "adverse action notice", "model documentation", "accountability chain"]
status: "corpus_drafted"

