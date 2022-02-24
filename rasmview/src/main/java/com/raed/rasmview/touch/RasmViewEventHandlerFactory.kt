package com.raed.rasmview.touch

import com.raed.rasmview.RasmContext

internal class RasmViewEventHandlerFactory {

    fun create(rasmContext: RasmContext): MotionEventHandler {
        return RasmViewEventHandler(
            TransformationEventHandler(rasmContext.transformation),
            BrushToolEventHandler(rasmContext),
        )
    }

}