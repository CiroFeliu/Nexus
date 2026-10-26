package app.luxion.nexus.blog

import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BlogFeedTest {

    private fun feedOf(vararg posts: Post): String {
        val catalog = BlogCatalog.of(posts.toList(), includeDrafts = false)
        return blogFeed(catalog, BlogPages(catalog, "https://cirofeliu.es"))
    }

    private fun parse(xml: String) =
        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml.byteInputStream()).documentElement

    @Test
    fun emptyFeedIsValidRssWithoutItems() {
        val xml = feedOf()
        val rss = parse(xml)

        assertEquals("rss", rss.tagName)
        assertEquals("2.0", rss.getAttribute("version"))
        assertEquals(0, rss.getElementsByTagName("item").length)
        assertFalse("lastBuildDate" in xml)
    }

    @Test
    fun itemsCarryAbsoluteLinksDatesAndSummaries() {
        val xml = feedOf(
            testPost("shogun-intro", date = "2026-11-03", summary = "Agents & worktrees", tags = listOf("ai")),
            testPost("later", date = "2026-12-01"),
            testPost("hidden", draft = true),
        )
        val items = parse(xml).getElementsByTagName("item")

        assertEquals(2, items.length)
        val second = items.item(1) as Element
        fun Element.child(name: String) = getElementsByTagName(name).item(0).textContent
        assertEquals("later", (items.item(0) as Element).child("title").removePrefix("Title of "))
        assertEquals("https://cirofeliu.es/blog/shogun-intro/", second.child("link"))
        assertEquals("https://cirofeliu.es/blog/shogun-intro/", second.child("guid"))
        assertEquals("Tue, 3 Nov 2026 00:00:00 GMT", second.child("pubDate"))
        assertEquals("Agents & worktrees", second.child("description"))
        assertEquals("ai", second.child("category"))
        assertTrue("<lastBuildDate>Tue, 1 Dec 2026 00:00:00 GMT</lastBuildDate>" in xml, xml)
    }
}
