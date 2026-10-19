package app.luxion.nexus.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LanguageSwitcher
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.PortfolioContentContainer
import app.luxion.nexus.theme.PortfolioLayout
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motion.reveal
import app.luxion.nexus.theme.motionSpec
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.ic_close
import nexus.shared.generated.resources.ic_menu
import org.jetbrains.compose.resources.painterResource

internal object PortfolioTopBarLabels {
    val openMenu = mapOf(Language.English to "Open menu", Language.Spanish to "Abrir menú")
    val closeMenu = mapOf(Language.English to "Close menu", Language.Spanish to "Cerrar menú")
}

@Composable
fun PortfolioTopBar(
    isScrolled: () -> Boolean,
    activeSection: () -> PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    onLanguageSelected: (Language) -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val scrolled = isScrolled()
    val containerColor by animateColorAsState(
        targetValue = if (scrolled) colors.surfaceContainer else colors.surfaceContainer.copy(alpha = 0f),
        animationSpec = motionSpec(PortfolioMotion.transition()),
        label = "topBarContainer",
    )
    val hairlineColor by animateColorAsState(
        targetValue = if (scrolled) colors.outlineVariant else colors.outlineVariant.copy(alpha = 0f),
        animationSpec = motionSpec(PortfolioMotion.transition()),
        label = "topBarHairline",
    )
    val isCompact = LocalWidthClass.current == WidthClass.Compact
    Column(modifier = modifier.fillMaxWidth().background(containerColor)) {
        PortfolioContentContainer(verticalPadding = PaddingValues()) {
            Row(
                modifier = Modifier.fillMaxWidth().height(PortfolioLayout.topBarHeight - 1.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Wordmark(onClick = { onSectionSelected(PortfolioSection.HeroAbout) })
                Spacer(modifier = Modifier.weight(1f))
                if (isCompact) {
                    MenuIconButton(
                        isOpen = false,
                        onClick = onMenuClick,
                    )
                } else {
                    SectionNavigation(activeSection = activeSection(), onSectionSelected = onSectionSelected)
                    Spacer(modifier = Modifier.width(PortfolioSpacing.medium))
                    LanguageSwitcher(onLanguageSelected = onLanguageSelected)
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(hairlineColor))
    }
}

@Composable
private fun Wordmark(onClick: () -> Unit) {
    val name = HeroAboutSection.content.getValue(LocalAppLanguage.current).name
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .interactive(interactionSource)
            .clip(PortfolioShapeRoles.interactive)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(vertical = PortfolioSpacing.small),
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun MenuIconButton(isOpen: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val language = LocalAppLanguage.current
    val interactionSource = remember { MutableInteractionSource() }
    IconButton(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = modifier.interactive(interactionSource),
    ) {
        Icon(
            painter = painterResource(if (isOpen) Res.drawable.ic_close else Res.drawable.ic_menu),
            contentDescription = if (isOpen) {
                PortfolioTopBarLabels.closeMenu.getValue(language)
            } else {
                PortfolioTopBarLabels.openMenu.getValue(language)
            },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CompactNavigationMenu(
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val language = LocalAppLanguage.current
    val closeFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { closeFocus.requestFocus() }
    BackHandler(onBack = onDismiss)
    Surface(
        modifier = modifier
            .fillMaxSize()
            .onPreviewKeyEvent { event ->
                if (event.key == Key.Escape && event.type == KeyEventType.KeyDown) {
                    onDismiss()
                    true
                } else {
                    false
                }
            }
            .focusGroup(),
        color = MaterialTheme.colorScheme.background,
    ) {
        PortfolioContentContainer(verticalPadding = PaddingValues()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(PortfolioLayout.topBarHeight - 1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Wordmark(onClick = { onSectionSelected(PortfolioSection.HeroAbout) })
                    Spacer(modifier = Modifier.weight(1f))
                    MenuIconButton(
                        isOpen = true,
                        onClick = onDismiss,
                        modifier = Modifier.focusRequester(closeFocus),
                    )
                }
                Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
                Column(verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.small)) {
                    PortfolioSection.entries.forEachIndexed { index, section ->
                        MenuLink(
                            label = section.label.getValue(language),
                            isActive = section == activeSection,
                            onClick = { onSectionSelected(section) },
                            modifier = Modifier.reveal(order = index, revealed = true),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(PortfolioSpacing.extraLarge))
                LanguageSwitcher(
                    onLanguageSelected = onLanguageSelected,
                    modifier = Modifier.reveal(order = PortfolioSection.entries.size, revealed = true),
                )
            }
        }
    }
}

@Composable
private fun MenuLink(label: String, isActive: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .interactive(interactionSource)
            .clip(PortfolioShapeRoles.interactive)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(vertical = PortfolioSpacing.small),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium,
            color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
    }
}
