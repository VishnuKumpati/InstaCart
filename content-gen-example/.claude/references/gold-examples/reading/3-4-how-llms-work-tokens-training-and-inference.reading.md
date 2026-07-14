# How LLMs Work — Tokens, Training, and Inference

## Overview

Every time you interact with a Large Language Model (LLM), a series of processes take place behind the scenes. First, your input is converted into **tokens**, which are small units of text that the model can process. These tokens are then passed through billions of learned parameters, allowing the model to analyze patterns and relationships in the data. Finally, the model generates a response one token at a time.

Understanding how tokens, training, and inference work is important because these processes determine how LLMs generate responses, what they can do effectively, and where their limitations come from.

This topic explores the internal workings of LLMs, from how text is represented and processed to how models are trained and how responses are generated. Understanding these concepts will help you better evaluate the capabilities, performance, and limitations of modern AI systems.

This topic also supports **A1 — Specification Portfolio (due W3, 10%)** by providing the technical foundation needed to understand how LLM-based tools operate and how their design influences their behavior.

## Key Concepts

### Tokens: What the Model Actually Sees

An LLM does not read text the way humans do. Instead, it processes **tokens**, which are small units of text.

A **token** can be:

- A complete word (e.g., *cat*)
- Part of a word (e.g., *un*, *believ*, *able*)
- A single character or symbol for less common text

Before processing any input, the model uses a **tokenizer** to convert text into tokens. Each token is then assigned a unique numerical ID from the model's **token vocabulary**. The model works with these IDs rather than raw text.

Using tokens instead of whole words allows the model to handle multiple languages, new words, technical terms, and even spelling mistakes without requiring an extremely large vocabulary.

After tokenization, each token is converted into a numerical representation called an **embedding**. These embeddings capture information about the token's meaning and its relationship to other tokens in the text.

The maximum number of tokens a model can process at one time is called the **context window**. This includes the user's input, previous conversation history, system instructions, and the model's generated response. Any information outside the context window cannot be accessed by the model.

### Pre-Training: Where Knowledge Comes From

An LLM gains most of its knowledge during a process called **pre-training**.

During pre-training, the model is exposed to massive amounts of text collected from sources such as websites, books, research papers, code repositories, and encyclopedias. The model learns by repeatedly performing a simple task:

> Predict the next token in a sequence.

For example:

> "The capital of France is ___"

The model learns that **Paris** is the most likely next token.

To improve its predictions, the model adjusts its internal parameters, known as **weights**, using a learning process called **backpropagation**. Over billions or trillions of training examples, the model gradually learns language patterns, factual relationships, and reasoning structures.

The result of this process is a **base model** (or **foundation model**). A base model is very good at predicting text but is not necessarily optimized to follow instructions or behave like a helpful assistant.

Pre-training also determines the model's **training cutoff**, which is the point after which new events and information are not included in its learned knowledge. For this reason, LLMs may not know about recent events unless current information is provided through prompts or external tools.
### Fine-Tuning and RLHF: From Base Model to Assistant

After pre-training, a base model undergoes additional training to become a useful AI assistant.

**Fine-tuning** trains the model on carefully curated prompt-response examples that demonstrate how a helpful assistant should respond. Because the model has already learned general language patterns during pre-training, only a much smaller dataset is needed for this stage.

Fine-tuning can also be used to create domain-specific models. For example, a model fine-tuned on medical data may perform better on healthcare-related questions, while one fine-tuned on customer support data may be better suited for service-related tasks.

**Reinforcement Learning from Human Feedback (RLHF)** further improves the model's responses using human preferences.

The RLHF process typically involves:

1. Human evaluators compare multiple model responses.
2. A **reward model** learns which responses people prefer.
3. The LLM is updated to generate responses that receive higher reward scores.

This helps the model produce responses that are more helpful, relevant, and aligned with user expectations.

After pre-training, fine-tuning, and RLHF are completed, the model's **weights are frozen**. This means the model does not automatically learn from individual user conversations. Each new session starts with the same trained model unless it is retrained or updated by its developers.

### Inference: Prefill, Decode, and Autoregressive Generation

**Inference** is the process that occurs when a user interacts with an LLM. It consists of two main stages: **Prefill** and **Decode**.

![LLM pipeline diagram](./diagram.png)

*The LLM pipeline: training creates the model weights, while inference uses those weights to generate responses.*

#### Prefill

During the **prefill** stage, the model processes the entire input at once. This includes:

- System instructions
- Conversation history
- The user's latest message

The input is converted into tokens and passed through the transformer network. During this process, the model creates a **KV Cache (Key-Value Cache)**, which stores important intermediate computations.

The KV Cache allows the model to reuse previously computed information instead of recalculating everything during response generation. This significantly improves efficiency and reduces response time.

#### Decode

The **decode** stage is where the response is generated.

The model:

1. Predicts the most likely next token.
2. Adds the token to the sequence.
3. Uses the KV Cache to process only the new token.
4. Repeats the process until the response is complete.

This step-by-step generation process is called **autoregressive generation** because each new token depends on all previously generated tokens.

This is also why responses can be streamed in real time—the model generates and displays tokens as they are produced.

#### How the Model Predicts Tokens

During each generation step, token embeddings pass through multiple layers of the transformer network.

At each layer:

- The **attention mechanism** helps the model focus on relevant parts of the input.
- Token representations are updated based on their relationship to other tokens in the context.

After passing through all transformer layers, the model calculates the probability of every possible next token and selects one to continue the response.

This process repeats until the model reaches an end-of-sequence token or a predefined output limit.

## Worked Example

A user enters the prompt:

> **"What is the tallest mountain in India?"**

### Step 1 – Tokenization

The input text is first converted into **tokens**. Each token is mapped to a numerical ID that the model can process. If a system prompt or previous conversation exists, those tokens are included as part of the input sequence.

### Step 2 – Prefill

The model processes all input tokens in a single forward pass. Using the **attention mechanism**, it identifies relationships between important words such as *tallest*, *mountain*, and *India*.

At the end of this stage, the model generates:

- A probability distribution for the next token.
- A **KV Cache** containing intermediate computations that can be reused during generation.

### Step 3 – First Decode Step

The model selects the most likely next token based on the probability distribution. For this query, the generated response may begin with a token representing **"Kangchenjunga"**.

### Step 4 – Autoregressive Generation

The model continues generating one token at a time:

1. Predict the next token.
2. Append it to the response.
3. Use the KV Cache to avoid recomputing previous tokens.
4. Repeat until the response is complete.

This process gradually produces a sentence such as:

> "Kangchenjunga is the highest mountain in India and the third-highest mountain in the world."

### Step 5 – Detokenization

The generated token IDs are converted back into human-readable text and returned to the user. The response may be displayed gradually (streaming) or delivered as a complete message.

The entire process usually takes only a few seconds, with the **KV Cache** playing a key role in making response generation efficient.

## In Practice

### Deployment Patterns

- **Cloud-Based Inference** – The model runs on the provider's servers, and users access it through APIs. This approach is easy to use but requires data to be sent to external systems.

- **Self-Hosted Inference** – Organizations run open-weight models such as Llama or Mistral on their own infrastructure. This provides greater control over privacy, security, and compliance.

- **Edge Inference** – Smaller models run directly on devices such as laptops, smartphones, or embedded systems. This reduces latency and keeps data local but usually offers lower performance than large cloud models.

### Performance Metrics

- **Latency** – The time taken to generate a complete response after a prompt is submitted.
- **Throughput** – The number of tokens or requests a system can process over a given period.
- **Time to First Token (TTFT)** – The time before the first generated token appears in a streaming response.

Long prompts and large documents can increase latency and TTFT because the model must process more input tokens.

### Batching

To improve efficiency, inference servers often process multiple requests together.

- **Static Batching** – A fixed group of requests is processed at the same time.
- **Continuous Batching** – New requests are added dynamically as others finish, improving resource utilization and reducing waiting time.

### Tokenization Challenges for Indian Languages

Languages such as Hindi, Tamil, and Telugu often require more tokens than English when processed by many LLMs. This can increase both inference cost and response time.

To address this issue, organizations are developing models with better support for Indic languages and more efficient tokenization strategies.

### Prompt Design Best Practices

- Keep prompts concise and relevant.
- Avoid unnecessary instructions or repeated information.
- Be aware of context window limits.
- Use techniques such as chunking, summarization, or RAG when working with large documents.
- Remember that the model can only use information available within its context window unless external tools are provided.

Well-designed prompts improve response quality while reducing cost and latency.

## Key Takeaways

- LLMs process text as **tokens**, which are small units of text converted into numerical IDs. The tokenizer determines how efficiently different languages and content types are represented.

- During **pre-training**, the model learns language patterns and knowledge by predicting the next token across massive datasets. **Fine-tuning** and **RLHF** then help transform the base model into a useful assistant.

- **Inference** consists of two stages: **Prefill**, where the input is processed and the KV Cache is created, and **Decode**, where the response is generated one token at a time.

- LLMs do not learn from individual conversations. Their knowledge is limited by their training data and training cutoff date unless additional information is provided through prompts or external tools.

- Running LLMs requires significant computational resources. Performance is influenced by factors such as model size, context length, latency, throughput, and batching strategies.

- Understanding tokens, training, and inference helps explain both the capabilities and limitations of modern LLMs, including why they sometimes make mistakes, have knowledge cutoffs, or incur high computational costs.

## References

[1] BentoML. "How Does LLM Inference Work?" *LLM Inference Handbook*. https://bentoml.com/llm/llm-inference-basics/how-does-llm-inference-work
