package app.luxion.nexus.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

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
