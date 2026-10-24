package app.luxion.nexus.blog

import kotlin.io.path.createDirectories
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostDiscoveryTest {

    @Test
    fun folderNameBecomesTheSlug() {
        val root = tempDir()
        root.writePost("shogun-first-steps", validFrontMatter())

        val sources = discoverPosts(root)

        assertEquals(listOf("shogun-first-steps"), sources.map { it.slug })
        assertEquals("blog/posts/shogun-first-steps/index.md", sources.single().location)
    }

    @Test
    fun invalidSlugFailsNamingTheFolder() {
        val root = tempDir()
        root.writePost("Shogun_First_Steps", validFrontMatter())

        assertProblem("blog/posts/Shogun_First_Steps: folder name must be a lowercase kebab-case slug") {
            discoverPosts(root)
        }
    }

    @Test
    fun folderWithoutIndexFailsNamingTheFolder() {
        val root = tempDir()
        root.resolve("empty-post").createDirectories()

        assertProblem("blog/posts/empty-post: missing index.md") { discoverPosts(root) }
    }

    @Test
    fun missingOrEmptyRootHasNoPosts() {
        assertTrue(discoverPosts(tempDir().resolve("absent")).isEmpty())
        assertTrue(discoverPosts(tempDir()).isEmpty())
    }

    @Test
    fun looseFilesAndHiddenFoldersAreIgnored() {
        val root = tempDir()
        root.resolve(".DS_Store").writeText("")
        root.resolve(".hidden").createDirectories()
        root.writePost("real-post", validFrontMatter())

        assertEquals(listOf("real-post"), discoverPosts(root).map { it.slug })
    }
}
