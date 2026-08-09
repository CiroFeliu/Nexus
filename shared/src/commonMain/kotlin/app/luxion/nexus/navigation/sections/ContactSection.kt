package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.cv.buildCvContent
import app.luxion.nexus.cv.exportCvToPdf
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.PortfolioSpacing

object ContactSection {
    private val title = mapOf(Language.English to "Contact", Language.Spanish to "Contacto")
    private val downloadCvLabel = mapOf(Language.English to "Download CV", Language.Spanish to "Descargar CV")

    internal data class ContactLink(val label: Map<Language, String>, val url: String)

    internal val links = listOf(
        ContactLink(mapOf(Language.English to "Email", Language.Spanish to "Correo"), "mailto:ricardociro97@gmail.com"),
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
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Text(text = title.getValue(language), style = MaterialTheme.typography.headlineMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium)) {
                links.forEach { link ->
                    OutlinedButton(onClick = { openUrl(link.url) }) {
                        Text(link.label.getValue(language), style = MaterialTheme.typography.labelLarge)
                    }
                }
                OutlinedButton(onClick = { exportCvToPdf(buildCvContent(language)) }) {
                    Text(downloadCvLabel.getValue(language), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
