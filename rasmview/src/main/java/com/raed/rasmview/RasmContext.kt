package com.raed.rasmview

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
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
    var rotationEnabled = false
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

    internal fun resetTransformation(containerWidth: Int, containerHeight: Int) {
        transformation.setRectToRect(
            RectF(0F, 0F, rasmBitmap.width.toFloat(), rasmBitmap.height.toFloat()),
            RectF(0f, 0f, containerWidth.toFloat(), containerHeight.toFloat()),
            Matrix.ScaleToFit.CENTER,
        )
    }

}
