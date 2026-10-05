package app.luxion.nexus.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PortfolioFooterTest {

    private val copyrightEnglish = "© 2026 Ciro Feliu. All rights reserved."
    private val buildNoteEnglish = "Built with Compose Multiplatform, shared across web, Android, desktop, and iOS."
    private val copyrightSpanish = "© 2026 Ciro Feliu. Todos los derechos reservados."
    private val buildNoteSpanish = "Construido con Compose Multiplatform, compartido entre web, Android, escritorio e iOS."

    @Test
    fun rendersCopyrightAndBuildNoteInEnglish() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                PortfolioFooter.Content()
            }
        }

        onNodeWithText(copyrightEnglish).assertExists()
        onNodeWithText(buildNoteEnglish).assertExists()
    }

    @Test
    fun rendersCopyrightAndBuildNoteInSpanish() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                CompositionLocalProvider(LocalAppLanguage provides Language.Spanish) {
                    PortfolioFooter.Content()
                }
            }
        }

        onNodeWithText(copyrightSpanish).assertExists()
        onNodeWithText(buildNoteSpanish).assertExists()
    }
}
