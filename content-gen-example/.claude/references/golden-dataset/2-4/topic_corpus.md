---
topic_id: "2.4"
title: "Pattern recognition — how machines find rules in repeated data"
position_in_module: 1
generated_at: 2026-06-11T00:00:00Z
resource_count: 0
---

# 1. Pattern recognition — how machines find rules in repeated data — Topic Corpus

## 2. Prerequisites

- **1.2 — Deterministic systems — same input always gives the same output.** You should know what it means for a system to behave the same way every time.
- **1.3 — Probabilistic systems — same input can give different outputs.** You should know that some systems work with likelihoods, not certainties.
- **1.9 — Algorithmic thinking — what makes a set of steps an algorithm.** You should know that an algorithm is a precise, ordered set of steps.
- **2.1 — What makes a good specification — testable, bounded, observable, actionable.** You should know what a specification is and what makes one good.
- **2.3 — How to identify the inputs, expected outputs, and failure conditions of a task.** You should know how to describe a task in terms of what goes in, what should come out, and what counts as failure.

## 3. Learning Objectives

After working through this topic, you will be able to:

- Explain, in plain language, what a pattern is and what pattern recognition means.
- Describe the difference between a rule a person writes down and a rule a machine finds by looking at many repeated examples.
- Walk through the three-step loop — see examples, form a rule, apply the rule to a new case — using an everyday example.
- Identify everyday systems whose behaviour comes from rules found in repeated data, such as autocomplete and spam filters.
- Explain why a rule found from examples can be wrong, and why that means machine output needs checking against a clear specification.

## 4. Introduction

Try this. What comes next in this sequence: 2, 4, 6, 8, ...?

You said 10. Nobody told you the rule. You looked at a few repeated examples, noticed that each number goes up by 2, and used that rule to predict the next one. That whole move — examples in, rule out — took you about a second.

That move has a name: **pattern recognition**. It is something your brain does constantly, and it is also the core idea behind how modern AI systems behave. Your phone keyboard suggests your next word because it has seen which words usually follow each other. Your email app moves a message to spam because that message looks like thousands of spam messages seen before. Neither system was given a hand-written rule for your exact message. Each one found rules in repeated data.

This matters for this week's theme — specifying for AI. In topics 2.1 to 2.3 you learned how to write a good specification. This topic explains the other side of the conversation: what kind of "thinker" is on the receiving end of your specification. Once you see that machines work from patterns rather than understanding, you will see exactly why vague instructions go wrong.

## 5. Core Concepts

### What is a pattern?

Start with the everyday meaning, because it is the right one.

**Pattern** — something that repeats in a predictable way.

Patterns are everywhere once you look:

- The chai stall near your bus stop is crowded every weekday at 8 a.m. and empty at 3 p.m.
- Your friend replies to messages instantly in the evening but never before noon.
- In the sequence 5, 10, 15, 20, each number is 5 more than the last.
- Most emails that say "CONGRATULATIONS! You have WON!" are junk.

Notice what all four have in common. None of them is a single event. A pattern only exists across **repeated** observations. One crowded morning at the chai stall is just a busy day. Twenty crowded mornings in a row is a pattern.

That word "repeated" is doing real work in this topic's title. Machines find rules in *repeated* data — one example is never enough.

### Pattern recognition — something you already do

**Pattern recognition** — noticing what repeats across many examples, and turning that repetition into a rule you can use on new cases.

Humans are pattern-recognition machines. Why does this matter? Because you already know how this works from the inside — you just have not named the steps. Here they are:

1. **See examples.** You observe many cases: twenty mornings at the chai stall.
2. **Form a rule.** You notice what repeats: "weekday mornings are crowded."
3. **Apply the rule to a new case.** Next Tuesday at 8 a.m., before you even arrive, you predict a crowd.

That third step has its own name, and it is the most important new term in this topic:

**Generalisation** — taking a rule found in past examples and applying it to a new case you have never seen before.

Generalisation is the payoff of pattern recognition. A rule that only describes the past is just a summary. A rule you can use on *tomorrow* is useful. When your keyboard suggests the next word of a sentence you have never typed before, that is generalisation: a rule found in old data, applied to a brand-new case.

### Two ways a machine can get a rule

In Week 1 you learned that an algorithm is a precise set of steps, and that a deterministic system follows its steps the same way every time. In that world, where does the rule come from? A person writes it.

There is a second way: instead of writing the rule, you show the machine many examples and let it find the rule that fits the repetition. These two approaches behave very differently, so put them side by side:

| | Rule written by a person | Rule found from repeated data |
|---|---|---|
| **Where the rule comes from** | A human thinks it up and writes it down | The machine spots what repeats across many examples |
| **Example** | "If the email sender is not in my contacts, flag it" | "Emails that look like these 10,000 junk emails are probably junk" |
| **Can you read the rule?** | Yes — it is written out, like pseudocode | Often not — the rule lives in the machine's internal patterns, not on paper |
| **What happens on a new case?** | Only handles cases the author thought of | Generalises — makes a best guess even on cases nobody anticipated |
| **Typical behaviour** | Deterministic — same input, same output | Often probabilistic — answers come as likelihoods, like "92% likely spam" |
| **How it fails** | Misses cases the author never imagined | Confidently follows a pattern that does not actually hold |

Both columns produce rules. The difference is who finds the rule, and whether anyone can read it afterwards.

The right column should ring a bell from topic 1.3. Systems built on found rules usually deal in likelihoods — "probably spam," "most likely next word" — which is exactly the probabilistic behaviour you met in Week 1. Pattern recognition is *why* those systems are probabilistic: a pattern is a strong tendency, not a guarantee, so the rule it produces speaks in probabilities too.

You'll meet machine learning properly in Week 3.

### How repeated data becomes a rule

Let's slow down the middle step — "form a rule" — because it is where the magic seems to be, and the magic turns out to be ordinary.

Take a concrete case. Suppose you collect 1,000 emails that a person has already sorted into "junk" and "not junk." Now look for what repeats on each side:

- The word "winner" appears in 40% of junk emails but only 1% of normal emails.
- Junk emails use far more ALL-CAPITAL words.
- Normal emails usually come from addresses the person has replied to before.

Each repetition becomes a clue, and the clues combine into a rule: *the more an email resembles the junk pile, the more likely it is junk.* No one wrote that rule line by line. It emerged from counting what repeats.

Why does more data help? Because repetition is the evidence. Three junk emails could share the word "winner" by pure coincidence. Ten thousand junk emails sharing it is no coincidence — it is a reliable signal. More repeated examples means more confidence that a pattern is real and not luck.

This is the honest, non-magical answer to the question in this topic's title. How do machines find rules in repeated data? They detect what repeats, measure how strongly it repeats, and turn the strongest repetitions into a rule for judging new cases.

### More than one rule can fit the same examples

Go back to the opening sequence: 2, 4, 6, 8. You said the rule was "add 2." But look again — at least two other rules fit those same four numbers perfectly:

- "List the even numbers in order."
- "Add 2, but after 8, start again from 2."

All three rules agree on the examples you saw. They disagree about what comes after 8 (the first two say 10; the third says 2). Why does this matter? Because the examples alone cannot tell you which rule is the true one. The data you have seen is always consistent with more than one rule, and the rules only part ways on cases you have not seen yet.

This is the deepest reason pattern recognition deals in likelihood rather than certainty. Whether the pattern-finder is you or a machine, it is always choosing among several rules that fit the past, betting that the simplest or strongest one will keep holding in the future. More repeated data shrinks the field of candidate rules — after seeing 2, 4, 6, 8, 10, 12, the "restart at 2" rule is dead — but no amount of data shrinks it to exactly one. There is always a leftover bet, and that bet is where wrong-but-confident answers come from.

### When found rules go wrong

Pattern recognition is powerful, but a pattern is only as good as the examples it came from. Found rules fail in three characteristic ways. Each one matters to you as a specifier, so learn all three:

1. **The pattern was a coincidence.** With too few examples, repetition can be luck. Flip a coin three times, get three heads, and the "pattern" says heads forever. The rule fits the past perfectly and the future not at all.
2. **The examples were not representative.** Suppose all 1,000 example emails came from one person's inbox. The found rule learns *that person's* junk. Apply it to a doctor's inbox — where "winner" might appear in genuine patient lottery-board newsletters — and it misfires. The rule is only as broad as the data it saw.
3. **The world changed.** Patterns describe the past. The chai stall rule breaks the day a new stall opens across the road. Junk-mail senders change tactics precisely to break the filters' found rules. A rule found in old data slowly goes stale.

There is a name for the first two failures combined:

**Overgeneralisation** — stretching a rule beyond the examples that justify it, so it confidently gives wrong answers on cases it never really learned about.

Notice the word "confidently." This is the unsettling part. A system using found rules does not know when it has left familiar territory. It applies its rule to every input you give it and produces an answer either way. The answer *looks* the same whether the pattern truly applies or not.

### Why this matters for specifying

Now connect this back to the week's theme. You write a specification; a pattern-based system carries it out. Knowing how that system "thinks" changes how you write.

- **The system fills every gap with a pattern.** When your instruction is vague, the machine does not stop and ask what you meant. It falls back on whatever is most common in the data it has seen — the most typical format, the most usual length, the most average tone. "Make it better" gets you the *average* idea of better, which you saw fail in topic 2.2.
- **A good specification overrides the default pattern.** "Rewrite at Grade 8 level, max 80 words" works because it pins down exactly the things the machine would otherwise pattern-guess. Every testable, bounded, observable detail from topic 2.1 is one less gap for a pattern to fill.
- **Confident output is not verified output.** Because found rules fail silently — coincidence, unrepresentative examples, a changed world — the failure conditions you learned to define in topic 2.3 are not optional paperwork. They are how you catch a pattern that did not hold.

Here is the one-sentence version to carry forward: **you are not instructing a mind that understands your intent; you are steering a system that completes patterns — so your specification must say everything that matters.**

## 6. Implementation

Pattern recognition has a procedure, and you can run it by hand. Doing it yourself once makes the machine version obvious. Here is the human-scale algorithm — notice it is a genuine algorithm in the Week 1 sense: finite, definite steps with an input and an output.

**Input:** a set of repeated examples. **Output:** a rule you can apply to new cases.

1. **Collect examples.** Gather as many cases as you can. (Sequence: 3, 6, 9, 12.)
2. **Look for what repeats.** Compare the examples and write down everything that holds across all of them. ("Each number is 3 more than the previous one." Also true: "every number is a multiple of 3.")
3. **State a candidate rule.** Pick the repetition that best explains the examples and phrase it precisely. ("Add 3 to get the next number.")
4. **Test the rule on a held-back case.** Take an example you deliberately did *not* use in steps 1–3 and check the rule predicts it. If the next number in your source is 15, the rule passes. If it is 24, your rule was a coincidence — go back to step 2.
5. **Use the rule on new cases — with appropriate confidence.** More examples passed in step 4 means more trust; few examples means treat every answer as a guess.

Step 4 is the step beginners skip and professionals never skip. A rule that merely fits the examples you looked at proves nothing — *any* coincidence fits the examples it came from. A rule that predicts a case it has never seen has demonstrated generalisation. Keep this test-on-unseen-cases habit; it returns in topic 2.8 when you test whether an AI did exactly what your specification asked.

## 7. Real-World Patterns

You met four of these systems in Week 1 as everyday algorithms. Look at them again with this topic's lens — each one behaves the way it does because of rules found in repeated data:

- **Keyboard autocomplete.** Across enormous amounts of typed text, certain words follow certain words far more often than chance. Type "good" and the suggestions "morning" and "night" appear — not because someone wrote a "good morning" rule, but because that pairing repeats massively. The suggestion is a generalisation from repetition.
- **Spam filtering.** Exactly the worked example from Core Concepts, running at scale: new message arrives, gets compared against the patterns of millions of sorted messages, lands in inbox or junk with a likelihood attached. Probabilistic behaviour, straight from topic 1.3.
- **Recommendations ("You may also like...").** Shopping and streaming apps notice repetition across customers: people who bought X very often bought Y. When you buy X, the found rule generalises to you. Sometimes it is uncannily right; sometimes it overgeneralises — you bought one baby gift and now everything is nappies. That misfire is overgeneralisation in the wild: one example, stretched into a rule.
- **Fraud alerts.** Your bank has seen the repeated shape of your spending — where, when, how much. A purchase that breaks your pattern triggers a call. Notice the inversion: here the system's job is spotting a case where the learned pattern *fails* to hold.

One observation ties all four together. Each system works extremely well on typical cases and gets strange on unusual ones — the unusual case is exactly where repeated data runs out and the found rule is guessing.

## 8. Best Practices

Heuristics for working alongside pattern-based systems — these follow directly from how found rules fail:

- **Do ask: "what examples would this system have seen?"** Output quality tracks the data behind it. Common, well-represented tasks get strong patterns; rare or novel tasks get guesses dressed up as answers.
- **Do treat fluent confidence as zero evidence of correctness.** A pattern-completing system sounds equally sure inside and outside its competence. Verify against your specification, not against the system's tone.
- **Do specify whatever you are not willing to leave to the average.** Every unstated requirement — format, length, level, audience — will be filled by the most typical pattern, not by your intent.
- **Don't assume a rule that worked last month still works.** Patterns go stale when the world changes. Re-test.
- **Don't trust a rule tested only on the examples that produced it.** Insist on step 4: held-back, unseen cases.

## 9. Hands-On Exercise

A 15-minute spot-the-rule drill, done with a classmate. Each of you writes three number sequences, each following a secret rule (e.g. "double the previous number"), showing only the first four terms. Swap papers. For each sequence: write down the rule you think governs it, predict the fifth term, then check with the author. For any miss, identify which failure it was — too few examples, or two different rules both fitting the visible terms? Notice how confident you felt just before being wrong. That feeling is overgeneralisation from the inside.

## 10. Key Takeaways

- A pattern is something that repeats across many observations; pattern recognition turns that repetition into a rule, and generalisation applies the rule to new, unseen cases.
- Machines get rules two ways: a person writes them explicitly, or the machine finds them by detecting what repeats in large amounts of example data.
- Found rules are evidence-based guesses — they typically speak in likelihoods, which is why pattern-based systems are probabilistic rather than deterministic.
- Found rules fail silently through coincidence, unrepresentative examples, or a changed world — and the system sounds exactly as confident when it is wrong.
- Because a pattern-based system fills every gap in your instructions with its most typical pattern, a precise specification — testable, bounded, observable — is how you keep control of the output.

## 11. Next Steps

_System-derived from the next entry in curriculum/sequence.md._
