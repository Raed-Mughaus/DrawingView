package com.raed.rasmview.brushtool.renderer

import android.graphics.*
import com.raed.rasmview.brushtool.model.TouchEvent
import kotlin.math.ceil

internal abstract class StampRenderer(
    destinationBitmap: Bitmap,
    private val rotation: Int,
    private val rotationRandomness: Float,
) {

    abstract val stampWidth: Int
    abstract val stampHeight: Int

    private val canvas = Canvas(destinationBitmap)
    private val boundaryRectF = RectF()

    private val matrix = Matrix()

    fun render(event: TouchEvent, outRenderingBoundary: Rect?) {
        matrix.reset()
        matrix.setTranslate(event.x, event.y)
        matrix.preRotate(rotation + rotationRandomness * (randomFloat() * 360 - 180))
        matrix.preTranslate(-stampWidth / 2f, -stampHeight / 2f)

        canvas.setMatrix(matrix)
        drawStamp(canvas)

        if (outRenderingBoundary != null) {
            boundaryRectF.set(0f, 0f, stampWidth.toFloat(), stampHeight.toFloat())
            matrix.mapRect(boundaryRectF)
            outRenderingBoundary.set(boundaryRectF)
        }
    }

    abstract fun drawStamp(canvas: Canvas)

}

private fun randomFloat() = Math.random().toFloat()

fun Rect.set(rectF: RectF) = set(
    rectF.left.toInt(),
    rectF.top.toInt(),
    ceil(rectF.right).toInt(),
    ceil(rectF.bottom).toInt(),
)
