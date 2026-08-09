## 1. Contact section

- [ ] 1.1 Add visible email text to `ContactSection.Content()`, wrapped in `SelectionContainer`
- [ ] 1.2 Add a bilingual closing statement below the identity/links content
- [ ] 1.3 Verify layout doesn't crowd the existing button row on narrow widths

## 2. Footer

- [ ] 2.1 Create the footer composable (new file under `navigation/`) with copyright line + build-tech note, bilingual
- [ ] 2.2 Render it from `PortfolioShell` once, after all `PortfolioSection.entries` content, inside the scrollable column

## 3. Verification

- [ ] 3.1 On `:webApp`, manually select the visible email text and paste it elsewhere to confirm it's genuinely copyable (not just visually selectable) — fall back to plain non-selectable text if it isn't, per design.md
- [ ] 3.2 Add/extend `commonTest` coverage for the new Contact and footer content
- [ ] 3.3 Run `./gradlew test` for all modules

## 4. Spec housekeeping

- [ ] 4.1 Confirm `openspec validate polish-contact-and-footer --strict` passes before archiving
