package com.raed.rasmview.brushtool

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap

class BrushToolBitmaps(
    val layerBitmap: Bitmap,
    val strokeBitmap: Bitmap,
    val resultBitmap: Bitmap,
) {

    companion object {

        fun createFromDrawing(drawing: Bitmap): BrushToolBitmaps {
            val layerBitmap = if (drawing.config == ARGB_8888 && drawing.isMutable) {
                drawing
            } else {
                drawing.copy(ARGB_8888, true)
            }
            return BrushToolBitmaps(
                layerBitmap,
                createBitmap(layerBitmap.width, layerBitmap.height, ARGB_8888),
                createBitmap(layerBitmap.width, layerBitmap.height, ARGB_8888),
            )
        }

    }

}
