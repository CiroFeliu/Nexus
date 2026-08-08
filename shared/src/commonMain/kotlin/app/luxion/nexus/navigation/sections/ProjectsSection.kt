package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.PortfolioSpacing

// Projects: a card showcase of featured work, each linking out to its repo/demo/product page.
object ProjectsSection {
    const val title = "Projects"
    private const val subtitle = "A selection of apps and platforms I've built or contributed to, " +
        "from personal work to production products used by thousands of people."

    private data class Project(
        val company: String,
        val name: String,
        val description: String,
        val techStack: List<String>,
        // Null when there's nothing public to link to (NDA, or no published repo yet) — the
        // card falls back to showing `unavailableNote` instead of a link button.
        val link: String? = null,
        val unavailableNote: String? = null,
    )

    // Real project history across three roles; sourced directly from the linked pages, not
    // invented. Most of Ciro's S2 Grupo work stays fully classified and is excluded entirely.
    private val projects = listOf(
        Project(
            company = "Personal",
            name = "Nexus",
            description = "This portfolio itself: a Kotlin Multiplatform + Compose Multiplatform app " +
                "sharing one UI across Web, Android, iOS, and Desktop.",
            techStack = listOf("Kotlin Multiplatform", "Compose Multiplatform"),
            link = "https://github.com/CiroFeliu/Nexus",
        ),
        Project(
            company = "Personal",
            name = "ShogunAi",
            description = "A desktop orchestrator that automates creating and managing isolated Git " +
                "worktrees for parallel task work, provisioning each one with the local secrets it " +
                "needs to build.",
            techStack = listOf("Kotlin Multiplatform", "Desktop"),
            link = "https://github.com/LuxionServer/ShogunAi",
        ),
        Project(
            company = "Fermax",
            name = "DuoxMe",
            description = "Fermax's video intercom companion app: answer door calls, view visitor " +
                "footage, and unlock doors remotely from anywhere.",
            techStack = listOf("Android", "iOS"),
            link = "https://fermax.com/duoxme/es",
        ),
        Project(
            company = "Fermax",
            name = "MeetMe",
            description = "Fermax's app for MEET video door entry systems: answer calls, unlock doors, " +
                "and share access with up to 5 users.",
            techStack = listOf("Android", "iOS"),
            link = "https://fermax.com/spain/meetme",
        ),
        Project(
            company = "S2 Grupo",
            name = "Mhia",
            description = "A cybersecurity and elderly home-care app, developed at S2 Grupo.",
            techStack = listOf("Android", "Cybersecurity"),
            link = "https://s2grupo.es/wp-content/uploads/2025/09/EINF-2024-S2GRUPO_consolidadoRD.pdf",
        ),
        Project(
            company = "S2 Grupo",
            name = "Internal Tools",
            description = "Contributed to an internal developer-tooling project at S2 Grupo. Details " +
                "are covered by an NDA and can't be shared publicly.",
            techStack = emptyList(),
            unavailableNote = "Confidential — under NDA",
        ),
        Project(
            company = "S2 Grupo",
            name = "Device Communications",
            description = "Contributed to a project building native communications between devices " +
                "at S2 Grupo. Details are covered by an NDA and can't be shared publicly.",
            techStack = emptyList(),
            unavailableNote = "Confidential — under NDA",
        ),
        Project(
            company = "Rudo",
            name = "Revieve",
            description = "AI-powered SaaS platform for the beauty industry: skin/hair analysis and AR " +
                "virtual try-on for brands and retailers.",
            techStack = listOf("AI", "SaaS"),
            link = "https://www.revieve.com/",
        ),
        Project(
            company = "Rudo",
            name = "Pinwins",
            description = "A social network for sports enthusiasts to build a profile, connect with " +
                "other athletes, and discover nearby events.",
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/pinwins/",
        ),
        Project(
            company = "Rudo",
            name = "The Fitzgerald",
            description = "Native app for a gourmet burger restaurant chain, letting customers find " +
                "the nearest location and order food.",
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/the-fitzgerald/",
        ),
        Project(
            company = "Rudo",
            name = "HCB Paciente",
            description = "Patient app for Hospital Clínica Benidorm: book appointments and access " +
                "medical results.",
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/hospital-clinica-benidorm/",
        ),
        Project(
            company = "Rudo",
            name = "Chatripp",
            description = "A social app that matches travelers heading to the same destination so " +
                "they can connect and chat.",
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/chatripp/",
        ),
        Project(
            company = "Rudo",
            name = "Hofmann",
            description = "App for personalizing and ordering printed photo products, like albums, " +
                "books, and calendars.",
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/hofmann/",
        ),
        Project(
            company = "Rudo",
            name = "Extra Promotions",
            description = "A project built during my time at Rudo, a mobile app agency specializing " +
                "in native Android and iOS development.",
            techStack = listOf("Android", "iOS"),
            link = "https://www.extrapromotions.com/",
        ),
        Project(
            company = "Personal",
            name = "Zenith",
            description = "A hardware side project outside my usual software work: built a Digital " +
                "FPV racing drone from scratch, from the frame build to the electronics and " +
                "flight-controller setup.",
            techStack = listOf("Hardware", "FPV"),
            unavailableNote = "Personal hardware build — no public repo",
        ),
    )

    @Composable
    fun Content() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.small))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 640.dp),
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                FlowRow(
                    modifier = Modifier.widthIn(max = 1120.dp),
                    horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.large, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
                ) {
                    projects.forEach { project -> ProjectCard(project) }
                }
            }
        }
    }

    @Composable
    private fun ProjectCard(project: Project) {
        ElevatedCard(
            modifier = Modifier.width(320.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(modifier = Modifier.padding(PortfolioSpacing.large)) {
                Text(
                    text = project.company.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(PortfolioSpacing.extraSmall))
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(PortfolioSpacing.small))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(PortfolioSpacing.medium))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraSmall)) {
                    project.techStack.forEach { tech -> TechTag(tech) }
                }
                Spacer(modifier = Modifier.height(PortfolioSpacing.large))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(PortfolioSpacing.medium))
                val link = project.link
                if (link != null) {
                    Button(
                        onClick = { openUrl(link) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(),
                    ) {
                        Text(text = "View project ↗")
                    }
                } else {
                    Text(
                        text = project.unavailableNote ?: "Not publicly available",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }

    @Composable
    private fun TechTag(text: String) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(
                    horizontal = PortfolioSpacing.small,
                    vertical = PortfolioSpacing.extraSmall,
                ),
            )
        }
    }
}
