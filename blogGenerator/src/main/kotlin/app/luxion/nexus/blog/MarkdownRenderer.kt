package app.luxion.nexus.blog

import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Code
import org.commonmark.node.CustomBlock
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.SoftLineBreak
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlRenderer
import java.nio.file.Path

class MarkdownRenderer {
    private val extensions = listOf(TablesExtension.create(), StrikethroughExtension.create())
    private val parser = Parser.builder().extensions(extensions).build()
    private val htmlRenderer = HtmlRenderer.builder()
        .extensions(extensions)
        .escapeHtml(true)
        .sanitizeUrls(true)
        .nodeRendererFactory { context -> MediaNodeRenderer(context) }
        .build()

    fun render(location: String, folder: Path, markdown: String): String {
        val document = parser.parse(markdown)
        val problems = mutableListOf<String>()
        val embeds = mutableListOf<Pair<Paragraph, EmbedBlock>>()
        collectImages(document).forEach { image ->
            val media = try {
                classifyMedia(image.destination)
            } catch (exception: IllegalArgumentException) {
                problems += "$location: ${exception.message}"
                return@forEach
            }
            relativeMediaProblem(folder, image.destination)?.let { problems += "$location: $it" }
            if (media is Media.Image) return@forEach
            val paragraph = image.parent as? Paragraph
            if (paragraph != null && paragraph.firstChild === image && image.next == null) {
                embeds += paragraph to EmbedBlock(media, altText(image))
            } else {
                problems += "$location: video '${image.destination}' must be on its own line, " +
                    "separated from surrounding text by blank lines"
            }
        }
        if (problems.isNotEmpty()) throw BlogContentException(problems)
        embeds.forEach { (paragraph, embed) ->
            paragraph.insertBefore(embed)
            paragraph.unlink()
        }
        return htmlRenderer.render(document)
    }
}

private class EmbedBlock(val media: Media, val caption: String) : CustomBlock()

private fun collectImages(document: Node): List<Image> {
    val images = mutableListOf<Image>()
    document.accept(object : AbstractVisitor() {
        override fun visit(image: Image) {
            images += image
            visitChildren(image)
        }
    })
    return images
}

private fun altText(node: Node): String = buildString {
    node.accept(object : AbstractVisitor() {
        override fun visit(text: Text) {
            append(text.literal)
        }

        override fun visit(code: Code) {
            append(code.literal)
        }

        override fun visit(softLineBreak: SoftLineBreak) {
            append(' ')
        }

        override fun visit(hardLineBreak: HardLineBreak) {
            append(' ')
        }
    })
}.trim()

private class MediaNodeRenderer(private val context: HtmlNodeRendererContext) : NodeRenderer {
    private val html = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> = setOf(Image::class.java, EmbedBlock::class.java)

    override fun render(node: Node) {
        when (node) {
            is EmbedBlock -> renderEmbed(node)
            is Image -> renderImage(node)
        }
    }

    private fun renderImage(image: Image) {
        val attributes = linkedMapOf(
            "src" to context.encodeUrl(image.destination),
            "alt" to altText(image),
            "loading" to "lazy",
        )
        image.title?.takeIf(String::isNotBlank)?.let { attributes["title"] = it }
        html.tag("img", attributes, true)
    }

    private fun renderEmbed(embed: EmbedBlock) {
        html.line()
        when (val media = embed.media) {
            is Media.Video -> {
                html.tag("figure", mapOf("class" to "media media-video"))
                val videoAttributes = linkedMapOf("controls" to "", "preload" to "metadata", "playsinline" to "")
                if (embed.caption.isNotEmpty()) videoAttributes["aria-label"] = embed.caption
                html.tag("video", videoAttributes)
                html.tag("source", mapOf("src" to context.encodeUrl(media.src), "type" to media.mimeType), true)
                html.tag("/video")
            }
            is Media.YouTube -> {
                html.tag("figure", mapOf("class" to "media media-youtube"))
                html.tag("div", mapOf("class" to "embed"))
                html.tag(
                    "iframe",
                    linkedMapOf(
                        "src" to media.embedUrl,
                        "title" to embed.caption.ifEmpty { "YouTube video" },
                        "loading" to "lazy",
                        "allow" to "accelerometer; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share",
                        "referrerpolicy" to "strict-origin-when-cross-origin",
                        "allowfullscreen" to "",
                    ),
                )
                html.tag("/iframe")
                html.tag("/div")
            }
            is Media.Image -> Unit
        }
        if (embed.caption.isNotEmpty()) {
            html.tag("figcaption")
            html.text(embed.caption)
            html.tag("/figcaption")
        }
        html.tag("/figure")
        html.line()
    }
}
