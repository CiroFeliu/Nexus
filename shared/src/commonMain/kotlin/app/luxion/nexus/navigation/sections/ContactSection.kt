package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import app.luxion.nexus.cv.buildCvContent
import app.luxion.nexus.cv.exportCvToPdf
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motion.reveal
import kotlinx.coroutines.delay
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.ic_brand_github
import nexus.shared.generated.resources.ic_brand_linkedin
import nexus.shared.generated.resources.ic_check
import nexus.shared.generated.resources.ic_content_copy
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

object ContactSection {
    private const val EMAIL_ADDRESS = "ricardociro97@gmail.com"
    private const val COPIED_FEEDBACK_MILLIS = 2000L

    private val downloadCvLabel = mapOf(Language.English to "Download CV", Language.Spanish to "Descargar CV")
    private val copyEmailLabel = mapOf(Language.English to "Copy email address", Language.Spanish to "Copiar correo")
    private val copiedLabel = mapOf(Language.English to "Copied", Language.Spanish to "Copiado")
    private val closingStatement = mapOf(
        Language.English to "Always happy to talk shop. Feel free to reach out.",
        Language.Spanish to "Siempre con ganas de hablar de tecnología. Escríbeme cuando quieras.",
    )

    internal data class ContactLink(val label: Map<Language, String>, val url: String)

    private val emailLink = ContactLink(
        mapOf(Language.English to "Email", Language.Spanish to "Correo"),
        "mailto:$EMAIL_ADDRESS",
    )
    private val linkedInLink = ContactLink(
        mapOf(Language.English to "LinkedIn", Language.Spanish to "LinkedIn"),
        "https://www.linkedin.com/in/ricardo-ciro-a43594190/",
    )
    private val gitHubLink = ContactLink(
        mapOf(Language.English to "GitHub", Language.Spanish to "GitHub"),
        "https://github.com/CiroFeliu",
    )

    internal val links = listOf(emailLink, linkedInLink, gitHubLink)

    internal val brandLinks = listOf(
        linkedInLink to Res.drawable.ic_brand_linkedin,
        gitHubLink to Res.drawable.ic_brand_github,
    )

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        val isCompact = LocalWidthClass.current == WidthClass.Compact
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeadline(text = closingStatement.getValue(language))
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            Column(
                modifier = Modifier.reveal(order = 1),
                verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
            ) {
                EmailRow(language = language, isCompact = isCompact)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
                    verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    brandLinks.forEach { (link, icon) ->
                        BrandLink(label = link.label.getValue(language), icon = icon, onClick = { openUrl(link.url) })
                    }
                    val downloadSource = remember { MutableInteractionSource() }
                    Button(
                        onClick = { exportCvToPdf(buildCvContent(language)) },
                        interactionSource = downloadSource,
                        modifier = Modifier.interactive(downloadSource),
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(text = downloadCvLabel.getValue(language), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }

    @Composable
    private fun EmailRow(language: Language, isCompact: Boolean) {
        val colors = MaterialTheme.colorScheme
        val emailText = remember(colors.primary) {
            buildAnnotatedString {
                withLink(
                    LinkAnnotation.Clickable(
                        tag = "email",
                        styles = TextLinkStyles(
                            style = SpanStyle(color = colors.onBackground),
                            hoveredStyle = SpanStyle(
                                color = colors.primary,
                                textDecoration = TextDecoration.Underline,
                            ),
                        ),
                        linkInteractionListener = { openUrl(emailLink.url) },
                    ),
                ) { append(EMAIL_ADDRESS) }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            SelectionContainer(modifier = Modifier.weight(1f, fill = false)) {
                Text(
                    text = emailText,
                    style = if (isCompact) {
                        MaterialTheme.typography.headlineSmall
                    } else {
                        MaterialTheme.typography.headlineLarge
                    },
                )
            }
            CopyEmailButton(language = language)
        }
    }

    @Suppress("DEPRECATION")
    @Composable
    private fun CopyEmailButton(language: Language) {
        val clipboard = LocalClipboardManager.current
        var copied by remember { mutableStateOf(false) }
        LaunchedEffect(copied) {
            if (copied) {
                delay(COPIED_FEEDBACK_MILLIS)
                copied = false
            }
        }
        val interactionSource = remember { MutableInteractionSource() }
        IconButton(
            onClick = {
                clipboard.setText(AnnotatedString(EMAIL_ADDRESS))
                copied = true
            },
            interactionSource = interactionSource,
            modifier = Modifier.interactive(interactionSource),
        ) {
            Icon(
                painter = painterResource(if (copied) Res.drawable.ic_check else Res.drawable.ic_content_copy),
                contentDescription = if (copied) copiedLabel.getValue(language) else copyEmailLabel.getValue(language),
                tint = if (copied) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    @Composable
    private fun BrandLink(label: String, icon: DrawableResource, onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .interactive(interactionSource)
                .clip(PortfolioShapeRoles.interactive)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    role = Role.Button,
                    onClick = onClick,
                )
                .padding(vertical = PortfolioSpacing.small, horizontal = PortfolioSpacing.extraSmall),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp),
                )
                Text(text = label, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
