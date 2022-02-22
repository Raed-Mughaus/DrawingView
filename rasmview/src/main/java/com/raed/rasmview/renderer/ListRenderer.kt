package com.raed.rasmview.renderer

import android.graphics.Canvas

internal class ListRenderer(
    private var renderers: List<Renderer>,
): Renderer {

    constructor(vararg renderers: Renderer): this(renderers.toList())

    override fun render(canvas: Canvas) {
        for (renderer in renderers) {
            renderer.render(canvas)
        }
    }

}
