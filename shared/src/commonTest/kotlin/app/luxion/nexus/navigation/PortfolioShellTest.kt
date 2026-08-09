package app.luxion.nexus.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PortfolioShellTest {

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
            onAllNodesWithText(anchor)[0].assertExists()
        }
    }

    @Test
    fun rendersFooterOnceAfterAllSections() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                PortfolioShell(onLanguageSelected = {})
            }
        }

        onNodeWithText("© 2026 Ciro Feliu. All rights reserved.").assertExists()
    }

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
