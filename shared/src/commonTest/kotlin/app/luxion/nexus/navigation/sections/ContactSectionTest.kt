package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ContactSectionTest {

    // `title` is private and language-keyed now, so this hardcodes the known default-language
    // (English) copy rather than reading it back.
    private val titleEnglish = "Contact"

    // Read from the internal `links` list (visible to tests via the friend module relationship)
    // instead of duplicating every label, so this stays correct if links are added or removed.
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

    // New with `add-dynamic-cv-export`: a "Download CV" button alongside the contact links.
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
