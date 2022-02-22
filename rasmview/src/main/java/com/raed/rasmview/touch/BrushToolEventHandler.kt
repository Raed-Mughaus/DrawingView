package com.raed.rasmview.touch

import android.view.MotionEvent
import com.raed.brushtool.BrushToolFactory
import com.raed.brushtool.model.TouchEvent
import com.raed.rasmview.RasmContext

class BrushToolEventHandler(
    private val rasmContext: RasmContext,
): MotionEventHandler {

    private val brushTool = BrushToolFactory(rasmContext.brushToolBitmaps)
        .create(rasmContext.brushColor, rasmContext.brushConfig)
    private val touchEvent = TouchEvent()
    private var pointerId = 0
    private var ignoreEvents = false
    
    override fun handleFirstTouch(event: MotionEvent) {
        val pointerIdx = 0
        pointerId = event.getPointerId(pointerIdx)

        touchEvent.set(event, pointerIdx, 0)
        startDrawing(touchEvent)

        for (historyPosition in 1..event.historySize) {
            touchEvent.set(event, pointerIdx, historyPosition)
            brushTool.continueDrawing(touchEvent)
        }
    }

    override fun handleTouch(event: MotionEvent) {
        if (ignoreEvents) {
            return
        }
        val pointerIdx = event.findPointerIndex(pointerId)

        for (i in 0 until event.historySize) {
            touchEvent.set(event, pointerIdx, i)
            brushTool.continueDrawing(touchEvent)
        }

        touchEvent.set(event, pointerIdx, event.historySize)
        if (event.isActionUp(pointerIdx)) {
            ignoreEvents = true
            endDrawing(touchEvent)
        } else {
            brushTool.continueDrawing(touchEvent)
        }
    }

    override fun handleLastTouch(event: MotionEvent) {
        if (ignoreEvents) {
            return
        }
        val pointerIdx = event.findPointerIndex(pointerId)

        for (i in 0 until event.historySize) {
            touchEvent.set(event, pointerIdx, i)
            brushTool.continueDrawing(touchEvent)
        }

        touchEvent.set(event, pointerIdx, event.historySize)
        endDrawing(touchEvent)
    }

    private fun startDrawing(event: TouchEvent) {
        rasmContext.isBrushToolActive = true
        rasmContext.brushToolBitmaps.strokeBitmap.eraseColor(0)
        rasmContext.brushToolBitmaps.resultBitmap.eraseColor(0)
        brushTool.startDrawing(event)
    }

    private fun endDrawing(event: TouchEvent) {
        brushTool.endDrawing(event)
        rasmContext.isBrushToolActive = false
    }

    override fun cancel() {
        if (!ignoreEvents) {
            brushTool.cancel()
        }
    }

}

private fun TouchEvent.set(event: MotionEvent, pointerIdx: Int, historyPosition: Int) {
    require(historyPosition <= event.historySize)
    if (historyPosition == event.historySize) {
        x = event.getX(pointerIdx)
        y = event.getY(pointerIdx)
    } else {
        x = event.getHistoricalX(pointerIdx, historyPosition)
        y = event.getHistoricalY(pointerIdx, historyPosition)
    }
}


private fun MotionEvent.isActionUp(pointerIdx: Int): Boolean {
    return actionMasked == MotionEvent.ACTION_UP ||
            (actionMasked == MotionEvent.ACTION_POINTER_UP && pointerIdx == actionIndex)
}
