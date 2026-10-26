package app.luxion.nexus.blog

import java.nio.file.Path
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val options = args.toList().chunked(2).associate { it.first() to it.getOrElse(1) { "" } }
    val config = BlogConfig(
        postsDir = Path.of(options.getValue("--posts")),
        fontsDir = Path.of(options.getValue("--fonts")),
        outputDir = Path.of(options.getValue("--output")),
        baseUrl = options["--base-url"] ?: "https://cirofeliu.es",
        includeDrafts = options["--include-drafts"].toBoolean(),
    )
    try {
        generateBlog(config)
    } catch (exception: BlogContentException) {
        System.err.println("Blog content is invalid:")
        exception.problems.forEach { System.err.println("  - $it") }
        exitProcess(1)
    }
}
