## 1. Page structure

- [ ] 1.1 Render sections on one background with full-width hairline separators; remove the alternating `Surface`s
- [ ] 1.2 Large left-aligned section headlines, section padding from layout tokens
- [ ] 1.3 Import Material Symbols `open_in_new`, `content_copy`, `menu`, `close` and GitHub/LinkedIn marks as vector drawables

## 2. Projects

- [ ] 2.1 Pure bento span function (item count + width class to spans) with unit tests (cell count equals item count)
- [ ] 2.2 Bento `Layout` for featured projects with `ProjectVisual` (image or designed fallback)
- [ ] 2.3 Request project images from Ciro and add the ones supplied as WebP sized for display
- [ ] 2.4 Index list for non-featured projects with filter chips derived from that list and an empty state
- [ ] 2.5 Replace the `Canvas` link icon and filled buttons with links/icon buttons
- [ ] 2.6 Update `ProjectsSection` tests

## 3. Other sections

- [ ] 3.1 Experience editorial rows (display company, mono dates), stacked on compact
- [ ] 3.2 Skills as category column lists; optional core-stack marquee (static under reduced motion)
- [ ] 3.3 Contact statement block with display email link, copy action, brand links, single filled "Download CV"; footer inside the block

## 4. Verification

- [ ] 4.1 Run `./gradlew test`
- [ ] 4.2 Web light/dark at 360/768/1280/1920 in EN and ES; desktop run; Android debug build
- [ ] 4.3 Run the `compose-design-taste` pre-flight checklist (layout families, bento, eyebrows, CTA intents)
- [ ] 4.4 `openspec validate overhaul-section-layouts --strict`
