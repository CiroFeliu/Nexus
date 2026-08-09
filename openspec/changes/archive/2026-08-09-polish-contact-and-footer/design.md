## Context

`ContactSection.Content()` renders a `Row` of `OutlinedButton`s (Email/LinkedIn/GitHub/Download CV) and nothing else — no plain text, no footer exists anywhere in the shell. Separately, this portfolio's web build renders through Compose Multiplatform's DOM/canvas hybrid renderer with `user-select: none` set on the shadow-DOM host (observed while auditing the running app), so plain `Text()` is not natively OS-selectable the way normal HTML text would be.

## Goals / Non-Goals

**Goals:**
- Visitor can read (and ideally copy) the email address without having to know to click the "Email" button.
- The page has a clear, deliberate ending instead of stopping abruptly after the contact buttons.

**Non-Goals:**
- No social-share links beyond the three already present (Email/LinkedIn/GitHub).
- No newsletter/contact form — `contact-section`'s existing "No contact form in v1" requirement is unaffected.
- No per-section footers — one footer, once, at the end of the page.

## Decisions

- **Visible email text**: render the email address as a `Text()` composable next to (not replacing) the existing "Email" button. Wrap it in Compose Multiplatform's `SelectionContainer` so it's actually copyable on every target, not just visible — plain `Text()` alone would be visible but not selectable given the `user-select: none` host behavior noted above.
- **Footer placement**: render inside the scrollable content (last item after `Contact`), not in the sticky header row — a footer is page content the visitor scrolls past, unlike the language switcher / section nav which must stay visible.
- **Footer scope**: kept intentionally minimal (copyright + build-tech note) — this is a polish change, not a sitemap/footer-nav redesign.

## Risks / Trade-offs

- [Risk] `SelectionContainer` behavior on `wasmJs`/`js` targets for actually producing OS-copyable text (vs. just visually selectable within the canvas) needs to be verified hands-on before this is considered done — Compose Multiplatform's web text-selection support has matured but should not be assumed without checking on this project's current Compose version. → Mitigation: task list includes an explicit manual verification step (select the email text on `:webApp`, paste it into another app) before marking the requirement met; if selection doesn't produce real clipboard text on web, fall back to at minimum showing the plain, visible (non-selectable) email text — still strictly better than today's buttons-only state.
- [Risk] Footer copy ("built with Compose Multiplatform") could read as filler if not worded carefully. → Mitigation: keep it one short line, bilingual, matching the existing tone already used in the Hero bio ("This portfolio itself is a Compose Multiplatform build...").
