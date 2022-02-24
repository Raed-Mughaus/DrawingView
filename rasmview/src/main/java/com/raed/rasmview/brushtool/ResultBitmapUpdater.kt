package com.raed.rasmview.brushtool

import android.graphics.*
import com.raed.rasmview.brushtool.model.BrushConfig
import kotlin.math.roundToInt

private var SRC_PAINT = Paint().apply {
    xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
}

internal class ResultBitmapUpdater(
    brushToolBitmaps: BrushToolBitmaps,
    private val brushConfig: BrushConfig,
) {

    private val layerBitmap = brushToolBitmaps.layerBitmap
    private val strokeBitmap = brushToolBitmaps.strokeBitmap
    private val resultCanvas = Canvas(brushToolBitmaps.resultBitmap)

    private val strokePaint = createStrokePaint()

    fun update() = update(Rect(0, 0, resultCanvas.width, resultCanvas.height))

    fun update(rect: Rect) {
        if (brushConfig.isEraser) {
            resultCanvas.drawBitmap(strokeBitmap, rect, rect, SRC_PAINT)
        } else {
            resultCanvas.drawBitmap(layerBitmap, rect, rect, SRC_PAINT)
            resultCanvas.drawBitmap(strokeBitmap, rect, rect, strokePaint)
        }
    }

    private fun createStrokePaint(): Paint? {
        return if (brushConfig.opacity != 0f) {
            Paint().apply {
                alpha = (brushConfig.opacity * 255).roundToInt()
            }
        } else {
            null
        }
    }

}
