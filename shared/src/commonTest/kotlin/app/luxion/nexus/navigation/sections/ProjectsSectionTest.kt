package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ProjectsSectionTest {

    private val titleEnglish = "Projects"
    private val featuredLabelEnglish = "Featured"

    private val featuredProjectNames = ProjectsSection.projects
        .filter { it.featured }
        .map { it.content.getValue(Language.English).name }
    private val nonFeaturedProjectNames = ProjectsSection.projects
        .filterNot { it.featured }
        .map { it.content.getValue(Language.English).name }

    @Test
    fun rendersTitleFeaturedSectionAndEveryProjectCard() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ProjectsSection.Content()
            }
        }

        onNodeWithText(titleEnglish).assertExists()
        onNodeWithText(featuredLabelEnglish).assertExists()
        featuredProjectNames.forEach { name ->
            onAllNodesWithText(name).assertCountEquals(2)
        }
        nonFeaturedProjectNames.forEach { name ->
            onNodeWithText(name).assertExists()
        }
    }

    @Test
    fun featuredSectionContainsExactlyTheCuratedProjects() {
        val expectedFeatured = setOf("Nexus", "ShogunAi", "DuoxMe", "Revieve", "HCB Paciente", "Zenith")
        assertEquals(expectedFeatured, featuredProjectNames.toSet())
    }

    @Test
    fun filterOptionsExcludeOverlyCommonTags() {
        assertFalse("Android" in ProjectsSection.filterOptions)
        assertFalse("iOS" in ProjectsSection.filterOptions)
    }

    @Test
    fun filterOptionsIncludeCompaniesAndDifferentiatingTags() {
        assertTrue("Fermax" in ProjectsSection.filterOptions)
        assertTrue("Rudo" in ProjectsSection.filterOptions)
        assertTrue("AI" in ProjectsSection.filterOptions)
        assertTrue("Cybersecurity" in ProjectsSection.filterOptions)
    }

    @Test
    fun matchesFilterWithNullSelectionMatchesEveryProject() {
        ProjectsSection.projects.forEach { project ->
            assertTrue(ProjectsSection.matchesFilter(project, null))
        }
    }

    @Test
    fun matchesFilterByCompany() {
        val fermaxProject = ProjectsSection.projects.first { it.company == "Fermax" }
        val rudoProject = ProjectsSection.projects.first { it.company == "Rudo" }
        assertTrue(ProjectsSection.matchesFilter(fermaxProject, "Fermax"))
        assertFalse(ProjectsSection.matchesFilter(rudoProject, "Fermax"))
    }

    @Test
    fun matchesFilterByTag() {
        val aiProject = ProjectsSection.projects.first { "AI" in it.techStack }
        val nonAiProject = ProjectsSection.projects.first { "AI" !in it.techStack }
        assertTrue(ProjectsSection.matchesFilter(aiProject, "AI"))
        assertFalse(ProjectsSection.matchesFilter(nonAiProject, "AI"))
    }
}
