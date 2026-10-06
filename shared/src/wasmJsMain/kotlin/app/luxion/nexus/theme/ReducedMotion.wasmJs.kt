package app.luxion.nexus.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import web.cssom.MediaQuery
import web.cssom.matchMedia

@Composable
actual fun rememberReducedMotionPreference(): Boolean =
    remember { matchMedia(MediaQuery("(prefers-reduced-motion: reduce)")).matches }
