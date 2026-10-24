package app.luxion.nexus.blog

import kotlin.io.path.createDirectories
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarkdownRendererTest {

    private val location = "blog/posts/a-post/index.md"
    private val folder = tempDir().resolve("a-post").createDirectories().also {
        it.resolve("demo.mp4").writeText("x")
        it.resolve("diagram.png").writeText("x")
    }

    private fun render(markdown: String) = MarkdownRenderer().render(location, folder, markdown)

    @Test
    fun standaloneVideoBecomesACaptionedFigure() {
        val html = render("Intro.\n\n![Agents working in parallel](demo.mp4)\n\nOutro.")

        assertTrue(
            "<figure class=\"media media-video\"><video controls=\"\" preload=\"metadata\" playsinline=\"\" " +
                "aria-label=\"Agents working in parallel\"><source src=\"demo.mp4\" type=\"video/mp4\" /></video>" +
                "<figcaption>Agents working in parallel</figcaption></figure>" in html,
            html,
        )
        assertFalse("<p><figure" in html, html)
    }

    @Test
    fun youTubeLinkBecomesANoCookieEmbed() {
        val html = render("![Launch demo](https://www.youtube.com/watch?v=dQw4w9WgXcQ)")

        assertTrue("<figure class=\"media media-youtube\"><div class=\"embed\">" in html, html)
        assertTrue("src=\"https://www.youtube-nocookie.com/embed/dQw4w9WgXcQ\"" in html, html)
        assertTrue("title=\"Launch demo\"" in html, html)
        assertTrue("<figcaption>Launch demo</figcaption>" in html, html)
        assertFalse("youtube.com/watch" in html, html)
    }

    @Test
    fun plainImageRendersLazily() {
        val html = render("![A diagram](diagram.png)")

        assertTrue("<img src=\"diagram.png\" alt=\"A diagram\" loading=\"lazy\" />" in html, html)
    }

    @Test
    fun fencedCodeKeepsItsLanguageClassAndIsEscaped() {
        val html = render("```kotlin\nval list = listOf<String>() // a < b && c\n```")

        assertTrue(
            "<pre><code class=\"language-kotlin\">val list = listOf&lt;String&gt;() // a &lt; b &amp;&amp; c\n</code></pre>" in html,
            html,
        )
    }

    @Test
    fun rawHtmlIsEscaped() {
        val html = render("Before\n\n<script>alert(1)</script>\n\nInline <b onclick=\"x()\">bold</b>")

        assertFalse("<script" in html, html)
        assertFalse("<b onclick" in html, html)
        assertTrue("&lt;script&gt;alert(1)&lt;/script&gt;" in html, html)
    }

    @Test
    fun unsafeLinkTargetsAreSanitised() {
        val html = render("[click](javascript:alert(1))")

        assertFalse("javascript:" in html, html)
    }

    @Test
    fun tablesAndStrikethroughAreSupported() {
        val html = render("| a | b |\n|---|---|\n| 1 | 2 |\n\n~~old~~")

        assertTrue("<table>" in html, html)
        assertTrue("<del>old</del>" in html, html)
    }

    @Test
    fun videoInlineWithTextFails() {
        assertProblem("video 'demo.mp4' must be on its own line") {
            render("See ![Demo](demo.mp4) for details")
        }
    }

    @Test
    fun missingMediaFails() {
        assertProblem("$location: media file 'missing.mp4' does not exist") {
            render("![Demo](missing.mp4)")
        }
    }

    @Test
    fun mediaOutsideThePostFolderFails() {
        assertProblem("media reference '../other-post/logo.png' must point to a file inside the post folder") {
            render("![Logo](../other-post/logo.png)")
        }
    }

    @Test
    fun unrecognisedYouTubeUrlFails() {
        assertProblem("$location: could not find a YouTube video id in 'https://www.youtube.com/channel/abc'") {
            render("![Channel](https://www.youtube.com/channel/abc)")
        }
    }
}
