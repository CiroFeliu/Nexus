## 1. Navigation bar

- [ ] 1.1 Create `PortfolioTopBar` with wordmark, inline section links and `EN | ES` toggle; replace the two stacked controls in `PortfolioShell`
- [ ] 1.2 Animated accent underline for the active section, instant under reduced motion
- [ ] 1.3 Scroll-dependent bottom hairline driven by `derivedStateOf`
- [ ] 1.4 Compact layout: menu button + `DropdownMenu` with section links
- [ ] 1.5 Apply the shared interactive modifier (cursor, focus) to links, toggle and menu button

## 2. Hero

- [ ] 2.1 Split layout at medium+ (text column + 4:5 portrait), stacked left-aligned layout at compact
- [ ] 2.2 Replace the circular 96dp avatar with the shaped portrait; keep the initials fallback with the same shape
- [ ] 2.3 Replace the hero bio with the drafted 20-word copy in `design.md` (Ciro reviews)
- [ ] 2.4 Add primary "Download CV" and secondary "Contact" (scrolls to Contact) actions; pass the scroll callback without coupling the section file to the shell
- [ ] 2.5 One-time staggered entry animation respecting reduced motion
- [ ] 2.6 Verify the hero fits 1280x720 in both languages (headline <= 2 lines, CTAs visible, labels on one line)

## 3. Tests and verification

- [ ] 3.1 Update `SectionNavigationTest` / `PortfolioShellTest` for the single bar and compact menu
- [ ] 3.2 Update the hero smoke test (actions present, photo/fallback)
- [ ] 3.3 Run `./gradlew test`
- [ ] 3.4 Web: light/dark at 360/768/1280/1920; desktop app run
- [ ] 3.5 `openspec validate evolve-hero-and-navigation --strict`
