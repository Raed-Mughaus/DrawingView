package com.raed.rasmview.touch.handler

import android.view.MotionEvent

internal class RasmViewEventHandler(
    private val transformationEventHandler: TransformationEventHandler,
    private val brushToolEventHandler: TransformerEventHandler,
): MotionEventHandler {

    override fun handleFirstTouch(event: MotionEvent) {
        transformationEventHandler.handleFirstTouch(event)
        brushToolEventHandler.handleFirstTouch(event)
    }

    override fun handleTouch(event: MotionEvent) {
        transformationEventHandler.handleTouch(event)
        brushToolEventHandler.handleTouch(event)
    }

    override fun handleLastTouch(event: MotionEvent) {
        transformationEventHandler.handleLastTouch(event)
        brushToolEventHandler.handleLastTouch(event)
    }

    override fun cancel() {
        transformationEventHandler.cancel()
        brushToolEventHandler.cancel()
    }

}
