package app.luxion.nexus.navigation.sections

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.LineBreak
import app.luxion.nexus.theme.LocalWidthClass
import app.luxion.nexus.theme.WidthClass
import app.luxion.nexus.theme.motion.reveal

@Composable
internal fun SectionHeadline(text: String, modifier: Modifier = Modifier) {
    val style = when (LocalWidthClass.current) {
        WidthClass.Compact -> MaterialTheme.typography.displaySmall
        WidthClass.Medium -> MaterialTheme.typography.displayMedium
        WidthClass.Expanded, WidthClass.Large -> MaterialTheme.typography.displayLarge
    }
    Text(
        text = text,
        style = style.copy(lineBreak = LineBreak.Heading),
        modifier = modifier.reveal(order = 0).semantics { heading() },
    )
}
