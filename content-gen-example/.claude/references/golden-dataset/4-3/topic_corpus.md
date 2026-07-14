---
topic_id: "4.3"
title: "Retrieval-Augmented Generation (RAG) — giving AI access to external knowledge at query time"
position_in_module: 1
generated_at: 2026-06-11T00:00:00Z
resource_count: 0
---

# Retrieval-Augmented Generation (RAG) — Giving AI Access to External Knowledge at Query Time — Topic Corpus

## Prerequisites

- **3.4 — How LLMs work: tokens, training, and inference.** You need to know that an LLM (Large Language Model) reads text as tokens, learns during training, and generates answers during inference. You also need the idea of the context window — the limited amount of text the model can consider at one time.
- **3.9 — Hallucination: what it is and why it happens.** RAG exists largely because models sometimes state false things confidently. You need that concept to understand what problem RAG attacks.
- **4.1 — Foundation models.** RAG sits on top of a foundation model. You need to know that such a model is trained once at enormous scale and then reused for many tasks.
- **4.2 — Fine-tuning.** RAG is best understood side by side with fine-tuning. You need to know that fine-tuning means continuing to train a foundation model on smaller, domain-specific data.

Helpful but not required: 3.6 (temperature and sampling) explains why answers vary; this topic does not depend on it, but it deepens the contrast between what the model *knows* and how it *phrases* things.

## Learning Objectives

After working through this topic, you will be able to:

- **Explain** the knowledge problem that RAG solves: a trained model's knowledge is frozen at training time and cannot see your private or recent information.
- **Define** Retrieval-Augmented Generation in plain language and unpack each of its three words.
- **Describe** the five steps of the RAG flow — query, embed, retrieve, inject, respond — and state what each step produces.
- **Trace** a concrete question end-to-end through a RAG system, identifying what the model sees at each stage.
- **Compare** RAG and fine-tuning, and pick the right one for a given scenario (fresh facts vs. new behavior).
- **Identify** what RAG does and does not fix — including why it reduces, but does not eliminate, wrong answers.

## Introduction

Ask a chatbot, "How many vacation days do I get at my company?" and it will do one of two things. It might admit it has no idea. Or — worse — it might confidently invent an answer: "Most employees receive 15 days of paid vacation." That number came from nowhere. Your company's actual policy lives in an HR document the model has never seen. You met this failure in Topic 3.9: it is a hallucination, a confident statement not grounded in real knowledge.

Why does this happen? Because of something you learned in Topic 3.4: a model's knowledge comes from its training data, and after training, its parameters are frozen. The model is like a brilliant student who memorized a huge library years ago and then was locked out of every library since. It cannot read your company handbook. It cannot see yesterday's news. It cannot check today's prices. Everything it "knows" was baked in before you ever typed a word.

Retrieval-Augmented Generation — RAG for short — is the most widely used fix for this problem. The idea is almost embarrassingly simple: instead of hoping the model memorized the answer, **look the answer up first, then hand the relevant text to the model along with the question**. The model stops working from memory and starts working from evidence you supplied. This topic walks through exactly how that works, step by step, and when you would choose it over the alternative you already know: fine-tuning.

## Core Concepts

### The knowledge problem: frozen, general, and blind to your data

Start with the problem, because RAG makes no sense without it. A foundation model (Topic 4.1) has three knowledge gaps that no amount of clever prompting can close on its own:

- **Its knowledge is frozen in time.** Training happens once, at scale, and then stops. Anything that happened after training — a product launched last month, a policy updated last week — simply is not in the model.
- **Its knowledge is general, not yours.** The model trained on broad public text. Your company's handbook, your project's design notes, your customer records — none of that was in the training data, and you would not want it to be.
- **It cannot tell remembering from guessing.** As you saw in Topic 3.9, the model produces the most likely next tokens whether or not it actually has solid knowledge. When the real answer is missing, a plausible-sounding fake often comes out instead.

Why not just retrain the model every time something changes? Because training a foundation model costs enormous amounts of computing power, data, and time (Topic 4.1). Even fine-tuning (Topic 4.2) — the cheaper adaptation step — takes curated example data, engineering effort, and hours-to-days of work. You cannot fine-tune every morning because the cafeteria menu changed.

So the question becomes: **how do you give a frozen model fresh, private, specific knowledge at the exact moment it needs it — without retraining anything?**

### The key insight: the prompt is a door

Here is the insight that makes RAG work. There is one channel into the model that is *not* frozen: **the prompt**. Everything you type into the context window, the model reads fresh, at inference time, every single time.

You have already seen a tiny version of this. If you paste a paragraph into a chatbot and ask, "Summarize this," the model handles text it has never seen before — because you put that text directly in front of it. The model does not need to have memorized your paragraph. It just needs to *read* it.

RAG industrializes that trick. Instead of you manually finding and pasting the right document, the system does it automatically:

1. You ask a question.
2. The system searches your document collection for the passages most relevant to that question.
3. It pastes those passages into the prompt, right next to your question.
4. The model answers by reading the passages — not by recalling its training.

Why not skip the searching and paste *everything* into the prompt? Two reasons:

- **The context window is finite** (Topic 3.4). A company's document library can run to millions of words; the prompt can hold only a small fraction of that.
- **Relevance beats volume.** Even when a long prompt fits, burying one useful paragraph under fifty useless ones makes the model's job harder, not easier. You want the librarian to hand over one folder, not wheel in the whole archive.

So a real system needs a way to *select* — to find the few passages worth injecting. That selection step is retrieval, and it is what the rest of this topic builds.

**Retrieval-Augmented Generation** — a technique where an AI system first *retrieves* relevant text from an external knowledge source, then *augments* (adds to) the model's prompt with that text, so the model's *generation* (its answer) is grounded in the retrieved material.

Unpack the three words, right to left:

- **Generation** — the part you already know: the LLM generating an answer token by token (Topic 3.4).
- **Augmented** — the prompt is enriched. The model does not receive your bare question; it receives your question *plus* supporting text.
- **Retrieval** — the new machinery: automatically finding the right supporting text at the moment you ask. "Retrieve" just means "fetch" — like a librarian fetching the one folder you need from a huge archive.

One phrase in the topic title deserves emphasis: **at query time**. A query is just the question or request you send to the system. "At query time" means the knowledge arrives the instant you ask — not months earlier during training. That timing difference is the entire point. Training-time knowledge is frozen; query-time knowledge is as fresh as the documents you keep.

### The knowledge base: where the answers live

For retrieval to work, there must be somewhere to retrieve *from*.

**Knowledge base** — the organized collection of documents a RAG system is allowed to search. It can be anything in text form: HR policies, product manuals, support articles, contracts, meeting notes, a wiki.

Two things matter about the knowledge base:

- **You control it.** Unlike training data, which is fixed inside the model, the knowledge base is just files. Update a policy document today, and the system can use the new version on the very next question. Nothing is retrained.
- **It is prepared ahead of time.** Documents are split into smaller passages so the system can fetch just the relevant piece rather than a whole 80-page manual. Each passage is called a **chunk** — a bite-sized slice of a document, typically a few paragraphs long. Why chunk? Because the context window (Topic 3.4) is limited. You cannot paste the entire handbook into every prompt; you want the three paragraphs that actually answer the question.

How big should a chunk be? There is a real trade-off here, and you can reason about it without any math:

- **Too large** (whole chapters): the chunk contains the answer plus pages of noise. Injected into the prompt, it crowds the limited context window and buries the relevant sentence.
- **Too small** (single sentences): the chunk loses its surroundings. "Up to 5 days may be carried over" is useless if the sentence saying *which* policy it belongs to was cut away into a different chunk.
- **Just right** (a few paragraphs): enough surrounding text to be understood on its own, small enough that several chunks fit comfortably in one prompt.

A useful mental model: the knowledge base is a well-organized filing cabinet, and chunking is the act of putting each policy on its own labeled card instead of stuffing whole binders into one drawer.

### Finding the right chunk: matching by meaning

Now the hard part. A user asks: "How many days off do I get?" The relevant chunk in the handbook says: "Employees accrue 1.5 days of paid leave per month of service." Notice the problem — the question and the answer share almost no words. "Days off" never appears in the policy text. A naive word-matching search would miss it.

RAG systems therefore search by **meaning**, not by exact words. They do this using embeddings, which you met in Topic 3.4: an **embedding** is a list of numbers that represents what a piece of text *means*, positioned so that texts with similar meanings get similar numbers. Think of it as a coordinate on a giant "map of meaning" — "days off," "vacation," and "paid leave" all land in the same neighborhood of the map, even though they share no letters.

The search then works like this:

- Ahead of time, every chunk in the knowledge base is converted into an embedding and stored.
- When a question arrives, the question is converted into an embedding too.
- The system compares the question's embedding against the stored chunk embeddings and picks the closest neighbors on the meaning-map.

**Similarity matching** — comparing embeddings to find which stored chunks are closest in meaning to the question. The chunks with the closest embeddings are returned as the search results.

That is all the depth this topic needs. How those embeddings are stored and searched efficiently across millions of chunks is its own subject — you will meet vector databases and build out the full retrieval pipeline in Week 14. For now, hold the simple version: *the question and every chunk become points on a meaning-map, and the system grabs the chunks nearest to the question.*

### The five-step RAG flow: query → embed → retrieve → inject → respond

Put the pieces together and you get the canonical RAG flow. Five steps, every time a question is asked:

1. **Query.** The user asks a question in plain language. Example: "How many vacation days do I get?"
2. **Embed.** The system converts that question into an embedding — its coordinates on the meaning-map.
3. **Retrieve.** The system compares the question's embedding to the stored chunk embeddings and pulls back the few most similar chunks — say, the top 3. These are the passages most likely to contain the answer.
4. **Inject.** The system builds a new, bigger prompt: the retrieved chunks plus the original question, usually with an instruction like "Answer the question using only the information provided below." Injecting just means inserting the retrieved text into the prompt before the model sees it.
5. **Respond.** The LLM reads the augmented prompt and generates an answer during ordinary inference (Topic 3.4) — except now the answer is drawn from the supplied evidence, and a good system also points to which document the answer came from.

Two details are worth pausing on.

**The model is unchanged.** Read the five steps again: at no point does anyone touch the model's parameters (Topic 3.5). The foundation model is exactly as frozen as it was before. All the new knowledge flows in through the prompt, at query time. That is why RAG is sometimes described as giving the model an *open-book exam*: the book changed, not the student.

**Each step has its own failure mode.** When a RAG answer goes wrong, the flow tells you where to look:

| Step | What can go wrong | What it looks like to the user |
|---|---|---|
| 1. Query | The question is vague ("What about leave?") | Retrieval has nothing precise to match against, so weakly related chunks come back |
| 2. Embed | The question's meaning is captured poorly (rare wording, heavy abbreviations) | The question lands in the wrong neighborhood of the meaning-map |
| 3. Retrieve | The right chunk exists but isn't among the closest matches | The model answers from the wrong evidence — confidently |
| 4. Inject | Too many or badly chosen chunks crowd the prompt | The model misses the key sentence in the noise |
| 5. Respond | The model misreads or blends the retrieved chunks | A grounded-looking answer that the cited source doesn't actually say |

This is the same decomposition habit you built in Week 1: a system that fails is easier to fix when you can name the step that failed.

**The model never sees the whole library.** Only the handful of retrieved chunks enter the prompt. This respects the context window limit and keeps the model focused. The quality of the final answer therefore depends heavily on step 3 — if retrieval fetches the wrong chunks, the model answers from the wrong evidence. More on that under Best Practices.

### Grounding: why RAG answers are more trustworthy

**Grounding** — tying the model's answer to specific, checkable source material instead of the model's internal memory.

Grounding is the payoff of the whole flow, and it connects directly back to Topic 3.9. A hallucination happens when the model fills a knowledge gap with plausible-sounding tokens. RAG attacks that failure from two sides:

- **It closes the gap.** The most common reason to hallucinate — "the real answer is not in my training data" — disappears when the real answer is sitting right there in the prompt.
- **It makes answers checkable.** Because the system knows which chunks it injected, it can cite them: "According to the Employee Handbook, section 4.2 …". You, the human, can open that document and verify. An ungrounded answer offers you nothing to check; a grounded answer shows its work.

See the difference side by side. Same question — "What is the refund window for sale items?":

- **Ungrounded answer:** "Sale items can usually be refunded within 30 days." *Usually? Says who? There is nothing to check — you either trust it or you don't.*
- **Grounded answer:** "Sale items can be refunded within 14 days (Returns Policy, §3, updated May 2026)." *One click to the source settles it.*

The second answer might still be wrong — but it is wrong in a way you can *catch*. That property, checkability, is what separates an AI system you can deploy in a business from a clever demo.

Be careful with the claim, though. RAG *reduces* hallucination; it does not abolish it. The model can still misread a retrieved chunk, blend two chunks incorrectly, or fall back on its training memory when retrieval comes up empty. Why this works as well as it does — and where it still fails — gets a fuller treatment in Week 14. For now, the honest one-line version: **RAG turns "trust my memory" into "here's my source," which is a major upgrade, not a guarantee.**

### What RAG is not — three common confusions

Three misconceptions show up constantly when people first meet RAG. Clearing them now will save you trouble for the rest of the course:

- **RAG is not the model learning.** Nothing is remembered. The retrieved chunks live in the prompt for one exchange and then they are gone. Ask the same question tomorrow and the system retrieves all over again. If the model seems to "know" your handbook, it is because the handbook is being re-fetched every time — the model itself ends every conversation exactly as ignorant of your documents as it began.
- **RAG is not just a search engine.** A search engine hands you ten links and leaves the reading to you. RAG does the search *and then* the reading and the answering: it retrieves the passages, reads them, and composes one direct answer in plain language. Search finds documents; RAG answers questions *using* documents.
- **RAG is not a truth machine.** Retrieval finds the chunks most *similar in meaning* to the question — similarity is not the same as correctness. If the knowledge base contains an obsolete policy, the obsolete policy is exactly what gets retrieved, injected, and confidently cited. RAG guarantees the answer is grounded in your documents; it cannot guarantee your documents are right.

Keep these three in your pocket: **no learning, not just search, not automatic truth.** Most real-world disappointment with RAG systems traces back to forgetting one of them.

### RAG vs. fine-tuning: two different tools

You now know two ways to adapt a foundation model to your needs, and they are constantly confused with each other. Topic 4.2 taught fine-tuning: continuing training on domain-specific examples, which *changes the model's parameters*. RAG changes *nothing inside the model*; it changes what the model gets to read.

A clean way to hold the difference: **fine-tuning teaches the model new behavior; RAG hands the model new information.** Fine-tuning is sending the student back to school; RAG is letting the student bring the textbook into the exam.

| | Fine-tuning (4.2) | RAG (this topic) |
|---|---|---|
| What changes | The model's parameters (weights) | The prompt — extra retrieved text is injected |
| Best at | New *behavior*: style, tone, format, domain vocabulary | New *facts*: current, private, or specialized information |
| How fresh is the knowledge | Frozen at fine-tuning time — goes stale | As fresh as the knowledge base — update a file, the next answer reflects it |
| Cost to update | Re-run training: data prep, compute, hours to days | Edit or add documents: minutes |
| Can it cite a source | No — knowledge is blended invisibly into weights | Yes — answers can point at the retrieved chunks |
| Typical example | A model that always responds in your firm's legal drafting style | A chatbot answering from this month's product manual |

Two scenario checks to make the contrast stick:

- *"Our support bot must answer from a product catalog that changes weekly."* Facts that change constantly → **RAG**. Fine-tuning weekly would be slow, expensive, and always behind.
- *"Our model writes acceptable answers but in the wrong tone — we need every reply in our brand voice."* That is behavior, not missing facts → **fine-tuning**. No retrieved document can change *how* the model writes.

And the practical punchline: the two are not rivals. Real systems often use both — a model fine-tuned for the domain's style, wrapped in RAG for the domain's current facts.

### RAG in the 2026 AI stack

This week's session is about the layers of a modern AI system, and RAG is the layer that connects a general-purpose model to *your* knowledge. The pattern beneath it is bigger than documents, and it is where this module is heading: a model becomes far more useful when it can reach *outside itself* at the moment of answering. RAG is the first and most common form of that reaching-out. In the next topics you will meet agents and tool use — those are Topics 4.4 and 4.5, so leave them there for now.

It helps to see where RAG sits among the adaptation options you now own, stacked from heaviest to lightest:

1. **Train a foundation model** (Topic 4.1) — build general capability from scratch. Done by a handful of organizations, at enormous cost.
2. **Fine-tune it** (Topic 4.2) — adjust the model's parameters on domain examples to change its behavior. Done occasionally, when behavior needs to change.
3. **Augment it with retrieval** (this topic) — leave the model alone and control what it reads at query time. Done continuously, every single question.

Each layer down is cheaper, faster to change, and touches less of the system. That is why RAG became the default first answer to "how do we make a general model useful on *our* data": it delivers most of the practical value at a tiny fraction of the cost, and it leaves the model itself untouched.

This is also exactly what this week's lab demonstrates live: the same model is asked the same question twice — once bare, once with retrieval over a real document set — and the class compares the two answers for accuracy and checkability. When you watch that demo, narrate the five steps to yourself as they happen.

## Implementation

There is no code in this course, but RAG is a procedure, and you should be able to walk it like one. Here is the full life of one question inside a RAG system, split into the *setup* phase (done once, ahead of time) and the *query* phase (done every time someone asks).

**Phase A — Setup (before any questions arrive):**

1. **Collect** the documents that should be answerable — say, the 40 documents of a company's HR policy library.
2. **Chunk** each document into passages of a few paragraphs each. The 40 documents might become 1,200 chunks.
3. **Embed** every chunk — compute each chunk's meaning-coordinates.
4. **Store** all chunk embeddings, each linked back to its original text and source document.

**Phase B — Query time (the five-step flow, every question):**

1. **Query.** Priya, a new employee, types: *"Can I carry unused leave into next year?"*
2. **Embed.** Her question becomes an embedding — a point on the meaning-map near concepts like *leave*, *carry-over*, *annual allowance*.
3. **Retrieve.** Similarity matching against the 1,200 stored chunks returns the top 3, for example:
   - Chunk 412 (Leave Policy, §5): "Up to 5 unused leave days may be carried into the following calendar year…"
   - Chunk 415 (Leave Policy, §6): "Carried-over days must be used by March 31…"
   - Chunk 87 (Onboarding FAQ): "Leave balances are visible in the HR portal…"
4. **Inject.** The system assembles the augmented prompt:
   > "Use only the context below to answer. If the context does not contain the answer, say so.
   > Context: [chunk 412] [chunk 415] [chunk 87]
   > Question: Can I carry unused leave into next year?"
5. **Respond.** The model reads the evidence and generates: *"Yes — you can carry up to 5 unused leave days into the next calendar year, but you must use them by March 31 (Leave Policy, §5–6)."*

Now contrast the failure case without RAG. The bare model, asked the same question, has never seen this company's leave policy. Its training data contains hundreds of *other* leave policies, so the most likely tokens produce something like *"Typically, companies allow 10 days of carry-over"* — fluent, plausible, and wrong for Priya's company. Same model, same question; the only difference is what was placed in front of it at query time.

Run the comparison once more with a twist: suppose the company *changes* the carry-over limit from 5 days to 8. With RAG, someone edits the Leave Policy document, the chunk is re-embedded, and Priya's question tomorrow returns "up to 8 days" — total turnaround, minutes. With fine-tuning as the delivery mechanism, the same change would mean preparing new training examples, re-running the fine-tune, and redeploying the model. This single maintenance scenario is why, for fast-changing facts, the industry default is RAG.

Notice also what the injected instruction does in step 4: *"If the context does not contain the answer, say so."* This converts an empty retrieval from a hallucination trigger into an honest "I don't know" — one of the cheapest reliability wins in the whole pattern.

## Real-World Patterns

RAG is arguably the single most deployed LLM pattern in industry right now. Four places you will run into it:

- **Customer-support assistants.** A retailer's chatbot answers from the current returns policy, shipping table, and product manuals. When the policy changes, the team replaces one document — no model work at all. This is the canonical RAG business case: facts that change faster than anyone could ever retrain.
- **Internal knowledge assistants.** "Ask HR," "Ask IT," "Ask Legal" bots that sit on top of a company's private wikis and policy libraries. The knowledge is private — it was never in any training set and never should be — so query-time injection is the *only* way a model can answer about it.
- **Search engines with AI answers.** When a search engine writes a paragraph-style answer with little source links underneath, you are looking at RAG at web scale: retrieve relevant pages, inject them, generate a grounded summary with citations.
- **Research and document-heavy professions.** Legal and medical assistants that answer only from a vetted corpus — case law, clinical guidelines — precisely because in those fields an ungrounded guess is not an inconvenience but a liability. The citation trail is the product.
- **Personal and team assistants over your own files.** Tools that let you "chat with" a folder of PDFs, your meeting notes, or a shared drive are small-scale RAG: your files become the knowledge base, your question is embedded, and the answer cites the page it came from. The pattern scales down to one person as cleanly as it scales up to a corporation.

The common thread: every one of these needs knowledge that is *fresh*, *private*, or *checkable* — the three things a frozen foundation model cannot offer on its own.

## Best Practices

Heuristics for judging or designing a RAG setup — all consequences of the five-step flow:

- **The knowledge base is the ceiling.** A RAG system can only be as accurate as its documents. Outdated policy file in, outdated answer out — delivered with full confidence and a citation. Curating and refreshing the knowledge base is the highest-leverage maintenance task.
- **Retrieval is the weakest link.** If step 3 fetches the wrong chunks, step 5 grounds the answer in the wrong evidence. When a RAG system answers badly, check *what was retrieved* before blaming the model.
- **Instruct the model to stay inside the evidence.** The injected prompt should say "answer only from the context, and say so if it isn't there." Without that, the model quietly falls back on training memory when retrieval misses — reintroducing exactly the hallucinations RAG was meant to prevent.
- **Demand citations.** A grounded answer that names its source can be verified in seconds; one that does not is just a confident voice. If a deployed RAG system never shows sources, treat its answers with the same skepticism as a bare model's.
- **Match the tool to the gap.** Missing or changing *facts* → RAG. Wrong *style or behavior* → fine-tuning. Reaching for fine-tuning to inject facts is the classic anti-pattern: slow, expensive, stale on arrival, and impossible to cite.
- **Test with questions whose answers you already know.** The fastest health check for any RAG system: ask ten questions you can verify against the source documents yourself, and check both the answers and the cited chunks. If the citations point at irrelevant passages, your problem is retrieval, not the model.
- **Verify anyway.** RAG reduces the hallucination rate; it does not repeal Topic 3.9. The evaluation habits you built in Week 3 still apply to every grounded answer.

## Hands-On Exercise

A paper-and-browser version of this week's lab demo:

1. Pick a factual question about an organization you know — e.g., "What is my college's late-submission policy?"
2. Ask a chatbot the question cold, with no added material. Save the answer.
3. Find the real policy text (handbook page, website) and paste it into a fresh chat above the same question, prefixed with: "Answer only from the text below."
4. Compare the two answers against the source: which one is correct, and which one *shows you* it is correct?
5. You just performed steps 4–5 of RAG by hand — the only parts a real system automates are embedding and retrieval.

## Key Takeaways

- A foundation model's knowledge is frozen at training time; it cannot see recent events or your private documents, and it papers over those gaps with confident hallucinations.
- RAG fixes this at **query time**: retrieve the relevant chunks from a knowledge base, inject them into the prompt, and let the model answer from supplied evidence instead of memory.
- The flow is five steps — **query → embed → retrieve → inject → respond** — and the model itself is never modified; all new knowledge enters through the prompt.
- Retrieval matches by *meaning*, not exact words: the question and every chunk become embeddings, and the closest chunks on the meaning-map win.
- RAG delivers fresh, private, **citable** answers and reduces hallucination — but the knowledge base sets the quality ceiling, and retrieval misses can still produce wrong answers.
- Choose RAG for new *facts* and fine-tuning for new *behavior*; production systems frequently combine both.

## Next Steps

_System-derived from the next entry in curriculum/sequence.md._
