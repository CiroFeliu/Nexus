package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.luxion.nexus.cv.buildCvContent
import app.luxion.nexus.cv.exportCvToPdf
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.interactive

object ContactSection {
    private const val EMAIL_ADDRESS = "ricardociro97@gmail.com"

    private val title = mapOf(Language.English to "Contact", Language.Spanish to "Contacto")
    private val downloadCvLabel = mapOf(Language.English to "Download CV", Language.Spanish to "Descargar CV")
    private val closingStatement = mapOf(
        Language.English to "Always happy to talk shop. Feel free to reach out.",
        Language.Spanish to "Siempre con ganas de hablar de tecnología. Escríbeme cuando quieras.",
    )

    internal data class ContactLink(val label: Map<Language, String>, val url: String)

    internal val links = listOf(
        ContactLink(mapOf(Language.English to "Email", Language.Spanish to "Correo"), "mailto:$EMAIL_ADDRESS"),
        ContactLink(
            mapOf(Language.English to "LinkedIn", Language.Spanish to "LinkedIn"),
            "https://www.linkedin.com/in/ricardo-ciro-a43594190/",
        ),
        ContactLink(mapOf(Language.English to "GitHub", Language.Spanish to "GitHub"), "https://github.com/CiroFeliu"),
    )

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Text(text = title.getValue(language), style = MaterialTheme.typography.headlineMedium)
            SelectionContainer {
                Text(text = EMAIL_ADDRESS, style = MaterialTheme.typography.bodyLarge)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium)) {
                links.forEach { link ->
                    ContactAction(label = link.label.getValue(language), onClick = { openUrl(link.url) })
                }
                ContactAction(
                    label = downloadCvLabel.getValue(language),
                    onClick = { exportCvToPdf(buildCvContent(language)) },
                )
            }
            Text(text = closingStatement.getValue(language), style = MaterialTheme.typography.bodyMedium)
        }
    }

    @Composable
    private fun ContactAction(label: String, onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        OutlinedButton(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier.interactive(interactionSource),
        ) {
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}
