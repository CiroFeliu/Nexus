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
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.PortfolioSpacing

// Contact: direct, no-friction ways to reach Ciro. No form/backend in v1 — see
// openspec/changes/add-contact-section/design.md for why.
//
// Text-only labels, no icon glyphs: the default Skia font on wasm/desktop has no emoji
// table (renders tofu boxes), and wiring up an icon library is outside this file's scope.
object ContactSection {
    const val title = "Contact"

    // internal so app.luxion.nexus.cv can assemble CvContent from this, the single source
    // of truth for contact links — see openspec/changes/add-dynamic-cv-export/design.md.
    internal data class ContactLink(val label: String, val url: String)

    internal val links = listOf(
        ContactLink("Email", "mailto:ricardociro97@gmail.com"),
        ContactLink("LinkedIn", "https://www.linkedin.com/in/ricardo-ciro-a43594190/"),
        ContactLink("GitHub", "https://github.com/CiroFeliu"),
    )

    @Composable
    fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium)) {
                links.forEach { link ->
                    OutlinedButton(onClick = { openUrl(link.url) }) {
                        Text(link.label, style = MaterialTheme.typography.labelLarge)
                    }
                }
                OutlinedButton(onClick = { exportCvToPdf(buildCvContent()) }) {
                    Text("Download CV", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
