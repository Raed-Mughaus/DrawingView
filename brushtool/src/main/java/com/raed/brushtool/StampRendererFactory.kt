package com.raed.brushtool

import android.graphics.*
import com.raed.brushtool.model.BrushConfig
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

internal class StampRendererFactory(
    private val destinationBitmap: Bitmap,
) {

    fun create(brushColor: Int, brushConfig: BrushConfig): StampRenderer {
        val stamp = brushConfig.getResizedStamp()
        val color = brushColor.withAlpha(brushConfig.flow)
        return StampRenderer(
            destinationBitmap,
            stamp,
            stamp?.width ?: brushConfig.size,
            stamp?.height ?: brushConfig.size,
            brushConfig.createStampPaint(color),
            brushConfig.rotationRandomness,
        )
    }

}

private fun BrushConfig.createStampPaint(color: Int): Paint {
    return Paint(Paint.DITHER_FLAG or Paint.ANTI_ALIAS_FLAG).apply {
        when {
            isEraser -> {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
                alpha = Color.alpha(color)
            }
            stamp != null -> {
                colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
            else -> {
                this.color = color
            }
        }
    }
}

private fun BrushConfig.getResizedStamp(): Bitmap? {
    val stamp = stamp ?: return null
    val size = findStampSize().toFloat()
    val scale = size / min(stamp.width, stamp.height)
    val width = (scale * stamp.width).roundToInt()
    val height = (scale * stamp.height).roundToInt()
    return if (width == stamp.width && height == stamp.height) {
        stamp
    } else {
        Bitmap.createScaledBitmap(
            stamp,
            width,
            height,
            true,
        )
    }
}

private fun BrushConfig.findStampSize(): Int {
    val minSize = 1
    val maxSize = min(stamp!!.width, stamp!!.height)
    return min(maxSize, max(minSize, size))
}

private fun Int.withAlpha(alpha: Float) = withAlpha((255 * alpha).roundToInt())

private fun Int.withAlpha(alpha: Int): Int {
    return Color.argb(
        alpha,
        Color.red(this),
        Color.green(this),
        Color.blue(this),
    )
}
