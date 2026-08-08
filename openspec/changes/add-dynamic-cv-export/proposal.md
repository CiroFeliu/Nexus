## Why

A visitor evaluating Ciro often wants a portable, offline artifact to pass along internally (e.g. to a hiring manager or recruiter chain) rather than a link. A hand-maintained static resume file would drift from the live portfolio content; generating the PDF from the same shared content the portfolio already renders keeps the two permanently in sync.

## What Changes

- Add a shared CV content model in `:shared`, derived from the same data already backing the Hero/About, Skills, Experience, and Projects sections — one source of truth, no content duplicated into a separate resume file.
- Add a "Download CV" action, exposed from the Contact section, that generates a PDF on demand and hands it to the platform's native save/share mechanism.
- Add per-platform PDF export via `expect`/`actual`: browser print-to-PDF on web, `android.graphics.pdf.PdfDocument` + share intent on Android, `UIGraphicsPDFRenderer` + share sheet on iOS, and a JVM PDF writer + native save dialog on desktop.

## Capabilities

### New Capabilities
- `cv-export`: shared CV content model plus the per-platform capability to render it to a PDF and deliver it to the user (download/save/share, whichever fits the platform).

### Modified Capabilities
- `contact-section`: gains a "Download CV" action alongside the existing email/LinkedIn/GitHub links.

## Impact

- `:shared` — new CV content model (reusing existing section data), new `expect fun` PDF export API
- `androidApp`, `iosApp`, `desktopApp`, `webApp` — `actual` PDF export implementations per platform
- `shared/.../navigation/sections/ContactSection.kt` — new action wired to the export capability
- New dependencies possible on JVM/Android/Desktop for PDF writing (exact library chosen during implementation, see design.md open questions); no new dependency expected for web (browser-native print) or iOS (UIKit-native)
