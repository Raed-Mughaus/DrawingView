package com.raed.brushtool

import android.graphics.Rect
import com.raed.brushtool.model.TouchEvent
import com.raed.brushtool.touch.handler.CubicInterpolationTouchHandler
import com.raed.brushtool.touch.handler.LinearInterpolationTouchHandler
import com.raed.brushtool.touch.handler.TouchHandler

class BrushTool internal constructor(
    private val stampRenderer: StampRenderer,
    private val resultBitmapUpdater: ResultBitmapUpdater,
) {

    private val boundaryRect = Rect()
    private var touchHandler = createTouchHandler()
    val strokeBoundary = Rect()

    fun startDrawing(event: TouchEvent) {
        strokeBoundary.setEmpty()
        touchHandler.handleFirstTouch(event)
        resultBitmapUpdater.update()
    }

    fun continueDrawing(event: TouchEvent) {
        boundaryRect.setEmpty()
        touchHandler.handleTouch(event)
        resultBitmapUpdater.update(boundaryRect)
    }

    fun endDrawing(event: TouchEvent) {
        touchHandler.handleLastTouch(event)
        resultBitmapUpdater.update()
    }

    fun cancel() {
        TODO()
    }

    private fun createTouchHandler(): TouchHandler {
        return CubicInterpolationTouchHandler(
            1f,//TODO
            LinearInterpolationTouchHandler(
                1f, //TODO,
                createRenderingTouchHandler(),
            )
        )
    }

    private fun createRenderingTouchHandler() = object : TouchHandler {

        private val stampBoundary = Rect()

        override fun handleFirstTouch(event: TouchEvent) { }

        override fun handleTouch(event: TouchEvent) {
            stampRenderer.render(event, stampBoundary)
            boundaryRect.union(stampBoundary)
            strokeBoundary.union(stampBoundary)
        }

        override fun handleLastTouch(event: TouchEvent) {
            stampRenderer.render(event, stampBoundary)
            strokeBoundary.union(stampBoundary)
        }

    }

}
