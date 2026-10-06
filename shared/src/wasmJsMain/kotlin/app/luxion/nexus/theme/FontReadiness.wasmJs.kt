package app.luxion.nexus.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.geist_bold
import nexus.shared.generated.resources.geist_medium
import nexus.shared.generated.resources.geist_regular
import nexus.shared.generated.resources.geist_semibold
import nexus.shared.generated.resources.geistmono_medium
import nexus.shared.generated.resources.geistmono_regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberPortfolioFontsReady(): Boolean {
    val fonts = listOf(
        preloadFont(Res.font.geist_regular, FontWeight.Normal),
        preloadFont(Res.font.geist_medium, FontWeight.Medium),
        preloadFont(Res.font.geist_semibold, FontWeight.SemiBold),
        preloadFont(Res.font.geist_bold, FontWeight.Bold),
        preloadFont(Res.font.geistmono_regular, FontWeight.Normal),
        preloadFont(Res.font.geistmono_medium, FontWeight.Medium),
    )
    return fonts.all { it.value != null }
}
