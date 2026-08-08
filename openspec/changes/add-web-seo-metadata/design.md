## Context

`webApp/src/webMain/resources/index.html` is a static resource served as-is by both the Wasm and JS targets — it's not generated from Kotlin code. Right now it only has a `<title>Nexus</title>` and a loading spinner; there's no metadata for search engines or link-preview crawlers (LinkedIn, Slack, Twitter/X, etc.).

## Goals / Non-Goals

**Goals:**
- Give the deployed web app a real `<title>`, meta description, and Open Graph tags so it's identifiable in search results and renders a proper card when a link is shared.
- Add a favicon so the browser tab looks intentional.
- Keep the English copy easy to swap for a localized version once the separate i18n change lands, without restructuring `index.html`.

**Non-Goals:**
- No server-side rendering, sitemap, or `robots.txt` — out of scope for this slice.
- No per-language metadata yet — `index.html` stays single-language (English) until the i18n change defines how localized static HTML is served; this change just avoids hardcoding copy in a way that would make that harder later (e.g. keeping the English strings in one clearly-marked block).
- No custom Open Graph image design — reuse the existing avatar-initials treatment as a placeholder or a plain solid-color card; a designed image can follow later.

## Decisions

- **Static HTML, not generated**: metadata is written directly into `index.html` rather than injected at build time from `:shared` content. `:shared` composables don't run before the Wasm/JS module loads, so there's no way to derive `<title>`/meta tags from Kotlin content without a build-time step; that's more machinery than this slice needs. Revisit if/when the i18n change needs per-language HTML variants.
- **Favicon format**: a single `favicon.svg` (scales cleanly, small) referenced via `<link rel="icon" type="image/svg+xml">`, with no `.ico` fallback — acceptable given the portfolio targets modern evergreen browsers (same assumption the Wasm-primary target already makes).
- **Open Graph image**: ship a simple static placeholder image now rather than blocking this change on final visual design; swapping the image file later doesn't require another spec change.

## Risks / Trade-offs

- [No `.ico` favicon] → Acceptable; every evergreen browser supports SVG favicons, and this project already treats Wasm/evergreen as the primary web target (JS build is the fallback, not IE-era compat).
- [Metadata hardcoded in English] → Acceptable short-term; flagged as a follow-up for the i18n change rather than solved here, to keep this change small and independently shippable.
