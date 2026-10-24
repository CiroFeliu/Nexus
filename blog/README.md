# Blog posts

Posts published at [cirofeliu.es/blog/](https://cirofeliu.es/blog/). Each post is Markdown in its own folder; the build turns them into static pages, so nothing here runs in the browser. The design behind it is in the `add-blog-section` OpenSpec change.

## Layout

```
blog/posts/<slug>/index.md     the post
blog/posts/<slug>/demo.mp4     its media, referenced by relative path
```

The slug is the folder name: lowercase kebab-case (`a-z`, `0-9`, single hyphens). It becomes the URL `/blog/<slug>/`.

## Front matter

```yaml
---
title: "Running agents in parallel with ShogunAi"
date: 2026-11-03
summary: "One or two sentences shown in the post list and the RSS feed."
lang: en
draft: true
translationKey: shogun-parallel-agents
tags: [kotlin, ai]
---
```

| Field | Required | Value |
|---|---|---|
| `title` | yes | text |
| `date` | yes | ISO date `YYYY-MM-DD` |
| `summary` | yes | one or two sentences |
| `lang` | yes | `en` or `es` |
| `draft` | no | `true` keeps the post out of production pages and the feed (default `false`) |
| `translationKey` | no | same value in both language versions of a post; each links to the other |
| `tags` | no | list of words |

Any other field fails the build, so typos are caught.

## Media and code

- `![Caption](demo.mp4)` or `.webm`: native video player with the caption below it.
- `![Caption](https://www.youtube.com/watch?v=ID)` or `https://youtu.be/ID`: privacy-enhanced YouTube embed (youtube-nocookie).
- `![Alt text](diagram.png)`: an image.
- Videos and YouTube links must be alone on their line, with blank lines around them.
- Relative media must exist in the post's own folder.
- Fenced code blocks take a language tag (```` ```kotlin ````).
- Raw HTML in the Markdown is shown as text, not rendered.

Keep self-hosted clips short and compressed (they live in this repo and ship with every deploy); put long videos on YouTube.

## Previewing

```bash
./gradlew :webApp:wasmJsBrowserDevelopmentRun -Pblog.drafts=true
```

Then open `/blog/` on the dev server. `-Pblog.drafts=true` includes drafts, marked with a badge; production builds never do. Invalid posts fail the build with a message naming the file and the problem.
