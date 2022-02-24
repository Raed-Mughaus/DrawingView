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
        val rasmWidth = rasmContext.rasmBitmap.width
        val rasmHeight = rasmContext.rasmBitmap.height
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                RectRenderer(
                    Rect(0, 0, rasmWidth, rasmHeight),
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.brushToolBitmaps.resultBitmap,
                ),
            ),
        )
    }

    private fun createLayerRenderer(rasmContext: RasmContext): Renderer {
        val rasmWidth = rasmContext.rasmBitmap.width
        val rasmHeight = rasmContext.rasmBitmap.height
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                RectRenderer(
                    Rect(0, 0, rasmWidth, rasmHeight),
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.rasmBitmap,
                ),
            ),
        )
    }

}
