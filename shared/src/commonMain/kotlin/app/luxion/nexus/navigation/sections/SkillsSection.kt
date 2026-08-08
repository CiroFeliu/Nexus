package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioSpacing

// One labeled group of skills, e.g. "Languages" -> ["Kotlin", "Swift", ...]. The skills
// themselves are tool/technology names and stay identical across languages; only the
// category label is translated.
private data class SkillCategory(val label: Map<Language, String>, val skills: List<String>)

// Ciro's skill set, grouped for quick scanning. Data-as-code so updating it later is a
// one-file edit; keep entries short (a tool/technology name, not a sentence).
private val skillCategories = listOf(
    SkillCategory(
        label = mapOf(Language.English to "Languages", Language.Spanish to "Lenguajes"),
        skills = listOf("Kotlin", "Swift", "Java"),
    ),
    SkillCategory(
        label = mapOf(Language.English to "Mobile / Android", Language.Spanish to "Móvil / Android"),
        skills = listOf(
            "Jetpack Compose",
            "Compose Multiplatform",
            "Android SDK",
            "Coroutines & Flow",
            "Kotlin Multiplatform",
            "SwiftUI",
        ),
    ),
    SkillCategory(
        label = mapOf(Language.English to "Architecture & Patterns", Language.Spanish to "Arquitectura y Patrones"),
        skills = listOf(
            "MVVM",
            "Clean Architecture",
            "Hexagonal Architecture",
            "Modularization",
            "Dependency Injection",
            "Design Systems",
        ),
    ),
    SkillCategory(
        label = mapOf(Language.English to "Tools & Platforms", Language.Spanish to "Herramientas y Plataformas"),
        skills = listOf("Gradle", "Git", "CI/CD", "Firebase", "Docker", "Agent Orchestration", "OpenSpec"),
    ),
)

// Skills & Stack: technologies, tools, and areas of expertise, grouped by category so a
// recruiter or engineer can scan Ciro's mobile-systems-architecture depth quickly.
object SkillsSection {
    private val title = mapOf(Language.English to "Skills & Stack", Language.Spanish to "Habilidades y Tecnologías")

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
        ) {
            Text(text = title.getValue(language), style = MaterialTheme.typography.headlineMedium)
            skillCategories.forEach { category ->
                SkillCategoryRow(category, language)
            }
        }
    }
}

// One category label followed by its skills as chips that wrap onto additional lines
// instead of overflowing on narrow viewports.
@Composable
private fun SkillCategoryRow(category: SkillCategory, language: Language) {
    Column(verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small)) {
        Text(text = category.label.getValue(language), style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
        ) {
            category.skills.forEach { skill ->
                SkillChip(skill)
            }
        }
    }
}

// A single skill rendered as a themed, rounded tag.
@Composable
private fun SkillChip(skill: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = skill,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = PortfolioSpacing.medium, vertical = PortfolioSpacing.small),
        )
    }
}
