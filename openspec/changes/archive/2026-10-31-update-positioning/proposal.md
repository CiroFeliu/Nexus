# Proposal

## Why

Ciro settled a single public identity, already applied on GitHub and LinkedIn: a product engineer who builds with AI agents in parallel, from spec to real device. `cirofeliu.es` still presents him as "Senior Android Developer & Mobile Systems Architect" in the hero, the document title, the meta and Open Graph tags, the preview image, the JSON-LD, the no-JavaScript fallback, the CV and the freelance experience entry. The web must say the same as the other profiles before the first blog post goes out on 2026-11-03.

## What Changes

- Hero: the role title becomes "Product engineer" / "Ingeniero de producto" and the intro becomes the second half of the headline ("I build with AI agents in parallel, from spec to real device." / "Desarrollo con agentes de IA en paralelo, de la spec al dispositivo real."), so the hero reads the full headline. Android and Kotlin stay as skills, not as the title.
- Bio (used by the CV export): replaced by the canonical three-sentence bio in English and Spanish.
- Freelance experience entry: the role title becomes "Contract engineer for AI-first product teams" in both languages, with the new English and Spanish descriptions.
- `index.html`: title, meta description, Open Graph title/description/image alt, JSON-LD `jobTitle` and the `<noscript>` block use the new headline and bio.
- `og-image.png`: regenerated with the new headline instead of the old title, keeping its layout, fonts and photo.
- Blog description: no longer frames the writing as Android and mobile architecture.
- `AGENTS.md`, `README.md` and `openspec/config.yaml`: the project line describes Nexus as Ciro Feliu's portfolio as a product engineer.

## Non-goals

- Rewriting the other experience entries with results, the ShogunAi project card, the NDA cards or the annual-accounts PDF link (a later change).
- Linking ShogunAi anywhere: it is not public yet. The bio names it without a link.
- Changing the skills section.

## Capabilities

### New Capabilities

None.

### Modified Capabilities

- `hero-about-section`: the role title and the intro together state the headline; the bio stays a separate, longer text used by the CV.
- `experience-section`: a role title may stay in English in the Spanish version when it is an English job title on purpose.
- `web-seo-metadata`: title, description, preview image, JSON-LD and fallback state the same headline as the hero.

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/sections/HeroAboutSection.kt`, `ExperienceSection.kt` and their tests in `commonTest`.
- `webApp/src/webMain/resources/index.html`, `og-image.png`.
- `blogGenerator/src/main/kotlin/app/luxion/nexus/blog/BlogPages.kt` (one constant; the in-progress `group-blog-translations` change edits other lines of the same file).
- `AGENTS.md`, `README.md`, `openspec/config.yaml`.
