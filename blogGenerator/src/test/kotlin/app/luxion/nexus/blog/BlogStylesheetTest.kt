package app.luxion.nexus.blog

import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BlogStylesheetTest {

    private val css = BlogStylesheetTest::class.java.getResource("/blog/blog.css")!!.readText()
    private val colorKt = Path.of(System.getProperty("nexus.repoRoot"))
        .resolve("shared/src/commonMain/kotlin/app/luxion/nexus/theme/Color.kt")
        .readText()

    private val cssColor = Regex("--color-([a-z-]+):\\s*#([0-9A-Fa-f]{6});")

    private fun cssColors(block: String): Map<String, String> = cssColor.findAll(block).associate { match ->
        val role = match.groupValues[1].split('-').mapIndexed { index, part ->
            if (index == 0) part else part.replaceFirstChar(Char::uppercase)
        }.joinToString("")
        role to match.groupValues[2].uppercase()
    }

    private fun schemeColors(schemeName: String): Map<String, String> {
        val block = colorKt.substringAfter("val $schemeName").substringBefore("\n)")
        return Regex("(\\w+) = Color\\(0xFF([0-9A-Fa-f]{6})\\)").findAll(block)
            .associate { it.groupValues[1] to it.groupValues[2].uppercase() }
    }

    @Test
    fun lightColorsMatchThePortfolioLightScheme() {
        val light = cssColors(css.substringBefore("@media (prefers-color-scheme: dark)"))
        val scheme = schemeColors("PortfolioLightColorScheme")

        assertTrue(light.isNotEmpty())
        light.forEach { (role, hex) -> assertEquals(scheme[role], hex, "light $role") }
    }

    @Test
    fun darkColorsMatchThePortfolioDarkScheme() {
        val darkBlock = css.substringAfter("@media (prefers-color-scheme: dark)").substringBefore("@media")
        val dark = cssColors(darkBlock)
        val light = cssColors(css.substringBefore("@media (prefers-color-scheme: dark)"))
        val scheme = schemeColors("PortfolioDarkColorScheme")

        assertEquals(light.keys, dark.keys)
        dark.forEach { (role, hex) -> assertEquals(scheme[role], hex, "dark $role") }
    }
}
