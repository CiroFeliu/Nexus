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
import app.luxion.nexus.theme.PortfolioSpacing

// One labeled group of skills, e.g. "Languages" -> ["Kotlin", "Swift", ...].
//
// internal so app.luxion.nexus.cv can assemble CvContent from this, the single source of
// truth for the skill list — see openspec/changes/add-dynamic-cv-export/design.md.
internal data class SkillCategory(val label: String, val skills: List<String>)

// Ciro's skill set, grouped for quick scanning. Data-as-code so updating it later is a
// one-file edit; keep entries short (a tool/technology name, not a sentence).
internal val skillCategories = listOf(
    SkillCategory(
        label = "Languages",
        skills = listOf("Kotlin", "Swift", "Java"),
    ),
    SkillCategory(
        label = "Mobile / Android",
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
        label = "Architecture & Patterns",
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
        label = "Tools & Platforms",
        skills = listOf("Gradle", "Git", "CI/CD", "Firebase", "Docker", "Agent Orchestration", "OpenSpec"),
    ),
)

// Skills & Stack: technologies, tools, and areas of expertise, grouped by category so a
// recruiter or engineer can scan Ciro's mobile-systems-architecture depth quickly.
object SkillsSection {
    const val title = "Skills & Stack"

    @Composable
    fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            skillCategories.forEach { category ->
                SkillCategoryRow(category)
            }
        }
    }
}

// One category label followed by its skills as chips that wrap onto additional lines
// instead of overflowing on narrow viewports.
@Composable
private fun SkillCategoryRow(category: SkillCategory) {
    Column(verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small)) {
        Text(text = category.label, style = MaterialTheme.typography.titleMedium)
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
