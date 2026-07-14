---
topic_id: 1.8
title: Flowcharts — visualising logic with standard shapes and arrows
position_in_module: 2
generated_at: 2026-06-11T00:00:00Z
resource_count: 0
---

# Flowcharts — visualising logic with standard shapes and arrows — Topic Corpus

## Prerequisites

- **1.5 — Decomposition — breaking a big problem into smaller solvable parts.** A flowchart shows the small steps you get after decomposing a problem. You need to be comfortable breaking a task into steps before you can draw it.
- **1.7 — Pseudocode — writing logic in plain English before writing code.** Flowcharts and pseudocode express the same thing — a sequence of steps and decisions — in two different forms. This topic builds directly on the IF / OTHERWISE and REPEAT ideas from pseudocode.

It also helps to remember **1.6 — Abstraction — hiding complexity at the right level**, because a good flowchart hides detail just like a good summary does.

## Learning Objectives

- Explain what a flowchart is and why it uses standard shapes instead of free-form drawings.
- Identify the five core flowchart symbols — terminator, process, input/output, decision, and arrow — and state what each one means.
- Read an unfamiliar flowchart and trace the path from Start to End, including through decisions and loops.
- Draw a flowchart for an everyday task, with exactly one Start, at least one decision, and labelled branch arrows.
- Compare flowcharts and pseudocode, and choose which form fits a given situation.

## Introduction

Imagine you wrote step-by-step instructions for making a cup of tea, in plain English, and handed them to a friend. They follow them word for word — and get stuck at step 4, because "boil the water if needed" left them asking: *needed by whom? How do I know?* You meant something perfectly clear in your head. On paper, it was ambiguous.

In the last topic you learned pseudocode — writing logic in plain English, one step per line. Pseudocode fixes a lot of that ambiguity. But there is one thing a list of text lines is bad at: **showing you the shape of the logic at a glance**. Where does the path split in two? Where does it loop back? In text, you have to read every line to find out. In a picture, you can see it instantly.

That picture is a **flowchart** — a diagram that shows the steps of a process as boxes, the decisions as diamonds, and the order as arrows. Flowcharts use a small set of *standard* shapes, agreed on for decades, so that anyone who knows the shapes can read anyone else's chart. This topic teaches you those shapes, how to read a flowchart someone else drew, and how to draw your own.

## Core Concepts

### What a flowchart is

A **flowchart** — a diagram that represents a process as shapes connected by arrows, where each shape is one step or decision and the arrows show the order you move through them.

Think of it as a map of your logic. Pseudocode tells you the steps in words; a flowchart *shows* you the route. You put your finger on Start, follow the arrows, do what each shape says, and lift your finger at End. If two people put their fingers on Start and follow the arrows honestly, they take the same route through the same steps — the chart leaves no room for "I assumed you meant…".

Why does this matter for you, here, in this course? Because before you can hand a task to a computer — or to an AI system — you have to be able to state the logic so clearly that *nothing is left to interpretation*. You practised that with decomposition (breaking the task down) and pseudocode (writing the steps). The flowchart is the third tool in that same kit: it makes the *structure* of the logic visible, so gaps and ambiguities jump out at you.

### Why standard shapes?

You could draw a process with any doodles you like — clouds for steps, stars for decisions. The problem is that nobody else would know your private code. Flowcharts solve this the same way road signs do.

A red octagon means "stop" everywhere, so drivers never have to guess. Flowchart shapes work the same way: **each shape has one fixed meaning**, so the shape itself carries information before you even read the text inside it. When you see a diamond, you know — before reading a word — that the process is about to split into two paths. That instant, shared understanding is the whole point of using standard shapes.

### The five core shapes

There are many flowchart symbols in the world, but five of them cover almost everything you will ever need. Learn these five and you can read most flowcharts ever drawn.

| Shape | Name | Meaning | Example text inside |
|---|---|---|---|
| Oval (rounded ends) | **Terminator** | Where the process starts or ends | "Start", "End" |
| Rectangle | **Process** | One action — something gets *done* | "Boil the water" |
| Parallelogram (slanted rectangle) | **Input/Output** | Information comes in or goes out | "Ask guest: milk?", "Serve the tea" |
| Diamond | **Decision** | A yes/no question — the path splits here | "Is the kettle empty?" |
| Arrow | **Flowline** | The order — which shape comes next | (label "Yes" / "No" on decision branches) |

Each one, in plain language:

1. **Terminator** — the oval. Every flowchart has exactly one "Start" oval, so a reader always knows where to put their finger first. It ends with an "End" oval (occasionally more than one, but one is cleaner). Without terminators, a chart is like a board game with no starting square.
2. **Process** — the rectangle. One rectangle holds one action: *boil the water*, *add the teabag*, *send the email*. If you find yourself writing two actions in one rectangle ("boil water and pour it"), that is a sign to split it into two rectangles — the same instinct as one-step-per-line in pseudocode.
3. **Input/Output** — the parallelogram. This is where the process talks to the outside world. Information coming *in* (asking the guest a question, reading a form) or going *out* (serving the tea, displaying a result) goes in a parallelogram. It is still a step, but a special kind: the process is exchanging information, not just doing work.
4. **Decision** — the diamond. The most important shape in the set. A diamond holds a question with a yes/no answer, and it is the *only* shape where the path splits. One arrow leaves for "Yes", a different arrow leaves for "No". This is the picture version of pseudocode's IF / OTHERWISE: IF the kettle is empty, fill it; OTHERWISE, carry on.
5. **Flowline** — the arrow. Arrows connect everything and give the chart its order. An arrow means "when you finish this shape, go to that one". The arrowhead matters: it points the direction of travel, so there is never any doubt about which way the logic flows.

### Arrows and direction of flow

Arrows look like the simplest part, but they carry real rules:

- **Follow the arrowheads, always.** You never travel backwards along an arrow.
- **By convention, charts flow top-to-bottom** (sometimes left-to-right). Readers expect Start near the top. A chart that wanders all over the page is technically valid but painful to read — like a paragraph with the sentences shuffled.
- **Every shape needs a way in and a way out** (except Start, which has no way in, and End, which has no way out). A rectangle with no outgoing arrow is a dead end: the reader gets there and has nowhere to go. Dead ends are bugs in your logic, and the flowchart makes them visible.

### Decisions create branches

Here is a small example. Suppose your tea-making chart reaches the question "Is there milk in the fridge?"

- The diamond holds the question: *Milk in fridge?*
- One arrow leaves the diamond labelled **Yes** → rectangle: *Add milk*.
- Another arrow leaves labelled **No** → rectangle: *Serve it black*.
- Both arrows eventually meet again and continue to *End*.

This split-into-two-paths is called a **branch** — the point where a process takes one route or another depending on an answer. Branching is what makes flowcharts more than pretty to-do lists. A plain numbered list can only describe one fixed sequence. The moment your logic contains "it depends…", you need a branch — and the diamond is how you draw it.

Why must the question be yes/no? Because two clearly-labelled exits are easy to follow and impossible to misread. If your question has three answers ("small, medium, or large?"), you can chain two diamonds: first *Small?* — Yes/No — then *Medium?* — Yes/No. Two simple diamonds beat one confusing one.

### Loops — arrows that go back

What if a step needs to repeat? Say you are waiting for the kettle: *check the kettle — boiled yet? If not, wait and check again.*

In a flowchart, repetition is drawn with an arrow that **loops back** to an earlier shape:

- Diamond: *Water boiled?*
- **No** → rectangle: *Wait 30 seconds* → arrow pointing back **up** to the *Water boiled?* diamond.
- **Yes** → continue onward to *Pour water into cup*.

The reader's finger literally goes around in a circle — check, wait, check, wait — until the answer flips to Yes. This is the picture version of pseudocode's REPEAT idea. And notice something the picture gives you for free: a loop is *visible as a circle on the page*. In text, you might not notice a repeated cycle; in a flowchart, you cannot miss it.

One caution the shape makes obvious: every loop needs a way out. If the "No" path loops back but nothing in the world can ever turn the answer to "Yes", the reader circles forever. Spotting a loop with no exit is one of the most valuable things a flowchart helps you do — the trap is right there in the drawing.

### Reading a flowchart you did not draw

Before you draw your own charts, practise reading other people's. The method is simple and physical:

1. **Find the Start oval.** Put your finger on it.
2. **Follow the arrow out.** Do (or imagine doing) whatever the next shape says.
3. **At a diamond, answer the question honestly** for the scenario you are imagining, then take the matching labelled arrow — Yes or No.
4. **Keep going until your finger reaches End.**

This finger-trace is called **tracing** — following one specific scenario through the chart, step by step, exactly as drawn. Tracing is how you check a chart, and it is also how you *test* one: trace it twice with two different scenarios (the kettle was empty; the kettle was full) and see whether both paths behave sensibly.

Why be this literal about it? Because the value of a flowchart is that it removes guessing. The moment you "fill in" a missing step from common sense, you have stopped reading the chart and started reading your own head — and that is exactly the habit this module is training you out of. A machine following instructions cannot fill gaps from common sense; tracing with strict honesty shows you what your logic looks like to a reader who can't either.

### Flowcharts vs pseudocode — two views of the same logic

Flowcharts do not replace pseudocode. They are two views of the same logic, each better at different jobs:

| | Pseudocode | Flowchart |
|---|---|---|
| Form | Plain-English text, one step per line | Shapes and arrows on a page |
| Best at | Lots of detailed steps; easy to write and edit quickly | Showing branches and loops at a glance |
| Branching | IF / OTHERWISE lines you must read to find | A diamond you can spot across the room |
| Audience | People comfortable reading structured text | Almost anyone — including non-technical readers |
| Weakness | Structure is invisible until you read every line | Gets crowded if the process has very many steps |

A practical rule of thumb: **sketch the flowchart to get the structure right, and write pseudocode when the step-by-step detail matters.** Many people do both — chart first, then pseudocode — and you will too in this course.

## Implementation

How do you actually draw a flowchart from scratch? Follow these steps. (Use paper, a whiteboard, or a drawing tool — in this course's labs you will use Excalidraw, a free online drawing tool with these shapes built in.)

1. **Name the process and its boundaries.** One sentence: what does this process do, where does it begin, where is it finished? Example: "Make a cup of tea, from walking into the kitchen to handing over the cup."
2. **Decompose first, on a scratch list.** Write the steps as a quick plain-English list — exactly the decomposition skill from earlier. Do not draw anything yet.
3. **Mark the questions.** Go through your list and circle anything that is a question or an "it depends". Each one will become a diamond. *Is the kettle empty? Does the guest want milk?*
4. **Draw Start.** One oval at the top: *Start*.
5. **Add shapes one step at a time, top to bottom.** Actions become rectangles. Questions become diamonds with two labelled exits (Yes / No). Asking or telling a person something becomes a parallelogram.
6. **Wire every branch to a destination.** Both the Yes arrow and the No arrow must lead somewhere — to another step, back up to an earlier shape (a loop), or onward to the end. No dead ends.
7. **Draw End.** One oval at the bottom: *End*. Check that every possible path through the chart can actually reach it.
8. **Test it with a trace.** Put your finger on Start and walk a pretend scenario through the chart: kettle was empty, guest wanted milk, no milk in the fridge. Does your finger always know where to go next? Then trace a *different* scenario. If your finger ever gets stuck or has a choice the chart doesn't answer, fix the chart.

Worked example — the full tea chart in text form (each line is one shape):

```
(Start)
  ↓
[Is the kettle empty?]            ← diamond
  Yes → [Fill the kettle]         ← rectangle, then arrow rejoins below
  No  → ↓
[Switch the kettle on]
  ↓
[Water boiled?]                   ← diamond
  No  → [Wait 30 seconds] → back up to "Water boiled?"   ← a loop
  Yes → ↓
[Put teabag in cup]
  ↓
[Pour water into cup]
  ↓
/Ask guest: milk?/                ← parallelogram (input)
  ↓
[Guest said yes?]                 ← diamond
  Yes → [Add milk]
  No  → ↓
/Serve the tea/                   ← parallelogram (output)
  ↓
(End)
```

Trace it yourself with a scenario: kettle already full, water boils on the second check, guest says no to milk. Your finger should pass through exactly: Start → kettle empty? No → switch on → boiled? No → wait → boiled? Yes → teabag → pour → ask → said yes? No → serve → End. If you followed that without guessing once, the chart did its job.

## Real-World Patterns

Flowcharts are not just a classroom exercise — they are one of the most widely used diagrams in working life, precisely because non-technical people can read them:

- **Troubleshooting guides.** "Printer not working? Is it plugged in? — Yes → Is the light on? …" Support teams publish these as flowcharts because a stressed reader can follow arrows even when they cannot follow prose.
- **Business process documents.** How a refund gets approved, how a job application moves through a company. The diamonds show exactly who decides what, and where a request can be rejected.
- **Medical and emergency checklists.** First-aid decision charts ("Is the person responsive? Yes/No…") use the same diamond-and-branch structure, because under pressure a picture beats a paragraph.
- **Planning logic before building anything.** Engineers and designers sketch a flowchart of a process *before* it exists, to argue about the logic while it is still cheap to change. This is exactly how you will use them in this course: charting the logic of a task before any system is built to perform it.

## Best Practices

Do:

- **One Start, one End.** A reader should never wonder where to begin or whether they are finished.
- **One action per rectangle.** "Boil water and pour it" is two steps wearing one box.
- **Label every branch arrow.** A diamond with two unlabelled exits is a coin flip, not a decision.
- **Flow top-to-bottom.** Honour the reading direction your reader expects.
- **Keep decisions yes/no.** Chain diamonds rather than inventing three-way exits.
- **Trace before you share.** Walk at least two different scenarios through the chart with your finger.

Avoid these anti-patterns:

- **Dead ends** — a shape with no outgoing arrow. Your reader arrives and is stranded.
- **Loops with no exit** — a "No" path that circles back with no way for the answer to ever become "Yes".
- **The wall-of-boxes chart** — 40 shapes crammed on one page. Use abstraction: collapse a detailed sub-task into one rectangle ("Prepare the kettle") and chart its details separately if needed. This is exactly the hiding-complexity skill from topic 1.6.
- **Decorative shape-picking** — using a diamond because it "looks nice" for a step that isn't a question. Each shape has one meaning; respect it or the chart lies to its reader.

## Hands-On Exercise

1. Pick a real-world task you do often — making breakfast, getting ready for work, deciding what to watch.
2. Decompose it into a scratch list of steps; circle every "it depends" moment.
3. Draw the flowchart in Excalidraw (or on paper): one Start oval, rectangles for actions, diamonds with labelled Yes/No arrows, one End oval. Include at least one decision and, if the task has any waiting or repeating, one loop.
4. Swap with a partner. They trace your chart with a finger, doing *exactly* what it says — no guessing, no filling gaps from common sense.
5. Note every place they got stuck or had to ask you a question. Each one is an ambiguity your chart let through. Fix it and trace again.

## Key Takeaways

- A flowchart is a diagram of a process: shapes are steps and decisions, arrows are the order — a map of your logic that two readers will follow the same way.
- Five shapes cover nearly everything: oval (start/end), rectangle (action), parallelogram (input/output), diamond (yes/no decision), arrow (flow).
- Shapes are standardised like road signs — each has one fixed meaning, so the shape communicates before you read the text inside it.
- Diamonds are where paths branch; an arrow looping back to an earlier shape is how repetition is drawn — and every loop needs a way out.
- Flowcharts and pseudocode express the same logic: the chart shows structure at a glance, the text carries fine detail. Sketch the chart first, then write the steps.

## Next Steps

_System-derived from the next entry in curriculum/sequence.md._
