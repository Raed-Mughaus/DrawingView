package com.raed.rasmview.brushtool

import com.raed.rasmview.brushtool.model.BrushConfig
import com.raed.rasmview.brushtool.renderer.StampRendererFactory

class BrushToolFactory(
    private val brushToolBitmaps: BrushToolBitmaps,
) {

    private var stampRendererFactory = StampRendererFactory(brushToolBitmaps.strokeBitmap)

    fun create(brushColor: Int, brushConfig: BrushConfig): BrushTool {
        return BrushTool(
            stampRendererFactory.create(brushColor, brushConfig),
            ResultBitmapUpdater(
                brushToolBitmaps,
                brushConfig,
            ),
            brushConfig.step,
        )
    }

}