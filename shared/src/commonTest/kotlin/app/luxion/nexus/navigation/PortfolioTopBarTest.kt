package app.luxion.nexus.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioTheme
import app.luxion.nexus.theme.WidthClass
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class PortfolioTopBarTest {

    @Test
    fun wideBarShowsBlogEntryThatInvokesItsCallback() = runComposeUiTest {
        var blogSelections = 0
        var sectionSelections = 0
        setContent {
            PortfolioTheme {
                CompositionLocalProvider(LocalWidthClass provides WidthClass.Expanded) {
                    PortfolioTopBar(
                        isScrolled = { false },
                        activeSection = { null },
                        onSectionSelected = { sectionSelections++ },
                        onLanguageSelected = {},
                        onBlogSelected = { blogSelections++ },
                        onMenuClick = {},
                    )
                }
            }
        }

        onNode(hasText("Blog") and hasClickAction()).performClick()

        assertEquals(1, blogSelections)
        assertEquals(0, sectionSelections)
    }

    @Test
    fun compactMenuShowsBlogEntryThatInvokesItsCallback() = runComposeUiTest {
        var blogSelections = 0
        setContent {
            PortfolioTheme {
                CompactNavigationMenu(
                    activeSection = null,
                    onSectionSelected = {},
                    onLanguageSelected = {},
                    onBlogSelected = { blogSelections++ },
                    onDismiss = {},
                )
            }
        }

        onNode(hasText("Blog") and hasClickAction()).performClick()

        assertEquals(1, blogSelections)
    }
}
