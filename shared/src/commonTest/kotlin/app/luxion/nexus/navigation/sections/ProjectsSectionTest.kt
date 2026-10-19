package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ProjectsSectionTest {

    private val featuredProjectNames = ProjectsSection.featuredProjects.map { it.content.getValue(Language.English).name }
    private val indexProjectNames = ProjectsSection.indexProjects.map { it.content.getValue(Language.English).name }

    @Test
    fun rendersEveryProjectExactlyOnce() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ProjectsSection.Content()
            }
        }

        onNodeWithText("Projects").assertExists()
        onNodeWithText("More projects").assertExists()
        (featuredProjectNames + indexProjectNames).forEach { name ->
            onAllNodesWithText(name).assertCountEquals(1)
        }
    }

    @Test
    fun selectingAFilterNarrowsOnlyTheIndexList() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ProjectsSection.Content()
            }
        }

        onNode(hasTextExactly("Fermax") and hasClickAction()).performSemanticsAction(SemanticsActions.OnClick)

        onNodeWithText("MeetMe").assertExists()
        onNodeWithText("Pinwins").assertDoesNotExist()
        featuredProjectNames.forEach { name -> onNodeWithText(name).assertExists() }
    }

    @Test
    fun featuredSectionContainsExactlyTheCuratedProjects() {
        val expectedFeatured = setOf("Nexus", "ShogunAi", "DuoxMe", "Revieve", "HCB Paciente", "Zenith")
        assertEquals(expectedFeatured, featuredProjectNames.toSet())
    }

    @Test
    fun indexListContainsNoFeaturedProject() {
        assertTrue(ProjectsSection.indexProjects.none { it.featured })
        assertEquals(ProjectsSection.projects.size, featuredProjectNames.size + indexProjectNames.size)
    }

    @Test
    fun noProjectHasAnImageYet() {
        assertTrue(ProjectsSection.projects.all { it.image == null })
    }

    @Test
    fun filterOptionsExcludeOverlyCommonTags() {
        assertFalse("Android" in ProjectsSection.filterOptions)
        assertFalse("iOS" in ProjectsSection.filterOptions)
    }

    @Test
    fun filterOptionsAreDerivedFromTheIndexList() {
        assertTrue("Fermax" in ProjectsSection.filterOptions)
        assertTrue("Rudo" in ProjectsSection.filterOptions)
        assertTrue("Cybersecurity" in ProjectsSection.filterOptions)
        assertFalse("Personal" in ProjectsSection.filterOptions)
        assertFalse("AI" in ProjectsSection.filterOptions)
    }

    @Test
    fun everyFilterOptionMatchesAtLeastOneIndexProject() {
        ProjectsSection.filterOptions.forEach { option ->
            assertTrue(ProjectsSection.filteredIndexProjects(option).isNotEmpty(), "$option matches nothing")
        }
    }

    @Test
    fun filterThatMatchesNothingYieldsEmptyList() {
        assertTrue(ProjectsSection.filteredIndexProjects("Hardware").isEmpty())
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
        val cyberProject = ProjectsSection.projects.first { "Cybersecurity" in it.techStack }
        val otherProject = ProjectsSection.projects.first { "Cybersecurity" !in it.techStack }
        assertTrue(ProjectsSection.matchesFilter(cyberProject, "Cybersecurity"))
        assertFalse(ProjectsSection.matchesFilter(otherProject, "Cybersecurity"))
    }
}
