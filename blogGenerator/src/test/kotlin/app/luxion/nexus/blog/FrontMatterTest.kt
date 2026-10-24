package app.luxion.nexus.blog

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FrontMatterTest {

    private val location = "blog/posts/a-post/index.md"

    @Test
    fun minimalPostIsPublishedUntranslatedAndUntagged() {
        val parsed = parsePost(location, validFrontMatter() + "\nBody text\n")

        assertEquals(
            PostMeta(
                title = "A title",
                date = LocalDate.of(2026, 11, 3),
                summary = "A summary.",
                lang = PostLanguage.English,
            ),
            parsed.meta,
        )
        assertEquals(false, parsed.meta.draft)
        assertNull(parsed.meta.translationKey)
        assertTrue(parsed.meta.tags.isEmpty())
        assertEquals("\nBody text\n", parsed.body)
    }

    @Test
    fun optionalFieldsAreRead() {
        val meta = parsePost(
            location,
            validFrontMatter(lang = "es", extra = listOf("draft: true", "translationKey: shogun-intro", "tags: [kotlin, ai]")),
        ).meta

        assertEquals(PostLanguage.Spanish, meta.lang)
        assertEquals(true, meta.draft)
        assertEquals("shogun-intro", meta.translationKey)
        assertEquals(listOf("kotlin", "ai"), meta.tags)
    }

    @Test
    fun missingRequiredFieldIsNamed() {
        assertProblem("$location: missing required field 'summary'") {
            parsePost(location, frontMatter("title: T", "date: 2026-11-03", "lang: en"))
        }
    }

    @Test
    fun everyProblemIsReportedAtOnce() {
        val exception = kotlin.test.assertFailsWith<BlogContentException> {
            parsePost(location, frontMatter("date: 2026-11-03"))
        }

        assertEquals(3, exception.problems.size)
    }

    @Test
    fun nonIsoDateIsRejected() {
        assertProblem("field 'date' must be an ISO date like 2026-11-03") {
            parseFrontMatter(location, "title: T\ndate: 03/11/2026\nsummary: S\nlang: en")
        }
    }

    @Test
    fun impossibleDateIsRejected() {
        assertProblem("'2026-02-30' is not a real calendar date") {
            parseFrontMatter(location, "title: T\ndate: 2026-02-30\nsummary: S\nlang: en")
        }
    }

    @Test
    fun unsupportedLanguageIsRejected() {
        assertProblem("field 'lang' must be one of en, es") {
            parseFrontMatter(location, "title: T\ndate: 2026-11-03\nsummary: S\nlang: fr")
        }
    }

    @Test
    fun unknownFieldIsRejected() {
        assertProblem("unknown front matter field 'translationkey'") {
            parseFrontMatter(location, "title: T\ndate: 2026-11-03\nsummary: S\nlang: en\ntranslationkey: x")
        }
    }

    @Test
    fun emptyTitleIsRejected() {
        assertProblem("field 'title' must not be empty") {
            parseFrontMatter(location, "title: \"  \"\ndate: 2026-11-03\nsummary: S\nlang: en")
        }
    }

    @Test
    fun nonBooleanDraftIsRejected() {
        assertProblem("field 'draft' must be true or false") {
            parseFrontMatter(location, "title: T\ndate: 2026-11-03\nsummary: S\nlang: en\ndraft: yes")
        }
    }

    @Test
    fun tagsMustBeAListOfText() {
        assertProblem("field 'tags' must be a list") {
            parseFrontMatter(location, "title: T\ndate: 2026-11-03\nsummary: S\nlang: en\ntags: kotlin")
        }
        assertProblem("field 'tags' must only contain non-empty text values") {
            parseFrontMatter(location, "title: T\ndate: 2026-11-03\nsummary: S\nlang: en\ntags: [kotlin, \"\"]")
        }
    }

    @Test
    fun missingFrontMatterIsRejected() {
        assertProblem("$location: must start with a front matter block") {
            parsePost(location, "# Just a heading\n")
        }
        assertProblem("$location: must start with a front matter block") {
            parsePost(location, "---\ntitle: T\n")
        }
    }

    @Test
    fun invalidYamlIsRejected() {
        assertProblem("front matter is not valid YAML") {
            parseFrontMatter(location, "title: [unclosed")
        }
    }
}
