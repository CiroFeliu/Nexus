package app.luxion.nexus.blog

const val BLOG_TITLE = "Blog · Ciro Feliu"
const val BLOG_DESCRIPTION =
    "Writing by Ciro Feliu on building with AI agents in parallel, from spec to real device."

private const val AUTHOR = "Ciro Feliu"

private val VERSION_LINK_ORDER = listOf(PostLanguage.Spanish, PostLanguage.English)

class BlogPages(private val catalog: BlogCatalog, baseUrl: String) {
    private val baseUrl = baseUrl.trimEnd('/')
    val blogUrl: String get() = "$baseUrl/blog/"

    fun postUrl(post: Post): String = "$baseUrl${postPath(post)}"

    fun listPage(): String = page(
        head = Head(
            language = PostLanguage.English,
            title = BLOG_TITLE,
            description = BLOG_DESCRIPTION,
            canonicalUrl = blogUrl,
            ogType = "website",
        ),
        isList = true,
    ) {
        append("<h1 class=\"page-title\">Blog</h1>\n")
        append("<p class=\"page-intro\">${escapeMarkup(BLOG_DESCRIPTION)}</p>\n")
        if (catalog.entries.isEmpty()) {
            append("<div class=\"empty-state\">\n")
            append("<p>No posts yet. The first one is on its way.</p>\n")
            append("<p><a href=\"/\">Back to the portfolio</a></p>\n")
            append("</div>\n")
        } else {
            append("<ol class=\"post-list\">\n")
            catalog.entries.forEach { entry -> append(listItem(entry)) }
            append("</ol>\n")
        }
    }

    fun postPage(post: Post): String {
        val meta = post.meta
        val labels = meta.lang.labels
        val translation = catalog.translationOf(post)
        val alternates = translation?.let { listOf(post, it) }.orEmpty()
            .map { it.meta.lang.code to postUrl(it) }
        return page(
            head = Head(
                language = meta.lang,
                title = "${meta.title} · $AUTHOR",
                description = meta.summary,
                canonicalUrl = postUrl(post),
                ogType = "article",
                publishedDate = meta.date.toString(),
                alternates = alternates,
            ),
            isList = false,
        ) {
            append("<article class=\"post\">\n")
            append("<a class=\"back-link\" href=\"/blog/\">&larr; ${escapeMarkup(labels.allPosts)}</a>\n")
            append("<header class=\"post-header\">\n")
            append(metaLine(post, meta.lang))
            append("<h1>${escapeMarkup(meta.title)}</h1>\n")
            append(tagList(meta.tags))
            if (translation != null) {
                val target = translation.meta.lang
                append(
                    "<p class=\"translation-link\"><a href=\"${postPath(translation)}\" hreflang=\"${target.code}\" " +
                        "rel=\"alternate\">${escapeMarkup(labels.translation)}</a></p>\n",
                )
            }
            append("</header>\n")
            append("<div class=\"post-body\">\n")
            append(post.bodyHtml)
            append("</div>\n")
            append("</article>\n")
        }
    }

    private fun listItem(entry: BlogEntry): String = buildString {
        val meta = entry.primary.meta
        append("<li class=\"post-item\" lang=\"${meta.lang.code}\">\n")
        append(entryMetaLine(entry))
        append("<h2><a href=\"${postPath(entry.primary)}\">${escapeMarkup(meta.title)}</a></h2>\n")
        append("<p class=\"post-summary\">${escapeMarkup(meta.summary)}</p>\n")
        append(tagList(meta.tags))
        append("</li>\n")
    }

    private fun entryMetaLine(entry: BlogEntry): String = buildString {
        append("<div class=\"post-meta\">")
        append("<time datetime=\"${entry.date}\">${formatDisplayDate(entry.date, PostLanguage.English)}</time>")
        append(versionLinks(entry.versions))
        if (entry.versions.any { it.meta.draft }) append(draftBadge(PostLanguage.English))
        append("</div>\n")
    }

    private fun versionLinks(versions: List<Post>): String =
        versions.sortedBy { VERSION_LINK_ORDER.indexOf(it.meta.lang) }.joinToString(
            separator = "<span aria-hidden=\"true\">·</span>",
            prefix = "<span class=\"versions\">",
            postfix = "</span>",
        ) { version ->
            val language = version.meta.lang
            "<a href=\"${postPath(version)}\" hreflang=\"${language.code}\" lang=\"${language.code}\" " +
                "title=\"${escapeMarkup(language.labels.languageName)}\">${language.code.uppercase()}</a>"
        }

    private fun metaLine(post: Post, dateLanguage: PostLanguage): String = buildString {
        val meta = post.meta
        append("<div class=\"post-meta\">")
        append("<time datetime=\"${meta.date}\">${formatDisplayDate(meta.date, dateLanguage)}</time>")
        append(
            "<span class=\"badge\" title=\"${escapeMarkup(meta.lang.labels.languageName)}\">" +
                "${meta.lang.code.uppercase()}</span>",
        )
        if (meta.draft) append(draftBadge(meta.lang))
        append("</div>\n")
    }

    private fun draftBadge(language: PostLanguage): String =
        "<span class=\"badge badge-draft\">${escapeMarkup(language.labels.draft)}</span>"

    private fun tagList(tags: List<String>): String =
        if (tags.isEmpty()) {
            ""
        } else {
            tags.joinToString(separator = "", prefix = "<ul class=\"tags\">", postfix = "</ul>\n") {
                "<li>${escapeMarkup(it)}</li>"
            }
        }

    private class Head(
        val language: PostLanguage,
        val title: String,
        val description: String,
        val canonicalUrl: String,
        val ogType: String,
        val publishedDate: String? = null,
        val alternates: List<Pair<String, String>> = emptyList(),
    )

    private fun page(head: Head, isList: Boolean, main: StringBuilder.() -> Unit): String = buildString {
        append("<!DOCTYPE html>\n")
        append("<html lang=\"${head.language.code}\">\n")
        append("<head>\n")
        append("<meta charset=\"UTF-8\">\n")
        append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
        append("<meta name=\"color-scheme\" content=\"light dark\">\n")
        append("<title>${escapeMarkup(head.title)}</title>\n")
        append("<meta name=\"description\" content=\"${escapeMarkup(head.description)}\">\n")
        append("<meta name=\"author\" content=\"$AUTHOR\">\n")
        append("<link rel=\"canonical\" href=\"${escapeMarkup(head.canonicalUrl)}\">\n")
        head.alternates.forEach { (code, url) ->
            append("<link rel=\"alternate\" hreflang=\"$code\" href=\"${escapeMarkup(url)}\">\n")
        }
        append("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"${escapeMarkup(BLOG_TITLE)}\" href=\"/blog/feed.xml\">\n")
        append("<meta property=\"og:type\" content=\"${head.ogType}\">\n")
        append("<meta property=\"og:url\" content=\"${escapeMarkup(head.canonicalUrl)}\">\n")
        append("<meta property=\"og:title\" content=\"${escapeMarkup(head.title)}\">\n")
        append("<meta property=\"og:description\" content=\"${escapeMarkup(head.description)}\">\n")
        append("<meta property=\"og:image\" content=\"$baseUrl/og-image.png\">\n")
        append("<meta property=\"og:locale\" content=\"${head.language.ogLocale}\">\n")
        head.publishedDate?.let { append("<meta property=\"article:published_time\" content=\"$it\">\n") }
        append("<meta name=\"twitter:card\" content=\"summary_large_image\">\n")
        append("<link rel=\"icon\" type=\"image/svg+xml\" href=\"/favicon.svg\">\n")
        append("<link rel=\"stylesheet\" href=\"/blog/assets/blog.css\">\n")
        append("</head>\n")
        append("<body>\n")
        append("<header class=\"site-header\"><div class=\"container\">")
        append("<a class=\"wordmark\" href=\"/\">$AUTHOR</a>")
        append("<nav class=\"site-nav\" aria-label=\"Blog\">")
        append("<a href=\"/\">Portfolio</a>")
        append("<a href=\"/blog/\"${if (isList) " aria-current=\"page\"" else ""}>Blog</a>")
        append("<a href=\"/blog/feed.xml\">RSS</a>")
        append("</nav></div></header>\n")
        append("<main class=\"container\">\n")
        main()
        append("</main>\n")
        append("<footer class=\"site-footer\"><div class=\"container\">")
        append("<a href=\"/\">$AUTHOR</a>")
        append("<a href=\"/blog/feed.xml\">RSS</a>")
        append("</div></footer>\n")
        append("</body>\n")
        append("</html>\n")
    }
}

fun postPath(post: Post): String = "/blog/${post.slug}/"
