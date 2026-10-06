## 1. Shared pieces

- [ ] 1.1 Wrap every section in `PortfolioContentContainer` with breakpoint section padding; left-align all section headers
- [ ] 1.2 Switch shell alternation to `background` / `surfaceContainerLow`
- [ ] 1.3 Import Material Symbols `open_in_new`, `content_copy`, `menu` and GitHub/LinkedIn marks as vector drawables

## 2. Projects

- [ ] 2.1 Featured projects as a 2-column (1 on compact) grid of flat tonal cards with aligned bottom links
- [ ] 2.2 Remaining projects as a compact list with filter chips derived from the non-featured catalogue, plus empty state
- [ ] 2.3 Replace the `Canvas` external-link icon and the filled "View project" buttons with text/icon links
- [ ] 2.4 Mono sentence-case company labels, tags restyled per the shape rule
- [ ] 2.5 Update `ProjectsSection` tests (featured rendered once, filter scope, empty state)

## 3. Other sections

- [ ] 3.1 Experience: date column at medium+, stacked on compact, mono dates
- [ ] 3.2 Skills: 2x2 category grid at expanded, single column otherwise
- [ ] 3.3 Contact: large selectable email + copy action, brand text links, single filled "Download CV"
- [ ] 3.4 Footer: left-aligned in the container with a top hairline

## 4. Verification

- [ ] 4.1 Run `./gradlew test`
- [ ] 4.2 Web: light/dark at 360/768/1280/1920 in EN and ES; check hover/cursor on every link
- [ ] 4.3 Desktop app run and Android debug build
- [ ] 4.4 Run the `compose-design-taste` pre-flight checklist
- [ ] 4.5 `openspec validate evolve-section-layouts --strict`
