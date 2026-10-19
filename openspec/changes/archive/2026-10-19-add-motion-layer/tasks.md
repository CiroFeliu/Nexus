## 1. Helpers

- [x] 1.1 `rememberMotionSpec()` returning token-based specs or `snap()` under reduced motion
- [x] 1.2 Reveal modifier (built-in visibility API if it works on Wasm inside `verticalScroll`, custom fallback otherwise) with hoisted "revealed" state
- [x] 1.3 Spotlight border modifier (hover pointers only)
- [x] 1.4 Parallax modifier reading scroll inside `graphicsLayer`

## 2. Apply

- [x] 2.1 Hero entry choreography (role, name lines, bio, actions, portrait)
- [x] 2.2 Section headline + first block reveal for every section after the hero
- [x] 2.3 Project tiles: hover lift, spotlight border, pressed scale
- [x] 2.4 Portrait parallax on medium+
- [x] 2.5 Nav underline spring, bar background transition, compact menu staggered entry

## 3. Verification

- [x] 3.1 Unit test: with reduced motion, every reveal target starts visible
- [ ] 3.2 Web: 60fps scroll on a mid laptop (Chrome performance panel), no layout shift during reveals
- [ ] 3.3 Toggle the OS reduced-motion setting on macOS and Android; confirm static behaviour
- [x] 3.4 Run `./gradlew test`
- [x] 3.5 `openspec validate add-motion-layer --strict`
