package app.luxion.nexus.blog

import java.nio.file.Path
import java.time.LocalDate

class BlogContentException(val problems: List<String>) : RuntimeException(problems.joinToString("\n"))

enum class PostLanguage(val code: String) {
    English("en"),
    Spanish("es");

    companion object {
        fun fromCode(code: String): PostLanguage? = entries.firstOrNull { it.code == code }
    }
}

data class PostMeta(
    val title: String,
    val date: LocalDate,
    val summary: String,
    val lang: PostLanguage,
    val draft: Boolean = false,
    val translationKey: String? = null,
    val tags: List<String> = emptyList(),
)

data class PostSource(val slug: String, val folder: Path, val location: String)

data class Post(val slug: String, val folder: Path, val meta: PostMeta, val bodyHtml: String)
