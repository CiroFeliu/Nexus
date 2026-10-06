package app.luxion.nexus.navigation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioSpacing

object HeroAboutSection {
    private const val AVATAR_INITIALS = "CF"

    internal data class HeroAboutContent(val name: String, val role: String, val bio: String)

    internal val content = mapOf(
        Language.English to HeroAboutContent(
            name = "Ciro Feliu",
            role = "Senior Android Developer & Mobile Systems Architect",
            bio = "I design and build mobile systems that stay maintainable as they " +
                "grow, with a focus on Android architecture, Kotlin Multiplatform, and the tooling " +
                "that keeps a codebase easy to work in years after it ships. This portfolio itself " +
                "is a Compose Multiplatform build, shared across web, Android, desktop, and iOS.",
        ),
        Language.Spanish to HeroAboutContent(
            name = "Ciro Feliu",
            role = "Desarrollador Android Senior y Arquitecto de Sistemas Móviles",
            bio = "Diseño y construyo sistemas móviles que se mantienen fáciles de mantener a " +
                "medida que crecen, centrado en arquitectura Android, Kotlin Multiplatform y las " +
                "herramientas que hacen que un código siga siendo cómodo de tocar años después de " +
                "publicarse. Este mismo portfolio es una build de Compose Multiplatform, compartida " +
                "entre web, Android, escritorio e iOS.",
        ),
    )

    @Composable
    fun Content(photo: Painter? = null) {
        val content = content.getValue(LocalAppLanguage.current)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.medium),
        ) {
            Avatar(initials = AVATAR_INITIALS, name = content.name, photo = photo)
            Text(text = content.name, style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
            Text(text = content.role, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            Text(text = content.bio, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }

    @Composable
    private fun Avatar(initials: String, name: String, photo: Painter?) {
        if (photo != null) {
            Image(
                painter = photo,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp).clip(CircleShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = initials, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}
