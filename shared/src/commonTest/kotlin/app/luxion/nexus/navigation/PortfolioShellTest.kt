package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class PortfolioShellTest {

    private val sectionAnchorsEnglish = listOf(
        "Ciro Feliu",
        "Skills & stack",
        "Experience",
        "Projects",
        "Always happy to talk shop. Feel free to reach out.",
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
    fun wideLayoutShowsInlineLinksAndLanguageToggleInOneBar() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                Box(modifier = Modifier.size(1280.dp, 720.dp)) {
                    PortfolioShell(onLanguageSelected = {})
                }
            }
        }

        PortfolioSection.entries.forEach { section ->
            onAllNodes(hasText(section.label.getValue(Language.English)) and hasClickAction()).onFirst().assertExists()
        }
        onNodeWithText("EN").assertExists()
        onNodeWithText("ES").assertExists()
        onNode(hasText("Blog") and hasClickAction()).assertExists()
        onNodeWithContentDescription("Open menu").assertDoesNotExist()
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

        onNodeWithText("Skills & stack").assertExists()
        onNodeWithText("ES").performClick()
        onNodeWithText("Habilidades y tecnologías").assertExists()
    }

    @Test
    fun compactMenuOpensAndClosesAfterSelectingASection() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                Box(modifier = Modifier.size(360.dp, 720.dp)) {
                    PortfolioShell(onLanguageSelected = {})
                }
            }
        }

        onNodeWithContentDescription("Open menu").performClick()
        onNodeWithContentDescription("Close menu").assertExists()
        onNode(hasText("Projects") and hasClickAction()).performClick()
        onNodeWithContentDescription("Close menu").assertDoesNotExist()
    }

    @Test
    fun compactMenuClosesWithTheCloseControl() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                Box(modifier = Modifier.size(360.dp, 720.dp)) {
                    PortfolioShell(onLanguageSelected = {})
                }
            }
        }

        onNodeWithContentDescription("Open menu").performClick()
        onNodeWithContentDescription("Close menu").performClick()
        onNodeWithContentDescription("Close menu").assertDoesNotExist()
    }

    @Test
    fun compactMenuClosesWithEscape() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                Box(modifier = Modifier.size(360.dp, 720.dp)) {
                    PortfolioShell(onLanguageSelected = {})
                }
            }
        }

        onNodeWithContentDescription("Open menu").performClick()
        onNodeWithContentDescription("Close menu").performKeyInput { pressKey(Key.Escape) }
        onNodeWithContentDescription("Close menu").assertDoesNotExist()
    }
}

class ShellGeometryTest {

    private val heights = mapOf(
        PortfolioSection.HeroAbout to 700,
        PortfolioSection.Skills to 500,
        PortfolioSection.Experience to 600,
        PortfolioSection.Projects to 1200,
        PortfolioSection.Contact to 400,
    )

    @Test
    fun sectionStartsAccumulateHeights() {
        assertEquals(
            mapOf(
                PortfolioSection.HeroAbout to 0,
                PortfolioSection.Skills to 700,
                PortfolioSection.Experience to 1200,
                PortfolioSection.Projects to 1800,
                PortfolioSection.Contact to 3000,
            ),
            sectionStarts(heights),
        )
    }

    @Test
    fun jumpTargetsAccountForTheBarAndClampToScrollRange() {
        val targets = jumpTargets(sectionStarts(heights), barHeight = 64, maxScroll = 2800)
        assertEquals(0, targets[PortfolioSection.HeroAbout])
        assertEquals(636, targets[PortfolioSection.Skills])
        assertEquals(2800, targets[PortfolioSection.Contact])
    }

    @Test
    fun sectionsRevealOnlyOnceTheyEnterTheViewport() {
        val starts = sectionStarts(heights)
        assertEquals(
            setOf(PortfolioSection.HeroAbout, PortfolioSection.Skills),
            revealableSections(starts, heights, viewportBottom = 900, threshold = 96),
        )
        assertEquals(
            PortfolioSection.entries.toSet(),
            revealableSections(starts, heights, viewportBottom = 3400, threshold = 96),
        )
    }

    @Test
    fun nothingRevealsBeforeEverySectionIsMeasured() {
        assertEquals(
            emptySet(),
            revealableSections(sectionStarts(emptyMap()), emptyMap(), viewportBottom = 900, threshold = 96),
        )
    }
}
