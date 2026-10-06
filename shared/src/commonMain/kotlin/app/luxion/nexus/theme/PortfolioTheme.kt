package app.luxion.nexus.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    val colorScheme = if (isSystemInDarkTheme()) PortfolioDarkColorScheme else PortfolioLightColorScheme
    val fontsReady = rememberPortfolioFontsReady()
    val sans = geistFontFamily()
    val mono = geistMonoFontFamily()
    val typography = remember(sans) { portfolioTypography(sans) }
    val monoTypography = remember(mono) { portfolioMonoTypography(mono) }
    CompositionLocalProvider(
        LocalReducedMotion provides rememberReducedMotionPreference(),
        LocalPortfolioMonoTypography provides monoTypography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = PortfolioShapes,
        ) {
            if (fontsReady) {
                content()
            } else {
                Box(Modifier.fillMaxSize().background(colorScheme.background))
            }
        }
    }
}
