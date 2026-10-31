package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.i18n.YearMonth
import app.luxion.nexus.i18n.formatDateRange
import app.luxion.nexus.theme.LocalPortfolioMonoTypography
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioLayout
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.motion.reveal

object ExperienceSection {
    private val title = mapOf(Language.English to "Experience", Language.Spanish to "Experiencia")

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        val isCompact = LocalWidthClass.current == WidthClass.Compact
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeadline(text = title.getValue(language))
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            Column(
                modifier = Modifier.fillMaxWidth().reveal(order = 1),
                verticalArrangement = Arrangement.spacedBy(if (isCompact) PortfolioSpacing.extraLarge else 56.dp),
            ) {
                entries.forEach { entry ->
                    if (isCompact) {
                        CompactExperienceRow(entry = entry, language = language)
                    } else {
                        EditorialExperienceRow(entry = entry, language = language)
                    }
                }
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

private const val FREELANCE_ROLE = "Contract engineer for AI-first product teams"

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
                role = FREELANCE_ROLE,
                description = "I join product teams that build with AI agents and deliver features end to " +
                    "end: spec, implementation verified with tests, and deployment. I prefer long-term " +
                    "relationships with one or two clients over many small projects.",
            ),
            Language.Spanish to ExperienceContent(
                role = FREELANCE_ROLE,
                description = "Me integro en equipos de producto que trabajan con agentes de IA y entrego " +
                    "funcionalidad de principio a fin: spec, implementación verificada con tests y " +
                    "despliegue. Busco relaciones largas con uno o dos clientes antes que muchos proyectos " +
                    "pequeños.",
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
private fun EditorialExperienceRow(entry: ExperienceEntry, language: Language) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
    ) {
        Text(
            text = entry.company,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.weight(0.4f),
        )
        ExperienceDetails(entry = entry, language = language, modifier = Modifier.weight(0.6f))
    }
}

@Composable
private fun CompactExperienceRow(entry: ExperienceEntry, language: Language) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.outlineVariant),
        )
        Column(modifier = Modifier.padding(start = PortfolioSpacing.medium)) {
            Text(text = entry.company, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(PortfolioSpacing.small))
            ExperienceDetails(entry = entry, language = language)
        }
    }
}

@Composable
private fun ExperienceDetails(entry: ExperienceEntry, language: Language, modifier: Modifier = Modifier) {
    val content = entry.content.getValue(language)
    Column(modifier = modifier.widthIn(max = PortfolioLayout.readableTextWidth)) {
        Text(
            text = entry.dateRange(language),
            style = LocalPortfolioMonoTypography.current.date,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(PortfolioSpacing.extraSmall))
        Text(text = content.role, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(PortfolioSpacing.small))
        Text(
            text = content.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
