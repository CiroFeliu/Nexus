package app.luxion.nexus.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WidthClass {
    Compact,
    Medium,
    Expanded,
    Large,
    ;

    companion object {
        fun fromWidth(width: Dp): WidthClass = when {
            width < PortfolioLayout.compactMaxWidth -> Compact
            width < PortfolioLayout.mediumMaxWidth -> Medium
            width < PortfolioLayout.expandedMaxWidth -> Expanded
            else -> Large
        }
    }
}

object PortfolioLayout {
    val maxContentWidth = 1200.dp
    val readableTextWidth = 640.dp
    val compactMaxWidth = 600.dp
    val mediumMaxWidth = 840.dp
    val expandedMaxWidth = 1200.dp

    fun horizontalGutter(widthClass: WidthClass): Dp = when (widthClass) {
        WidthClass.Compact -> 16.dp
        WidthClass.Medium -> 24.dp
        WidthClass.Expanded, WidthClass.Large -> 32.dp
    }

    fun sectionVerticalPadding(widthClass: WidthClass): Dp = when (widthClass) {
        WidthClass.Compact -> 48.dp
        WidthClass.Medium -> 64.dp
        WidthClass.Expanded -> 88.dp
        WidthClass.Large -> 104.dp
    }
}

val LocalWidthClass = staticCompositionLocalOf { WidthClass.Expanded }

@Composable
fun ProvideWidthClass(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(modifier = modifier) {
        CompositionLocalProvider(LocalWidthClass provides WidthClass.fromWidth(maxWidth)) {
            content()
        }
    }
}

@Composable
fun PortfolioContentContainer(
    modifier: Modifier = Modifier,
    verticalPadding: PaddingValues? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val widthClass = LocalWidthClass.current
    val vertical = PortfolioLayout.sectionVerticalPadding(widthClass)
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Box(
            modifier = Modifier
                .widthIn(max = PortfolioLayout.maxContentWidth)
                .fillMaxWidth()
                .padding(horizontal = PortfolioLayout.horizontalGutter(widthClass))
                .padding(verticalPadding ?: PaddingValues(vertical = vertical)),
            content = content,
        )
    }
}
