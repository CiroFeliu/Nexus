package app.luxion.nexus.cv

import app.luxion.nexus.i18n.Language
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.entries
import app.luxion.nexus.navigation.sections.skillCategories
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CvContentProviderTest {

    @Test
    fun buildsHeroFieldsFromHeroAboutSection() {
        val hero = HeroAboutSection.content.getValue(Language.English)

        val content = buildCvContent(Language.English)

        assertEquals(hero.name, content.name)
        assertEquals(hero.role, content.role)
        assertEquals(hero.bio, content.bio)
    }

    @Test
    fun includesEverySkillCategoryExperienceProjectAndContactLink() {
        val content = buildCvContent(Language.English)

        assertEquals(skillCategories.size, content.skillCategories.size)
        assertEquals(entries.size, content.experience.size)
        assertEquals(ProjectsSection.projects.size, content.projects.size)
        assertEquals(ContactSection.links.size, content.contactLinks.size)
    }

    @Test
    fun translatesContentButKeepsUntranslatedFieldsStable() {
        val english = buildCvContent(Language.English)
        val spanish = buildCvContent(Language.Spanish)

        assertEquals(english.name, spanish.name)
        assertEquals(english.experience.map { it.company }, spanish.experience.map { it.company })
        assertNotEquals(english.role, spanish.role)
        assertNotEquals(english.bio, spanish.bio)
    }
}
