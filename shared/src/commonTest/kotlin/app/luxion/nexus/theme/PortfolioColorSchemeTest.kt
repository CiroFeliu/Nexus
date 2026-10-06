package app.luxion.nexus.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.test.Test
import kotlin.test.assertTrue

class PortfolioColorSchemeTest {

    private val roles: List<Pair<String, (ColorScheme) -> Color>> = listOf(
        "primary" to { it.primary },
        "onPrimary" to { it.onPrimary },
        "primaryContainer" to { it.primaryContainer },
        "onPrimaryContainer" to { it.onPrimaryContainer },
        "inversePrimary" to { it.inversePrimary },
        "secondary" to { it.secondary },
        "onSecondary" to { it.onSecondary },
        "secondaryContainer" to { it.secondaryContainer },
        "onSecondaryContainer" to { it.onSecondaryContainer },
        "tertiary" to { it.tertiary },
        "onTertiary" to { it.onTertiary },
        "tertiaryContainer" to { it.tertiaryContainer },
        "onTertiaryContainer" to { it.onTertiaryContainer },
        "background" to { it.background },
        "onBackground" to { it.onBackground },
        "surface" to { it.surface },
        "onSurface" to { it.onSurface },
        "surfaceVariant" to { it.surfaceVariant },
        "onSurfaceVariant" to { it.onSurfaceVariant },
        "surfaceTint" to { it.surfaceTint },
        "inverseSurface" to { it.inverseSurface },
        "inverseOnSurface" to { it.inverseOnSurface },
        "error" to { it.error },
        "onError" to { it.onError },
        "errorContainer" to { it.errorContainer },
        "onErrorContainer" to { it.onErrorContainer },
        "outline" to { it.outline },
        "outlineVariant" to { it.outlineVariant },
        "scrim" to { it.scrim },
        "surfaceBright" to { it.surfaceBright },
        "surfaceDim" to { it.surfaceDim },
        "surfaceContainer" to { it.surfaceContainer },
        "surfaceContainerHigh" to { it.surfaceContainerHigh },
        "surfaceContainerHighest" to { it.surfaceContainerHighest },
        "surfaceContainerLow" to { it.surfaceContainerLow },
        "surfaceContainerLowest" to { it.surfaceContainerLowest },
        "primaryFixed" to { it.primaryFixed },
        "primaryFixedDim" to { it.primaryFixedDim },
        "onPrimaryFixed" to { it.onPrimaryFixed },
        "onPrimaryFixedVariant" to { it.onPrimaryFixedVariant },
        "secondaryFixed" to { it.secondaryFixed },
        "secondaryFixedDim" to { it.secondaryFixedDim },
        "onSecondaryFixed" to { it.onSecondaryFixed },
        "onSecondaryFixedVariant" to { it.onSecondaryFixedVariant },
        "tertiaryFixed" to { it.tertiaryFixed },
        "tertiaryFixedDim" to { it.tertiaryFixedDim },
        "onTertiaryFixed" to { it.onTertiaryFixed },
        "onTertiaryFixedVariant" to { it.onTertiaryFixedVariant },
    )

    @Test
    fun lightSchemeOverridesEveryBaselineRole() {
        assertNoBaselineRoles(PortfolioLightColorScheme, lightColorScheme())
    }

    @Test
    fun darkSchemeOverridesEveryBaselineRole() {
        assertNoBaselineRoles(PortfolioDarkColorScheme, darkColorScheme())
    }

    @Test
    fun bodyTextMeetsAaContrastOnEverySurface() {
        listOf(PortfolioLightColorScheme, PortfolioDarkColorScheme).forEach { scheme ->
            val surfaces = listOf(
                scheme.background,
                scheme.surface,
                scheme.surfaceContainerLowest,
                scheme.surfaceContainerLow,
                scheme.surfaceContainer,
                scheme.surfaceContainerHigh,
            )
            surfaces.forEach { surface ->
                listOf(scheme.onSurface, scheme.onSurfaceVariant, scheme.primary).forEach { foreground ->
                    val ratio = contrastRatio(foreground, surface)
                    assertTrue(ratio >= 4.5f, "Contrast $ratio below AA for $foreground on $surface")
                }
            }
            assertTrue(contrastRatio(scheme.onPrimary, scheme.primary) >= 4.5f)
            assertTrue(contrastRatio(scheme.onSecondaryContainer, scheme.secondaryContainer) >= 4.5f)
        }
    }

    private fun assertNoBaselineRoles(scheme: ColorScheme, baseline: ColorScheme) {
        val unchanged = roles.filter { (_, role) -> role(scheme) == role(baseline) }.map { it.first }
        assertTrue(unchanged.isEmpty(), "Roles still using the Material baseline: $unchanged")
    }

    private fun contrastRatio(a: Color, b: Color): Float {
        val lighter = maxOf(a.luminance(), b.luminance())
        val darker = minOf(a.luminance(), b.luminance())
        return (lighter + 0.05f) / (darker + 0.05f)
    }
}
