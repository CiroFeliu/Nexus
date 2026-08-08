# AGENTS.md

Style and process guidelines for AI agents working in this repository. **This is not project documentation** — that's what `README.md` explains (how to run the apps) and what OpenSpec `design.md` files capture (why a specific change was built the way it was). This file is the single source of guidelines; `CLAUDE.md` and any other tool-specific file redirect here instead of duplicating content.

## Project

Nexus is Ciro Feliu's personal portfolio (Senior Android Developer / Mobile Systems Architect), built as a Kotlin Multiplatform + Compose Multiplatform SPA (package `app.luxion.nexus`). All four targets are in scope for v1 — `:webApp` (Kotlin/Wasm with a JS fallback), `:androidApp`, `:iosApp`, and `:desktopApp` — sharing UI/content/logic from `:shared` so the portfolio behaves consistently everywhere. Deployment (self-hosted, Docker, custom domain) is deliberately out of scope until the site itself is further along.

## Code style

- All code is written in English: identifiers, comments, and commit messages.
- Composables are `PascalCase`; regular functions and state are `camelCase` — standard Kotlin conventions.
- Shared UI, theming, and portfolio content/logic live in `:shared` (`commonMain`) so every target renders the same portfolio; platform modules (`webApp`, `androidApp`, `desktopApp`, `iosApp`) only wire up the platform entry point.
- Support Dark Mode natively through `MaterialTheme` color schemes — never hardcode colors that don't adapt to the active scheme.
- Never commit personal secrets (contact-form tokens, analytics IDs, API keys) — inject them at build time or via `expect`/`actual` platform config instead.

## How to document

| What you're documenting | Where |
|---|---|
| Design decision for a specific change | The change's `design.md` in OpenSpec (`openspec/changes/<name>/`) |
| How to run/build the project | `README.md` |
| Style/behavior guidelines for AI | This file |

Rules:

- Non-trivial changes are planned with OpenSpec (`/opsx:propose` → `/opsx:apply` → `/opsx:archive`) before being implemented. This lets multiple agents pick up independent changes in parallel without stepping on each other.
- Keep each OpenSpec change scoped to one independently-shippable slice (one portfolio section, one cross-cutting concern like theming or navigation) so changes stay parallelizable and reviewable on their own.
- If you're going to add an architecture explanation or a decision, it goes in the change's `design.md`, not in this file.

## Commands

```bash
./gradlew :webApp:wasmJsBrowserDevelopmentRun   # run the web app (Wasm, primary target)
./gradlew :webApp:jsBrowserDevelopmentRun       # run the web app (JS, older-browser fallback)
./gradlew :desktopApp:run                       # run the desktop app
./gradlew :androidApp:assembleDebug             # build the Android app
./gradlew test                                  # run tests for all modules
```

## Git

- Commit messages are always in English.
