## Context

The portfolio's content (name, role, bio, skills, experience, projects, contact links) already lives in `:shared` as per-section Kotlin constants (see `HeroAboutSection`, `SkillsSection`, `ExperienceSection`, `ProjectsSection`). There is no backend and none is planned, so PDF generation must happen entirely on-device/in-browser, per platform, with no server round-trip.

"Dynamic" here means: the PDF is generated from the live shared content model at export time, not a static file checked into the repo — if a future change updates, say, Experience, the next CV export reflects it automatically with no separate resume file to remember to update.

## Goals / Non-Goals

**Goals:**
- One shared CV content model in `:shared`, assembled from the existing section data (no re-typing name/role/bio/skills/experience/projects a second time).
- A single "Download CV" action, exposed once (from Contact), that works on all four targets.
- Delivery uses each platform's native, idiomatic mechanism — browser download, Android share/save, iOS share sheet, desktop save dialog — rather than a one-size-fits-all approach.

**Non-Goals:**
- No custom PDF layout engine shared across platforms — page layout is produced per-platform (see Decisions), not by one shared renderer.
- No selectable/copyable-text guarantee on every platform in v1 — see the JVM/Android rasterization trade-off below.
- No server-side or pre-baked PDF generation, since deployment/backend is out of scope for the project overall.

## Decisions

- **Shared CV content model, per-platform rendering.** `:shared` exposes a plain data model (`CvContent`: name, role, bio, skills, experience entries, project entries, contact links) built from the existing section objects. Each platform's `actual` PDF exporter reads this model and produces PDF bytes its own way. Rationale: a single cross-platform PDF-drawing library for Compose Multiplatform isn't a settled/stable dependency yet at this project's level of investment; per-platform native PDF APIs are mature, well-documented, and dependency-light (or dependency-free) on three of the four targets.
- **Web: browser print-to-PDF.** A dedicated print-friendly view (CSS `@media print` rules, or a simple print-optimized HTML render of `CvContent`) is triggered via `window.print()`. The user picks "Save as PDF" in the browser's native print dialog. No PDF-writing dependency needed on web. Trade-off: the exact output depends on the user's browser/OS print dialog rather than being pixel-identical everywhere — acceptable for a CV.
- **Android: `android.graphics.pdf.PdfDocument`.** Built into the Android SDK, no external dependency. Draws each CV page to a `Canvas`, wrapped as PDF pages, then handed to `Intent.ACTION_SEND` (share) or scoped storage (save). Text is drawn via `Canvas.drawText`, so it stays selectable/searchable in the resulting PDF.
- **iOS: `UIGraphicsPDFRenderer`.** Native UIKit API, no external dependency, called from Kotlin/Native via its Objective-C/UIKit interop. Draws CV content into a PDF context, then hands the resulting `Data` to `UIActivityViewController` (share sheet).
- **Desktop (JVM): a JVM PDF-writing dependency, chosen during implementation.** Unlike Android, plain JVM has no built-in PDF writer. Candidates to evaluate at implementation time: Apache PDFBox (Apache-2.0, mature) vs. a minimal hand-rolled writer (avoids a dependency but more code to maintain). Default assumption for planning: Apache PDFBox, given its permissive license and text-based (not rasterized) output. Delivered via the OS-native "Save As" file dialog (`javax.swing.JFileChooser` or Compose Desktop's file dialog API).

## Risks / Trade-offs

- [Four separate PDF-generation code paths, one per platform] → Mitigation: all four read from the same shared `CvContent` model, so layout/content logic doesn't drift even though the drawing code differs; only the rendering backend is platform-specific.
- [Desktop needs a new external dependency; license/size not yet vetted] → Mitigation: flagged as an explicit open question below, to be resolved before `tasks.md` implementation work starts on the desktop target specifically.
- [Web output quality depends on the browser's print dialog, not fully controlled] → Acceptable for a CV use case; mitigated by print-specific CSS to keep layout readable regardless of browser print engine.
- [iOS Kotlin/Native UIKit interop for PDF drawing is less common than the other three targets] → Mitigation: scope the iOS task to be picked up with extra buffer time / a spike task before full implementation, called out explicitly in tasks.md.

## Open Questions

- Final choice of JVM/desktop PDF-writing dependency (Apache PDFBox vs. hand-rolled minimal writer) — resolve at the start of desktop implementation.
- Exact CV page layout/sections to include (full detail vs. a condensed one-pager) — content decision for whoever picks up the shared `CvContent` model task.
