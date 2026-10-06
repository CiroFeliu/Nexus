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
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.i18n.YearMonth
import app.luxion.nexus.i18n.formatDateRange
import app.luxion.nexus.theme.PortfolioSpacing

object ExperienceSection {
    private val title = mapOf(Language.English to "Experience", Language.Spanish to "Experiencia")

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = title.getValue(language), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            entries.forEachIndexed { index, entry ->
                TimelineEntry(entry = entry, language = language, isLast = index == entries.lastIndex)
            }
        }
    }
}

internal data class ExperienceContent(val role: String, val description: String)

internal data class ExperienceEntry(
    val content: Map<Language, ExperienceContent>,
    val company: String,
    val start: YearMonth,
    val end: YearMonth? = null,
) {
    fun dateRange(language: Language): String = formatDateRange(start, end, language)
}

internal val entries = listOf(
    ExperienceEntry(
        content = mapOf(
            Language.English to ExperienceContent(
                role = "Senior Mobile Developer",
                description = "I own mobile development at FERMAX and coordinate the mobile team.",
            ),
            Language.Spanish to ExperienceContent(
                role = "Desarrollador Móvil Senior",
                description = "Soy responsable del desarrollo móvil en FERMAX y coordino al equipo móvil.",
            ),
        ),
        company = "FERMAX",
        start = YearMonth(2025, 2),
    ),
    ExperienceEntry(
        content = mapOf(
            Language.English to ExperienceContent(
                role = "Mobile Systems Architect",
                description = "I deliver mobile projects for external clients on a freelance basis, " +
                    "alongside my full-time role.",
            ),
            Language.Spanish to ExperienceContent(
                role = "Arquitecto de Sistemas Móviles",
                description = "Desarrollo proyectos móviles para clientes externos como freelance, " +
                    "compaginándolo con mi empleo a tiempo completo.",
            ),
        ),
        company = "Freelance",
        start = YearMonth(2024, 8),
    ),
    ExperienceEntry(
        content = mapOf(
            Language.English to ExperienceContent(
                role = "Senior Mobile Developer",
                description = "I joined as an Android developer and became the company's go-to mobile expert.",
            ),
            Language.Spanish to ExperienceContent(
                role = "Desarrollador Móvil Senior",
                description = "Entré como desarrollador Android y acabé siendo el referente móvil " +
                    "de la empresa.",
            ),
        ),
        company = "S2 Grupo",
        start = YearMonth(2021, 12),
        end = YearMonth(2025, 2),
    ),
    ExperienceEntry(
        content = mapOf(
            Language.English to ExperienceContent(
                role = "Android Developer",
                description = "I started as an Android developer at this consultancy and later became " +
                    "its Android tech lead.",
            ),
            Language.Spanish to ExperienceContent(
                role = "Desarrollador Android",
                description = "Empecé como desarrollador Android en esta consultora y más tarde pasé a " +
                    "ser tech lead de Android.",
            ),
        ),
        company = "rudo apps",
        start = YearMonth(2019, 7),
        end = YearMonth(2021, 12),
    ),
)

@Composable
private fun TimelineEntry(entry: ExperienceEntry, language: Language, isLast: Boolean) {
    val content = entry.content.getValue(language)
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
            Text(text = content.role, style = MaterialTheme.typography.titleLarge)
            Text(
                text = entry.company,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = entry.dateRange(language),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.small))
            Text(text = content.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
