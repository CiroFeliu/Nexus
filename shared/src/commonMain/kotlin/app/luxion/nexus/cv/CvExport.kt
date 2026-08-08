package app.luxion.nexus.cv

// Renders `content` to a PDF and delivers it via the platform's native mechanism: a print
// dialog on web, a share sheet on Android/iOS, a save dialog on desktop. See
// openspec/changes/add-dynamic-cv-export/design.md for why each platform renders natively
// instead of sharing one PDF-drawing library.
expect fun exportCvToPdf(content: CvContent)
