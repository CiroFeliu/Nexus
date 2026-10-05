## Context

Contact is the portfolio's final call-to-action. Since deployment/self-hosting is explicitly deferred, there's no backend to receive form submissions yet — direct links are the right scope for v1.

## Goals / Non-Goals

**Goals:**
- Give visitors immediate, no-friction ways to reach Ciro: email, LinkedIn, GitHub.
- Reuse the shared `openUrl` capability from `add-portfolio-foundation` — no new platform code.

**Non-Goals:**
- A contact form with backend submission handling — revisit once Ciro's self-hosted deployment is in place.
- Social links beyond LinkedIn/GitHub/email — can be added later as a one-file edit.

## Decisions

- **Data-as-code**: contact links defined as a small constant list (`ContactLink(label, url)`) inside `ContactSection.kt`.
- **`mailto:` link for email**, opened through the same `openUrl` function used for HTTP(S) links, so there's one call path for all outbound contact actions.
- **Text-only labels, no icon glyphs**: emoji icons were tried first but render as tofu boxes — the default Skia font on wasm/desktop has no emoji table. Wiring up an icon library (e.g. `material-icons-extended`) is a `build.gradle.kts` change, outside this file-scoped change's impact. Revisit if a shared icon dependency is ever added for another section.

## Risks / Trade-offs

- [Risk] No contact form may feel incomplete for some visitors → Mitigation: explicitly a deferred, not dropped, feature — direct links (especially email) are a fully valid contact path for a technical portfolio.
