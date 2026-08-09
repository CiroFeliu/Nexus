package app.luxion.nexus.navigation

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.hero_photo
import org.jetbrains.compose.resources.painterResource

enum class PortfolioSection(val content: @Composable () -> Unit) {
    HeroAbout({ HeroAboutSection.Content(photo = painterResource(Res.drawable.hero_photo)) }),
    Skills({ SkillsSection.Content() }),
    Experience({ ExperienceSection.Content() }),
    Projects({ ProjectsSection.Content() }),
    Contact({ ContactSection.Content() }),
}
