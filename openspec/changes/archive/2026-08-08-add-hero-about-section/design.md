## Context

The Hero/About section is the portfolio's entry point — it needs to read as clean, technical, and confident within the first viewport, without relying on any content not yet defined by other sections.

## Goals / Non-Goals

**Goals:**
- Communicate who Ciro is and his role within a single screen's worth of content, on every target.
- Reuse `PortfolioTheme` tokens exclusively — no ad-hoc colors/fonts.

**Non-Goals:**
- Animations/parallax effects — a static, clean layout is enough for v1.
- Real photo asset pipeline — a placeholder avatar (initials or a static image resource) is acceptable until Ciro supplies a final asset.

## Decisions

- **Content lives entirely inside `HeroAboutSection.Content()`** (plus optional private helpers in the same file/sub-package) per the foundation's per-section-file isolation contract.
- **Static bio text as a Kotlin string constant** in this file for now — no CMS/remote content source, since the site has no backend yet.

## Risks / Trade-offs

- [Risk] Placeholder avatar looks unfinished → Mitigation: use a simple initials-based avatar (e.g., "CF" in a circle using theme colors) so it looks intentional, not broken, until a real photo is supplied.
