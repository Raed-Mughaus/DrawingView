package com.raed.rasmview.state

import com.raed.rasmview.RasmContext
import com.raed.rasmview.actions.Action

class RasmState(
    private val context: RasmContext,
) {

    private val listeners = mutableSetOf<(RasmState) -> Unit>()

    private val actionsStacks = ActionsStacks()

    fun addOnStateChangedListener(listener: (RasmState) -> Unit) {
        listeners.add(listener)
    }

    fun removeOnStateChangedListener(listener: (RasmState) -> Unit) {
        listeners.remove(listener)
    }

    internal fun update(action: Action) {
        val oppositeAction = action.getOppositeAction(context)
        actionsStacks.clearRedoStack()
        actionsStacks.pushUndo(oppositeAction)
        action.perform(context)
        notifyListeners()
    }

    fun undo() {
        if (!canCallUndo()) {
            throw IllegalStateException("Can't undo. Call rasmState.canCallUndo() before calling this method.")
        }
        val action = actionsStacks.popUndo()
        val oppositeAction = action.getOppositeAction(context)
        actionsStacks.pushRedo(oppositeAction)
        action.perform(context)
        notifyListeners()
    }

    fun redo() {
        if (!canCallRedo()) {
            throw IllegalStateException("Can't redo. Call rasmState.canCallRedo() before calling this method.")
        }
        val action = actionsStacks.popRedo()
        val oppositeAction = action.getOppositeAction(context)
        actionsStacks.pushUndo(oppositeAction)
        action.perform(context)
        notifyListeners()
    }

    fun canCallUndo() = actionsStacks.hasUndo()

    fun canCallRedo() = actionsStacks.hasRedo()

    private fun notifyListeners() {
        for (listener in listeners.toList()) {
            listener.invoke(this)
        }
    }

}
