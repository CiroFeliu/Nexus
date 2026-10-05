package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ContactSectionTest {

    private val titleEnglish = "Contact"
    private val emailAddress = "ricardociro97@gmail.com"
    private val closingStatementEnglish = "Always happy to talk shop — feel free to reach out."
    private val closingStatementSpanish = "Siempre con ganas de hablar de tecnología — no dudes en escribirme."

    private val expectedLinkLabels = ContactSection.links.map { it.label.getValue(Language.English) }

    @Test
    fun rendersTitleAndEveryContactLink() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText(titleEnglish).assertExists()
        expectedLinkLabels.forEach { label ->
            onNodeWithText(label).assertExists()
        }
    }

    @Test
    fun rendersDownloadCvButton() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText("Download CV").assertExists()
    }

    @Test
    fun rendersEmailAddressAsVisibleText() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText(emailAddress).assertExists()
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
    }
}
