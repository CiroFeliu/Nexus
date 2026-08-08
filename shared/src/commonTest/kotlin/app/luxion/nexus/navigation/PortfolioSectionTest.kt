package app.luxion.nexus.navigation

import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PortfolioSectionTest {

    @Test
    fun entriesAreDeclaredInDisplayOrder() {
        assertEquals(
            listOf(
                PortfolioSection.HeroAbout,
                PortfolioSection.Skills,
                PortfolioSection.Experience,
                PortfolioSection.Projects,
                PortfolioSection.Contact,
            ),
            PortfolioSection.entries.toList(),
        )
    }

    @Test
    fun everyEntryHasANonEmptyTitle() {
        PortfolioSection.entries.forEach { section ->
            assertTrue(section.title.isNotBlank(), "${section.name} has a blank title")
        }
    }

    @Test
    fun eachEntryTitleMatchesItsSectionObject() {
        assertEquals(HeroAboutSection.title, PortfolioSection.HeroAbout.title)
        assertEquals(SkillsSection.title, PortfolioSection.Skills.title)
        assertEquals(ExperienceSection.title, PortfolioSection.Experience.title)
        assertEquals(ProjectsSection.title, PortfolioSection.Projects.title)
        assertEquals(ContactSection.title, PortfolioSection.Contact.title)
    }
}
