package com.raed.rasmview.state

import com.raed.rasmview.actions.Action

/**
 * This class is responsible for managing two ActionRecord lists that are used to store 'undo' and
 * 'redo' action records.
 * There is a limit to the max size of the stored action records. When the size limit is reached,
 * old items from both lists will be removed until the size becomes bellow the max limit.
 */

private val MemoryLimit = Runtime.getRuntime().maxMemory() / 5 //TODO: allow client to choose this value

internal class ActionsStacks {

    private val mUndoStack = mutableListOf<Action>()
    private val mRedoStack = mutableListOf<Action>()

    fun pushRedo(entry: Action) {
        push(mRedoStack, entry)
    }

    fun pushUndo(entry: Action) {
        push(mUndoStack, entry)
    }

    fun clearRedoStack() {
        mRedoStack.clear()
    }

    fun popUndo(): Action {
        return pop(mUndoStack)
    }

    fun popRedo(): Action {
        return pop(mRedoStack)
    }

    fun hasRedo(): Boolean {
        return mRedoStack.size != 0
    }

    fun hasUndo(): Boolean {
        return mUndoStack.size != 0
    }

    private fun pop(list: MutableList<Action>): Action {
        return list.removeAt(list.size - 1)
    }

    private fun push(stack: MutableList<Action>, record: Action) {
        while (findCurrentSize() + record.size > MemoryLimit) {
            if (!dropAction()) //the size of this record is so large, it is larger than the MaxSize.
                return
        }
        stack.add(record)
    }

    private fun findCurrentSize(): Long {
        var size: Long = 0
        for (entry in mUndoStack) {
            size += entry.size
        }
        for (entry in mRedoStack) {
            size += entry.size
        }
        return size
    }

    private fun dropAction(): Boolean {
        if (mUndoStack.size == 0 && mRedoStack.size == 0) return false
        if (mUndoStack.size >= mRedoStack.size) mUndoStack.removeAt(0) else mRedoStack.removeAt(0)
        return true
    }

}