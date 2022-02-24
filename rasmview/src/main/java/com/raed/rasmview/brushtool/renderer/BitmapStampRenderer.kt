package com.raed.rasmview.brushtool.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

internal class BitmapStampRenderer(
    destinationBitmap: Bitmap,
    private val stamp: Bitmap,
    private val stampPaint: Paint,
    rotation: Int,
    rotationRandomness: Float,
): StampRenderer(destinationBitmap, rotation, rotationRandomness) {

    override val stampWidth get() = stamp.width
    override val stampHeight get() = stamp.height

    override fun drawStamp(canvas: Canvas) {
        canvas.drawBitmap(stamp, 0f, 0f, stampPaint)
    }

}
