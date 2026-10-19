package app.luxion.nexus.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.luminance
import kotlin.random.Random

private const val GRAIN_TILE_SIZE = 128
private const val GRAIN_LEVELS = 6
private const val GRAIN_SEED = 7
private const val GRAIN_ALPHA_LIGHT = 0.04f
private const val GRAIN_ALPHA_DARK = 0.06f

@Composable
fun GrainOverlay(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val grainColor = colors.onBackground
    val alpha = if (colors.background.luminance() < 0.5f) GRAIN_ALPHA_DARK else GRAIN_ALPHA_LIGHT
    val tile = remember(grainColor) { grainTile(grainColor) }
    Box(
        modifier = modifier.drawWithCache {
            val brush = ShaderBrush(ImageShader(tile, TileMode.Repeated, TileMode.Repeated))
            onDrawBehind { drawRect(brush = brush, alpha = alpha) }
        },
    )
}

private fun grainTile(color: Color): ImageBitmap {
    val bitmap = ImageBitmap(GRAIN_TILE_SIZE, GRAIN_TILE_SIZE)
    val canvas = Canvas(bitmap)
    val random = Random(GRAIN_SEED)
    val buckets = List(GRAIN_LEVELS) { mutableListOf<Offset>() }
    for (y in 0 until GRAIN_TILE_SIZE) {
        for (x in 0 until GRAIN_TILE_SIZE) {
            buckets[random.nextInt(GRAIN_LEVELS)].add(Offset(x + 0.5f, y + 0.5f))
        }
    }
    buckets.forEachIndexed { level, points ->
        val paint = Paint().apply {
            this.color = color.copy(alpha = level.toFloat() / (GRAIN_LEVELS - 1))
            strokeWidth = 1f
            strokeCap = StrokeCap.Square
        }
        canvas.drawPoints(PointMode.Points, points, paint)
    }
    return bitmap
}
