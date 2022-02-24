package com.raed.rasmview.touch

import android.view.MotionEvent

internal class RasmViewEventHandler(
    private val transformationEventHandler: TransformationEventHandler,
    private val brushToolEventHandler: BrushToolEventHandler,
): MotionEventHandler {

    override fun handleFirstTouch(event: MotionEvent) {
        brushToolEventHandler.handleFirstTouch(event)
    }

    override fun handleTouch(event: MotionEvent) {
        brushToolEventHandler.handleTouch(event)
    }

    override fun handleLastTouch(event: MotionEvent) {
        brushToolEventHandler.handleLastTouch(event)
    }

    override fun cancel() {
        brushToolEventHandler.cancel()
    }

}
