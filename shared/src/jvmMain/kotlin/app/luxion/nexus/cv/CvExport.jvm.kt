package app.luxion.nexus.cv

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

private const val MARGIN = 48f

actual fun exportCvToPdf(content: CvContent) {
    val document = PDDocument()
    val writer = CvPageWriter(document)
    writer.draw(content)
    writer.finish()

    val file = pickSaveFile(content)
    if (file == null) {
        document.close()
        return
    }
    document.save(file)
    document.close()
}

private fun pickSaveFile(content: CvContent): File? {
    val dialog = FileDialog(null as Frame?, "Save CV", FileDialog.SAVE)
    dialog.file = "${content.name.replace(" ", "_")}_CV.pdf"
    dialog.isVisible = true
    val name = dialog.file ?: return null
    val directory = dialog.directory ?: return null
    val fileName = if (name.endsWith(".pdf", ignoreCase = true)) name else "$name.pdf"
    return File(directory, fileName)
}

private class CvPageWriter(private val document: PDDocument) {
    private val titleFont = PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD)
    private val headingFont = PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD)
    private val subheadingFont = PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD)
    private val bodyFont = PDType1Font(Standard14Fonts.FontName.HELVETICA)
    private val metaFont = PDType1Font(Standard14Fonts.FontName.HELVETICA)

    private var page = startPage()
    private var contentStream = PDPageContentStream(document, page)
    private var y = page.mediaBox.height - MARGIN

    fun draw(content: CvContent) {
        drawLine(content.name, titleFont, 20f, gapAfter = 4f)
        drawLine(content.role, subheadingFont, 13f, gapAfter = 10f)
        drawWrapped(content.bio, bodyFont, 11f, gapAfter = 14f)

        drawLine("Skills", headingFont, 15f, gapAfter = 8f)
        content.skillCategories.forEach { category ->
            drawLine(category.label, subheadingFont, 12f, gapAfter = 2f)
            drawWrapped(category.skills.joinToString(", "), bodyFont, 11f, gapAfter = 8f)
        }

        drawLine("Experience", headingFont, 15f, gapAfter = 8f)
        content.experience.forEach { entry ->
            drawLine("${entry.role}, ${entry.company}", subheadingFont, 12f, gapAfter = 2f)
            drawLine(entry.dateRange, metaFont, 10f, gapAfter = 2f)
            drawWrapped(entry.description, bodyFont, 11f, gapAfter = 10f)
        }

        drawLine("Projects", headingFont, 15f, gapAfter = 8f)
        content.projects.forEach { project ->
            drawLine("${project.name}, ${project.company}", subheadingFont, 12f, gapAfter = 2f)
            drawWrapped(project.description, bodyFont, 11f, gapAfter = 2f)
            val note = project.link ?: project.unavailableNote
            if (note != null) drawLine(note, metaFont, 10f, gapAfter = 2f)
            if (project.techStack.isNotEmpty()) {
                drawLine(project.techStack.joinToString(", "), metaFont, 10f, gapAfter = 10f)
            } else {
                y -= 6f
            }
        }

        drawLine("Contact", headingFont, 15f, gapAfter = 8f)
        content.contactLinks.forEach { link ->
            drawLine("${link.label}: ${link.url}", bodyFont, 11f, gapAfter = 2f)
        }
    }

    fun finish() {
        contentStream.close()
    }

    private fun startPage(): PDPage = PDPage(PDRectangle.A4).also { document.addPage(it) }

    private fun newPageIfNeeded(lineHeight: Float) {
        if (y - lineHeight >= MARGIN) return
        contentStream.close()
        page = startPage()
        contentStream = PDPageContentStream(document, page)
        y = page.mediaBox.height - MARGIN
    }

    private fun drawLine(text: String, font: PDFont, size: Float, gapAfter: Float) {
        val lineHeight = size + gapAfter
        newPageIfNeeded(lineHeight)
        val safeText = text.sanitizeForStandardFont()
        contentStream.beginText()
        contentStream.setFont(font, size)
        contentStream.newLineAtOffset(MARGIN, y - size)
        contentStream.showText(safeText)
        contentStream.endText()
        y -= lineHeight
    }

    private fun drawWrapped(text: String, font: PDFont, size: Float, gapAfter: Float) {
        val maxWidth = page.mediaBox.width - 2 * MARGIN
        val lines = wrapText(text.sanitizeForStandardFont(), font, size, maxWidth)
        lines.forEachIndexed { index, line ->
            drawLine(line, font, size, if (index == lines.lastIndex) gapAfter else 2f)
        }
    }
}

private fun String.sanitizeForStandardFont(): String = replace('—', '-').replace('–', '-')

private fun wrapText(text: String, font: PDFont, size: Float, maxWidth: Float): List<String> {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var current = StringBuilder()
    for (word in words) {
        val candidate = if (current.isEmpty()) word else "$current $word"
        val width = font.getStringWidth(candidate) / 1000f * size
        if (current.isNotEmpty() && width > maxWidth) {
            lines += current.toString()
            current = StringBuilder(word)
        } else {
            current = StringBuilder(candidate)
        }
    }
    if (current.isNotEmpty()) lines += current.toString()
    return lines
}
