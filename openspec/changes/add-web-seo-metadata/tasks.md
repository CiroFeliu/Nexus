## 1. Assets

- [ ] 1.1 Create `favicon.svg` under `webApp/src/webMain/resources/`
- [ ] 1.2 Create a placeholder Open Graph preview image (e.g. `og-image.png` or `.svg`) under `webApp/src/webMain/resources/`

## 2. Metadata

- [ ] 2.1 Replace `<title>Nexus</title>` with the real name/role title
- [ ] 2.2 Add `<meta name="description">` summarizing the portfolio
- [ ] 2.3 Add `og:title`, `og:description`, `og:type`, `og:image` meta tags
- [ ] 2.4 Add `<link rel="icon" type="image/svg+xml" href="favicon.svg">`

## 3. Verification

- [ ] 3.1 Run `./gradlew :webApp:wasmJsBrowserDevelopmentRun` and confirm the browser tab shows the title and favicon
- [ ] 3.2 Validate Open Graph tags with a link-preview debugger (e.g. paste the built `index.html` output into an OG validator, or inspect via browser devtools)
