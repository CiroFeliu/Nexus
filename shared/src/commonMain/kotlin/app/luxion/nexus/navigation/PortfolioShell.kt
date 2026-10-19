package app.luxion.nexus.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.GrainOverlay
import app.luxion.nexus.theme.LocalReducedMotion
import app.luxion.nexus.theme.LocalViewportHeight
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioContentContainer
import app.luxion.nexus.theme.PortfolioLayout
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.ProvideWidthClass
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.motion.LocalSectionRevealed
import app.luxion.nexus.theme.motionSpec
import kotlinx.coroutines.launch

private val REVEAL_THRESHOLD = 96.dp

@Composable
fun PortfolioShell(onLanguageSelected: (Language) -> Unit) {
    ProvideWidthClass(modifier = Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        val density = LocalDensity.current
        val widthClass = LocalWidthClass.current
        val barHeightPx = with(density) { PortfolioLayout.topBarHeight.roundToPx() }
        val viewportHeightPx = with(density) { LocalViewportHeight.current.finiteOrZero().roundToPx() }
        val revealThresholdPx = with(density) { REVEAL_THRESHOLD.roundToPx() }
        val sectionHeights = remember { mutableStateMapOf<PortfolioSection, Int>() }
        val revealedSections = remember { mutableStateMapOf<PortfolioSection, Boolean>() }
        var menuOpen by remember { mutableStateOf(false) }
        val sectionStarts by remember { derivedStateOf { sectionStarts(sectionHeights) } }
        val jumpTargets by remember(barHeightPx) {
            derivedStateOf { jumpTargets(sectionStarts, barHeightPx, scrollState.maxValue) }
        }
        val activeSection by remember { derivedStateOf { deriveActiveSection(scrollState.value, jumpTargets) } }
        val isScrolled by remember { derivedStateOf { scrollState.value > 0 } }
        val scrollSpec = motionSpec(spring<Float>())
        val menuFadeSpec = tween<Float>(
            durationMillis = if (LocalReducedMotion.current) 0 else PortfolioMotion.MEDIUM_MILLIS,
            easing = PortfolioMotion.emphasizedEasing,
        )

        LaunchedEffect(viewportHeightPx, revealThresholdPx) {
            snapshotFlow {
                revealableSections(
                    starts = sectionStarts,
                    heights = sectionHeights.toMap(),
                    viewportBottom = scrollState.value + viewportHeightPx,
                    threshold = revealThresholdPx,
                )
            }.collect { sections -> sections.forEach { revealedSections[it] = true } }
        }
        LaunchedEffect(widthClass) {
            if (widthClass != WidthClass.Compact) menuOpen = false
        }

        val navigateTo: (PortfolioSection) -> Unit = remember(scrollSpec) {
            { section ->
                menuOpen = false
                coroutineScope.launch {
                    jumpTargets[section]?.let { target -> scrollState.animateScrollTo(target, scrollSpec) }
                }
            }
        }
        val host = remember(navigateTo) { SectionHost(navigateTo = navigateTo, scrollOffset = { scrollState.value }) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
                PortfolioSection.entries.forEachIndexed { index, section ->
                    val isHero = section == PortfolioSection.HeroAbout
                    Column(
                        modifier = Modifier
                            .zIndex(if (isHero) 1f else 0f)
                            .onSizeChanged { size -> sectionHeights[section] = size.height },
                    ) {
                        if (index > 0) SectionSeparator()
                        CompositionLocalProvider(
                            LocalSectionRevealed provides (isHero || revealedSections[section] == true),
                        ) {
                            PortfolioContentContainer(
                                verticalPadding = if (isHero) heroPadding() else null,
                            ) {
                                section.content(host)
                            }
                        }
                    }
                }
                PortfolioFooter.Content()
            }
            PortfolioTopBar(
                isScrolled = { isScrolled },
                activeSection = { activeSection },
                onSectionSelected = navigateTo,
                onLanguageSelected = onLanguageSelected,
                onMenuClick = { menuOpen = true },
                modifier = Modifier.align(Alignment.TopCenter),
            )
            AnimatedVisibility(
                visible = menuOpen,
                enter = fadeIn(menuFadeSpec),
                exit = fadeOut(menuFadeSpec),
            ) {
                CompactNavigationMenu(
                    activeSection = activeSection,
                    onSectionSelected = navigateTo,
                    onLanguageSelected = onLanguageSelected,
                    onDismiss = { menuOpen = false },
                )
            }
            GrainOverlay(modifier = Modifier.matchParentSize())
        }
    }
}

@Composable
private fun SectionSeparator() {
    PortfolioContentContainer(verticalPadding = PaddingValues()) {
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun heroPadding(): PaddingValues = PaddingValues(
    top = PortfolioLayout.topBarHeight + PortfolioSpacing.large,
    bottom = if (LocalWidthClass.current == WidthClass.Compact) {
        PortfolioLayout.sectionVerticalPadding(WidthClass.Compact)
    } else {
        PortfolioSpacing.large
    },
)

private fun Dp.finiteOrZero(): Dp = if (this == Dp.Unspecified || this == Dp.Infinity) 0.dp else this

internal fun sectionStarts(heights: Map<PortfolioSection, Int>): Map<PortfolioSection, Int> {
    var cumulativeHeight = 0
    return PortfolioSection.entries.associateWith { section ->
        val start = cumulativeHeight
        cumulativeHeight += heights[section] ?: 0
        start
    }
}

internal fun jumpTargets(starts: Map<PortfolioSection, Int>, barHeight: Int, maxScroll: Int): Map<PortfolioSection, Int> =
    starts.mapValues { (section, start) ->
        if (section == PortfolioSection.HeroAbout) 0 else (start - barHeight).coerceIn(0, maxScroll)
    }

internal fun revealableSections(
    starts: Map<PortfolioSection, Int>,
    heights: Map<PortfolioSection, Int>,
    viewportBottom: Int,
    threshold: Int,
): Set<PortfolioSection> {
    if (heights.size < PortfolioSection.entries.size) return emptySet()
    return PortfolioSection.entries.filter { section ->
        val start = starts[section] ?: return@filter false
        val height = heights.getValue(section)
        start + minOf(threshold, height / 4) <= viewportBottom
    }.toSet()
}
