package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.luxion.nexus.cv.buildCvContent
import app.luxion.nexus.cv.exportCvToPdf
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.LocalPortfolioMonoTypography
import app.luxion.nexus.theme.LocalReducedMotion
import app.luxion.nexus.theme.LocalViewportHeight
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioLayout
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.heroDisplayStyle
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motion.parallax
import app.luxion.nexus.theme.motion.reveal

object HeroAboutSection {
    private const val AVATAR_INITIALS = "CF"
    private const val VIEWPORT_FILL = 0.85f
    private const val PORTRAIT_ASPECT = 4f / 5f
    private const val PARALLAX_FACTOR = 0.15f
    private val PORTRAIT_MAX_WIDTH = 360.dp
    private val COMPACT_PORTRAIT_MAX_WIDTH = 320.dp
    private val PARALLAX_MAX_SHIFT = 32.dp

    internal data class HeroAboutContent(val name: String, val role: String, val intro: String, val bio: String)

    internal val content = mapOf(
        Language.English to HeroAboutContent(
            name = "Ciro Feliu",
            role = "Senior Developer & Mobile Systems Architect",
            intro = "I design and build mobile systems that stay maintainable as they grow, " +
                "from Android architecture to Kotlin Multiplatform.",
            bio = "I design and build mobile systems that stay maintainable as they " +
                "grow, with a focus on Android architecture, Kotlin Multiplatform, and the tooling " +
                "that keeps a codebase easy to work in years after it ships.",
        ),
        Language.Spanish to HeroAboutContent(
            name = "Ciro Feliu",
            role = "Desarrollador Senior y Arquitecto de Sistemas Móviles",
            intro = "Diseño y construyo sistemas móviles que siguen siendo fáciles de mantener al crecer, " +
                "de la arquitectura Android a Kotlin Multiplatform.",
            bio = "Diseño y construyo sistemas móviles que se mantienen fáciles de mantener a " +
                "medida que crecen, centrado en arquitectura Android, Kotlin Multiplatform y las " +
                "herramientas que hacen que un código siga siendo cómodo de tocar años después de " +
                "publicarse.",
        ),
    )

    internal val downloadCvLabel = mapOf(Language.English to "Download CV", Language.Spanish to "Descargar CV")
    internal val contactLabel = mapOf(Language.English to "Contact", Language.Spanish to "Contacto")

    @Composable
    fun Content(
        photo: Painter? = null,
        onContactClick: () -> Unit = {},
        scrollOffset: () -> Int = { 0 },
    ) {
        val content = content.getValue(LocalAppLanguage.current)
        val widthClass = LocalWidthClass.current
        val viewportHeight = LocalViewportHeight.current
        if (widthClass == WidthClass.Compact) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
            ) {
                HeroText(content = content, widthClass = widthClass, onContactClick = onContactClick)
                Portrait(
                    photo = photo,
                    name = content.name,
                    modifier = Modifier.widthIn(max = COMPACT_PORTRAIT_MAX_WIDTH).fillMaxWidth().reveal(order = 4),
                )
            }
        } else {
            val isMedium = widthClass == WidthClass.Medium
            val parallaxEnabled = !LocalReducedMotion.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = heroMinHeight(viewportHeight)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
            ) {
                Box(modifier = Modifier.weight(if (isMedium) 6f else 7f)) {
                    HeroText(content = content, widthClass = widthClass, onContactClick = onContactClick)
                }
                Box(
                    modifier = Modifier.weight(if (isMedium) 6f else 5f),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Portrait(
                        photo = photo,
                        name = content.name,
                        modifier = Modifier
                            .widthIn(max = PORTRAIT_MAX_WIDTH)
                            .fillMaxWidth()
                            .parallax(
                                enabled = parallaxEnabled,
                                scrollOffset = scrollOffset,
                                factor = PARALLAX_FACTOR,
                                maxShift = PARALLAX_MAX_SHIFT,
                            )
                            .reveal(order = 4, rise = 32.dp),
                    )
                }
            }
        }
    }

    private fun heroMinHeight(viewportHeight: Dp): Dp =
        if (viewportHeight == Dp.Unspecified || viewportHeight == Dp.Infinity) {
            0.dp
        } else {
            (viewportHeight * VIEWPORT_FILL - PortfolioLayout.topBarHeight).coerceAtLeast(0.dp)
        }

    @Composable
    private fun HeroText(content: HeroAboutContent, widthClass: WidthClass, onContactClick: () -> Unit) {
        val language = LocalAppLanguage.current
        val nameStyle = heroDisplayStyle(MaterialTheme.typography.displayLarge, widthClass)
            .copy(lineBreak = LineBreak.Heading)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = content.role,
                style = LocalPortfolioMonoTypography.current.label,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.reveal(order = 0),
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            Text(
                text = content.name.split(' ', limit = 2).joinToString(separator = "\n"),
                style = nameStyle,
                modifier = Modifier
                    .reveal(order = 1)
                    .clearAndSetSemantics {
                        text = AnnotatedString(content.name)
                        heading()
                    },
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            Text(
                text = content.intro,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.widthIn(max = PortfolioLayout.readableTextWidth).reveal(order = 2),
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
            Row(
                modifier = Modifier.reveal(order = 3),
                horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val downloadSource = remember { MutableInteractionSource() }
                Button(
                    onClick = { exportCvToPdf(buildCvContent(language)) },
                    interactionSource = downloadSource,
                    modifier = Modifier.interactive(downloadSource),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = downloadCvLabel.getValue(language), style = MaterialTheme.typography.labelLarge)
                }
                val contactSource = remember { MutableInteractionSource() }
                TextButton(
                    onClick = onContactClick,
                    interactionSource = contactSource,
                    modifier = Modifier.interactive(contactSource),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = contactLabel.getValue(language), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }

    @Composable
    private fun Portrait(photo: Painter?, name: String, modifier: Modifier = Modifier) {
        val portraitModifier = modifier.aspectRatio(PORTRAIT_ASPECT).clip(MaterialTheme.shapes.large)
        if (photo != null) {
            Image(
                painter = photo,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = portraitModifier,
            )
        } else {
            Box(
                modifier = portraitModifier.background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = AVATAR_INITIALS,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}
