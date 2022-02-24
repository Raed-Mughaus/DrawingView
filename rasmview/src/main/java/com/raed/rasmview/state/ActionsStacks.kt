package com.raed.rasmview.state

import com.raed.rasmview.actions.Action

/**
 * This class is responsible for managing two stacks that are used to store undo & redo actions.
 * There is a limit to the max size of the stored actions. When this limit is reached, old actions
 * from both stacks are dropped, until the size becomes bellow the max limit.
 */

private val MemoryLimit = Runtime.getRuntime().maxMemory() / 5 //TODO: allow client to choose this value

internal class ActionsStacks {

    private val mUndoStack = mutableListOf<Action>()
    private val mRedoStack = mutableListOf<Action>()

    fun pushRedo(action: Action) {
        mRedoStack.push(action)
    }

    fun pushUndo(action: Action) {
        mUndoStack.push(action)
    }

    fun clearRedoStack() {
        mRedoStack.clear()
    }

    fun popUndo(): Action {
        return mUndoStack.pop()
    }

    fun popRedo(): Action {
        return mRedoStack.pop()
    }

    fun hasRedo(): Boolean {
        return mRedoStack.size != 0
    }

    fun hasUndo(): Boolean {
        return mUndoStack.size != 0
    }

    private fun MutableList<Action>.pop(): Action {
        return removeAt(size - 1)
    }

    private fun MutableList<Action>.push(action: Action) {
        while (findCurrentSize() + action.size > MemoryLimit) {
            if (!dropAction()) //the size of this record is so large, it is larger than the MaxSize.
                return
        }
        add(action)
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