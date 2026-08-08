package app.luxion.nexus.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// Single theme entry point for the portfolio: follows the system Dark Mode preference and
// applies the shared color/typography/shape tokens through `MaterialTheme` on every target.
@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    val colorScheme = if (isSystemInDarkTheme()) PortfolioDarkColorScheme else PortfolioLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = PortfolioTypography,
        shapes = PortfolioShapes,
        content = content,
    )
}
