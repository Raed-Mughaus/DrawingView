package com.raed.rasmview.state

import com.raed.rasmview.RasmContext

class DrawingState {

    var onStateChangedListener: ((drawingState: DrawingState) -> Unit)? = null

    private val mActionRecordStacks = ActionRecordStacks()

    internal fun update(actionRecord: ActionRecord, context: RasmContext) {
        val oppositeActionRecord = actionRecord.generateOppositeActionRecord(context)
        mActionRecordStacks.clearRedoStack()
        mActionRecordStacks.pushUndo(oppositeActionRecord)
        actionRecord.performAction(context)
        onStateChangedListener?.invoke(this)
    }

    fun undo(context: RasmContext) {
        val actionRecord: ActionRecord = mActionRecordStacks.popUndo()
        val oppositeActionRecord = actionRecord.generateOppositeActionRecord(context)
        mActionRecordStacks.pushRedo(oppositeActionRecord)
        actionRecord.performAction(context)
        onStateChangedListener?.invoke(this)
    }

    fun redo(context: RasmContext) {
        val actionRecord: ActionRecord = mActionRecordStacks.popRedo()
        val oppositeActionRecord = actionRecord.generateOppositeActionRecord(context)
        mActionRecordStacks.pushUndo(oppositeActionRecord)
        actionRecord.performAction(context)
        onStateChangedListener?.invoke(this)
    }

    fun canCallUndo() = mActionRecordStacks.hasUndo()

    fun canCallRedo() = mActionRecordStacks.hasRedo()

}
