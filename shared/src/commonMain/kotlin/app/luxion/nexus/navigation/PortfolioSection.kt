package app.luxion.nexus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.hero_photo
import org.jetbrains.compose.resources.painterResource

@Immutable
class SectionHost(
    val navigateTo: (PortfolioSection) -> Unit,
    val scrollOffset: () -> Int,
)

enum class PortfolioSection(val label: Map<Language, String>, val content: @Composable (SectionHost) -> Unit) {
    HeroAbout(
        label = mapOf(Language.English to "About", Language.Spanish to "Sobre mí"),
        content = { host ->
            HeroAboutSection.Content(
                photo = painterResource(Res.drawable.hero_photo),
                onContactClick = { host.navigateTo(Contact) },
                scrollOffset = host.scrollOffset,
            )
        },
    ),
    Skills(
        label = mapOf(Language.English to "Skills", Language.Spanish to "Habilidades"),
        content = { SkillsSection.Content() },
    ),
    Experience(
        label = mapOf(Language.English to "Experience", Language.Spanish to "Experiencia"),
        content = { ExperienceSection.Content() },
    ),
    Projects(
        label = mapOf(Language.English to "Projects", Language.Spanish to "Proyectos"),
        content = { ProjectsSection.Content() },
    ),
    Contact(
        label = mapOf(Language.English to "Contact", Language.Spanish to "Contacto"),
        content = { ContactSection.Content() },
    ),
}
