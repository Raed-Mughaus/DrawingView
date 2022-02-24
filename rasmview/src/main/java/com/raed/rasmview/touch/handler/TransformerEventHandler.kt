package com.raed.rasmview.touch.handler

import android.graphics.Matrix
import android.view.MotionEvent

internal class TransformerEventHandler(
    private val matrix: Matrix,
    private val nextHandler: MotionEventHandler,
): MotionEventHandler {

    private val inverseMatrix = Matrix()

    override fun handleFirstTouch(event: MotionEvent) {
        val transformedEvent = transform(event)
        nextHandler.handleFirstTouch(transformedEvent)
        transformedEvent.recycle()
    }

    override fun handleTouch(event: MotionEvent) {
        val transformedEvent = transform(event)
        nextHandler.handleTouch(transformedEvent)
        transformedEvent.recycle()
    }

    override fun handleLastTouch(event: MotionEvent) {
        val transformedEvent = transform(event)
        nextHandler.handleLastTouch(transformedEvent)
        transformedEvent.recycle()
    }

    override fun cancel() {
        nextHandler.cancel()
    }

    private fun transform(event: MotionEvent): MotionEvent {
        matrix.invert(inverseMatrix)
        val copyEvent = MotionEvent.obtain(event)
        copyEvent.transform(inverseMatrix)
        return copyEvent
    }

}
