package com.raed.rasmview.renderer

import android.graphics.Rect
import com.raed.rasmview.RasmContext

internal class RasmRendererFactory {

    fun createOnscreenRenderer(rasmContext: RasmContext): Renderer? {
        if (!rasmContext.hasRasm) {
            return null
        }
        return if (rasmContext.brushToolStatus.active){
            createBrushToolResultRenderer(rasmContext)
        } else {
            createLayerRenderer(rasmContext)
        }
    }

    fun createOffscreenRenderer(rasmContext: RasmContext): Renderer {
        return ListRenderer(
            RectRenderer(
                Rect(0, 0, rasmContext.rasmWidth, rasmContext.rasmHeight),
                rasmContext.backgroundColor,
            ),
            BitmapRenderer(
                rasmContext.brushToolBitmaps.layerBitmap,
            ),
        )
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
        return TransformedRenderer(
            rasmContext.transformation,
            ListRenderer(
                RectRenderer(
                    Rect(0, 0, rasmContext.rasmWidth, rasmContext.rasmHeight),
                    rasmContext.backgroundColor,
                ),
                BitmapRenderer(
                    rasmContext.brushToolBitmaps.layerBitmap,
                ),
            ),
        )
    }

}
