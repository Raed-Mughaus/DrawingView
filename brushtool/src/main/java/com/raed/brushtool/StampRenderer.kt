package com.raed.brushtool

import android.graphics.*
import com.raed.brushtool.model.TouchEvent
import kotlin.math.ceil

internal class StampRenderer(
    destinationBitmap: Bitmap,
    private val stamp: Bitmap?,
    private var stampWidth: Int,
    private var stampHeight: Int,
    private val stampPaint: Paint,
    private val rotationRandomness: Float,
) {

    private val canvas = Canvas(destinationBitmap)
    private val boundaryRectF = RectF()

    init {
        require(stamp == null || stampWidth == stamp.width && stampHeight == stamp.height)
        require(stamp != null || stampWidth == stampHeight)
    }

    private val matrix = Matrix()

    fun render(event: TouchEvent, outRenderingBoundary: Rect?) {
        matrix.reset()
        matrix.postTranslate(event.x, event.y)
        matrix.postTranslate(-stampWidth / 2f, -stampHeight / 2f)
        matrix.postRotate(rotationRandomness * (randomFloat() * 360 - 180))

        canvas.setMatrix(matrix)
        if (stamp != null) {
            canvas.drawBitmap(stamp, 0f, 0f, stampPaint)
        } else {
            val r = stampWidth / 2f
            canvas.drawCircle(r, r, r, stampPaint)
        }

        if (outRenderingBoundary != null) {
            boundaryRectF.set(0f, 0f, stampWidth.toFloat(), stampHeight.toFloat())
            matrix.mapRect(boundaryRectF)
            outRenderingBoundary.set(boundaryRectF)
        }
    }



}

private fun randomFloat() = Math.random().toFloat()

fun Rect.set(rectF: RectF) = set(
    rectF.left.toInt(),
    rectF.top.toInt(),
    ceil(rectF.right).toInt(),
    ceil(rectF.bottom).toInt(),
)
