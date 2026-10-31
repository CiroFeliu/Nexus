# Tasks

## 1. Shared content

- [ ] 1.1 In `HeroAboutSection.kt`, set `role` to "Product engineer" / "Ingeniero de producto", `intro` to the second sentence of the headline in each language, and `bio` to the canonical three-sentence bio; verify with `./gradlew :shared:jvmTest` that the hero, CV and copy-style tests pass
- [ ] 1.2 In `ExperienceSection.kt`, set the freelance entry's role to "Contract engineer for AI-first product teams" in both languages and its descriptions to the canonical English and Spanish texts; add a test asserting the freelance role is the same in both languages and the descriptions differ
- [ ] 1.3 Add a test asserting the hero's English and Spanish role and intro equal the canonical headline parts and that no hero, experience or document-title string contains "Architect" or "Arquitecto"

## 2. Web shell

- [ ] 2.1 In `index.html`, update `<title>`, meta description, `og:title`, `og:description`, `og:image:alt`, the JSON-LD `jobTitle` and the `<noscript>` role and summary to the new headline and bio
- [ ] 2.2 Regenerate `og-image.png` (1200x630, PNG) with the new headline, keeping layout, fonts and photo; verify its size with `file` and inspect it visually

## 3. Blog and project docs

- [ ] 3.1 Update `BLOG_DESCRIPTION` in `BlogPages.kt` so it no longer frames the blog as Android and mobile architecture; verify with `./gradlew :blogGenerator:test`
- [ ] 3.2 Update the project line in `AGENTS.md`, `README.md` and `openspec/config.yaml`

## 4. Validation

- [ ] 4.1 Run `./gradlew test` and check no old title remains with `grep -rn "Mobile Systems Architect\|Arquitecto de Sistemas\|Senior Android Developer"` outside `openspec/changes/archive`
- [ ] 4.2 Run the web app and check the hero, the experience entry and the document title in English and Spanish, in light and dark mode, and that the English document title equals the static `<title>` in `index.html`
