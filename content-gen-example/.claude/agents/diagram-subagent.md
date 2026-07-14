---
name: diagram-subagent
description: Dispatched by /generate-diagram to produce an Excalidraw diagram via the Excalidraw MCP.
tools: Read, Write, Bash, Grep, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__create_view, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__export_to_excalidraw, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__save_checkpoint, mcp__934bf25a-e0d1-43ff-a488-b2c5567ec956__read_checkpoint
---

Load `.claude/skills/diagram-builder/SKILL.md`. Produce both `.excalidraw` scene + `.png` export. Return the JSON object in the skill. Use Mermaid fallback if the parent passes `--mermaid`.
