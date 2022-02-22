package com.raed.rasmview.renderer

import android.graphics.Canvas
import android.graphics.Matrix

internal class TransformedRenderer(
    private val matrix: Matrix,
    private val renderer: Renderer,
): Renderer {

    override fun render(canvas: Canvas) {
        canvas.save()
        canvas.setMatrix(matrix)
        renderer.render(canvas)
        canvas.restore()
    }

}
