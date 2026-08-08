package app.luxion.nexus.cv

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSString
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.writeToFile
import platform.UIKit.NSFontAttributeName
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIGraphicsPDFRendererContext
import platform.UIKit.drawAtPoint
import platform.UIKit.sizeWithAttributes

private const val PAGE_WIDTH = 595.0 // A4 at 72dpi
private const val PAGE_HEIGHT = 842.0
private const val MARGIN = 48.0

// iOS export uses UIKit's UIGraphicsPDFRenderer (no external dependency): each CV section
// is drawn as text into a PDF graphics context, paginated into A4-sized pages, then handed
// to a share sheet so the user can save it to Files or send it on. See tasks.md item 4.1 —
// this is the target with the least precedent for Kotlin/Native <-> UIKit interop of the
// four, so treat this file as the spike result: re-verify against a real simulator run.
@OptIn(ExperimentalForeignApi::class)
actual fun exportCvToPdf(content: CvContent) {
    val pageRect = CGRectMake(0.0, 0.0, PAGE_WIDTH, PAGE_HEIGHT)
    val renderer = UIGraphicsPDFRenderer(bounds = pageRect)
    val data = renderer.PDFDataWithActions { context ->
        CvPageWriter(context!!).draw(content)
    }

    val fileName = "${content.name.replace(" ", "_")}_CV.pdf"
    val filePath = NSTemporaryDirectory() + fileName
    data.writeToFile(filePath, atomically = true)

    val fileUrl = NSURL.fileURLWithPath(filePath)
    val activityController = UIActivityViewController(activityItems = listOf(fileUrl), applicationActivities = null)
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(activityController, animated = true, completion = null)
}

// Draws CV content across as many A4 pages as needed, tracking the current vertical offset
// and starting a new PDF page whenever the next line would overflow the margin.
@OptIn(ExperimentalForeignApi::class)
private class CvPageWriter(private val context: UIGraphicsPDFRendererContext) {
    private val titleFont = UIFont.boldSystemFontOfSize(20.0)
    private val headingFont = UIFont.boldSystemFontOfSize(15.0)
    private val subheadingFont = UIFont.boldSystemFontOfSize(12.0)
    private val bodyFont = UIFont.systemFontOfSize(11.0)
    private val metaFont = UIFont.systemFontOfSize(10.0)

    private var y = MARGIN

    init {
        context.beginPage()
    }

    fun draw(content: CvContent) {
        drawLine(content.name, titleFont, gapAfter = 4.0)
        drawLine(content.role, subheadingFont, gapAfter = 10.0)
        drawWrapped(content.bio, bodyFont, gapAfter = 14.0)

        drawLine("Skills", headingFont, gapAfter = 8.0)
        content.skillCategories.forEach { category ->
            drawLine(category.label, subheadingFont, gapAfter = 2.0)
            drawWrapped(category.skills.joinToString(", "), bodyFont, gapAfter = 8.0)
        }

        drawLine("Experience", headingFont, gapAfter = 8.0)
        content.experience.forEach { entry ->
            drawLine("${entry.role} — ${entry.company}", subheadingFont, gapAfter = 2.0)
            drawLine(entry.dateRange, metaFont, gapAfter = 2.0)
            drawWrapped(entry.description, bodyFont, gapAfter = 10.0)
        }

        drawLine("Projects", headingFont, gapAfter = 8.0)
        content.projects.forEach { project ->
            drawLine("${project.name} — ${project.company}", subheadingFont, gapAfter = 2.0)
            drawWrapped(project.description, bodyFont, gapAfter = 2.0)
            val note = project.link ?: project.unavailableNote
            if (note != null) drawLine(note, metaFont, gapAfter = 2.0)
            if (project.techStack.isNotEmpty()) {
                drawLine(project.techStack.joinToString(", "), metaFont, gapAfter = 10.0)
            } else {
                y += 6.0
            }
        }

        drawLine("Contact", headingFont, gapAfter = 8.0)
        content.contactLinks.forEach { link ->
            drawLine("${link.label}: ${link.url}", bodyFont, gapAfter = 2.0)
        }
    }

    private fun newPageIfNeeded(lineHeight: Double) {
        if (y + lineHeight <= PAGE_HEIGHT - MARGIN) return
        context.beginPage()
        y = MARGIN
    }

    private fun drawLine(text: String, font: UIFont, gapAfter: Double) {
        val lineHeight = font.lineHeight + gapAfter
        newPageIfNeeded(lineHeight)
        // `as NSString` triggers a "cast can never succeed" compiler warning — a known
        // false-positive for Kotlin/Native's toll-free String<->NSString bridge, not a bug.
        (text as NSString).drawAtPoint(CGPointMake(MARGIN, y), withAttributes = fontAttributes(font))
        y += lineHeight
    }

    private fun drawWrapped(text: String, font: UIFont, gapAfter: Double) {
        val maxWidth = PAGE_WIDTH - 2 * MARGIN
        val lines = wrapText(text, font, maxWidth)
        lines.forEachIndexed { index, line ->
            drawLine(line, font, if (index == lines.lastIndex) gapAfter else 2.0)
        }
    }
}

// Greedy word-wrap: appends words to the current line while they fit `maxWidth` per
// `font`'s metrics, wrapping to a new line otherwise.
@OptIn(ExperimentalForeignApi::class)
private fun wrapText(text: String, font: UIFont, maxWidth: Double): List<String> {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var current = StringBuilder()
    for (word in words) {
        val candidate = if (current.isEmpty()) word else "$current $word"
        val width = (candidate as NSString).sizeWithAttributes(fontAttributes(font)).useContents { width }
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

private fun fontAttributes(font: UIFont): Map<Any?, *> = mapOf<Any?, Any?>(NSFontAttributeName to font)
