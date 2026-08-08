## 1. Shared CV content model

- [ ] 1.1 Define `CvContent` data model in `:shared` (name, role, bio, skills, experience entries, project entries, contact links)
- [ ] 1.2 Assemble `CvContent` from the existing `HeroAboutSection`, `SkillsSection`, `ExperienceSection`, `ProjectsSection`, `ContactSection` data — no content duplication
- [ ] 1.3 Declare the `expect fun exportCvToPdf(content: CvContent)` (or equivalent) entry point in `:shared`

## 2. Web export (browser print-to-PDF)

- [ ] 2.1 Build a print-friendly render of `CvContent` (dedicated composable or HTML/CSS `@media print` rules)
- [ ] 2.2 Wire `actual exportCvToPdf` on `webApp` to trigger `window.print()` against the print-friendly view

## 3. Android export

- [ ] 3.1 Implement `actual exportCvToPdf` on `androidApp` using `android.graphics.pdf.PdfDocument`, drawing each CV section to a `Canvas`
- [ ] 3.2 Deliver the generated PDF via `Intent.ACTION_SEND` (share) or scoped storage save

## 4. iOS export

- [ ] 4.1 Spike: confirm `UIGraphicsPDFRenderer` interop from Kotlin/Native for this project's toolchain
- [ ] 4.2 Implement `actual exportCvToPdf` on `iosApp` using `UIGraphicsPDFRenderer`
- [ ] 4.3 Deliver the generated PDF via `UIActivityViewController` (share sheet)

## 5. Desktop export

- [ ] 5.1 Resolve the open question: choose JVM PDF-writing dependency (default assumption: Apache PDFBox) vs. hand-rolled writer
- [ ] 5.2 Implement `actual exportCvToPdf` on `desktopApp`, drawing each CV section as text-based PDF content
- [ ] 5.3 Deliver the generated PDF via a native "Save As" file dialog

## 6. UI integration

- [ ] 6.1 Add a "Download CV" action to `ContactSection.kt`, alongside the existing email/LinkedIn/GitHub links
- [ ] 6.2 Wire the action to `exportCvToPdf`, passing the assembled `CvContent`

## 7. Verification

- [ ] 7.1 Manually verify PDF export end-to-end on all four targets
- [ ] 7.2 Confirm the exported CV reflects a content edit (e.g. change an Experience entry, re-export, verify it's reflected) with no separate file to update
