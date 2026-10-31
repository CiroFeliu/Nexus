package app.luxion.nexus.blog

fun blogFeed(catalog: BlogCatalog, pages: BlogPages): String = buildString {
    append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    append("<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n")
    append("<channel>\n")
    append("<title>${escapeMarkup(BLOG_TITLE)}</title>\n")
    append("<link>${escapeMarkup(pages.blogUrl)}</link>\n")
    append("<description>${escapeMarkup(BLOG_DESCRIPTION)}</description>\n")
    append("<language>en</language>\n")
    append("<atom:link href=\"${escapeMarkup(pages.blogUrl)}feed.xml\" rel=\"self\" type=\"application/rss+xml\"/>\n")
    catalog.entries.maxOfOrNull { it.date }?.let { append("<lastBuildDate>${formatFeedDate(it)}</lastBuildDate>\n") }
    catalog.entries.forEach { entry ->
        val post = entry.primary
        val url = escapeMarkup(pages.postUrl(post))
        append("<item>\n")
        append("<title>${escapeMarkup(post.meta.title)}</title>\n")
        append("<link>$url</link>\n")
        append("<guid isPermaLink=\"true\">$url</guid>\n")
        append("<pubDate>${formatFeedDate(entry.date)}</pubDate>\n")
        append("<description>${escapeMarkup(post.meta.summary)}</description>\n")
        post.meta.tags.forEach { append("<category>${escapeMarkup(it)}</category>\n") }
        append("</item>\n")
    }
    append("</channel>\n")
    append("</rss>\n")
}
