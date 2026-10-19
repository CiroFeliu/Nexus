## Why

On the web target the whole UI is drawn into a canvas, so the HTML shell is everything crawlers, link previews and the first second of a visit see. Today:

- The loader is a grey/black SVG spinner on the browser's default white body: visitors in dark mode get a white flash before a dark site.
- `og:image` points to a relative `og-image.svg`. LinkedIn, X, WhatsApp and Slack do not render SVG previews and require an absolute URL, so shared links show no image.
- There is no structured data (`Person`), no `<noscript>` content, `lang="en"` is fixed even when the app renders Spanish, and the document title never follows the active language.
- Title and descriptions use em dashes, and the HTML carries a stale comment.

## What Changes

- Loader styled with the same background/foreground as the app in both color schemes (`prefers-color-scheme` CSS), centered, minimal, no layout jump into the first Compose frame; it is removed only when the app signals readiness (fonts preloaded, see `refine-design-foundation`).
- Replace the SVG OG image with a 1200x630 PNG built from the new visual language, referenced with an absolute `https://cirofeliu.es/...` URL; add `og:url`, `og:locale` (+ alternate), `twitter:card=summary_large_image`, canonical link.
- Add JSON-LD `Person` (name, job title, url, sameAs LinkedIn/GitHub).
- Add a `<noscript>` block with name, role, a short summary and contact links for crawlers and no-JS visitors.
- Keep `<html lang>` and `document.title` in sync with the active language from the Wasm/JS entry.
- Remove dashes and the stale comment from `index.html`.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `web-seo-metadata`: Open Graph requirement tightened (absolute raster image, extra tags); adds structured data, no-JS fallback, language-synced document metadata and a theme-matched loader.

## Impact

- `webApp/src/webMain/resources/index.html`, `styles.css`, new `og-image.png`, removal of `og-image.svg`
- `webApp/src/webMain/kotlin/app/luxion/nexus/main.kt` and a small web-only bridge to update `lang`/title (shared via `expect`/`actual` no-op on other targets)
- Server: none (static files only; Caddy already serves `site/`)
