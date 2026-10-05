## Why

`add-portfolio-foundation` creates `navigation/sections/ContactSection.kt` with a placeholder body. This change fills it in with direct ways to reach Ciro, closing out the portfolio's single-page flow.

**Depends on:** `add-portfolio-foundation` must be applied first (needs `PortfolioTheme`, `SectionPlaceholder`, `ContactSection` file, and the shared `openUrl` capability).

## What Changes

- Implement `ContactSection.Content()`: direct links (email, LinkedIn, GitHub) as themed buttons/icons, opened via the shared `openUrl` capability.
- No contact form/backend in v1 — Ciro's self-hosted deployment (Docker, custom domain) is deliberately deferred until the site is further along, and a form would need a backend to receive submissions; direct links need none.

## Capabilities

### New Capabilities
- `contact-section`: The Contact section's content — direct links to reach Ciro (email, LinkedIn, GitHub).

### Modified Capabilities
(none)

## Impact

- `:shared/src/commonMain/navigation/sections/ContactSection.kt` only. No shell, registry, theme, or other section files are touched, and no new platform code (reuses `openUrl` from the foundation change).
