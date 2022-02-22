package com.raed.brushtool

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas

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
                drawing.copyIntoMutableARGB8888()
            }
            return BrushToolBitmaps(
                layerBitmap,
                createBitmap(layerBitmap.width, layerBitmap.height, ARGB_8888),
                createBitmap(layerBitmap.width, layerBitmap.height, ARGB_8888),
            )
        }

    }

}

private fun Bitmap.copyIntoMutableARGB8888(): Bitmap {
    require(!isMutable || config != Bitmap.Config.ARGB_8888)
    val copyBitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)
    Canvas(copyBitmap).drawBitmap(this, 0f, 0f, null)
    return copyBitmap
}
