## 1. Shared CV content model

- [x] 1.1 Define `CvContent` data model in `:shared` (name, role, bio, skills, experience entries, project entries, contact links)
- [x] 1.2 Assemble `CvContent` from the existing `HeroAboutSection`, `SkillsSection`, `ExperienceSection`, `ProjectsSection`, `ContactSection` data — no content duplication
- [x] 1.3 Declare the `expect fun exportCvToPdf(content: CvContent)` (or equivalent) entry point in `:shared`

## 2. Web export (browser print-to-PDF)

- [x] 2.1 Build a print-friendly render of `CvContent` (dedicated composable or HTML/CSS `@media print` rules)
- [x] 2.2 Wire `actual exportCvToPdf` on `webApp` to trigger `window.print()` against the print-friendly view

## 3. Android export

- [x] 3.1 Implement `actual exportCvToPdf` on `androidApp` using `android.graphics.pdf.PdfDocument`, drawing each CV section to a `Canvas`
- [x] 3.2 Deliver the generated PDF via `Intent.ACTION_SEND` (share) or scoped storage save

## 4. iOS export

- [x] 4.1 Spike: confirm `UIGraphicsPDFRenderer` interop from Kotlin/Native for this project's toolchain
- [x] 4.2 Implement `actual exportCvToPdf` on `iosApp` using `UIGraphicsPDFRenderer`
- [x] 4.3 Deliver the generated PDF via `UIActivityViewController` (share sheet)

## 5. Desktop export

- [x] 5.1 Resolve the open question: choose JVM PDF-writing dependency (default assumption: Apache PDFBox) vs. hand-rolled writer — went with Apache PDFBox
- [x] 5.2 Implement `actual exportCvToPdf` on `desktopApp`, drawing each CV section as text-based PDF content
- [x] 5.3 Deliver the generated PDF via a native "Save As" file dialog

## 6. UI integration

- [x] 6.1 Add a "Download CV" action to `ContactSection.kt`, alongside the existing email/LinkedIn/GitHub links
- [x] 6.2 Wire the action to `exportCvToPdf`, passing the assembled `CvContent`

## 7. Verification

- [ ] 7.1 Manually verify PDF export end-to-end on all four targets
- [ ] 7.2 Confirm the exported CV reflects a content edit (e.g. change an Experience entry, re-export, verify it's reflected) with no separate file to update
