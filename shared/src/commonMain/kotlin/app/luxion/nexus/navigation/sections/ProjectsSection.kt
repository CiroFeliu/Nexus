package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.interactive

object ProjectsSection {
    private val title = mapOf(Language.English to "Projects", Language.Spanish to "Proyectos")
    private val subtitle = mapOf(
        Language.English to "A selection of apps and platforms I've built or contributed to, " +
            "from personal work to production products used by thousands of people.",
        Language.Spanish to "Una selección de apps y plataformas que he creado o en las que he " +
            "colaborado, desde proyectos personales hasta productos en producción usados por miles " +
            "de personas.",
    )
    private val viewProjectLabel = mapOf(Language.English to "View project", Language.Spanish to "Ver proyecto")
    private val defaultUnavailableNote = mapOf(
        Language.English to "Not publicly available",
        Language.Spanish to "No disponible públicamente",
    )
    private val featuredLabel = mapOf(Language.English to "Featured", Language.Spanish to "Destacados")
    private val allFilterLabel = mapOf(Language.English to "All", Language.Spanish to "Todos")

    internal data class ProjectContent(
        val name: String,
        val description: String,
        val unavailableNote: String? = null,
    )

    internal data class Project(
        val company: String,
        val content: Map<Language, ProjectContent>,
        val techStack: List<String>,
        val link: String? = null,
        val featured: Boolean = false,
    )

    private val excludedFilterTags = setOf("Android", "iOS")

    internal val projects = listOf(
        Project(
            company = "Personal",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Nexus",
                    description = "This portfolio itself: a Kotlin Multiplatform + Compose " +
                        "Multiplatform app sharing one UI across Web, Android, iOS, and Desktop.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Nexus",
                    description = "Este mismo portfolio: una app de Kotlin Multiplatform + Compose " +
                        "Multiplatform que comparte una única UI entre Web, Android, iOS y escritorio.",
                ),
            ),
            techStack = listOf("Kotlin Multiplatform", "Compose Multiplatform"),
            link = "https://github.com/CiroFeliu/Nexus",
            featured = true,
        ),
        Project(
            company = "Personal",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "ShogunAi",
                    description = "A desktop orchestrator that automates creating and managing " +
                        "isolated Git worktrees for parallel task work, provisioning each one with " +
                        "the local secrets it needs to build.",
                ),
                Language.Spanish to ProjectContent(
                    name = "ShogunAi",
                    description = "Un orquestador de escritorio que automatiza la creación y gestión " +
                        "de Git worktrees aislados para trabajar en tareas en paralelo, aprovisionando " +
                        "cada uno con los secretos locales que necesita para compilar.",
                ),
            ),
            techStack = listOf("Kotlin Multiplatform", "Desktop"),
            link = "https://github.com/LuxionServer/ShogunAi",
            featured = true,
        ),
        Project(
            company = "Fermax",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "DuoxMe",
                    description = "Fermax's video intercom companion app: answer door calls, view " +
                        "visitor footage, and unlock doors remotely from anywhere.",
                ),
                Language.Spanish to ProjectContent(
                    name = "DuoxMe",
                    description = "App complementaria de videoportero de Fermax: contesta llamadas " +
                        "de la puerta, revisa las grabaciones de visitantes y abre puertas en remoto " +
                        "desde cualquier lugar.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://fermax.com/duoxme/es",
            featured = true,
        ),
        Project(
            company = "Fermax",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "MeetMe",
                    description = "Fermax's app for MEET video door entry systems: answer calls, " +
                        "unlock doors, and share access with up to 5 users.",
                ),
                Language.Spanish to ProjectContent(
                    name = "MeetMe",
                    description = "App de Fermax para los sistemas de videoportero MEET: contesta " +
                        "llamadas, abre puertas y comparte el acceso con hasta 5 usuarios.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://fermax.com/spain/meetme",
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Mhia",
                    description = "A cybersecurity and elderly home-care app, developed at S2 Grupo.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Mhia",
                    description = "Una app de ciberseguridad y teleasistencia para personas mayores, " +
                        "desarrollada en S2 Grupo.",
                ),
            ),
            techStack = listOf("Android", "Cybersecurity"),
            link = "https://s2grupo.es/wp-content/uploads/2025/09/EINF-2024-S2GRUPO_consolidadoRD.pdf",
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Internal Tools",
                    description = "Contributed to an internal developer-tooling project at S2 Grupo. " +
                        "Details are covered by an NDA and can't be shared publicly.",
                    unavailableNote = "Confidential — under NDA",
                ),
                Language.Spanish to ProjectContent(
                    name = "Internal Tools",
                    description = "Colaboró en un proyecto interno de herramientas para desarrolladores " +
                        "en S2 Grupo. Los detalles están cubiertos por un acuerdo de confidencialidad " +
                        "y no pueden compartirse públicamente.",
                    unavailableNote = "Confidencial — bajo NDA",
                ),
            ),
            techStack = emptyList(),
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Device Communications",
                    description = "Contributed to a project building native communications between " +
                        "devices at S2 Grupo. Details are covered by an NDA and can't be shared publicly.",
                    unavailableNote = "Confidential — under NDA",
                ),
                Language.Spanish to ProjectContent(
                    name = "Device Communications",
                    description = "Colaboró en un proyecto de comunicaciones nativas entre dispositivos " +
                        "en S2 Grupo. Los detalles están cubiertos por un acuerdo de confidencialidad " +
                        "y no pueden compartirse públicamente.",
                    unavailableNote = "Confidencial — bajo NDA",
                ),
            ),
            techStack = emptyList(),
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Secure Development Awareness",
                    description = "Took part in S2 Grupo's secure-development awareness initiative, " +
                        "promoting secure coding practices across engineering teams.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Secure Development Awareness",
                    description = "Participó en la iniciativa de concienciación en desarrollo seguro " +
                        "de S2 Grupo, promoviendo prácticas de programación segura entre los equipos " +
                        "de ingeniería.",
                ),
            ),
            techStack = listOf("Secure Development", "Cybersecurity"),
            link = "https://s2grupo.es/en/soluciones/security-software/",
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Revieve",
                    description = "AI-powered SaaS platform for the beauty industry: skin/hair " +
                        "analysis and AR virtual try-on for brands and retailers.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Revieve",
                    description = "Plataforma SaaS con inteligencia artificial para la industria de la " +
                        "belleza: análisis de piel/cabello y pruebas virtuales en AR para marcas y " +
                        "comercios.",
                ),
            ),
            techStack = listOf("AI", "SaaS"),
            link = "https://www.revieve.com/",
            featured = true,
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Pinwins",
                    description = "A social network for sports enthusiasts to build a profile, " +
                        "connect with other athletes, and discover nearby events.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Pinwins",
                    description = "Una red social para aficionados al deporte donde crear un perfil, " +
                        "conectar con otros deportistas y descubrir eventos cercanos.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/pinwins/",
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "The Fitzgerald",
                    description = "Native app for a gourmet burger restaurant chain, letting customers " +
                        "find the nearest location and order food.",
                ),
                Language.Spanish to ProjectContent(
                    name = "The Fitzgerald",
                    description = "App nativa para una cadena de restaurantes de hamburguesas gourmet, " +
                        "que permite a los clientes encontrar el local más cercano y pedir comida.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/the-fitzgerald/",
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "HCB Paciente",
                    description = "Patient app for Hospital Clínica Benidorm: book appointments and " +
                        "access medical results.",
                ),
                Language.Spanish to ProjectContent(
                    name = "HCB Paciente",
                    description = "App de pacientes para el Hospital Clínica Benidorm: pedir citas y " +
                        "acceder a resultados médicos.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/hospital-clinica-benidorm/",
            featured = true,
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Chatripp",
                    description = "A social app that matches travelers heading to the same " +
                        "destination so they can connect and chat.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Chatripp",
                    description = "Una app social que conecta a viajeros que van al mismo destino " +
                        "para que puedan hablar entre ellos.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/chatripp/",
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Hofmann",
                    description = "App for personalizing and ordering printed photo products, like " +
                        "albums, books, and calendars.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Hofmann",
                    description = "App para personalizar y encargar productos fotográficos impresos, " +
                        "como álbumes, libros y calendarios.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://rudo.es/portfolio/hofmann/",
        ),
        Project(
            company = "Rudo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Extra Promotions",
                    description = "A project built during my time at Rudo, a mobile app agency " +
                        "specializing in native Android and iOS development.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Extra Promotions",
                    description = "Un proyecto desarrollado durante su etapa en Rudo, una agencia de " +
                        "apps móviles especializada en desarrollo nativo Android e iOS.",
                ),
            ),
            techStack = listOf("Android", "iOS"),
            link = "https://www.extrapromotions.com/",
        ),
        Project(
            company = "Personal",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Zenith",
                    description = "A hardware side project outside my usual software work: built a " +
                        "Digital FPV racing drone from scratch, from the frame build to the " +
                        "electronics and flight-controller setup.",
                    unavailableNote = "Personal hardware build — no public repo",
                ),
                Language.Spanish to ProjectContent(
                    name = "Zenith",
                    description = "Un proyecto personal de hardware fuera de su trabajo habitual de " +
                        "software: construyó un dron de carreras FPV digital desde cero, desde el " +
                        "montaje del chasis hasta la electrónica y la configuración del controlador " +
                        "de vuelo.",
                    unavailableNote = "Proyecto personal de hardware — sin repositorio público",
                ),
            ),
            techStack = listOf("Hardware", "FPV"),
            featured = true,
        ),
    )

    internal val filterOptions: List<String> =
        (projects.map { it.company } + projects.flatMap { it.techStack }.filterNot { it in excludedFilterTags })
            .distinct()

    internal fun matchesFilter(project: Project, filter: String?): Boolean =
        filter == null || project.company == filter || filter in project.techStack

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        var selectedFilter by remember { mutableStateOf<String?>(null) }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title.getValue(language),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.small))
            Text(
                text = subtitle.getValue(language),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 640.dp),
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
            Text(
                text = featuredLabel.getValue(language),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.medium))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                FlowRow(
                    modifier = Modifier.widthIn(max = 1120.dp),
                    horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.large, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
                ) {
                    projects.filter { it.featured }.forEach { project -> ProjectCard(project, language) }
                }
            }
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                FlowRow(
                    modifier = Modifier.widthIn(max = 1120.dp),
                    horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.small, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
                ) {
                    ProjectFilterChip(
                        label = allFilterLabel.getValue(language),
                        selected = selectedFilter == null,
                        onClick = { selectedFilter = null },
                    )
                    filterOptions.forEach { option ->
                        ProjectFilterChip(
                            label = option,
                            selected = selectedFilter == option,
                            onClick = { selectedFilter = if (selectedFilter == option) null else option },
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                FlowRow(
                    modifier = Modifier.widthIn(max = 1120.dp),
                    horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.large, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.large),
                ) {
                    projects.filter { matchesFilter(it, selectedFilter) }.forEach { project -> ProjectCard(project, language) }
                }
            }
        }
    }

    @Composable
    private fun ProjectCard(project: Project, language: Language) {
        val content = project.content.getValue(language)
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
                    text = content.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(PortfolioSpacing.small))
                Text(
                    text = content.description,
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
                    val interactionSource = remember { MutableInteractionSource() }
                    Button(
                        onClick = { openUrl(link) },
                        interactionSource = interactionSource,
                        modifier = Modifier.fillMaxWidth().interactive(interactionSource),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(),
                    ) {
                        Text(text = viewProjectLabel.getValue(language))
                        Spacer(modifier = Modifier.width(PortfolioSpacing.extraSmall))
                        ExternalLinkIcon(color = LocalContentColor.current)
                    }
                } else {
                    Text(
                        text = content.unavailableNote ?: defaultUnavailableNote.getValue(language),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }

    @Composable
    private fun ProjectFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        FilterChip(
            selected = selected,
            onClick = onClick,
            label = { Text(text = label) },
            interactionSource = interactionSource,
            modifier = Modifier.interactive(interactionSource),
        )
    }

    @Composable
    private fun ExternalLinkIcon(color: Color, modifier: Modifier = Modifier) {
        Canvas(modifier = modifier.size(14.dp)) {
            val strokeWidth = size.minDimension * 0.14f
            val inset = size.minDimension * 0.15f
            val tail = Offset(inset, size.height - inset)
            val head = Offset(size.width - inset, inset)
            drawLine(color = color, start = tail, end = head, strokeWidth = strokeWidth, cap = StrokeCap.Round)
            drawLine(
                color = color,
                start = head,
                end = head.copy(y = head.y + size.height * 0.4f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
            )
            drawLine(
                color = color,
                start = head,
                end = head.copy(x = head.x - size.width * 0.4f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
            )
        }
    }

    @Composable
    private fun TechTag(text: String) {
        Surface(
            shape = PortfolioShapeRoles.tag,
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
