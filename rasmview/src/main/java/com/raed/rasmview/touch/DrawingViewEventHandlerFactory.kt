package com.raed.rasmview.touch

import com.raed.rasmview.RasmContext

internal class DrawingViewEventHandlerFactory {

    fun create(rasmContext: RasmContext): MotionEventHandler {
        return DrawingViewEventHandler(
            DrawingTransformationEventHandler(rasmContext.transformation),
            BrushToolEventHandler(rasmContext),
        )
    }

}