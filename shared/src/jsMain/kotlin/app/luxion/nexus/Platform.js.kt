package app.luxion.nexus

import web.navigator.navigator
import web.window.WindowTarget
import web.window._blank
import web.window.window

class JsPlatform: Platform {
    private val userAgent = navigator.userAgent
    private val browserList = listOf("Chrome", "Firefox", "Safari", "Edge")

    override val name: String = userAgent.findAnyOf(browserList, ignoreCase = true)
            ?.let { (startIndex) -> userAgent.substring(startIndex).substringBefore(" ") }
            ?: "Unknown"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun openUrl(url: String) {
    window.open(url, WindowTarget._blank)
}