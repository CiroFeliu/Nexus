package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.LocalPortfolioMonoTypography
import app.luxion.nexus.theme.LocalReducedMotion
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.motion.reveal

internal data class SkillCategory(val label: Map<Language, String>, val skills: List<String>)

internal val skillCategories = listOf(
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
        label = mapOf(Language.English to "Architecture & patterns", Language.Spanish to "Arquitectura y patrones"),
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
        label = mapOf(Language.English to "Tools & platforms", Language.Spanish to "Herramientas y plataformas"),
        skills = listOf("Gradle", "Git", "CI/CD", "Firebase", "Docker", "Agent Orchestration", "OpenSpec"),
    ),
)

internal val coreStack = listOf(
    "Kotlin",
    "Jetpack Compose",
    "Kotlin Multiplatform",
    "Compose Multiplatform",
    "Coroutines & Flow",
    "Clean Architecture",
    "Gradle",
)

internal fun skillColumnCount(widthClass: WidthClass): Int = when (widthClass) {
    WidthClass.Compact -> 1
    WidthClass.Medium -> 2
    WidthClass.Expanded, WidthClass.Large -> 4
}

object SkillsSection {
    private const val CORE_STACK_SEPARATOR = "   /   "
    private val title = mapOf(Language.English to "Skills & stack", Language.Spanish to "Habilidades y tecnologías")

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        val columns = skillColumnCount(LocalWidthClass.current)
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeadline(text = title.getValue(language))
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            Column(
                modifier = Modifier.fillMaxWidth().reveal(order = 1),
                verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
            ) {
                skillCategories.chunked(columns).forEach { rowCategories ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraLarge),
                    ) {
                        rowCategories.forEach { category ->
                            SkillColumn(category = category, language = language, modifier = Modifier.weight(1f))
                        }
                        repeat(columns - rowCategories.size) { Spacer(modifier = Modifier.weight(1f)) }
                    }
                }
            }
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            CoreStackLine(separator = CORE_STACK_SEPARATOR)
        }
    }

    @Composable
    private fun CoreStackLine(separator: String) {
        val style = MaterialTheme.typography.headlineSmall
        val color = MaterialTheme.colorScheme.onSurfaceVariant
        if (LocalReducedMotion.current) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.large)) {
                coreStack.forEach { Text(text = it, style = style, color = color) }
            }
        } else {
            Text(
                text = (coreStack + coreStack).joinToString(separator = separator, postfix = separator),
                style = style,
                color = color,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clearAndSetSemantics { contentDescription = coreStack.joinToString(", ") }
                    .basicMarquee(iterations = Int.MAX_VALUE, spacing = MarqueeSpacing(0.dp)),
            )
        }
    }
}

@Composable
private fun SkillColumn(category: SkillCategory, language: Language, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = category.label.getValue(language), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(PortfolioSpacing.medium))
        category.skills.forEach { skill ->
            Text(
                text = skill,
                style = LocalPortfolioMonoTypography.current.item,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
