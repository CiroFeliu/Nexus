package app.luxion.nexus.blog

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BlogPagesTest {

    private fun versionLink(slug: String, code: String, languageName: String) =
        "<a href=\"/blog/$slug/\" hreflang=\"$code\" lang=\"$code\" title=\"$languageName\">${code.uppercase()}</a>"

    private fun entryCount(html: String) = Regex("class=\"post-item\"").findAll(html).count()

    private fun pagesOf(vararg posts: Post, includeDrafts: Boolean = false) =
        BlogPages(BlogCatalog.of(posts.toList(), includeDrafts), "https://cirofeliu.es/")

    @Test
    fun emptyListShowsTheEmptyState() {
        val html = pagesOf().listPage()

        assertTrue("class=\"empty-state\"" in html, html)
        assertTrue("No posts yet" in html, html)
        assertTrue("<a href=\"/\">Back to the portfolio</a>" in html, html)
        assertFalse("post-list" in html, html)
    }

    @Test
    fun listMixesLanguagesNewestFirstWithVersionLinksAndTags() {
        val html = pagesOf(
            testPost("older-english", date = "2026-11-03", tags = listOf("kotlin")),
            testPost("newer-spanish", date = "2026-12-01", lang = PostLanguage.Spanish),
        ).listPage()

        assertTrue(html.indexOf("/blog/newer-spanish/") < html.indexOf("/blog/older-english/"), html)
        assertTrue("<li class=\"post-item\" lang=\"es\">" in html, html)
        assertTrue(versionLink("newer-spanish", "es", "Español") in html, html)
        assertTrue(versionLink("older-english", "en", "English") in html, html)
        assertFalse("class=\"badge\"" in html, html)
        assertTrue("<time datetime=\"2026-11-03\">3 Nov 2026</time>" in html, html)
        assertTrue("<ul class=\"tags\"><li>kotlin</li></ul>" in html, html)
        assertTrue("Summary of older-english" in html, html)
    }

    @Test
    fun translatedArticleIsOneEntryShowingTheEnglishVersion() {
        val html = pagesOf(
            testPost("shogun-intro", translationKey = "intro", tags = listOf("ai")),
            testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "intro", tags = listOf("ia")),
        ).listPage()

        assertEquals(1, entryCount(html), html)
        assertTrue("<li class=\"post-item\" lang=\"en\">" in html, html)
        assertTrue("<h2><a href=\"/blog/shogun-intro/\">Title of shogun-intro</a></h2>" in html, html)
        assertTrue("Summary of shogun-intro<" in html, html)
        assertFalse("Title of shogun-intro-es" in html, html)
        assertFalse("Summary of shogun-intro-es" in html, html)
        assertTrue("<ul class=\"tags\"><li>ai</li></ul>" in html, html)
        assertTrue(
            "<span class=\"versions\">${versionLink("shogun-intro-es", "es", "Español")}" +
                "<span aria-hidden=\"true\">·</span>${versionLink("shogun-intro", "en", "English")}</span>" in html,
            html,
        )
    }

    @Test
    fun onlyPublishedSpanishVersionIsShownWhenEnglishIsADraft() {
        val html = pagesOf(
            testPost("shogun-intro", translationKey = "intro", draft = true),
            testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "intro"),
        ).listPage()

        assertEquals(1, entryCount(html), html)
        assertTrue("<h2><a href=\"/blog/shogun-intro-es/\">Title of shogun-intro-es</a></h2>" in html, html)
        assertTrue("<span class=\"versions\">${versionLink("shogun-intro-es", "es", "Español")}</span>" in html, html)
        assertFalse("/blog/shogun-intro/" in html, html)
    }

    @Test
    fun groupedEntryTakesTheDateOfItsMostRecentVersion() {
        val html = pagesOf(
            testPost("shogun-intro", date = "2026-11-03", translationKey = "intro"),
            testPost("shogun-intro-es", date = "2026-11-10", lang = PostLanguage.Spanish, translationKey = "intro"),
            testPost("other", date = "2026-11-05"),
        ).listPage()

        assertTrue("<time datetime=\"2026-11-10\">10 Nov 2026</time>" in html, html)
        assertFalse("datetime=\"2026-11-03\"" in html, html)
        assertTrue(html.indexOf("/blog/shogun-intro/\">") < html.indexOf("/blog/other/\">"), html)
    }

    @Test
    fun listIsIndexable() {
        val html = pagesOf().listPage()

        assertTrue("<html lang=\"en\">" in html, html)
        assertTrue("<link rel=\"canonical\" href=\"https://cirofeliu.es/blog/\">" in html, html)
        assertTrue("<meta property=\"og:type\" content=\"website\">" in html, html)
        assertTrue("type=\"application/rss+xml\"" in html, html)
        assertTrue("href=\"/blog/assets/blog.css\"" in html, html)
    }

    @Test
    fun spanishPostUsesSpanishLanguageAndLabels() {
        val post = testPost("hola", lang = PostLanguage.Spanish, date = "2026-11-03")
        val html = pagesOf(post).postPage(post)

        assertTrue("<html lang=\"es\">" in html, html)
        assertTrue("Todas las entradas" in html, html)
        assertTrue("<time datetime=\"2026-11-03\">3 nov 2026</time>" in html, html)
        assertTrue("<meta property=\"og:locale\" content=\"es_ES\">" in html, html)
    }

    @Test
    fun postPageCarriesMetadataAndBody() {
        val post = testPost("shogun-intro", title = "Shogun & friends", summary = "A \"quoted\" summary", tags = listOf("ai"))
        val html = pagesOf(post).postPage(post)

        assertTrue("<title>Shogun &amp; friends · Ciro Feliu</title>" in html, html)
        assertTrue("<meta name=\"description\" content=\"A &quot;quoted&quot; summary\">" in html, html)
        assertTrue("<link rel=\"canonical\" href=\"https://cirofeliu.es/blog/shogun-intro/\">" in html, html)
        assertTrue("<meta property=\"og:type\" content=\"article\">" in html, html)
        assertTrue("<meta property=\"article:published_time\" content=\"2026-11-03\">" in html, html)
        assertTrue("<h1>Shogun &amp; friends</h1>" in html, html)
        assertTrue("<a class=\"back-link\" href=\"/blog/\">" in html, html)
        assertTrue("<a class=\"wordmark\" href=\"/\">" in html, html)
        assertTrue("<p>Body of shogun-intro</p>" in html, html)
        assertTrue("<ul class=\"tags\"><li>ai</li></ul>" in html, html)
        assertFalse("translation-link" in html, html)
    }

    @Test
    fun translatedPostsLinkToEachOther() {
        val english = testPost("shogun-intro", translationKey = "intro")
        val spanish = testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "intro")
        val pages = pagesOf(english, spanish)

        val englishHtml = pages.postPage(english)
        val spanishHtml = pages.postPage(spanish)

        assertTrue(
            "<a href=\"/blog/shogun-intro-es/\" hreflang=\"es\" rel=\"alternate\">Read in Spanish</a>" in englishHtml,
            englishHtml,
        )
        assertTrue(
            "<a href=\"/blog/shogun-intro/\" hreflang=\"en\" rel=\"alternate\">Leer en inglés</a>" in spanishHtml,
            spanishHtml,
        )
        assertTrue(
            "<link rel=\"alternate\" hreflang=\"es\" href=\"https://cirofeliu.es/blog/shogun-intro-es/\">" in englishHtml,
            englishHtml,
        )
    }

    @Test
    fun postPageKeepsItsLanguageBadge() {
        val english = testPost("shogun-intro", translationKey = "intro")
        val spanish = testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "intro")
        val html = pagesOf(english, spanish).postPage(spanish)

        assertTrue("<span class=\"badge\" title=\"Español\">ES</span>" in html, html)
        assertFalse("class=\"versions\"" in html, html)
    }

    @Test
    fun draftsShowABadgeWhenPreviewed() {
        val draft = testPost("wip", draft = true)
        val pages = pagesOf(draft, includeDrafts = true)

        assertTrue("badge-draft\">Draft</span>" in pages.listPage())
        assertTrue("badge-draft\">Draft</span>" in pages.postPage(draft))
    }
}
