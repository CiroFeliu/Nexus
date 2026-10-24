package app.luxion.nexus.blog

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class BlogCatalogTest {

    @Test
    fun postsAreNewestFirstWithSlugAsTieBreaker() {
        val catalog = BlogCatalog.of(
            listOf(
                testPost("b-post", date = "2026-11-03"),
                testPost("newest", date = "2026-12-01"),
                testPost("a-post", date = "2026-11-03"),
            ),
            includeDrafts = false,
        )

        assertEquals(listOf("newest", "a-post", "b-post"), catalog.posts.map { it.slug })
    }

    @Test
    fun draftsAreExcludedUnlessRequested() {
        val posts = listOf(testPost("published"), testPost("draft", draft = true))

        assertEquals(listOf("published"), BlogCatalog.of(posts, includeDrafts = false).posts.map { it.slug })
        assertEquals(2, BlogCatalog.of(posts, includeDrafts = true).posts.size)
    }

    @Test
    fun postsSharingAKeyAreEachOthersTranslation() {
        val english = testPost("shogun-intro", translationKey = "shogun-intro")
        val spanish = testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "shogun-intro")
        val catalog = BlogCatalog.of(listOf(english, spanish), includeDrafts = false)

        assertSame(spanish, catalog.translationOf(english))
        assertSame(english, catalog.translationOf(spanish))
    }

    @Test
    fun draftTranslationIsNotLinked() {
        val english = testPost("shogun-intro", translationKey = "shogun-intro")
        val spanish = testPost("shogun-intro-es", lang = PostLanguage.Spanish, translationKey = "shogun-intro", draft = true)

        assertNull(BlogCatalog.of(listOf(english, spanish), includeDrafts = false).translationOf(english))
    }

    @Test
    fun untranslatedPostHasNoTranslation() {
        val post = testPost("alone")

        assertNull(BlogCatalog.of(listOf(post), includeDrafts = false).translationOf(post))
    }

    @Test
    fun sameKeyAndLanguageTwiceFailsNamingBothPosts() {
        assertProblem("translationKey 'shogun-intro' is used by more than one 'en' post: one, two") {
            BlogCatalog.of(
                listOf(
                    testPost("one", translationKey = "shogun-intro"),
                    testPost("two", translationKey = "shogun-intro", draft = true),
                ),
                includeDrafts = false,
            )
        }
    }
}
