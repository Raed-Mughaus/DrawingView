package com.raed.rasmview.actions

import com.raed.rasmview.RasmContext

internal class ChangeBackgroundAction(
    private val newColor: Int,
): Action {

    override fun perform(context: RasmContext) {
        context.backgroundColor = newColor
    }

    override fun getOppositeAction(context: RasmContext): Action {
        return ChangeBackgroundAction(context.backgroundColor)
    }

}
