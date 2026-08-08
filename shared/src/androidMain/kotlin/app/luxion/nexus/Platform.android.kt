package app.luxion.nexus

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

// Holds the application context so `openUrl` can start an Activity outside a Composable
// scope. Set once from `MainActivity.onCreate` before the first frame is composed.
object AndroidAppContext {
    lateinit var context: Context
}

actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidAppContext.context.startActivity(intent)
}