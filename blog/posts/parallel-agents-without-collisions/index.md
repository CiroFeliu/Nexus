---
title: "Three agents on the same project without stepping on each other"
date: 2026-10-26
summary: "A worktree isolates an agent's code, but not its environment. I built ShogunAi so every worktree gets its own ports, services and devices, and several agents can work at the same time on the same machine."
lang: en
translationKey: shogunai-intro
tags: [shogunai, agents, worktrees]
---

I've been working with several AI agents at once for months now. At work I have between 20 and 30 worktrees in different states, one per task, and on my own projects I use Claude and, when I run out of quota, Antigravity. Running three agents in parallel, each on its own task, is normal for me, and the tools make it easy: Claude Code, Codex or Cursor create a Git worktree per agent, and each one works on its own branch without touching the others' code.

The problem starts when all three try to run the project.

## The worktree isolates the code, but not the environment

A worktree is a working copy of the repository in another folder, so the code is taken care of, but an agent needs a lot more than code to work:

- The local files that aren't in Git and without which the build won't start, like a `.env` or a `local.properties`.
- Its own ports. If the project runs the API on 8080 and the database on 5432, the second agent gets a "port is already allocated".
- Its own services. If the `compose.yaml` sets the project name, as many do, two `docker compose up` runs from two worktrees share containers, and the second one recreates the first one's. The worst part is there's no error at all: the first agent keeps testing on `localhost:8080`, but what answers is already the other agent's code.
- Its own devices. Two agents installing on the same emulator or simulator overwrite each other's app.
- Daemons and caches with limits. Every worktree that builds with Gradle or Xcode leaves daemons and caches behind that slowly eat your memory and disk.

An agent doesn't see any of this on its own, and it doesn't see the others either. Today's agents cope fairly well: they check what's running and, if 8080 is taken, they make up other ports or stop to ask you. It works, but each one improvises in its own way and in every session, and when they're done you're left with three stacks running on ports you didn't pick. You could put it in the `AGENTS.md`, and for ports that does the job, but an instruction doesn't hand out emulators, cap the daemons' memory or know what the agent next door has taken. So either the agent spends its session fighting the environment, or you end up directing traffic, which is what kept happening to me.

## What ShogunAi does

ShogunAi is the environment layer for agents working in parallel on one machine. It doesn't run agents or replace your tool; what it does is prepare and watch over the worktrees where Claude, Codex, Cursor or whatever you use is working.

The repository declares once what a worktree needs, in a `.shogunai.json` versioned with the code:

```json
{
  "copy": [".env"],
  "providers": {
    "docker-compose": { "portVariables": ["DB_PORT", "API_PORT"] }
  }
}
```

And the `compose.yaml` publishes its ports through those variables:

```yaml
services:
  db:
    image: postgres:17
    ports: ["${DB_PORT:-5432}:5432"]
  api:
    build: .
    ports: ["${API_PORT:-8080}:8080"]
```

From then on, every new worktree gets its copy of the `.env`, its own Compose project (`COMPOSE_PROJECT_NAME`) and its own free ports, reserved for it. All three agents run their `docker compose up` and none of them gets in the others' way.

## Three agents, one project

![Three Claude Code agents working on the same backend, each with its own database and ports, and ShogunAi's control tower showing their state](demo.mp4)

In the video there are three Claude Code sessions working on three tasks in the same backend, first without ShogunAi and then with it. Without it, each agent checks by hand what's running, one stops to ask me what to do and another makes up its own ports, and at the end three stacks are left running on 8080, 18080 and 8180. With ShogunAi, each agent gets its ports when it starts, brings up its database and API without looking around or asking, and removing the worktree takes its Compose down. In the desktop app, the control tower, you can see at a glance which worktree each agent is in, which one is waiting for you to answer and which one is done.

## It adapts to your tool, not the other way round

ShogunAi has two parts. On one side there's the `shogun` command and its hooks, which is where the work happens: with three hooks in `.claude/settings.json`, `claude --worktree` creates its worktrees through ShogunAi and every session starts with its variables already loaded, and there are recipes for Herdr, Cursor, Codex, direnv or any script. On the other side there's the desktop app, to see everything at a glance across all your projects, including the worktrees other tools created.

Docker Compose is just one example. The project grew around Android, so there are providers for the stacks where a worktree is hardest to prepare: an Android emulator or an iOS simulator per worktree, Gradle daemons with bounded memory, Maven builds and Xcode caches shared between worktrees. If a repository doesn't turn them on, it never sees them.

## Getting started

On macOS:

```bash
brew tap LuxionServer/shogunai
brew trust --tap LuxionServer/shogunai
brew install --cask shogunai
```

On Windows with `winget install LuxionServer.ShogunAi` or with Scoop, and on Linux with the `.deb` from the [latest release](https://github.com/LuxionServer/ShogunAi/releases/latest).

Open the app, add your repository and, if it has a `compose.yaml` or a Gradle build, it suggests the providers that fit. Everything else is in the [repository](https://github.com/LuxionServer/ShogunAi), which is open source, and if you want to reproduce the video as is, the demo project with its three tasks is in [shogunai-demo](https://github.com/LuxionServer/shogunai-demo).

If you also run several agents at once and any of this sounds familiar, I'd really like to know what collides in your stack. [Open an issue](https://github.com/LuxionServer/ShogunAi/issues) or get in touch.
