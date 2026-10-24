package app.luxion.nexus.blog

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.writeText

data class BlogConfig(
    val postsDir: Path,
    val fontsDir: Path,
    val outputDir: Path,
    val baseUrl: String = "https://cirofeliu.es",
    val includeDrafts: Boolean = false,
)

private const val STYLESHEET_RESOURCE = "/blog/blog.css"

fun loadPosts(postsDir: Path): List<Post> {
    val renderer = MarkdownRenderer()
    val problems = mutableListOf<String>()
    val posts = discoverPosts(postsDir).mapNotNull { source ->
        try {
            val parsed = parsePost(source.location, readPostText(source))
            val body = renderer.render(source.location, source.folder, parsed.body)
            Post(slug = source.slug, folder = source.folder, meta = parsed.meta, bodyHtml = body)
        } catch (exception: BlogContentException) {
            problems += exception.problems
            null
        }
    }
    if (problems.isNotEmpty()) throw BlogContentException(problems)
    return posts
}

fun generateBlog(config: BlogConfig) {
    val catalog = BlogCatalog.of(loadPosts(config.postsDir), config.includeDrafts)
    val pages = BlogPages(catalog, config.baseUrl)
    val blogDir = config.outputDir.resolve("blog")
    deleteRecursively(config.outputDir)
    blogDir.createDirectories()
    blogDir.resolve("index.html").writeText(pages.listPage())
    blogDir.resolve("feed.xml").writeText(blogFeed(catalog, pages))
    writeAssets(blogDir.resolve("assets"), config.fontsDir)
    catalog.posts.forEach { post ->
        val postDir = blogDir.resolve(post.slug).createDirectories()
        copyMedia(post.folder, postDir)
        postDir.resolve("index.html").writeText(pages.postPage(post))
    }
}

private fun writeAssets(assetsDir: Path, fontsDir: Path) {
    val fontsOut = assetsDir.resolve("fonts").createDirectories()
    val stylesheet = BlogConfig::class.java.getResourceAsStream(STYLESHEET_RESOURCE)
        ?: error("Missing stylesheet resource $STYLESHEET_RESOURCE")
    stylesheet.use { Files.copy(it, assetsDir.resolve("blog.css"), StandardCopyOption.REPLACE_EXISTING) }
    if (fontsDir.isDirectory()) {
        fontsDir.listDirectoryEntries("*.ttf").sortedBy { it.name }.forEach { font ->
            Files.copy(font, fontsOut.resolve(font.name), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}

private fun copyMedia(sourceDir: Path, targetDir: Path) {
    Files.walk(sourceDir).use { paths ->
        paths.filter { it.isRegularFile() }
            .filter { path -> sourceDir.relativize(path).none { it.name.startsWith(".") } }
            .filter { it != sourceDir.resolve(POST_FILE_NAME) }
            .sorted()
            .forEach { file ->
                val target = targetDir.resolve(sourceDir.relativize(file).toString())
                target.parent.createDirectories()
                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING)
            }
    }
}

private fun deleteRecursively(path: Path) {
    if (!path.exists()) return
    Files.walk(path).use { paths -> paths.sorted(Comparator.reverseOrder()).forEach(Files::delete) }
}
