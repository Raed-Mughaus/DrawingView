package com.raed.rasmview.renderer

import android.graphics.Bitmap
import android.graphics.Canvas

internal class BitmapRenderer(
    private val bitmap: Bitmap,
): Renderer {

    override fun render(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

}