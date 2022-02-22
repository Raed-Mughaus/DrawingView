package com.raed.rasmview.renderer

import android.graphics.Canvas

internal class ColorRender(
    private var color: Int,
): Renderer {

    override fun render(canvas: Canvas) {
        canvas.drawColor(color)
    }

}