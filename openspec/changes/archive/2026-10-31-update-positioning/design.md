# Design

## Context

The canonical texts were fixed by Ciro on 2026-10-31 and are already live on GitHub and LinkedIn. This change only copies them into the portfolio; wording is not up for redesign here.

## Decisions

### The headline is split between the hero's role label and its intro

The headline has two parts: a title ("Product engineer.") and a statement ("I build with AI agents in parallel, from spec to real device."). The hero already has a short monospace role label above the name and an intro paragraph below it, and the hero spec caps the text under the name at 20 words so it fits the first viewport. Putting the title in `role` and the statement in `intro` shows the whole headline in the first viewport without changing the layout, keeps `role` short enough for the document title (`Ciro Feliu · Product engineer`), the CV header and the JSON-LD `jobTitle`, and leaves `bio` free for the full three-sentence bio, which the CV prints.

Alternative considered: the whole headline as `role`. Rejected because the role label is a one-line monospace kicker and the document title would become a sentence.

### The freelance title stays in English in Spanish

Ciro chose "Contract engineer for AI-first product teams" as the title in both languages, because it is the title clients search for. The experience spec required every role title to render in the active language, so the requirement now allows a title kept in English on purpose; the description is still translated.

### Freelance description length

The canonical freelance description has two sentences and wraps to about four lines at 1280px, above the previous "roughly 2 lines" guideline. The wording is fixed by Ciro, so the requirement now caps descriptions at two sentences instead.

### The preview image is regenerated, not edited

`og-image.png` has the old title baked into the pixels. It is redrawn at 1200x630 with the same layout, the Geist fonts already shipped in `composeResources/font` and the same hero photo, replacing only the kicker and the title lines. The script that draws it is a throwaway in the session's scratchpad, not committed, in line with how the current image was produced.

### Conflict with `group-blog-translations`

That change edits `BlogPages.kt` and blog tests but not `BLOG_DESCRIPTION` or any portfolio content file. This change touches only that constant there, so a merge conflict, if any, is limited to nearby lines.
