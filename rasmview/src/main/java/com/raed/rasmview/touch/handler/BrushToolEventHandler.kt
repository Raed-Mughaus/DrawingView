package com.raed.rasmview.touch.handler

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import com.raed.rasmview.brushtool.BrushToolFactory
import com.raed.rasmview.brushtool.model.TouchEvent
import com.raed.rasmview.RasmContext
import com.raed.rasmview.actions.DrawBitmapAction
import kotlin.math.max
import kotlin.math.min

internal class BrushToolEventHandler(
    private val rasmContext: RasmContext,
): MotionEventHandler {

    private val brushTool = BrushToolFactory(rasmContext.brushToolBitmaps)
        .create(rasmContext.brushColor, rasmContext.brushConfig)
    private val touchEvent = TouchEvent()
    private var pointerId = 0
    private var ignoreEvents = false
    private var firstEventTime = 0L

    override fun handleFirstTouch(event: MotionEvent) {
        firstEventTime = System.currentTimeMillis()
        if (event.pointerCount > 1) {
            ignoreEvents = true
            return
        }
        val pointerIdx = 0
        pointerId = event.getPointerId(pointerIdx)

        touchEvent.set(event, pointerIdx)
        startDrawing(touchEvent)
    }

    override fun handleTouch(event: MotionEvent) {
        if (ignoreEvents) {
            return
        }
        val pointerIdx = event.findPointerIndex(pointerId)
        touchEvent.set(event, pointerIdx)
        if (event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            ignoreEvents = true
            cancelDrawing()
        } else {
            brushTool.continueDrawing(touchEvent)
        }
    }

    override fun handleLastTouch(event: MotionEvent) {
        if (ignoreEvents) {
            return
        }
        val pointerIdx = event.findPointerIndex(pointerId)
        touchEvent.set(event, pointerIdx)
        endDrawing(touchEvent)
    }

    override fun cancel() {
        if (!ignoreEvents) {
            cancelDrawing()
        }
    }

    private fun startDrawing(event: TouchEvent) {
        rasmContext.brushToolStatus.active = true
        rasmContext.brushToolBitmaps.resultBitmap.eraseColor(0)
        rasmContext.brushToolBitmaps.strokeBitmap.eraseColor(0)
        if (rasmContext.brushConfig.isEraser) {
            Canvas(rasmContext.brushToolBitmaps.strokeBitmap)
                .drawBitmap(
                    rasmContext.brushToolBitmaps.layerBitmap,
                    0f, 0f, null,
                )
        }
        brushTool.startDrawing(event)
    }

    private fun endDrawing(event: TouchEvent) {
        brushTool.endDrawing(event)
        rasmContext.brushToolStatus.active = false
        updateRasmState()
    }

    private fun cancelDrawing() {
        brushTool.cancel()
        rasmContext.brushToolStatus.active = false
        if (System.currentTimeMillis() - firstEventTime > 500) {
            updateRasmState()
        }
    }

    private fun updateRasmState() {
        val strokeBoundary = Rect(brushTool.strokeBoundary)
        val resultBitmap = rasmContext.brushToolBitmaps.resultBitmap
        strokeBoundary.left = max(strokeBoundary.left, 0)
        strokeBoundary.top = max(strokeBoundary.top, 0)
        strokeBoundary.right = min(strokeBoundary.right, resultBitmap.width)
        strokeBoundary.bottom = min(strokeBoundary.bottom, resultBitmap.height)
        if (strokeBoundary.width() > 0 && strokeBoundary.height() > 0) {
            rasmContext.state.update(
                DrawBitmapAction(
                    resultBitmap,
                    strokeBoundary,
                    strokeBoundary,
                ),
            )
        }
    }

}

private fun TouchEvent.set(event: MotionEvent, pointerIdx: Int) {
    x = event.getX(pointerIdx)
    y = event.getY(pointerIdx)
}
