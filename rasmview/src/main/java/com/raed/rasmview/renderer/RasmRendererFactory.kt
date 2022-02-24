package com.raed.rasmview.renderer

import com.raed.rasmview.RasmContext

internal class RasmRendererFactory {

    fun create(rasmContext: RasmContext): Renderer {
        return if (rasmContext.isBrushToolActive){
            createBrushToolResultRenderer(rasmContext)
        } else {
            createLayerRenderer(rasmContext)
        }
    }

    private fun createBrushToolResultRenderer(rasmContext: RasmContext): Renderer {
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                ColorRender(
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.brushToolBitmaps.resultBitmap,
                ),
            ),
        )
    }

    private fun createLayerRenderer(rasmContext: RasmContext): Renderer {
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                ColorRender(
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.rasmBitmap,
                ),
            ),
        )
    }

}
