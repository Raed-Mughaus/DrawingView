package com.raed.rasmview.renderer

import android.graphics.Rect
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
                RectRenderer(
                    Rect(0, 0, rasmContext.rasmWidth, rasmContext.rasmHeight),
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.brushToolBitmaps.resultBitmap,
                ),
            ),
        )
    }

    private fun createLayerRenderer(rasmContext: RasmContext): Renderer {
        val layerBitmap = rasmContext.brushToolBitmaps.layerBitmap
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                RectRenderer(
                    Rect(0, 0, rasmContext.rasmWidth, rasmContext.rasmHeight),
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    layerBitmap,
                ),
            ),
        )
    }

}
