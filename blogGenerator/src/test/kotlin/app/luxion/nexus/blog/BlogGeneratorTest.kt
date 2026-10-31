package app.luxion.nexus.blog

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BlogGeneratorTest {

    private val fixtures = Path.of(BlogGeneratorTest::class.java.getResource("/fixtures")!!.toURI())

    private fun generate(postsDir: Path = fixtures.resolve("posts"), includeDrafts: Boolean = false): Path {
        val output = tempDir()
        generateBlog(BlogConfig(postsDir, fixtures.resolve("fonts"), output, includeDrafts = includeDrafts))
        return output.resolve("blog")
    }

    private fun Path.files(): Map<String, ByteArray> = Files.walk(this).use { paths ->
        paths.filter { it.isRegularFile() }.toList().associate { relativize(it).toString() to Files.readAllBytes(it) }
    }

    @Test
    fun generatesPagesFeedAssetsAndMediaForPublishedPosts() {
        val blog = generate()

        assertEquals(
            setOf(
                "index.html",
                "feed.xml",
                "assets/blog.css",
                "assets/fonts/geist_regular.ttf",
                "hello-multiplatform/index.html",
                "hello-multiplatform/demo.mp4",
                "hola-multiplataforma/index.html",
            ),
            blog.files().keys,
        )
        val english = blog.resolve("hello-multiplatform/index.html").readText()
        assertTrue("<source src=\"demo.mp4\" type=\"video/mp4\" />" in english)
        assertTrue("https://www.youtube-nocookie.com/embed/dQw4w9WgXcQ" in english)
        assertTrue("<code class=\"language-kotlin\">" in english)
        assertTrue("href=\"/blog/hola-multiplataforma/\" hreflang=\"es\"" in english)
        val list = blog.resolve("index.html").readText()
        assertEquals(1, "class=\"post-item\"".toRegex().findAll(list).count(), list)
        assertTrue("<h2><a href=\"/blog/hello-multiplatform/\">Hello, Multiplatform</a></h2>" in list, list)
        assertTrue("<time datetime=\"2026-11-04\">4 Nov 2026</time>" in list, list)
        assertTrue("href=\"/blog/hola-multiplataforma/\" hreflang=\"es\"" in list, list)
        assertFalse("unfinished-notes" in list)
        val feed = blog.resolve("feed.xml").readText()
        assertEquals(1, "<item>".toRegex().findAll(feed).count(), feed)
        assertFalse("hola-multiplataforma" in feed, feed)
        assertFalse("unfinished-notes" in feed)
    }

    @Test
    fun outputIsByteIdenticalAcrossRuns() {
        val first = generate().files()
        val second = generate().files()

        assertEquals(first.keys, second.keys)
        first.forEach { (path, bytes) -> assertTrue(bytes.contentEquals(second.getValue(path)), path) }
    }

    @Test
    fun draftsArePublishedOnlyWhenPreviewing() {
        val blog = generate(includeDrafts = true)

        assertTrue(blog.resolve("unfinished-notes/index.html").exists())
        assertTrue(blog.resolve("unfinished-notes/sketch.png").exists())
    }

    @Test
    fun noPostsGeneratesTheEmptyStateAndAnEmptyFeed() {
        val blog = generate(postsDir = tempDir().resolve("missing"))

        assertTrue("class=\"empty-state\"" in blog.resolve("index.html").readText())
        assertFalse("<item>" in blog.resolve("feed.xml").readText())
    }

    @Test
    fun staleOutputIsRemoved() {
        val output = tempDir()
        output.resolve("blog").toFile().mkdirs()
        output.resolve("blog/removed-post.html").writeText("stale")

        generateBlog(BlogConfig(tempDir(), fixtures.resolve("fonts"), output))

        assertFalse(output.resolve("blog/removed-post.html").exists())
    }

    @Test
    fun invalidPostsFailWithEveryProblem() {
        val posts = tempDir()
        posts.writePost("no-summary", frontMatter("title: T", "date: 2026-11-03", "lang: en"))
        posts.writePost("bad-lang", frontMatter("title: T", "date: 2026-11-03", "summary: S", "lang: fr"))

        val exception = kotlin.test.assertFailsWith<BlogContentException> { generate(postsDir = posts) }

        assertEquals(2, exception.problems.size)
        assertTrue(exception.problems.any { "blog/posts/no-summary/index.md" in it })
        assertTrue(exception.problems.any { "blog/posts/bad-lang/index.md" in it })
    }

    @Test
    fun invalidDraftStillFailsTheBuild() {
        val posts = tempDir()
        posts.writePost("draft", frontMatter("title: T", "date: nope", "summary: S", "lang: en", "draft: true"))

        assertProblem("blog/posts/draft/index.md: field 'date'") { generate(postsDir = posts) }
    }
}
