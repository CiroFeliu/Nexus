## Why

The web app is the primary target, but `index.html` ships with a generic `<title>Nexus</title>`, no meta description, no Open Graph tags, and no favicon. Search engines and link previews (LinkedIn, Slack, etc.) have nothing meaningful to show, which undermines the portfolio's purpose of being found and shared.

## What Changes

- Replace the generic `<title>` with a real one (name + role, e.g. "Ciro Feliu — Senior Android Developer & Mobile Systems Architect").
- Add a `<meta name="description">` summarizing the portfolio.
- Add Open Graph tags (`og:title`, `og:description`, `og:type`, `og:image`) so shared links render a proper preview card.
- Add a favicon (and reference it from `index.html`).
- Keep the source of the title/description text in one place so it can later be swapped per-language once i18n lands, without re-touching `index.html` structure.

## Capabilities

### New Capabilities
- `web-seo-metadata`: static HTML metadata (title, description, Open Graph, favicon) served by the web app for search engines and link-preview crawlers.

### Modified Capabilities
- (none — no existing spec covers web-only static metadata)

## Impact

- `webApp/src/webMain/resources/index.html`
- `webApp/src/webMain/resources/` (new favicon asset, and an Open Graph preview image if one doesn't exist yet)
- No changes to `:shared`, `:androidApp`, `:iosApp`, `:desktopApp` — this is web-only.
