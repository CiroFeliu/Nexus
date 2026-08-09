package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.theme.PortfolioSpacing

@Composable
fun SectionPlaceholder(title: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large)) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = "TODO", style = MaterialTheme.typography.bodyMedium)
    }
}
