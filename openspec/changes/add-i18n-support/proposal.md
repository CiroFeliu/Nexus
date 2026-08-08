## Why

The portfolio is currently English-only, with every section's copy hardcoded as `const val String` in its own file. Ciro is based in an Spanish-speaking market as well as targeting international/English-speaking recruiters and engineers, so the portfolio should greet each visitor in their language automatically, while still letting them switch manually.

## What Changes

- Introduce a shared `Language` model (English, Spanish) and an app-wide language state, resolved on startup from the platform's system locale, with English as the fallback for unsupported locales.
- Add a manual language override control, exposed from the shell (not tied to any one section), that lets a visitor switch language regardless of system locale.
- Persist the manual override across sessions, on all four targets, so a returning visitor's explicit choice sticks even if their system locale hasn't changed.
- Translate all existing portfolio content — Hero/About, Skills, Experience, Projects, Contact — into Spanish and English, replacing each section's hardcoded strings with language-aware content lookups.

## Capabilities

### New Capabilities
- `i18n-support`: language model, system-locale detection, manual override, and cross-session persistence, shared across all four targets.

### Modified Capabilities
- `hero-about-section`: content renders in the active language (English/Spanish).
- `skills-section`: content renders in the active language (English/Spanish).
- `experience-section`: content renders in the active language (English/Spanish).
- `projects-section`: content renders in the active language (English/Spanish).
- `contact-section`: content renders in the active language (English/Spanish).
- `navigation-shell`: hosts the manual language-override control, visible regardless of scroll position.

## Impact

- `:shared` — new `Language` model, locale-detection `expect fun`, persistence `expect`/`actual` (or a KMP settings dependency — see design.md), `LocalAppLanguage` composition local
- Every file under `shared/.../navigation/sections/` — hardcoded strings replaced with per-language content
- `shared/.../navigation/PortfolioShell.kt` — adds the language-switcher control
- `androidApp`, `iosApp`, `desktopApp`, `webApp` — `actual` implementations of locale detection and persisted-preference storage
