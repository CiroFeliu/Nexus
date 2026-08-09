## Why

The Contact section is currently four pill buttons and nothing else — no visible email address, no closing statement, and the page simply stops after the button row with no footer. It reads as unfinished rather than a deliberate close to the portfolio.

## What Changes

- Show the email address as visible, selectable text in the Contact section (in addition to, not instead of, the existing "Email" `mailto:` button).
- Add a short closing line/statement to the Contact section (e.g. availability or a friendly sign-off), bilingual per existing `Language`-keyed content conventions.
- Add a minimal footer below all sections: copyright line and a short "built with Compose Multiplatform" note, rendered once at the end of the page (not per-section).

## Capabilities

### New Capabilities
- `portfolio-footer`: a page-level footer rendered once after all `PortfolioSection` content, containing a copyright line and a build-tech note.

### Modified Capabilities
- `contact-section`: adds a requirement that the email address renders as visible/selectable text and that the section includes a closing statement.

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/sections/ContactSection.kt`: add visible email text and closing line.
- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/PortfolioShell.kt`: render the new footer composable after the scrollable section `Column`'s content (still inside the scroll area, since it's part of the page content, not a sticky control like the language switcher).
- New file under `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/` for the footer composable.
