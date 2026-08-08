package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ContactSectionTest {

    // Mirrors the link labels declared in `ContactSection`'s private `links` list.
    private val expectedLinkLabels = listOf("Email", "LinkedIn", "GitHub")

    @Test
    fun rendersTitleAndEveryContactLink() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ContactSection.Content()
            }
        }

        onNodeWithText(ContactSection.title).assertExists()
        expectedLinkLabels.forEach { label ->
            onNodeWithText(label).assertExists()
        }
    }
}
