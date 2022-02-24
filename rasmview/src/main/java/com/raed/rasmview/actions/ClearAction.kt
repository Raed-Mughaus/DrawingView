package com.raed.rasmview.actions

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Rect
import com.raed.rasmview.RasmContext

internal class ClearAction: Action {

    override fun perform(context: RasmContext) {
        context.rasmBitmap.eraseColor(0)
    }

    override fun getOppositeAction(context: RasmContext): Action {
        val drawingCopy = context.rasmBitmap.copy(Bitmap.Config.ARGB_8888, false)
        val rect = Rect(0, 0, drawingCopy.width, drawingCopy.height)
        return DrawBitmapAction(drawingCopy, rect, rect)
    }

}