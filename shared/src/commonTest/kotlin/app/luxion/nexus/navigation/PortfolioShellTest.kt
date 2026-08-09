package app.luxion.nexus.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PortfolioShellTest {

    // Each section's title is now a private, per-language map on its own object, so these
    // hardcode the known default-language (English) copy rather than reading it back — the
    // name is included too, even though it's untranslated, since it's HeroAbout's anchor.
    private val sectionAnchorsEnglish = listOf(
        "Ciro Feliu",
        "Skills & Stack",
        "Experience",
        "Projects",
        "Contact",
    )

    @Test
    fun rendersEveryDeclaredSection() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                PortfolioShell(onLanguageSelected = {})
            }
        }

        sectionAnchorsEnglish.forEach { anchor ->
            onNodeWithText(anchor).assertExists()
        }
    }

    // `PortfolioShell` only forwards `onLanguageSelected` — the language itself lives in
    // `LocalAppLanguage`, owned by `App` in production. This mirrors that wiring locally so the
    // test can drive `LanguageSwitcher` and observe the shell's sections actually re-render.
    @Test
    fun switchingLanguageRerendersSectionsInTheNewLanguage() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                var language by remember { mutableStateOf(Language.Default) }
                CompositionLocalProvider(LocalAppLanguage provides language) {
                    PortfolioShell(onLanguageSelected = { language = it })
                }
            }
        }

        onNodeWithText("Skills & Stack").assertExists()
        onNodeWithText("ES").performClick()
        onNodeWithText("Habilidades y Tecnologías").assertExists()
    }
}
