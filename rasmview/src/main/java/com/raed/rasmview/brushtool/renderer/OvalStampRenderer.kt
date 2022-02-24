package com.raed.rasmview.brushtool.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

internal class OvalStampRenderer(
    destinationBitmap: Bitmap,
    private val stampSize: Int,
    private val stampPaint: Paint,
    rotation: Int,
    rotationRandomness: Float,
): StampRenderer(destinationBitmap, rotation, rotationRandomness) {

    override val stampWidth get() = stampSize
    override val stampHeight get() = stampSize

    override fun drawStamp(canvas: Canvas) {
        canvas.drawOval(0f, 0f, stampSize.toFloat(), stampSize / 4f, stampPaint)
    }

}