# What makes a good specification — testable, bounded, observable, actionable

## Overview

A specification is the instruction you give before work begins — it tells a system, a person, or an AI tool exactly what result you want. In Week 1, you learned that algorithms must be finite (have an end), definite (have clear steps), and produce an output you can see. You also learned that AI does not always give the same answer twice — give it the same input today and tomorrow, and you may get two different results. This matters because a vague instruction given to a regular program produces the same wrong result every time, but given to an AI, it produces a different wrong result every time — which is much harder to fix. That is why a good specification must have four properties — testable, bounded, observable, and actionable — to turn a vague request into an instruction clear enough to check, repeat, and improve.

## Key Concepts

Think of these four properties as four checks you run before giving an AI any instruction. Missing even one is usually why AI output does not turn out the way you hoped — not because the AI is broken, but because the instruction was not clear enough.

### 1. Testable

A testable specification means you can check — after the AI finishes — whether the output is correct or not. You need a clear pass or fail condition.

- **Good:** "List 3 things I need to bring on a camping trip, each in one sentence." You can count the items and check the length — done.
- **Bad:** "Tell me what to bring camping." How many items? How much detail? There is no way to know if it gave you enough.

### 2. Bounded

A bounded specification sets clear limits so the AI only works on what you asked for — nothing more, nothing less.

- **Good:** "Fix only the spelling mistakes in the first paragraph; do not change any words or sentences." The AI knows exactly what to fix and what to leave alone.
- **Bad:** "Fix my paragraph." The AI might rewrite your sentences, change your words, or restructure things you never wanted touched.

### 3. Observable

An observable specification produces output you can read and verify straight away — no guessing or extra work needed to figure out if the task succeeded.

- **Good:** "Give me 3 reasons why students procrastinate, one reason per line." You can read the three lines immediately and check if it answered the question.
- **Bad:** "Explain why students procrastinate." The AI might write three paragraphs of general advice and you are left wondering — did it actually answer what I asked?

### 4. Actionable

An actionable specification gives the AI enough detail to start the task immediately, without having to guess what you mean.

- **Good:** "Rewrite this sentence in simple English so a 10-year-old can understand it, and keep it under 20 words." The AI knows exactly what to do and where to stop.
- **Bad:** "Make this sentence better." Better how? Shorter? Simpler? More formal? The AI will guess — and its guess may not match what you wanted.

## Worked Example

Let's take a bad instruction and fix it step by step.

**Starting point:** "Make this email better." — this is a poor specification. You cannot check if it worked, there are no limits on what to change, and the AI has no idea what "better" actually means to you.

Here is how to fix it, one step at a time:

1. **Give it a direction →** "Rewrite this email to sound more professional."
2. **Set a limit →** "...keep it under 100 words."
3. **Add a clear rule →** "...do not change the deadline date."
4. **Tell it what to give back →** "...output only the rewritten email, no extra explanation."

**Final result:** "Rewrite this email to sound more professional, keep it under 100 words, do not change the deadline date, and output only the rewritten email with no extra explanation."

Now you can check it with four simple yes/no questions:
- Does it sound professional? ✓
- Is it under 100 words? ✓
- Is the deadline date unchanged? ✓
- Is it just the email text, nothing extra? ✓

If any answer is no — you know exactly what went wrong and how to fix it.

## In Practice

When writing a specification for any AI task, check it against the four properties before you submit it. Ask yourself these four questions:

- Can I tell if this worked or not? **(testable)**
- Did I set clear limits on what the AI should and should not touch? **(bounded)**
- Can I read the output and know straight away whether it worked? **(observable)**
- Does the AI have everything it needs to start without guessing? **(actionable)**

Missing any one of these is the most common reason AI output disappoints — not because the AI is bad at its job, but because the instruction was not clear enough.

Here are three common mistakes students make:

- **A longer instruction is not always a better one.** Only add extra words if they help with one of the four properties above. Words that do not do any of that just make your instruction harder to follow.
- **One good result does not mean your specification is reliable.** Because AI can give a different answer each time, getting a good output once does not mean it will work again next time. Always test your specification a few times before trusting it.
- **AI will not stop and ask you what you meant.** Unlike a classmate, it will not say "I am not sure what you mean here." It will silently fill in the gaps with its own guess and carry on — and you will have no idea it did that.

In the lab activity you will write five specifications across five different task types — a translation task, a summarization task, a rewriting task, a classification task, and a generation task. You will then score each one against the four properties. This exercise is direct preparation for A1, where you will submit a portfolio of ten specifications each with your own evaluation of how well it meets the four properties.

## Key Takeaways

- **Testable:** after every run, you should be able to clearly say — did this pass or fail? Include a condition you can check.
- **Bounded:** always set clear limits on what the AI should and should not touch, so it stays within the task you gave it.
- **Observable:** the output should be something you can read and check straight away — no digging through vague responses or guessing required.
- **Actionable:** give enough detail so the AI can start the task immediately without having to fill in the gaps itself.
- All four properties work together — your specification needs to satisfy all four to produce a reliable result every time, not just once.

## References

1. MIT Sloan Educational Technology, "Effective Prompts for AI: The Essentials." <https://mitsloanedtech.mit.edu/ai/basics/effective-prompts/>
2. Google Cloud, "What is Prompt Engineering?" <https://cloud.google.com/discover/what-is-prompt-engineering>
3. DAIR.AI, "Prompt Engineering Guide." <https://www.promptingguide.ai/>
