package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.openUrl
import app.luxion.nexus.theme.LocalPortfolioMonoTypography
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioLayout
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motion.hoverLift
import app.luxion.nexus.theme.motion.reveal
import app.luxion.nexus.theme.motion.spotlightBorder
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.ic_open_in_new
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

object ProjectsSection {
    private val title = mapOf(Language.English to "Projects", Language.Spanish to "Proyectos")
    private val subtitle = mapOf(
        Language.English to "A selection of apps and platforms I've built or contributed to, " +
            "from personal work to production products used by thousands of people.",
        Language.Spanish to "Una selección de apps y plataformas que he creado o en las que he " +
            "colaborado, desde proyectos personales hasta productos en producción usados por miles " +
            "de personas.",
    )
    private val openProjectLabel = mapOf(Language.English to "Open", Language.Spanish to "Abrir")
    private val defaultUnavailableNote = mapOf(
        Language.English to "Not publicly available",
        Language.Spanish to "No disponible públicamente",
    )
    internal val moreProjectsLabel = mapOf(Language.English to "More projects", Language.Spanish to "Más proyectos")
    private val allFilterLabel = mapOf(Language.English to "All", Language.Spanish to "Todos")
    internal val emptyFilterMessage = mapOf(
        Language.English to "No projects match this filter.",
        Language.Spanish to "Ningún proyecto coincide con este filtro.",
    )

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
        val image: DrawableResource? = null,
    )

    private val excludedFilterTags = setOf("Android", "iOS")

    internal val projects = listOf(
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
            company = "Personal",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Nexus",
                    description = "This portfolio itself, built with Kotlin Multiplatform and " +
                        "Compose Multiplatform.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Nexus",
                    description = "Este mismo portfolio, construido con Kotlin Multiplatform y " +
                        "Compose Multiplatform.",
                ),
            ),
            techStack = listOf("Kotlin Multiplatform", "Compose Multiplatform"),
            link = "https://github.com/CiroFeliu/Nexus",
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
                    description = "I contributed to an internal developer-tooling project at S2 Grupo. " +
                        "The details are covered by an NDA and can't be shared publicly.",
                    unavailableNote = "Confidential, under NDA",
                ),
                Language.Spanish to ProjectContent(
                    name = "Internal Tools",
                    description = "Colaboré en un proyecto interno de herramientas para desarrolladores " +
                        "en S2 Grupo. Los detalles están cubiertos por un acuerdo de confidencialidad " +
                        "y no puedo compartirlos públicamente.",
                    unavailableNote = "Confidencial, bajo NDA",
                ),
            ),
            techStack = emptyList(),
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Device Communications",
                    description = "I contributed to a project building native communications between " +
                        "devices at S2 Grupo. The details are covered by an NDA and can't be shared publicly.",
                    unavailableNote = "Confidential, under NDA",
                ),
                Language.Spanish to ProjectContent(
                    name = "Device Communications",
                    description = "Colaboré en un proyecto de comunicaciones nativas entre dispositivos " +
                        "en S2 Grupo. Los detalles están cubiertos por un acuerdo de confidencialidad " +
                        "y no puedo compartirlos públicamente.",
                    unavailableNote = "Confidencial, bajo NDA",
                ),
            ),
            techStack = emptyList(),
        ),
        Project(
            company = "S2 Grupo",
            content = mapOf(
                Language.English to ProjectContent(
                    name = "Secure Development Awareness",
                    description = "I took part in S2 Grupo's secure-development awareness initiative, " +
                        "promoting secure coding practices across engineering teams.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Secure Development Awareness",
                    description = "Participé en la iniciativa de concienciación en desarrollo seguro " +
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
                    description = "A project I built during my time at Rudo, a mobile app agency " +
                        "specializing in native Android and iOS development.",
                ),
                Language.Spanish to ProjectContent(
                    name = "Extra Promotions",
                    description = "Un proyecto que desarrollé durante mi etapa en Rudo, una agencia de " +
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
                    unavailableNote = "Personal hardware build, no public repo",
                ),
                Language.Spanish to ProjectContent(
                    name = "Zenith",
                    description = "Un proyecto personal de hardware fuera de mi trabajo habitual de " +
                        "software: construí un dron de carreras FPV digital desde cero, desde el " +
                        "montaje del chasis hasta la electrónica y la configuración del controlador " +
                        "de vuelo.",
                    unavailableNote = "Proyecto personal de hardware, sin repositorio público",
                ),
            ),
            techStack = listOf("Hardware", "FPV"),
            featured = true,
        ),
    )

    internal val featuredProjects: List<Project> get() = projects.filter { it.featured }

    internal val indexProjects: List<Project> get() = projects.filterNot { it.featured }

    internal val filterOptions: List<String> =
        (indexProjects.map { it.company } + indexProjects.flatMap { it.techStack }.filterNot { it in excludedFilterTags })
            .distinct()

    internal fun matchesFilter(project: Project, filter: String?): Boolean =
        filter == null || project.company == filter || filter in project.techStack

    internal fun filteredIndexProjects(filter: String?): List<Project> =
        indexProjects.filter { matchesFilter(it, filter) }

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        val widthClass = LocalWidthClass.current
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeadline(text = title.getValue(language))
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            Text(
                text = subtitle.getValue(language),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.widthIn(max = PortfolioLayout.readableTextWidth).reveal(order = 1),
            )
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            FeaturedBento(language = language, widthClass = widthClass, modifier = Modifier.reveal(order = 2))
            Spacer(modifier = Modifier.height(PortfolioSpacing.sectionGap))
            ProjectIndex(language = language, isCompact = widthClass == WidthClass.Compact)
        }
    }

    @Composable
    private fun FeaturedBento(language: Language, widthClass: WidthClass, modifier: Modifier = Modifier) {
        val featured = featuredProjects
        val rowHeight = when (widthClass) {
            WidthClass.Compact -> null
            WidthClass.Medium -> 280.dp
            WidthClass.Expanded, WidthClass.Large -> 236.dp
        }
        BentoGrid(
            cells = bentoCells(featured.size, widthClass),
            columns = bentoColumnCount(widthClass),
            rowHeight = rowHeight,
            spacing = PortfolioSpacing.medium,
            modifier = modifier.fillMaxWidth(),
        ) {
            featured.forEachIndexed { index, project ->
                FeaturedTile(project = project, tintIndex = index, fixedHeight = rowHeight != null, language = language)
            }
        }
    }

    @Composable
    private fun FeaturedTile(project: Project, tintIndex: Int, fixedHeight: Boolean, language: Language) {
        val content = project.content.getValue(language)
        val shape = PortfolioShapeRoles.container
        val colors = MaterialTheme.colorScheme
        val interactionSource = remember { MutableInteractionSource() }
        val link = project.link
        val interaction = if (link != null) {
            Modifier
                .interactive(interactionSource, focusShape = shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    role = Role.Button,
                    onClickLabel = "${openProjectLabel.getValue(language)} ${content.name}",
                    onClick = { openUrl(link) },
                )
        } else {
            Modifier.clip(shape).hoverable(interactionSource)
        }
        Column(
            modifier = Modifier
                .hoverLift(interactionSource)
                .spotlightBorder(shape = shape, color = colors.primary)
                .then(interaction)
                .background(colors.surfaceContainerLow)
                .border(1.dp, colors.outlineVariant, shape),
        ) {
            val visualModifier = if (fixedHeight) {
                Modifier.fillMaxWidth().weight(1f)
            } else {
                Modifier.fillMaxWidth().aspectRatio(16f / 10f)
            }
            ProjectVisual(project = project, content = content, tintIndex = tintIndex, modifier = visualModifier)
            TileDetails(project = project, content = content, language = language)
        }
    }

    @Composable
    private fun ColumnScope.TileDetails(project: Project, content: ProjectContent, language: Language) {
        val mono = LocalPortfolioMonoTypography.current
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.medium + PortfolioSpacing.extraSmall),
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
        ) {
            if (project.image != null) {
                Text(text = project.company, style = mono.label, color = MaterialTheme.colorScheme.primary)
                Text(text = content.name, style = MaterialTheme.typography.titleLarge)
            }
            Text(
                text = content.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            val meta = if (project.link == null) {
                content.unavailableNote ?: defaultUnavailableNote.getValue(language)
            } else {
                project.techStack.joinToString(", ")
            }
            if (meta.isNotEmpty()) {
                Text(text = meta, style = mono.tag, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
            }
        }
    }

    @Composable
    private fun ProjectVisual(project: Project, content: ProjectContent, tintIndex: Int, modifier: Modifier = Modifier) {
        val image = project.image
        if (image != null) {
            Image(
                painter = painterResource(image),
                contentDescription = content.name,
                contentScale = ContentScale.Crop,
                modifier = modifier,
            )
            return
        }
        val colors = MaterialTheme.colorScheme
        val (container, onContainer) = when (tintIndex % 3) {
            0 -> colors.primaryContainer to colors.onPrimaryContainer
            1 -> colors.tertiaryContainer to colors.onTertiaryContainer
            else -> colors.surfaceContainerHigh to colors.onSurface
        }
        Box(
            modifier = modifier
                .background(container)
                .placeholderTexture(color = onContainer.copy(alpha = 0.08f), hatched = tintIndex % 2 == 1)
                .padding(PortfolioSpacing.medium + PortfolioSpacing.extraSmall),
        ) {
            Text(
                text = project.company,
                style = LocalPortfolioMonoTypography.current.label,
                color = onContainer.copy(alpha = 0.75f),
                modifier = Modifier.align(Alignment.TopStart),
            )
            if (project.link != null) {
                Icon(
                    painter = painterResource(Res.drawable.ic_open_in_new),
                    contentDescription = null,
                    tint = onContainer,
                    modifier = Modifier.align(Alignment.TopEnd).size(20.dp),
                )
            }
            Text(
                text = content.name,
                style = MaterialTheme.typography.displaySmall,
                color = onContainer,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomStart),
            )
        }
    }

    private fun Modifier.placeholderTexture(color: Color, hatched: Boolean): Modifier = drawWithCache {
        val step = 24.dp.toPx()
        val stroke = 1.dp.toPx()
        onDrawBehind {
            if (hatched) {
                var start = -size.height
                while (start < size.width) {
                    drawLine(color, Offset(start, size.height), Offset(start + size.height, 0f), stroke)
                    start += step / 2
                }
            } else {
                var x = step
                while (x < size.width) {
                    drawLine(color, Offset(x, 0f), Offset(x, size.height), stroke)
                    x += step
                }
                var y = step
                while (y < size.height) {
                    drawLine(color, Offset(0f, y), Offset(size.width, y), stroke)
                    y += step
                }
            }
        }
    }

    @Composable
    private fun ProjectIndex(language: Language, isCompact: Boolean) {
        var selectedFilter by remember { mutableStateOf<String?>(null) }
        val visibleProjects = filteredIndexProjects(selectedFilter)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = moreProjectsLabel.getValue(language), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.small),
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
            Spacer(modifier = Modifier.height(PortfolioSpacing.large))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = PortfolioSpacing.small)) {
                if (visibleProjects.isEmpty()) {
                    Text(
                        text = emptyFilterMessage.getValue(language),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = PortfolioSpacing.large),
                    )
                } else {
                    visibleProjects.forEach { project -> IndexRow(project = project, language = language, isCompact = isCompact) }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }

    @Composable
    private fun IndexRow(project: Project, language: Language, isCompact: Boolean) {
        val content = project.content.getValue(language)
        val mono = LocalPortfolioMonoTypography.current
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = PortfolioSpacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Column(modifier = Modifier.weight(1.2f)) {
                Text(text = content.name, style = MaterialTheme.typography.titleMedium)
                if (isCompact) {
                    Text(text = project.company, style = mono.tag, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (!isCompact) {
                Text(
                    text = project.company,
                    style = mono.tag,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(0.6f),
                )
                Text(
                    text = project.techStack.joinToString(", "),
                    style = mono.tag,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                )
            }
            val link = project.link
            if (link != null) {
                val interactionSource = remember { MutableInteractionSource() }
                IconButton(
                    onClick = { openUrl(link) },
                    interactionSource = interactionSource,
                    modifier = Modifier.interactive(interactionSource),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_open_in_new),
                        contentDescription = "${openProjectLabel.getValue(language)} ${content.name}",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
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
}
