package app.luxion.nexus.cv

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import app.luxion.nexus.AndroidAppContext
import java.io.File
import java.io.FileOutputStream

private const val PAGE_WIDTH_PX = 595
private const val PAGE_HEIGHT_PX = 842
private const val MARGIN_PX = 48f

actual fun exportCvToPdf(content: CvContent) {
    val context = AndroidAppContext.context
    val document = PdfDocument()
    val writer = CvPageWriter(document)
    writer.draw(content)
    writer.finish()

    val fileName = "${content.name.replace(" ", "_")}_CV.pdf"
    val outputDir = File(context.cacheDir, "cv").apply { mkdirs() }
    val file = File(outputDir, fileName)
    FileOutputStream(file).use { document.writeTo(it) }
    document.close()

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    val chooser = Intent.createChooser(shareIntent, "Share CV").apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(chooser)
}

private class CvPageWriter(private val document: PdfDocument) {
    private val titlePaint = Paint().apply { textSize = 20f; isFakeBoldText = true }
    private val headingPaint = Paint().apply { textSize = 15f; isFakeBoldText = true }
    private val subheadingPaint = Paint().apply { textSize = 12f; isFakeBoldText = true }
    private val bodyPaint = Paint().apply { textSize = 11f }
    private val metaPaint = Paint().apply { textSize = 10f; color = Color.DKGRAY }

    private var pageNumber = 0
    private var page = startPage()
    private var canvas = page.canvas
    private var y = MARGIN_PX

    fun draw(content: CvContent) {
        drawLine(content.name, titlePaint, gapAfter = 4f)
        drawLine(content.role, subheadingPaint, gapAfter = 10f)
        drawWrapped(content.bio, bodyPaint, gapAfter = 14f)

        drawLine("Skills", headingPaint, gapAfter = 8f)
        content.skillCategories.forEach { category ->
            drawLine(category.label, subheadingPaint, gapAfter = 2f)
            drawWrapped(category.skills.joinToString(", "), bodyPaint, gapAfter = 8f)
        }

        drawLine("Experience", headingPaint, gapAfter = 8f)
        content.experience.forEach { entry ->
            drawLine("${entry.role}, ${entry.company}", subheadingPaint, gapAfter = 2f)
            drawLine(entry.dateRange, metaPaint, gapAfter = 2f)
            drawWrapped(entry.description, bodyPaint, gapAfter = 10f)
        }

        drawLine("Projects", headingPaint, gapAfter = 8f)
        content.projects.forEach { project ->
            drawLine("${project.name}, ${project.company}", subheadingPaint, gapAfter = 2f)
            drawWrapped(project.description, bodyPaint, gapAfter = 2f)
            val note = project.link ?: project.unavailableNote
            if (note != null) drawLine(note, metaPaint, gapAfter = 2f)
            if (project.techStack.isNotEmpty()) {
                drawLine(project.techStack.joinToString(", "), metaPaint, gapAfter = 10f)
            } else {
                y += 6f
            }
        }

        drawLine("Contact", headingPaint, gapAfter = 8f)
        content.contactLinks.forEach { link ->
            drawLine("${link.label}: ${link.url}", bodyPaint, gapAfter = 2f)
        }
    }

    fun finish() {
        document.finishPage(page)
    }

    private fun startPage(): PdfDocument.Page {
        val info = PdfDocument.PageInfo.Builder(PAGE_WIDTH_PX, PAGE_HEIGHT_PX, ++pageNumber).create()
        return document.startPage(info)
    }

    private fun newPageIfNeeded(lineHeight: Float) {
        if (y + lineHeight <= PAGE_HEIGHT_PX - MARGIN_PX) return
        document.finishPage(page)
        page = startPage()
        canvas = page.canvas
        y = MARGIN_PX
    }

    private fun drawLine(text: String, paint: Paint, gapAfter: Float) {
        val lineHeight = paint.textSize + gapAfter
        newPageIfNeeded(lineHeight)
        canvas.drawText(text, MARGIN_PX, y + paint.textSize, paint)
        y += lineHeight
    }

    private fun drawWrapped(text: String, paint: Paint, gapAfter: Float) {
        val maxWidth = PAGE_WIDTH_PX - 2 * MARGIN_PX
        val lines = wrapText(text, paint, maxWidth)
        lines.forEachIndexed { index, line ->
            drawLine(line, paint, if (index == lines.lastIndex) gapAfter else 2f)
        }
    }
}

private fun wrapText(text: String, paint: Paint, maxWidth: Float): List<String> {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var current = StringBuilder()
    for (word in words) {
        val candidate = if (current.isEmpty()) word else "$current $word"
        if (current.isNotEmpty() && paint.measureText(candidate) > maxWidth) {
            lines += current.toString()
            current = StringBuilder(word)
        } else {
            current = StringBuilder(candidate)
        }
    }
    if (current.isNotEmpty()) lines += current.toString()
    return lines
}
