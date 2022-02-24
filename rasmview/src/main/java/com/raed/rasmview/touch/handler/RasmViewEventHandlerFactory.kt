package com.raed.rasmview.touch.handler

import com.raed.rasmview.RasmContext

internal class RasmViewEventHandlerFactory {

    fun create(rasmContext: RasmContext): MotionEventHandler {
        return RasmViewEventHandler(
            TransformationEventHandler(
                rasmContext.transformation,
                rasmContext.rotationEnabled,
            ),
            TransformerEventHandler(
                rasmContext.transformation,
                BrushToolEventHandler(rasmContext),
            ),
        )
    }

}