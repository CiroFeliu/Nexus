package app.luxion.nexus.blog

import java.time.LocalDate

data class BlogEntry(val primary: Post, val versions: List<Post>) {
    val date: LocalDate get() = versions.maxOf { it.meta.date }
}

class BlogCatalog private constructor(val posts: List<Post>) {

    val entries: List<BlogEntry> = groupIntoEntries(posts)

    fun translationOf(post: Post): Post? {
        val key = post.meta.translationKey ?: return null
        return posts.firstOrNull { it !== post && it.meta.translationKey == key && it.meta.lang != post.meta.lang }
    }

    companion object {
        fun of(allPosts: List<Post>, includeDrafts: Boolean): BlogCatalog {
            val conflicts = allPosts
                .filter { it.meta.translationKey != null }
                .groupBy { it.meta.translationKey to it.meta.lang }
                .filterValues { it.size > 1 }
                .map { (key, posts) ->
                    "translationKey '${key.first}' is used by more than one '${key.second.code}' post: " +
                        posts.joinToString { it.slug }
                }
            if (conflicts.isNotEmpty()) throw BlogContentException(conflicts)
            val published = allPosts
                .filter { includeDrafts || !it.meta.draft }
                .sortedWith(compareByDescending<Post> { it.meta.date }.thenBy { it.slug })
            return BlogCatalog(published)
        }

        private fun groupIntoEntries(posts: List<Post>): List<BlogEntry> =
            posts.groupBy { it.meta.translationKey?.let { key -> "translation:$key" } ?: "post:${it.slug}" }
                .values
                .map { versions ->
                    val primary = versions.firstOrNull { it.meta.lang == PostLanguage.English } ?: versions.first()
                    BlogEntry(primary, versions)
                }
                .sortedWith(compareByDescending<BlogEntry> { it.date }.thenBy { it.primary.slug })
    }
}
