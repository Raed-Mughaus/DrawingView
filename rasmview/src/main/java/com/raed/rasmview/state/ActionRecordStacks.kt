package com.raed.rasmview.state

/**
 * This class is responsible for managing two ActionRecord lists that are used to store 'undo' and
 * 'redo' action records.
 * There is a limit to the max size of the stored action records. When the size limit is reached,
 * old items from both lists will be removed until the size becomes bellow the max limit.
 */

private val MemoryLimit = Runtime.getRuntime().maxMemory() / 5 //TODO: allow client to choose this value

internal class ActionRecordStacks {

    private val mUndoStack = mutableListOf<ActionRecord>()
    private val mRedoStack = mutableListOf<ActionRecord>()

    fun pushRedo(entry: ActionRecord) {
        push(mRedoStack, entry)
    }

    fun pushUndo(entry: ActionRecord) {
        push(mUndoStack, entry)
    }

    fun clearRedoStack() {
        mRedoStack.clear()
    }

    fun popUndo(): ActionRecord {
        return pop(mUndoStack)
    }

    fun popRedo(): ActionRecord {
        return pop(mRedoStack)
    }

    fun hasRedo(): Boolean {
        return mRedoStack.size != 0
    }

    fun hasUndo(): Boolean {
        return mUndoStack.size != 0
    }

    private fun pop(list: MutableList<ActionRecord>): ActionRecord {
        return list.removeAt(list.size - 1)
    }

    private fun push(stack: MutableList<ActionRecord>, record: ActionRecord) {
        while (findCurrentSize() + record.actionSize > MemoryLimit) {
            if (!dropRecord()) //the size of this record is so large, it is larger than the MaxSize.
                return
        }
        stack.add(record)
    }

    private fun findCurrentSize(): Long {
        var size: Long = 0
        for (entry in mUndoStack) {
            size += entry.actionSize
        }
        for (entry in mRedoStack) {
            size += entry.actionSize
        }
        return size
    }

    private fun dropRecord(): Boolean {
        if (mUndoStack.size == 0 && mRedoStack.size == 0) return false
        if (mUndoStack.size >= mRedoStack.size) mUndoStack.removeAt(0) else mRedoStack.removeAt(0)
        return true
    }

}