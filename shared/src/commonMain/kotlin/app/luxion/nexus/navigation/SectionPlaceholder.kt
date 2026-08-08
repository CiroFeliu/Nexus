package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.theme.PortfolioSpacing

// Body rendered for any section whose real content hasn't landed yet: its title plus an
// explicit "TODO" marker, so an unfinished section is obvious in a build or screenshot.
@Composable
fun SectionPlaceholder(title: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large)) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = "TODO", style = MaterialTheme.typography.bodyMedium)
    }
}
