---
title: "Three agents on the same mobile app, each with its own emulator"
date: 2026-11-17
summary: "I started sharing my emulators between agents with a TXT file on my desktop and a few rules in the AGENTS.md. It worked for a while, and here's why it broke and how ShogunAi hands them out now."
lang: en
translationKey: shogunai-mobile
tags: [shogunai, agents, android, ios]
---

The first time two agents stepped on each other on a device was at work. I was validating a task while reproducing a bug, and one of the agents started complaining that the app's state kept changing. I caught it fast, because with four or five sessions in parallel I soon saw it was another one touching its app, and I thought the obvious: I had a problem and I had to find a solution.

The solution was very homemade: a TXT file on my desktop so the agents took turns on my physical devices and my emulators, and a few rules in the `AGENTS.md` so that every time an agent had to use a device, it took its turn.

## I tried it with an instruction

In the [first post](https://cirofeliu.es/blog/parallel-agents-without-collisions/) I covered the case of a backend, and there one line in the `AGENTS.md` is enough for each agent to use its own ports. Some colleagues have told me that, then, all of this is solved with an instruction, and the TXT file was exactly that.

It worked for a while, but when sessions left a device they no longer used written down, or things like that, it broke. It wasn't deterministic, like any instruction you give an agent. On top of that, I had to create and delete the emulators and simulators by hand, and I got to have five or six at once.

It's not that I was losing a lot of time right then, but I saw it scaled badly, that I was going to become a bottleneck by having to serve and provide for the agents myself. I needed something that worked through commands and, since I already had a project that managed worktrees, I thought of tying them together.

## Why mobile

Because I'm a mobile developer, and to a hammer everything looks like a nail. What makes mobile different is above all the emulators and on-device testing: an agent can't sign off on a screen without installing the app and looking at it, and they all install the same app on whatever they find running.

## How ShogunAi hands them out

The repository declares once what a worktree needs, in the `.shogunai.json`. This is the one for the demo app, built with Kotlin Multiplatform for Android and iOS:

```json
{
  "link": ["/local.properties"],
  "providers": {
    "gradle": { "daemonMaxHeap": "3g", "kotlinDaemonMaxHeap": "2g", "idleTimeoutMinutes": 30 },
    "emulator": { "avd": "Medium_Phone_API_35" },
    "ios-simulator": { "device": "iPhone 17", "runtime": "iOS 26.5" },
    "xcode": { "compilationCache": true }
  }
}
```

From then on, each agent starts its worktree's devices with two commands:

```bash
shogun emulator start
shogun simulator start
```

The first one gives it its own Android emulator and the second one creates an iOS simulator named after the worktree. What an agent installs and captures goes only to its own devices, and that's now decided by a command, which always does the same thing.

How many fit is decided by the machine, not the repository. There's a limit of emulators, simulators and Gradle daemons per machine, and when an agent asks for a device and no more fit, it waits its turn in a queue until another one finishes. I built it this way because I'm fine with it waiting. I've been tuning the settings until, on my work computer, I live at practically 100% CPU, right now with about 6 Android emulators, 2 iOS simulators and about 8 daemons, roughly. The bottleneck now is the hardware, the raw power, but it's not that limiting, because day to day you do more things than just develop.

And removing a worktree shuts down what's its own: its emulator, its simulator and its idle Gradle daemons.

## Three agents, one app

![Three Claude Code agents working on the same mobile app: without ShogunAi they step on each other on the same emulator and simulator, and with ShogunAi each one gets its own and the third one waits its turn](demo.mp4)

I ran the test with three Claude Code sessions, each with a task on the same app: a search screen, favorites and a detail screen. Each task counts as done when its tests pass and there's an Android screenshot and an iOS one showing the change.

Without ShogunAi, with one emulator and one simulator running, all three installed the same app on the same devices and kept overwriting it. The funny thing is they notice, one of them wrote it as is: "The emulator is showing another agent's build. I'll reinstall mine and take the screenshot right away". Another one tapped the screen and found another agent's detail screen open. The ones that finished did it in about 4 minutes and with the right screenshots, but by reinstalling and capturing in a rush before someone else stepped on them, and with nothing guaranteeing that what they're checking is their own. In the run in the video, one didn't even get to install: it saw another agent was using the emulator and stopped to ask me what to do.

With ShogunAi, two emulators and two simulators fit on my machine. Two agents took theirs and the third one hit the limit, queued up in the background and kept going with the code and the tests while it waited. When one of the others finished and its reservation expired, it got its devices and finished. Between the three of them they sent 36 commands to Android and every one went to the emulator of its own worktree. Six screenshots, none stepped on.

What doesn't come for free is time: with ShogunAi each agent boots its own emulator, and on my machine that meant between 4 and 13 minutes per task, depending on the run, against the 4 of sharing everything. And the third one waited about 20 minutes in the queue, which is how long it takes for the reservation of an agent that's already done to expire. With two emulators booting at once the computer struggles, and with four it just couldn't.

## When you don't need it

I was building it for myself, because it was useful to me, so I don't know how many people have the same problem. If what you do is make changes in several repositories at the same time, I guess you can cover it with an instruction. Where I see the most potential for the tool is when you make a lot of changes in parallel on a single project.

If I started the project again now, I wouldn't lose as much time on the worktree side as I did at the beginning, before focusing on provisioning.

## How to try it

The demo app is in [shogunai-demo-mobile](https://github.com/LuxionServer/shogunai-demo-mobile), with its three tasks and the `without-shogunai` branch to see the difference, and installing ShogunAi is covered in the [repository](https://github.com/LuxionServer/ShogunAi). If you work with Flutter there's a provider too.

I'm publishing this to stop developing in the dark. If you're a mobile developer and you have the same problems, try the tool and give me feedback.
