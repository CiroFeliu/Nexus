package app.luxion.nexus.blog

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

private val SLUG_PATTERN = Regex("[a-z0-9]+(-[a-z0-9]+)*")

const val POST_FILE_NAME = "index.md"

fun discoverPosts(postsRoot: Path, rootLabel: String = "blog/posts"): List<PostSource> {
    if (!postsRoot.isDirectory()) return emptyList()
    val problems = mutableListOf<String>()
    val sources = postsRoot.listDirectoryEntries()
        .filter { it.isDirectory() && !it.name.startsWith(".") }
        .sortedBy { it.name }
        .mapNotNull { folder ->
            val label = "$rootLabel/${folder.name}"
            when {
                !SLUG_PATTERN.matches(folder.name) -> {
                    problems += "$label: folder name must be a lowercase kebab-case slug (a-z, 0-9 and single hyphens)"
                    null
                }
                !folder.resolve(POST_FILE_NAME).isRegularFile() -> {
                    problems += "$label: missing $POST_FILE_NAME"
                    null
                }
                else -> PostSource(slug = folder.name, folder = folder, location = "$label/$POST_FILE_NAME")
            }
        }
    if (problems.isNotEmpty()) throw BlogContentException(problems)
    return sources
}

fun readPostText(source: PostSource): String = Files.readString(source.folder.resolve(POST_FILE_NAME))
