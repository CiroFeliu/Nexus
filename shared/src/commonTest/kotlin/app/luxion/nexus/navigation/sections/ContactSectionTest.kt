package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class ContactSectionTest {

    private val emailAddress = "ricardociro97@gmail.com"
    private val closingStatementEnglish = "Always happy to talk shop. Feel free to reach out."
    private val closingStatementSpanish = "Siempre con ganas de hablar de tecnología. Escríbeme cuando quieras."

    @Test
    fun rendersEmailAndBrandLinks() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText(emailAddress).assertExists()
        ContactSection.brandLinks.forEach { (link, _) ->
            onNodeWithText(link.label.getValue(Language.English)).assertExists()
        }
        onNodeWithContentDescription("Copy email address").assertExists()
    }

    @Test
    fun linksCoverEmailLinkedInAndGitHub() {
        assertEquals(
            listOf("mailto:$emailAddress", "https://www.linkedin.com/in/ricardo-ciro-a43594190/", "https://github.com/CiroFeliu"),
            ContactSection.links.map { it.url },
        )
    }

    @Test
    fun rendersSingleDownloadCvButton() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText("Download CV").assertExists()
    }

    @Test
    fun rendersClosingStatementInEnglish() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText(closingStatementEnglish).assertExists()
    }

    @Test
    fun rendersClosingStatementInSpanish() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                CompositionLocalProvider(LocalAppLanguage provides Language.Spanish) {
                    ContactSection.Content()
                }
            }
        }

        onNodeWithText(closingStatementSpanish).assertExists()
        onNodeWithText("Descargar CV").assertExists()
    }
}
