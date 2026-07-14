---
topic_id: 3.6
title: Temperature and sampling — why the same question gives different answers
position_in_module: 3
generated_at: 2026-06-11T00:00:00Z
resource_count: 0
---

# 1. Temperature and Sampling — Why the Same Question Gives Different Answers — Topic Corpus

## 2. Prerequisites

- **1.3 — Probabilistic systems — same input can give different outputs.** You learned that some systems are deterministic (same input, same output, every time) and some are probabilistic (same input can give different outputs). This topic explains the exact machinery that makes an LLM (Large Language Model) probabilistic.
- **1.4 — Why AI gives different answers to the same question.** In week 1 you saw *that* AI answers vary, and the words "temperature" and "sampling" were named but deliberately not explained. Today they are the headline. This topic pays off that promise.
- **3.4 — How LLMs work — tokens, training, and inference.** You need three ideas from that topic: a **token** (a chunk of text, roughly a word or part of a word), **inference** (what happens when you actually use the model), and the fact that the model generates its answer **one token at a time**, ending each step with a probability for every possible next token.
- **3.5 — What parameters are and why they matter.** You learned that a model's knowledge lives in its **parameters** — the billions of internal numbers set during training. This topic shows that the parameters are only half the story of an answer. The other half is a choice made *after* the parameters have done their work.

## 3. Learning Objectives

After working through this topic, you will be able to:

- **Explain** in plain language why an LLM can give two different answers to the exact same question, using the words *probability distribution*, *sampling*, and *temperature* correctly.
- **Describe** what happens in a single token-picking step: scores in, percentages out, one token drawn.
- **Contrast** greedy decoding (always pick the top token) with sampling (draw a token by weighted chance), and state one strength and one weakness of each.
- **Predict** how raising or lowering the temperature setting changes a model's output, and pick a sensible temperature for a given task (for example: a legal summary versus a poem).
- **Explain** why factual questions tend to get consistent answers while creative questions vary widely, using the idea of sharp versus flat probability distributions.
- **Identify** two common misunderstandings about temperature — that low temperature guarantees truth, and that temperature 0 guarantees perfectly identical answers.

## 4. Introduction

Try this small experiment. Ask a chatbot: *"Give me a name for a tea stall."* It says "ChaiPoint Corner." Now ask the identical question again, in a fresh chat, word for word. This time it says "The Steeping Story." Same question. Same model. Same billions of parameters. Different answer.

In week 1 this looked almost spooky. A calculator never does this — `2 + 2` is `4` today, tomorrow, and forever. We labelled the calculator *deterministic* and the chatbot *probabilistic*, and we promised the real explanation would come later. Later is now.

The explanation rests on two ideas, and only two. First: at every step, the model does not produce *an answer* — it produces a *scored list of possible next tokens*, like a shortlist with marks attached. Second: the system then *picks* one token from that list, and the standard way of picking involves a controlled roll of the dice. That picking process is called **sampling**, and the knob that controls how daring the dice roll is allowed to be is called **temperature**. Once you understand these two ideas, the "spooky" behavior becomes a simple, sensible engineering choice — one you can even adjust yourself.

## 5. Core Concepts

### 5.1 Where every answer starts: a scored list of guesses

Recall the core loop from topic 3.4. When an LLM writes a reply, it does not plan the whole answer in advance. It builds the reply one token at a time, and after each token it asks the same question again: *"Given everything so far, what comes next?"*

Here is the crucial detail. The model's billions of parameters do not output one token. They output a *score for every token the model knows* — and a typical model knows somewhere around 50,000 to 200,000 tokens. Every single one of them gets a number saying how well it would fit as the next token. This is a direct consequence of how the model was trained: in topic 3.4 you saw that training is one long exercise in next-token prediction, so "rate every candidate token" is the one skill the model has practiced trillions of times.

Those raw scores are then converted into percentages that add up to 100%. A list of options where each option has a percentage chance is called a **probability distribution** — a menu of possibilities, each tagged with how likely it is.

A concrete example. Suppose the text so far is:

> "For breakfast in Mumbai, many people enjoy a hot cup of ___"

The model's distribution for the next token might look like this (simplified to five entries — the real list has tens of thousands, most with chances near zero):

| Candidate next token | Probability |
|---|---|
| `chai` | 62% |
| `tea` | 21% |
| `coffee` | 12% |
| `milk` | 4% |
| `soup` | 1% |

Two things are worth noticing:

- The model is *not* saying "the answer is chai." It is saying "chai fits best, tea fits decently, coffee fits reasonably, soup barely fits."
- This list is **completely deterministic**. Feed in the exact same text and the exact same model, and you get the exact same percentages, every time. The parameters from topic 3.5 are doing fixed, repeatable arithmetic.

Why does that second point matter? Because it tells you the variation in AI answers does *not* come from the model's knowledge being fuzzy or unstable. The scored list is rock solid. The variation comes from what happens next.

### 5.2 Sharp lists and flat lists: the model's built-in confidence

Before looking at how a token gets picked, look at the *shape* of the list itself, because not all distributions look alike.

Some contexts leave almost no room for debate. Complete this sentence:

> "The capital of France is ___"

The model's distribution here is **sharp** (also called *peaked*): one token, `Paris`, might hold 98% of the probability, with everything else sharing crumbs. The model has, in effect, high confidence — its training data overwhelmingly agreed on what follows.

Other contexts are wide open. Complete this one:

> "A good name for a tea stall would be ___"

Thousands of continuations are reasonable, and none dominates. The distribution is **flat**: the top candidate might hold only 4%, the next 3%, and so on down a long tail. The model is signalling, honestly, that the context does not pin down the answer.

Why introduce this now? Because the shape of the distribution decides how much the picking method matters:

| Distribution shape | Typical context | Effect of a chance-based pick |
|---|---|---|
| Sharp (one token dominates) | Facts, arithmetic, fixed phrases | Barely any — the favourite nearly always wins |
| Flat (many tokens close together) | Creative writing, naming, open questions | Large — different runs genuinely diverge |

Keep this picture in hand. It will explain, later in this topic, why a chatbot gives you the same fact ten times in a row but a different stall name on every try — *even at the same settings*.

One refinement before moving on. Even a factual answer is not sharp at *every* token. "The capital of France is Paris" and "Paris is the capital of France" carry the same fact in different clothes. The token holding the fact (`Paris`) sits in a sharp distribution; the connective words around it ("is", "the", sentence order) sit in flatter ones. This is why repeated factual answers usually agree on **content** while still varying in **wording** — the sharp tokens hold firm while the flat ones wander.

### 5.3 Option one: always take the top pick (greedy decoding)

The simplest possible rule for choosing from the list: take the highest-scoring token, every time. This rule is called **greedy decoding** — "greedy" because at each step it grabs the single best-looking option and never gambles.

In our breakfast example, greedy decoding picks `chai` (62%). Always. Run it a thousand times and you get `chai` a thousand times. The whole system becomes deterministic — same question, same answer — exactly like the calculator.

So why isn't greedy decoding the standard? It sounds ideal. The catch is that "most likely next token, chosen one step at a time" does not mean "best overall answer." Greedy decoding has well-known weaknesses:

- **It gets repetitive.** Always taking the safest token tends to produce flat, formulaic text — and can trap the model in loops. A classic failure looks like: *"The market was busy. The market was busy. The market was busy."* Each repetition makes the phrase look even more "expected" in context, so the top-pick rule keeps choosing it, forever.
- **It is short-sighted.** A slightly less likely token now can lead to a much better sentence later. Greedy decoding never takes that path, because it never looks past the current step.
- **One question, one phrasing — forever.** Ask for a tea stall name and you will get the same name every time. For any creative task, that is a serious flaw.

Pose the question a beginner should ask here: *if the top token is the model's best guess, how can always choosing it make answers worse?* Because language has many right answers. "Enjoy a hot cup of chai" and "enjoy a hot cup of coffee" are both perfectly good sentences. The probability distribution is honestly reporting that several continuations are reasonable. Greedy decoding throws that honesty away and pretends only one continuation exists.

### 5.4 Option two: hold a weighted lottery (sampling)

The standard alternative is **sampling** — picking the next token by chance, where each token's chance of being picked equals its probability in the list.

Why that name? "Sampling" is borrowed from statistics, where it means drawing one item from a pool according to known chances — the way a quality inspector pulls one mango from a crate, or a survey picks one household from a city. Here the pool is the token list, and the chances are the percentages the model just computed. Nothing more exotic than that.

The everyday picture is a raffle drum. For the breakfast example, imagine 100 raffle tickets:

- 62 tickets say `chai`
- 21 tickets say `tea`
- 12 tickets say `coffee`
- 4 tickets say `milk`
- 1 ticket says `soup`

Shake the drum, draw one ticket, and that token is appended to the answer. Likely tokens hold most of the tickets, so they usually win — but they don't *always* win. Roughly one run in five, the model writes `tea` instead of `chai`. Once in a hundred runs, it writes `soup`.

Where does the "shake of the drum" actually come from? Computers cannot shrug; they use a **random number generator** — a small program that produces an unpredictable-looking stream of numbers — to decide which ticket is drawn. Some developer tools even let you fix the generator's starting point, called a **seed**: same seed, same stream of numbers, same draws, same answer. That detail confirms the big picture — the "randomness" is itself engineered, and every part of this pipeline is a deliberate choice.

This mechanism is the direct, complete answer to the topic's headline question. **Why does the same question give different answers? Because at every token, the model holds a fresh weighted lottery, and lottery draws differ from run to run.**

It is worth seeing how one different draw snowballs. Watch two runs of the same prompt diverge:

1. **Run A** draws `chai`. The text is now "…a hot cup of chai". The next distribution, built on that text, favours tokens like `with` ("chai with a samosa…").
2. **Run B** draws `coffee` at the same step. The text is now "…a hot cup of coffee". The next distribution is *different* — it now favours tokens like `from` ("coffee from a South Indian filter…").
3. From this point the two runs are answering from different sentences. Every later draw works on different text, so the answers drift further and further apart.

An answer of 200 tokens is 200 consecutive draws, and the first draw that lands differently re-routes everything after it. This is the precise machinery behind the "probabilistic system" label from topic 1.3 — and notice how the shape idea from section 5.2 plugs in. On a sharp distribution the lottery is nearly rigged, so runs rarely diverge; on a flat one, divergence is almost guaranteed by the second sentence.

What does sampling buy in exchange for giving up repeatability?

- **Variety.** Ask for ten tea stall names, get ten different names.
- **Natural-sounding text.** Human writing is not a chain of maximum-probability words. Sampled text mirrors the genuine spread of human language better than greedy text does.
- **Escape from loops.** A random draw breaks the repetition traps that greedy decoding falls into.

The cost is the obvious one: you cannot promise the same output twice. Whether that cost is acceptable depends entirely on the task — which is exactly why engineers wanted a knob to tune the trade-off.

### 5.5 Temperature: the knob on the lottery

**Temperature** is a number, set when the model is called, that reshapes the probability distribution *before* the lottery draw. It does not touch the model's parameters and it does not add or remove knowledge. It only redistributes the raffle tickets.

The intuition behind the name comes from physics: heat particles and they jump around more; cool them and they settle down. Same idea here:

- **Low temperature** = calm and predictable. The rich get richer: high-probability tokens get an even larger share of tickets, long-shot tokens get squeezed toward zero. The draw almost always lands on a top pick.
- **High temperature** = energetic and adventurous. The gap narrows: strong tokens lose some tickets, weak tokens gain some. Long shots win noticeably more often.
- **Temperature around 1** = leave the distribution exactly as the model produced it.

Here is the breakfast distribution at three temperatures (numbers simplified for illustration):

| Candidate | Low temp (0.2) | Neutral (1.0) | High temp (1.5) |
|---|---|---|---|
| `chai` | 93% | 62% | 44% |
| `tea` | 5% | 21% | 24% |
| `coffee` | 2% | 12% | 18% |
| `milk` | <1% | 4% | 9% |
| `soup` | <1% | 1% | 5% |

Read the table row by row. `chai` is the favourite in every column — temperature never changes the *ranking*, only the *margins*. At 0.2 the lottery is nearly a formality; at 1.5 even `soup` wins one run in twenty.

In the physics picture: low temperature makes an already-sharp distribution sharper still, and high temperature flattens even a sharp one. Temperature and distribution shape (section 5.2) are two hands on the same dial — the model's context sets the starting shape, and temperature then exaggerates or relaxes it.

Two ends of the dial deserve special mention:

- **Temperature 0** is defined as "skip the lottery, take the top token" — it collapses sampling into greedy decoding. People say "temp 0 for reproducible output" for exactly this reason.
- **Very high temperatures** (most systems cap the dial around 2) flatten the distribution so much that barely-fitting tokens win often. The output drifts from "creative" through "weird" into genuinely incoherent word salad. More temperature is not more intelligence; past a point it is just more noise.

What about the middle of the dial? There is no formula that says a task needs exactly 0.4 rather than 0.6. Practitioners pick a starting value from rules of thumb (you will see a table of them in Real-World Patterns), try it, and adjust based on the outputs. Treat published values as starting points, not laws.

Temperature is not the only knob on the lottery. A sibling control called **top-p sampling** trims the raffle before the draw: it keeps only the smallest group of top tokens whose probabilities add up to *p* (say, 90%) and discards the long tail of barely-plausible ones. Many APIs put top-p right next to temperature in the same settings panel, and the two do the same family of job from different angles — temperature reshapes the odds, top-p shortens the guest list. Temperature remains the main dial in this topic, but when you see top-p beside it, you now know what it is doing.

### 5.6 What temperature is NOT

Temperature is one of the most misunderstood settings in AI. Three corrections, each worth holding onto:

1. **Low temperature is not a truth guarantee.** If the model's distribution puts 70% on a *wrong* token — because its training data was wrong or the question is beyond it — then low temperature picks that wrong token *more* reliably. Temperature 0 means "confidently consistent," and a model can be confidently, consistently mistaken. A confidently wrong answer does not go away because the dial went down; it just gets repeated word-for-word. Worse, the polished consistency can *feel* like accuracy to a reader — the same wrong claim, stated identically every time, looks authoritative. (This failure mode, called hallucination, is topic 3.9.)
2. **High temperature is not human-style creativity.** The model does not "feel inspired" at temperature 1.5. It is simply allowed to pick lower-ranked tokens more often. That *often* reads as creative variety — but the model has no creative intent, only a flatter raffle.
3. **Temperature 0 is not a perfect repeatability switch in practice.** In theory, temp 0 plus the same input gives identical output forever. In real deployed systems, tiny engineering details — such as how the provider's hardware rounds numbers when many requests are processed together — can still produce occasional small variations. Treat temperature 0 as "as repeatable as it gets," not as a notarized guarantee.
4. **Sampling does not mean the model is guessing blindly.** Hearing "the answer is drawn by lottery" can make the whole system sound like a coin flip. It is not. The lottery is *weighted by everything the model learned in training* — the parameters from topic 3.5 decide who gets the tickets, and a token the model considers absurd holds essentially none. Randomness chooses *among the model's good options*; it does not invent options the model never rated.

### 5.7 So why build it this way at all?

Step back and ask the design question directly: engineers *could* ship every model at temperature 0 and make AI behave like a calculator. Why don't they?

Because for most things people use a language model for — drafting messages, brainstorming, explaining, conversing — there is no single correct output. There are thousands of good outputs. Sampling lets the model explore that space; greedy decoding would lock it onto one bland path.

There is also a subtler reason. A model that answered every question with frozen, identical wording would *feel* mechanical in conversation, and its one chosen phrasing would not even be reliably "the best" — just the locally safest, with all of greedy decoding's blind spots baked in permanently. Sampling at a moderate temperature is, in practice, both more pleasant and often higher quality.

So the variation you noticed in week 1 is not a defect that slipped past testing. It is a deliberate trade: a controlled dose of randomness, in exchange for variety, naturalness, and usefulness — with temperature as the dose control.

## 6. Implementation

You can hold the entire mechanism in your head as one numbered procedure. This is what happens for **every single token** of every answer you have ever received from a chatbot:

1. **Score.** The model's parameters process all text so far and assign a raw score to every token in its vocabulary. (Deterministic — same input, same scores.)
2. **Apply temperature.** Each raw score is divided by the temperature value. Dividing by a small number (low temp) stretches the gaps between scores apart; dividing by a large number (high temp) squashes the scores closer together.
3. **Convert to percentages.** The adjusted scores are turned into a probability distribution — percentages over all tokens, summing to 100%. Stretched gaps from step 2 become lopsided percentages; squashed gaps become even ones.
4. **Draw.** One token is selected. At temperature 0, skip the lottery and take the top token. Otherwise, hold the weighted lottery: each token's chance of selection equals its percentage.
5. **Append and repeat.** The drawn token is added to the text, and the loop returns to step 1 — with a distribution that now reflects the token just drawn — until the model produces its stop signal.

### Worked micro-example: the temperature arithmetic

Say two tokens have raw scores 4 and 2.

- **At temperature 0.5:** divide both by 0.5 → scores become 8 and 4. The gap *between* them grew from 2 to 4. After converting to percentages, the leader's share is now overwhelming — the draw is nearly a sure thing.
- **At temperature 2.0:** divide both by 2.0 → scores become 2 and 1. The gap shrank to 1. The percentages land much closer together — the underdog wins far more often.

That is genuinely all the mathematics temperature involves: one division, applied to every score, before the percentages are computed. The drama is entirely in what that one division does to the gaps.

### Worked end-to-end example: three tokens of one answer

Prompt: *"Suggest a name for a tea stall."* Temperature: 1.0. Watch three steps of the loop:

1. **Step 1 — Score & draw.** The distribution over first tokens is flat (a naming task — section 5.2). Top candidates might be `Chai` (5%), `The` (4%), `Cutting` (3%), and thousands of others. The lottery draws `The`.
2. **Step 2 — Re-score on new text.** The text is now "The". The next distribution, conditioned on it, favours nouns and adjectives that start stall names: `Steeping` (6%), `Morning` (5%), `Chai` (5%)… The draw lands on `Steeping`.
3. **Step 3 — Again.** The text is "The Steeping". Now the distribution is *sharp* — after that adjective, few tokens fit, and `Story` might hold 40%. The draw, very likely, returns `Story`.

Result: "The Steeping Story." Run the same prompt again and step 1 alone will probably go elsewhere — `Chai` first, say — and a completely different name unfolds. Notice how the loop alternates between flat moments (real forks in the road) and sharp moments (the language almost completes itself). Sampling rides both.

### A common confusion, answered

*"Does the model re-think its whole answer at a different temperature?"* No. The model's thinking — the scoring in step 1 — is identical at every temperature. Temperature only intervenes between the scores and the draw (steps 2–4). Two runs at temperature 0.2 and 1.5 start from the *same* scored list at the first token; they simply hold differently shaped lotteries over it.

One sentence of caution about where the steps live: everything above happens at **inference** time, on every request. Nothing here retrains the model or alters its parameters. Change the temperature and you change only how the next draw is held — the model's knowledge is untouched.

## 7. Real-World Patterns

- **Consumer chatbots ship with a moderate default.** When you use a chat app, you are usually sampling at a provider-chosen temperature in the rough neighborhood of 0.7–1.0 — warm enough for natural variety, cool enough to stay on track. That default, not anything mystical, is why your week-1 experiment produced different tea stall names.
- **The "regenerate" button is just a re-draw.** When you click regenerate, the app re-runs the same lottery on the same question. New draws, new answer. At temperature 0, regenerate would hand back (almost always) the identical text — which is precisely why apps aimed at open-ended conversation don't run at 0.
- **Builders set the dial per task.** Developers calling a model through an API (Application Programming Interface — a way for one program to request work from another over the internet) pass temperature as an explicit setting per request. Typical working pattern:

| Task | Typical temperature | Why |
|---|---|---|
| Extracting data into a fixed format | 0 – 0.2 | Any variation is a bug |
| Customer-support answers | 0.2 – 0.5 | Consistent, on-policy phrasing |
| General chat and explanation | 0.6 – 1.0 | Natural, varied conversation |
| Brainstorming, naming, fiction | 1.0 – 1.5 | Diversity is the whole point |

- **One product, many dials.** Real applications often mix settings inside a single feature. A travel app might draft an itinerary description at temperature 1.0 (engaging prose) but extract the dates and prices from a booking email at temperature 0 (any variation there corrupts data). Knowing that the dial exists per-request, not per-model, is what makes this pattern possible.
- **Same model, different personalities.** Two apps built on the *same* underlying model can feel noticeably different — one crisp and repetitive, one chatty and surprising — purely because their builders chose different temperatures (along with different instructions). When you compare AI products, remember you are never comparing bare models; you are comparing models *plus* their sampling settings.
- **Testing AI systems means accounting for sampling.** Anyone evaluating an AI product quickly learns that a single run proves little: a good answer might have been a lucky draw, and a bad one an unlucky draw. Serious evaluation either pins temperature to 0 (test the model's top-line behavior, repeatably) or runs the same question many times and looks at the spread. Keep this in mind whenever you probe a model's capabilities yourself — one trial is an anecdote, not a measurement.

## 8. Best Practices

- **Match the dial to the cost of variation.** Before picking a temperature, ask one question: *if two runs differ, is that a feature or a bug?* Feature → raise it. Bug → lower it. That single heuristic covers most real decisions. A contract summary, a price extraction, a compliance answer — variation is a bug, go low. A slogan, a story, a list of ideas — variation is the product, go high.
- **Don't reach for temperature to fix wrong answers.** Lowering temperature makes answers more *repeatable*, not more *correct*. If the model is factually wrong at temp 1, it will usually be identically wrong at temp 0. Wrongness is a knowledge problem; temperature is a variability problem. Keep the two diagnoses separate.
- **Don't equate "different answers" with "broken model."** Variation within a sensible range is the system working as designed. The red flag is not difference in *wording* across runs — it is difference in *facts* across runs, which signals the model is guessing in that territory (a flat distribution where a sharp one should be).
- **When comparing prompts or models, freeze what you can.** Comparing two prompts while sampling at high temperature confounds your experiment: you can't tell whether the better output came from the better prompt or the luckier draw. Lower the temperature, or repeat each condition several times, before concluding anything.
- **Use re-draws deliberately for creative work.** For brainstorming, don't settle for the first output — regenerate several times and harvest the best draws. The lottery is working for you; pull the handle more than once.
- **Note the settings when you share results.** "The model said X" means little without "at what temperature, on which try." When you report or compare AI outputs — in a journal, a test log, or a team discussion — record the temperature (if known) and how many runs you did.
- **Expect the dial to be hidden in consumer apps.** Chat websites rarely expose temperature; developer APIs and "playground" consoles almost always do. If you can't find the setting, you're using a product that chose it for you — the mechanism is still running underneath.

## 9. Hands-On Exercise

A 10-minute experiment that makes sampling visible with nothing but a chat window:

1. Ask a chatbot a **creative** question — "Suggest a name for a tea stall" — five times, each in a fresh chat. Record all five answers.
2. Ask a **factual** question — "What is the chemical formula of water?" — five times, fresh chats again. Record the answers.
3. Compare the two lists. Count how many *distinct* answers each question produced. The creative answers should vary a lot; the factual ones should agree on content (H₂O) even when the wording shifts. Explain *why* using this topic's vocabulary: which prompt produced a flat distribution, which produced a sharp one, and what did that do to the draws?
4. If you have access to a playground or API console with a temperature slider, repeat step 1 at the lowest and highest settings and describe the difference in one sentence each.

## 10. Key Takeaways

- At every step of writing an answer, an LLM produces a **probability distribution** — a scored list of every possible next token — and that list itself is fully deterministic.
- Different answers to the same question come from **sampling**: the next token is drawn by weighted lottery from that list, and a 200-token answer is 200 fresh draws, where one different draw re-routes everything after it.
- Distribution **shape** matters: sharp distributions (facts) barely vary under sampling, while flat distributions (creative tasks) diverge run after run — even at identical settings.
- **Greedy decoding** (always take the top token) makes output repeatable but bland, repetitive, and short-sighted; sampling trades repeatability for variety and naturalness.
- **Temperature** reshapes the distribution before the draw: low values concentrate probability on the favourites (predictable output, temperature 0 ≈ greedy), high values flatten it (varied output, eventually incoherence). It never changes the ranking, the parameters, or the model's knowledge.
- Low temperature buys consistency, **not correctness** — a model that is wrong stays wrong at temperature 0; it just becomes reliably, repeatably wrong.

## 11. Next Steps

_System-derived from the next entry in curriculum/sequence.md._
