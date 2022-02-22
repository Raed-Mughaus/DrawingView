package com.raed.rasmview

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Matrix
import com.raed.brushtool.BrushToolBitmaps
import com.raed.rasmview.state.DrawingState
import com.raed.brushtool.data.Brush
import com.raed.brushtool.data.BrushesRepository

class RasmContext(
    drawing: Bitmap,
) {

    constructor(
        drawingWidth: Int,
        drawingHeight: Int,
    ): this(Bitmap.createBitmap(drawingWidth, drawingHeight, ARGB_8888))

    internal val brushToolBitmaps = BrushToolBitmaps.createFromDrawing(drawing)
    internal var isBrushToolActive = false
    val drawing get() = brushToolBitmaps.layerBitmap
    val state = DrawingState()
    val transformation = Matrix()
    var brushConfig = BrushesRepository().get(Brush.Pen)
    var brushColor = 0xff2187bb.toInt()
    var backgroundColor = -1

}
