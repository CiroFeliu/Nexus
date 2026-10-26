package app.luxion.nexus.blog

import java.net.URI
import java.net.URISyntaxException
import java.net.URLDecoder
import java.nio.file.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

sealed interface Media {
    data class Video(val src: String, val mimeType: String) : Media

    data class YouTube(val videoId: String) : Media {
        val embedUrl: String get() = "https://www.youtube-nocookie.com/embed/$videoId"
    }

    data class Image(val src: String) : Media
}

private val VIDEO_TYPES = mapOf("mp4" to "video/mp4", "webm" to "video/webm")
private val YOUTUBE_HOSTS = setOf("youtube.com", "www.youtube.com", "m.youtube.com")
private val SHORT_YOUTUBE_HOSTS = setOf("youtu.be", "www.youtu.be")
private val VIDEO_ID = Regex("[A-Za-z0-9_-]{11}")
private val SCHEME = Regex("^[a-zA-Z][a-zA-Z0-9+.-]*:")
private val ALLOWED_SCHEMES = setOf("http", "https")

fun classifyMedia(destination: String): Media {
    val uri = parseUri(destination)
    val scheme = SCHEME.find(destination)?.value?.dropLast(1)?.lowercase()
    require(scheme == null || scheme in ALLOWED_SCHEMES) { "unsupported URL scheme in '$destination' (use http or https)" }
    val host = uri?.host?.lowercase()
    if (host in YOUTUBE_HOSTS || host in SHORT_YOUTUBE_HOSTS) {
        val videoId = youTubeVideoId(host!!, uri!!)
        require(videoId != null) { "could not find a YouTube video id in '$destination'" }
        return Media.YouTube(videoId)
    }
    val extension = pathPart(destination).substringAfterLast('/').substringAfterLast('.', "").lowercase()
    return VIDEO_TYPES[extension]?.let { Media.Video(destination, it) } ?: Media.Image(destination)
}

fun isExternalReference(destination: String): Boolean =
    SCHEME.containsMatchIn(destination) || destination.startsWith("/")

fun relativeMediaProblem(folder: Path, destination: String): String? {
    if (isExternalReference(destination)) return null
    val relative = URLDecoder.decode(pathPart(destination).replace("+", "%2B"), Charsets.UTF_8)
    val base = folder.toAbsolutePath().normalize()
    val target = base.resolve(relative).normalize()
    return when {
        relative.isBlank() || !target.startsWith(base) || target == base ->
            "media reference '$destination' must point to a file inside the post folder"
        target.name == POST_FILE_NAME || !target.isRegularFile() ->
            "media file '$destination' does not exist in the post folder"
        else -> null
    }
}

private fun youTubeVideoId(host: String, uri: URI): String? {
    val candidate = if (host in SHORT_YOUTUBE_HOSTS) {
        uri.path.orEmpty().removePrefix("/").substringBefore('/')
    } else if (uri.path == "/watch") {
        uri.rawQuery.orEmpty().split('&').firstOrNull { it.startsWith("v=") }?.removePrefix("v=")
    } else {
        null
    }
    return candidate?.takeIf { VIDEO_ID.matches(it) }
}

private fun parseUri(destination: String): URI? = try {
    URI(destination)
} catch (_: URISyntaxException) {
    null
}

private fun pathPart(destination: String): String = destination.substringBefore('#').substringBefore('?')
