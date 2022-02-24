package com.raed.rasmview.actions

import com.raed.rasmview.RasmContext


internal interface Action {

    val size get() = 0

    /**
     * Move the context from state A to B.
     */
    fun perform(context: RasmContext)

    /**
     * Should be called in state A (before calling perform(context))
     * @return an action that move the context from state B to A.
     */
    fun getOppositeAction(context: RasmContext): Action

}
