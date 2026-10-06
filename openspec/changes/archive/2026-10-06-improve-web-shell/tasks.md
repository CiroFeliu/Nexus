## 1. Loader

- [x] 1.1 Replace the SVG spinner with a theme-matched HTML/CSS loader (light + dark via `prefers-color-scheme`, reduced-motion aware)
- [x] 1.2 Remove the loader from the Compose entry once fonts are ready and the first frame is composed

## 2. Metadata

- [x] 2.1 Remove dashes and the stale comment from `index.html`; update title/description copy
- [x] 2.2 Create `og-image.png` (1200x630) in the new visual language and reference it with an absolute URL; delete `og-image.svg`
- [x] 2.3 Add `og:url`, `og:locale` + alternate, `twitter:card`, canonical link
- [x] 2.4 Add JSON-LD `Person`
- [x] 2.5 Add a `<noscript>` summary with contact links

## 3. Language sync

- [x] 3.1 Add `applyDocumentLanguage` (`expect`/`actual`, web sets `lang` and title, other targets no-op) and call it on language change

## 4. Verification

- [x] 4.1 Load the production build in light and dark with cache disabled; no white flash
- [ ] 4.2 Validate previews with a link-preview debugger (LinkedIn Post Inspector or similar) after deploy
- [ ] 4.3 Validate JSON-LD with a structured-data validator
- [x] 4.4 Run `./gradlew test`
- [x] 4.5 `openspec validate improve-web-shell --strict`
