package app.luxion.nexus.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// A clean, technical palette built around a single accent blue, used for links,
// calls to action, and emphasis across every section.
private val PrimaryLight = Color(0xFF2F6FED)
private val OnPrimaryLight = Color(0xFFFFFFFF)
private val PrimaryContainerLight = Color(0xFFD7E3FF)
private val OnPrimaryContainerLight = Color(0xFF001A41)

private val SecondaryLight = Color(0xFF4A5568)
private val OnSecondaryLight = Color(0xFFFFFFFF)
private val SecondaryContainerLight = Color(0xFFDCE3EF)
private val OnSecondaryContainerLight = Color(0xFF141B29)

private val BackgroundLight = Color(0xFFFAFAFC)
private val OnBackgroundLight = Color(0xFF1A1C1E)
private val SurfaceLight = Color(0xFFFAFAFC)
private val OnSurfaceLight = Color(0xFF1A1C1E)
private val SurfaceVariantLight = Color(0xFFE1E2E8)
private val OnSurfaceVariantLight = Color(0xFF44474C)
private val OutlineLight = Color(0xFF74777C)

private val ErrorLight = Color(0xFFBA1A1A)
private val OnErrorLight = Color(0xFFFFFFFF)

val PortfolioLightColorScheme: ColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    error = ErrorLight,
    onError = OnErrorLight,
)

// Dark palette mirrors the light one on a low-glare, near-black surface.
private val PrimaryDark = Color(0xFFAAC7FF)
private val OnPrimaryDark = Color(0xFF002E69)
private val PrimaryContainerDark = Color(0xFF184292)
private val OnPrimaryContainerDark = Color(0xFFD7E3FF)

private val SecondaryDark = Color(0xFFC0C7D6)
private val OnSecondaryDark = Color(0xFF2B323F)
private val SecondaryContainerDark = Color(0xFF333A47)
private val OnSecondaryContainerDark = Color(0xFFDCE3EF)

private val BackgroundDark = Color(0xFF121316)
private val OnBackgroundDark = Color(0xFFE3E2E6)
private val SurfaceDark = Color(0xFF121316)
private val OnSurfaceDark = Color(0xFFE3E2E6)
private val SurfaceVariantDark = Color(0xFF44474C)
private val OnSurfaceVariantDark = Color(0xFFC5C6CC)
private val OutlineDark = Color(0xFF8E9196)

private val ErrorDark = Color(0xFFFFB4AB)
private val OnErrorDark = Color(0xFF690005)

val PortfolioDarkColorScheme: ColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    error = ErrorDark,
    onError = OnErrorDark,
)
