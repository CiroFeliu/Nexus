package app.luxion.nexus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// Opens `url` in the platform's default browser/handler. Shared by every section that
// needs to open an external link (repo/demo links, social/contact links).
expect fun openUrl(url: String)