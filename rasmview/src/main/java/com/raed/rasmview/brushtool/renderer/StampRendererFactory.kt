package com.raed.rasmview.brushtool.renderer

import android.graphics.*
import com.raed.rasmview.brushtool.model.BrushConfig
import com.raed.rasmview.brushtool.model.BrushStamp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

internal class StampRendererFactory(
    private val destinationBitmap: Bitmap,
) {

    fun create(brushColor: Int, brushConfig: BrushConfig): StampRenderer {
        val color = brushColor.withAlpha(brushConfig.flow)
        val stampPaint = brushConfig.createStampPaint(color)
        return when (val brushStamp = brushConfig.stamp) {
            is BrushStamp.BitmapStamp -> {
                val stamp = brushStamp.getResizedStamp(brushConfig.sizeInPixels)
                BitmapStampRenderer(
                    destinationBitmap,
                    stamp,
                    stampPaint,
                    brushConfig.rotation,
                    brushConfig.rotationRandomness,
                )
            }
            BrushStamp.CircleStamp -> {
                CircleStampRenderer(
                    destinationBitmap,
                    brushConfig.sizeInPixels,
                    stampPaint,
                    brushConfig.rotation,
                    brushConfig.rotationRandomness,
                )
            }
            BrushStamp.OvalStamp -> {
                OvalStampRenderer(
                    destinationBitmap,
                    brushConfig.sizeInPixels,
                    stampPaint,
                    brushConfig.rotation,
                    brushConfig.rotationRandomness,
                )
            }
        }
    }

}

private fun BrushConfig.createStampPaint(color: Int): Paint {
    return Paint(Paint.DITHER_FLAG or Paint.ANTI_ALIAS_FLAG).apply {
        when {
            isEraser -> {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
                alpha = Color.alpha(color)
            }
            stamp is BrushStamp.BitmapStamp -> {
                colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
            else -> {
                this.color = color
            }
        }
    }
}

private fun BrushStamp.BitmapStamp.getResizedStamp(size: Int): Bitmap {
    val scale = size.toFloat() / min(stamp.width, stamp.height)
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

private fun Int.withAlpha(alpha: Float) = withAlpha((255 * alpha).roundToInt())

private fun Int.withAlpha(alpha: Int): Int {
    return Color.argb(
        alpha,
        Color.red(this),
        Color.green(this),
        Color.blue(this),
    )
}

private fun lerp(a: Int, b: Int, t: Float) = (a + t * (b - a)).roundToInt()
