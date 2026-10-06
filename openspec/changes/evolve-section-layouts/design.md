## Context

Sections are isolated files under `navigation/sections/`, rendered in order by `PortfolioShell`, which wraps each in a `Surface` alternating `background`/`surface`. `ProjectsSection` owns the project data, `filterOptions` and `matchesFilter`, already unit tested. This is the **evolve** variant; it changes layout and component styling only, never section order, ids or labels.

## Goals / Non-Goals

**Goals:**
- No duplicated content, no repeated primary buttons, no hand-drawn icons.
- Each section uses a distinct layout family (cards, list, two-column rows, grid of blocks, statement + links).
- Consistent width, gutters and vertical rhythm from the shared tokens.

**Non-Goals:**
- Scroll-linked motion, bento grids, single-background page (overhaul variant).
- Project screenshots (overhaul asks for them; evolve stays text-first).
- New content.

## Decisions

- **Projects split.** `featuredProjects = projects.filter { it.featured }` render as cards; `otherProjects = projects.filterNot { it.featured }` render as a list. `filterOptions` is derived from `otherProjects` only, so every chip can match something in the list. `matchesFilter` keeps its signature; the list applies it to `otherProjects`. If a filter yields nothing (should not happen with derived options, but guards future data), an inline empty message renders.
- **Project card.** `Surface(color = surfaceContainer, shape = large)` with 24dp padding, company (mono label), name (`titleLarge`), description (`bodyMedium`, `onSurfaceVariant`), tags (mono, small tonal chips), and a bottom-pinned text link "View project" + icon, or the unavailable note. Cards in the same row share height (`IntrinsicSize.Min` row or a 2-column `Layout`) so links align.
- **Project list row.** One `Row` per project: name + company on the left, tags in the middle (hidden on compact), link icon button on the right with `contentDescription` "View project: <name>". Rows separated by spacing and a single `outlineVariant` hairline group border, not a divider per row.
- **Icons.** Material Symbols Rounded (`open_in_new`, `content_copy`, `menu` for the nav) imported as vector drawable XML; GitHub and LinkedIn marks from their official brand assets (Simple Icons where available), tinted from the scheme. No `material-icons-extended` dependency.
- **Experience rows.** Medium+: `Row` with a 180dp date column (Geist Mono, `onSurfaceVariant`), the existing timeline rail, then content. Compact: rail + content, date first line in mono.
- **Skills grid.** Expanded+: two `Row`s of two category blocks (`weight(1f)` each); category label `titleMedium`, chips in a `FlowRow`. Compact/medium: single column.
- **Contact.** Email in `headlineSmall` inside `SelectionContainer` with a copy `IconButton` (clipboard via `LocalClipboard`); text links for LinkedIn/GitHub with brand marks; filled "Download CV". Closing statement below in `bodyLarge`.
- **Rhythm.** `PortfolioShell` alternates `background` / `surfaceContainerLow`; the existing `design-system` alternation requirement remains satisfied (different tokens, same scheme).

## Risks / Trade-offs

- [Risk] Moving featured projects out of the filterable list changes filtering semantics (featured never filtered). → Spec delta makes it explicit; the Featured block is always visible above.
- [Risk] Brand mark usage rules (LinkedIn) restrict recolouring. → Use the official monochrome variant where the brand allows it; otherwise keep the brand colour.
- [Trade-off] Equal-height card rows need a custom layout or intrinsic measurement; small cost, only six cards.

## Open Questions

- Whether "Internal Tools" / "Device Communications" (NDA, no link) stay in the list or are merged into one "Confidential work at S2 Grupo" row.
