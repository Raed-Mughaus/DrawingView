package com.raed.rasmview

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Matrix
import com.raed.rasmview.actions.ChangeBackgroundAction
import com.raed.rasmview.actions.ClearAction
import com.raed.rasmview.brushtool.BrushToolBitmaps
import com.raed.rasmview.brushtool.model.BrushConfig
import com.raed.rasmview.state.RasmState

class RasmContext {

    private var nullableBrushToolBitmaps: BrushToolBitmaps? = null
        set(value) {
            require(field == null)
            require(value != null)
            field = value
        }
    internal val brushToolBitmaps get() = nullableBrushToolBitmaps!!
    internal var isBrushToolActive = false
    val isInitialized get() = nullableBrushToolBitmaps != null
    val rasmBitmap get() = brushToolBitmaps.layerBitmap
    val state = RasmState(this)
    val transformation = Matrix()
    var brushConfig = BrushConfig()
    var brushColor = 0xff2187bb.toInt()
    internal var backgroundColor = -1

    fun init(
        drawingWidth: Int,
        drawingHeight: Int,
    ) = init(Bitmap.createBitmap(drawingWidth, drawingHeight, ARGB_8888))

    fun init(drawing: Bitmap) {
        nullableBrushToolBitmaps = BrushToolBitmaps.createFromDrawing(drawing)
    }

    fun clear() {
        state.update(
            ClearAction(),
        )
    }

    fun setBackgroundColor(color: Int) {
        state.update(
            ChangeBackgroundAction(color),
        )
    }

    fun resetZoom() {
        TODO()
    }

}
