package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.luxion.nexus.theme.PortfolioSpacing

// Experience/Timeline: Ciro's work history, rendered as a vertical timeline ordered
// most-recent-first.
object ExperienceSection {
    const val title = "Experience"

    @Composable
    fun Content() {
        Column(modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large)) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            entries.forEachIndexed { index, entry ->
                TimelineEntry(entry = entry, isLast = index == entries.lastIndex)
            }
        }
    }
}

private data class ExperienceEntry(
    val role: String,
    val company: String,
    val dateRange: String,
    val description: String,
)

// Role history, most-recent-first. This is the only place that needs editing to update
// the timeline.
private val entries = listOf(
    ExperienceEntry(
        role = "Senior Mobile Developer",
        company = "FERMAX",
        dateRange = "Feb 2025 — Present",
        description = "Owns mobile development at FERMAX, coordinating the mobile team.",
    ),
    ExperienceEntry(
        role = "Mobile Systems Architect",
        company = "Freelance",
        dateRange = "Aug 2024 — Present",
        description = "Delivers mobile projects for external clients on a freelance basis, alongside full-time work.",
    ),
    ExperienceEntry(
        role = "Senior Mobile Developer",
        company = "S2 Grupo",
        dateRange = "Dec 2021 — Feb 2025",
        description = "Joined as an Android developer and became the company's go-to mobile expert.",
    ),
    ExperienceEntry(
        role = "Android Developer",
        company = "rudo apps",
        dateRange = "Jul 2019 — Dec 2021",
        description = "Started as an Android developer at this consultancy, later becoming Android tech lead.",
    ),
)

// One timeline row: a dot + connector line (omitted for the last entry) alongside the
// entry's role, company, dates, and description.
@Composable
private fun TimelineEntry(entry: ExperienceEntry, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(PortfolioSpacing.large),
        ) {
            Box(
                modifier = Modifier
                    .size(PortfolioSpacing.small)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.outline),
                )
            }
        }
        Spacer(modifier = Modifier.width(PortfolioSpacing.medium))
        Column(modifier = Modifier.padding(bottom = PortfolioSpacing.large)) {
            Text(text = entry.role, style = MaterialTheme.typography.titleLarge)
            Text(
                text = entry.company,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = entry.dateRange,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.small))
            Text(text = entry.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
