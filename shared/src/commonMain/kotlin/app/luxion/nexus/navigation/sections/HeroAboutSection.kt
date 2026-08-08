package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.luxion.nexus.theme.PortfolioSpacing

// Hero/About: introduction and personal summary — the first thing any visitor sees.
object HeroAboutSection {
    const val title = "Hero / About"

    private const val NAME = "Ciro Feliu"
    private const val ROLE = "Senior Android Developer & Mobile Systems Architect"
    private const val AVATAR_INITIALS = "CF"
    private const val BIO = "I design and build mobile systems that stay maintainable as they " +
        "grow, with a focus on Android architecture, Kotlin Multiplatform, and the tooling " +
        "that keeps a codebase easy to work in years after it ships. This portfolio itself " +
        "is a Compose Multiplatform build, shared across web, Android, desktop, and iOS."

    @Composable
    fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Avatar(AVATAR_INITIALS)
            Text(text = NAME, style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
            Text(text = ROLE, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            Text(text = BIO, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }

    // A simple initials-based placeholder avatar, styled with theme colors, so the section
    // looks intentional even before a real photo asset is supplied.
    @Composable
    private fun Avatar(initials: String) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = initials, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}
