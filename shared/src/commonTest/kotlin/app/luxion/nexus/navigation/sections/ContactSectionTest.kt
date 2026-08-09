package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ContactSectionTest {

    private val titleEnglish = "Contact"

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
}
