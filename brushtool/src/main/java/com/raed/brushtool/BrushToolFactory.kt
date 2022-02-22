package com.raed.brushtool

import com.raed.brushtool.model.BrushConfig

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
        )
    }

}