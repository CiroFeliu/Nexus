package app.luxion.nexus.blog

import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

fun tempDir(): Path = Files.createTempDirectory("blog-test")

fun frontMatter(vararg fields: String): String = fields.joinToString("\n", prefix = "---\n", postfix = "\n---\n")

fun Path.writePost(slug: String, text: String): Path {
    val folder = resolve(slug).createDirectories()
    folder.resolve("index.md").writeText(text)
    return folder
}

fun validFrontMatter(lang: String = "en", extra: List<String> = emptyList()): String = frontMatter(
    "title: \"A title\"",
    "date: 2026-11-03",
    "summary: \"A summary.\"",
    "lang: $lang",
    *extra.toTypedArray(),
)

fun testPost(
    slug: String,
    date: String = "2026-11-03",
    lang: PostLanguage = PostLanguage.English,
    draft: Boolean = false,
    translationKey: String? = null,
    tags: List<String> = emptyList(),
    title: String = "Title of $slug",
    summary: String = "Summary of $slug",
    bodyHtml: String = "<p>Body of $slug</p>\n",
) = Post(
    slug = slug,
    folder = Path.of(slug),
    meta = PostMeta(
        title = title,
        date = LocalDate.parse(date),
        summary = summary,
        lang = lang,
        draft = draft,
        translationKey = translationKey,
        tags = tags,
    ),
    bodyHtml = bodyHtml,
)

fun assertProblem(expectedFragment: String, block: () -> Unit) {
    val exception = kotlin.test.assertFailsWith<BlogContentException> { block() }
    kotlin.test.assertTrue(
        exception.problems.any { expectedFragment in it },
        "Expected a problem containing '$expectedFragment' but got: ${exception.problems}",
    )
}
