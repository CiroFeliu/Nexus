## Context

`webApp/src/webMain/resources/index.html` holds the title, description, OG tags (relative SVG image) and an inline SVG spinner; `styles.css` only resets the body. `main.kt` calls `ComposeViewport { App() }`. The site is deployed as static files to `cirofeliu.es` behind Cloudflare.

## Goals / Non-Goals

**Goals:**
- No white flash for dark-mode visitors; loader looks like the site.
- Link previews show a real image on the major platforms.
- Crawlers get name, role, summary and links without executing Wasm.

**Non-Goals:**
- Server-side rendering or per-language static HTML files.
- Analytics, cookie banners.
- Changing deployment.

## Decisions

- **Loader.** Pure HTML/CSS in `index.html`: background and text colors as CSS custom properties with a `@media (prefers-color-scheme: dark)` override that match the theme's `background`/`onSurfaceVariant`. Content: "Ciro Feliu" in a system sans (Geist is not loaded yet in HTML) and a thin indeterminate bar, honoring `prefers-reduced-motion` (static bar). The Compose entry removes the loader element once fonts are preloaded and the first frame is composed.
- **OG image.** A static PNG (1200x630, < 300 KB) designed with the new palette and typography: name, role, accent detail, real photo optional. Generated once and committed; not rendered at build time. Absolute URL because crawlers resolve relative `og:image` inconsistently.
- **Structured data.** Inline JSON-LD `Person` with `name`, `jobTitle`, `url`, `image`, `sameAs` (LinkedIn, GitHub). English values (crawler-facing), kept in sync manually with `HeroAboutSection` and `ContactSection`.
- **noscript.** Minimal semantic HTML (h1 name, p role, p summary, ul of links). Hidden whenever JS runs, so it never competes with the canvas.
- **Language sync.** `expect fun applyDocumentLanguage(language: Language, title: String)` called from `App` when the language changes: web actuals set `document.documentElement.lang` and `document.title`; other targets no-op. Keeps screen readers and browser translation prompts correct.

## Risks / Trade-offs

- [Risk] JSON-LD/noscript content can drift from the app copy. → Tasks include a manual check; content is short.
- [Trade-off] A raster OG image must be regenerated if the role changes. Acceptable: it changes rarely.

## Open Questions

- Whether the OG image includes the headshot (more personal) or only typography (cleaner). Default: typography plus a small photo crop.
