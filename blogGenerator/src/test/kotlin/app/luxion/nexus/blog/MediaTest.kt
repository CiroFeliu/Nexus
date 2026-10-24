package app.luxion.nexus.blog

import kotlin.io.path.createDirectories
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MediaTest {

    @Test
    fun selfHostedVideosAreRecognisedByExtension() {
        assertEquals(Media.Video("demo.mp4", "video/mp4"), classifyMedia("demo.mp4"))
        assertEquals(Media.Video("clips/Demo.WEBM", "video/webm"), classifyMedia("clips/Demo.WEBM"))
    }

    @Test
    fun youTubeWatchAndShortLinksYieldTheVideoId() {
        listOf(
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "https://youtube.com/watch?feature=share&v=dQw4w9WgXcQ",
            "https://m.youtube.com/watch?v=dQw4w9WgXcQ",
            "https://youtu.be/dQw4w9WgXcQ",
            "https://youtu.be/dQw4w9WgXcQ?t=42",
        ).forEach { url ->
            assertEquals(Media.YouTube("dQw4w9WgXcQ"), classifyMedia(url), url)
        }
        assertEquals("https://www.youtube-nocookie.com/embed/dQw4w9WgXcQ", Media.YouTube("dQw4w9WgXcQ").embedUrl)
    }

    @Test
    fun youTubeUrlWithoutIdIsRejected() {
        listOf("https://www.youtube.com/channel/abc", "https://youtu.be/", "https://www.youtube.com/watch?v=short").forEach {
            assertFailsWith<IllegalArgumentException>(it) { classifyMedia(it) }
        }
    }

    @Test
    fun otherReferencesAreImages() {
        assertEquals(Media.Image("diagram.png"), classifyMedia("diagram.png"))
        assertEquals(Media.Image("https://example.com/a.svg"), classifyMedia("https://example.com/a.svg"))
    }

    @Test
    fun unsafeSchemesAreRejected() {
        assertFailsWith<IllegalArgumentException> { classifyMedia("javascript:alert(1)") }
        assertFailsWith<IllegalArgumentException> { classifyMedia("data:image/png;base64,AAAA") }
    }

    @Test
    fun relativeMediaMustExistInsideThePostFolder() {
        val folder = tempDir().resolve("a-post").createDirectories()
        folder.resolve("demo.mp4").writeText("x")
        folder.resolve("my clip.webm").writeText("x")

        assertNull(relativeMediaProblem(folder, "demo.mp4"))
        assertNull(relativeMediaProblem(folder, "./demo.mp4"))
        assertNull(relativeMediaProblem(folder, "my%20clip.webm"))
        assertNull(relativeMediaProblem(folder, "https://example.com/elsewhere.png"))
        assertTrue(relativeMediaProblem(folder, "missing.mp4")!!.contains("does not exist"))
        assertTrue(relativeMediaProblem(folder, "../other-post/logo.png")!!.contains("inside the post folder"))
        assertTrue(relativeMediaProblem(folder, "index.md")!!.contains("does not exist"))
    }
}
