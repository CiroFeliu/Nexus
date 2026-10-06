## 1. Shell and navigation

- [ ] 1.1 Restructure `PortfolioShell` so the bar floats over a full-window scroll container; adjust section jump targets by the bar height
- [ ] 1.2 `PortfolioTopBar`: wordmark, inline links, `EN | ES` toggle, transparent/solid states from `derivedStateOf`
- [ ] 1.3 Active-section underline (positions from `onGloballyPositioned`)
- [ ] 1.4 Compact full-screen menu overlay with focus handling and `Esc`/back to close
- [ ] 1.5 Shared interactive modifier on all nav controls

## 2. Hero

- [ ] 2.1 Asymmetric 7/5 grid at expanded+, 6/6 at medium, stacked at compact
- [ ] 2.2 `heroDisplay` style and two-line name layout; role in mono; bio from the drafted 20-word copy in `design.md` (Ciro reviews)
- [ ] 2.3 Primary "Download CV" + secondary "Contact" scrolling to Contact, without coupling the section file to the shell
- [ ] 2.4 4:5 portrait with reserved space, downward offset overlapping the next section on medium+
- [ ] 2.5 Use the current `hero_photo.jpg` with the portrait column capped so the crop stays sharp enough
- [ ] 2.6 Grain overlay outside the scroll container
- [ ] 2.7 Verify first-viewport fit at 1280x720 and 1920x1080 in EN and ES

## 3. Tests and verification

- [ ] 3.1 Update shell/navigation tests (floating bar, compact menu open/select/close)
- [ ] 3.2 Update hero smoke test (actions, portrait/fallback)
- [ ] 3.3 Run `./gradlew test`
- [ ] 3.4 Web light/dark at 360/768/1280/1920; desktop run; 60fps scroll check with grain on
- [ ] 3.5 `openspec validate overhaul-hero-and-navigation --strict`
