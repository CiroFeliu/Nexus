package app.luxion.nexus.cv

// A standalone HTML document rendering of [CvContent], loaded into a hidden iframe by the web
// actuals and printed there via that iframe's own `window.print()`. Printing happens in the
// iframe's own browsing context — never in the main document — so it can't disturb the
// portfolio's own canvas/layout, and the iframe's plain (non-`overflow: hidden`) body lets
// multi-page content paginate normally instead of being clipped to one viewport.
internal fun CvContent.toPrintDocument(): String = buildString {
    append("<!DOCTYPE html><html><head><meta charset=\"utf-8\">")
    append("<title>").append(name.escapeHtml()).append(" - CV</title>")
    append("<style>").append(PRINT_STYLESHEET).append("</style>")
    append("</head><body>")
    append(toPrintHtml())
    append("</body></html>")
}

private const val PRINT_STYLESHEET = """
    body { font-family: sans-serif; color: #000; margin: 0; padding: 32px; }
    h1 { margin: 0 0 4px; }
    h2 { margin: 20px 0 8px; }
    h3 { margin: 10px 0 2px; }
    p { margin: 0 0 8px; }
    .cv-role { font-weight: bold; margin-top: 0; }
    .cv-meta { color: #555; font-size: 0.9em; }
    ul { margin: 0; padding-left: 20px; }
"""

private fun CvContent.toPrintHtml(): String = buildString {
    appendHeading(name, level = 1)
    appendParagraph(role, cssClass = "cv-role")
    appendParagraph(bio)

    appendHeading("Skills", level = 2)
    skillCategories.forEach { category ->
        appendHeading(category.label, level = 3)
        appendParagraph(category.skills.joinToString(", "))
    }

    appendHeading("Experience", level = 2)
    experience.forEach { entry ->
        appendHeading("${entry.role} — ${entry.company}", level = 3)
        appendParagraph(entry.dateRange, cssClass = "cv-meta")
        appendParagraph(entry.description)
    }

    appendHeading("Projects", level = 2)
    projects.forEach { project ->
        appendHeading("${project.name} — ${project.company}", level = 3)
        appendParagraph(project.description)
        if (project.techStack.isNotEmpty()) {
            appendParagraph(project.techStack.joinToString(", "), cssClass = "cv-meta")
        }
        val note = project.link ?: project.unavailableNote
        if (note != null) {
            appendParagraph(note, cssClass = "cv-meta")
        }
    }

    appendHeading("Contact", level = 2)
    append("<ul>")
    contactLinks.forEach { link ->
        append("<li>").append(link.label.escapeHtml()).append(": ").append(link.url.escapeHtml()).append("</li>")
    }
    append("</ul>")
}

private fun StringBuilder.appendHeading(text: String, level: Int) {
    append("<h").append(level).append('>').append(text.escapeHtml()).append("</h").append(level).append('>')
}

private fun StringBuilder.appendParagraph(text: String, cssClass: String? = null) {
    val classAttr = if (cssClass != null) " class=\"$cssClass\"" else ""
    append("<p").append(classAttr).append('>').append(text.escapeHtml()).append("</p>")
}

private fun String.escapeHtml(): String = replace("&", "&amp;")
    .replace("<", "&lt;")
    .replace(">", "&gt;")
    .replace("\"", "&quot;")
