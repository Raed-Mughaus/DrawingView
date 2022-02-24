package com.raed.rasmview.brushtool

import android.graphics.Rect
import com.raed.rasmview.brushtool.model.TouchEvent
import com.raed.rasmview.brushtool.handler.CubicInterpolationTouchHandler
import com.raed.rasmview.brushtool.handler.LinearInterpolationTouchHandler
import com.raed.rasmview.brushtool.handler.TouchHandler
import com.raed.rasmview.brushtool.renderer.StampRenderer

class BrushTool internal constructor(
    private val stampRenderer: StampRenderer,
    private val resultBitmapUpdater: ResultBitmapUpdater,
    private val step: Float,
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
        boundaryRect.inset(-5, -5)
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
            step,//TODO
            LinearInterpolationTouchHandler(
                step, //TODO,
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
