package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LanguageSwitcher
import kotlinx.coroutines.launch

@Composable
fun PortfolioShell(onLanguageSelected: (Language) -> Unit) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val sectionHeights = remember { mutableStateMapOf<PortfolioSection, Int>() }
    val sectionOffsets by remember {
        derivedStateOf {
            var cumulativeHeight = 0
            PortfolioSection.entries.associateWith { section ->
                val offset = cumulativeHeight
                cumulativeHeight += sectionHeights[section] ?: 0
                offset.coerceAtMost(scrollState.maxValue)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionNavigation(
            activeSection = deriveActiveSection(scrollState.value, sectionOffsets),
            onSectionSelected = { section ->
                coroutineScope.launch {
                    sectionOffsets[section]?.let { offset -> scrollState.animateScrollTo(offset) }
                }
            },
        )
        LanguageSwitcher(onLanguageSelected = onLanguageSelected)
        Column(modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(scrollState)) {
            PortfolioSection.entries.forEachIndexed { index, section ->
                val sectionColor = if (index % 2 == 0) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.surface
                }
                Box(
                    modifier = Modifier.onSizeChanged { size ->
                        sectionHeights[section] = size.height
                    },
                ) {
                    Surface(modifier = Modifier.fillMaxWidth(), color = sectionColor) {
                        section.content()
                    }
                }
            }
            PortfolioFooter.Content()
        }
    }
}
