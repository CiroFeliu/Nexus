# AGENTS.md

Style and process guidelines for AI agents working in this repository. **This is not project documentation** — that's what `README.md` explains (how to run the apps) and what OpenSpec `design.md` files capture (why a specific change was built the way it was). This file is the single source of guidelines; `CLAUDE.md` and any other tool-specific file redirect here instead of duplicating content.

## Project

Nexus is Ciro Feliu's personal portfolio (product engineer building with AI agents in parallel, from spec to real device), built as a Kotlin Multiplatform + Compose Multiplatform SPA (package `app.luxion.nexus`). All four targets are in scope for v1 — `:webApp` (Kotlin/Wasm only for v1; a JS fallback for browsers without WasmGC is deferred to a later change), `:androidApp`, `:iosApp`, and `:desktopApp` — sharing UI/content/logic from `:shared` so the portfolio behaves consistently everywhere. Deployment is self-hosted on Ciro's own server under `cirofeliu.es` (not GitHub Pages): every push to `master` runs `.github/workflows/deploy.yml`, which tests, builds the Wasm distribution, rsyncs it to the server over a deploy-only SSH key restricted with `rrsync`, purges the Cloudflare zone's cache with a purge-only API token and checks that the live `webApp.js` and its `.wasm` files load. Only content-hashed files (the `.wasm` binaries) are cached long; everything else is served `no-cache`, so a deploy never leaves a stale `webApp.js` pointing at deleted binaries. The server side is Caddy in Docker behind a Cloudflare-proxied zone with an Origin Certificate (Full strict), and UFW only lets Cloudflare's IP ranges reach ports 80/443. The design rationale lives in the archived `automate-webapp-deployment` change; server-side operational details live in Ciro's private server docs, not in this repo.

`:webApp` is the priority target within v1 — it's the primary deliverable (what recruiters/visitors see); `:androidApp`, `:iosApp`, and `:desktopApp` exist to demonstrate multiplatform reach. When work isn't otherwise scoped, prioritize `:webApp` + `:shared`. Design tone is clean/technical/structured/modern.

Ciro is deeply fluent in Kotlin/Compose on native Android; Nexus is his first Compose Multiplatform *Web* project. Skip basic Kotlin/Android/Compose explanations — focus added detail on Web-specific concerns (Wasm vs JS tradeoffs, browser rendering quirks, SEO/SPA routing), which are newer ground for him.

Project-specific conventions belong in this file, not in an AI agent's personal/auto memory — keep this file as the single source so any agent or teammate picks up the same context regardless of machine.

## Code style

- All code is written in English: identifiers, comments, and commit messages.
- Composables are `PascalCase`; regular functions and state are `camelCase` — standard Kotlin conventions.
- Shared UI, theming, and portfolio content/logic live in `:shared` (`commonMain`) so every target renders the same portfolio; platform modules (`webApp`, `androidApp`, `desktopApp`, `iosApp`) only wire up the platform entry point.
- Support Dark Mode natively through `MaterialTheme` color schemes — never hardcode colors that don't adapt to the active scheme.

## Public repository

`CiroFeliu/Nexus` is a **public** GitHub repo. Everything committed — code, OpenSpec artifacts, workflow files, commit messages, PR bodies — is published permanently: force-pushing or rewriting history afterwards does not unpublish it (forks, clones, caches, GitHub's retention of dangling commits). Treat every commit as a public post.

Never commit:

- Secrets of any kind: API keys, contact-form tokens, analytics IDs tied to a private account, passwords, private SSH/TLS keys, certificates' private halves, signing keystores, `.env` files, `local.properties`. Inject them at build time (GitHub Actions secrets, environment variables) or via `expect`/`actual` platform config instead.
- Operational details of Ciro's server: IPs, SSH hosts/aliases, usernames, filesystem paths on the server, `authorized_keys` entries, firewall rules beyond the high-level summary above, Caddy/Docker config, certificate file names. The repo may describe the deployment architecture at the level already in this file and in the archived `automate-webapp-deployment` design; anything more specific belongs in the server's private docs, not here.
- Content copied from Ciro's private global `~/AGENTS.md` or any other machine-local file.
- Personal data beyond what the portfolio intentionally publishes (no private phone numbers, home address, third parties' data).
- Machine-specific state: absolute paths containing a home directory, `.claude/settings.local.json`, IDE workspace files, logs, build outputs.

Before every commit, review `git diff --staged` for the above, stage explicit paths instead of `git add -A`/`git add .`, and add any new kind of local/generated file to `.gitignore` instead of leaving it to chance. If something sensitive was already committed or pushed, stop and tell Ciro immediately instead of silently amending or force-pushing: a leaked secret must be rotated first, history cleanup is secondary.

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
- Use the `openspec` CLI (`openspec archive`, `openspec validate`, `openspec list`) for all OpenSpec operations — never hand-edit `openspec/specs/` or manually move folders under `openspec/changes/` to replicate what the CLI does. The CLI enforces rules (e.g. every spec needs a `## Purpose` section) that a manual merge can silently violate.
- Sync with `develop` before archiving a change, and don't archive a change you don't own — archiving directly on `develop` outside a PR risks a second branch racing to archive the same change independently.

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
- Feature-branch PRs target `develop`, not `master`. `master` is reserved (releases).
