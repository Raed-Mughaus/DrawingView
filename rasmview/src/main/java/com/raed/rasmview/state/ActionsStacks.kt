package com.raed.rasmview.state

import com.raed.rasmview.actions.Action

/**
 * This class is responsible for managing two stacks that are used to store undo & redo actions.
 * There is a limit to the max size of the stored actions. When this limit is reached, old actions
 * from both stacks are dropped, until the size becomes bellow the max limit.
 */

private val MemoryLimit = Runtime.getRuntime().maxMemory() / 5 //TODO: allow client to choose this value

internal class ActionsStacks {

    private val undoStack = mutableListOf<Action>()
    private val redoStack = mutableListOf<Action>()

    fun pushRedo(action: Action) {
        redoStack.push(action)
    }

    fun pushUndo(action: Action) {
        undoStack.push(action)
    }

    fun clearRedoStack() {
        redoStack.clear()
    }

    fun clear() {
        undoStack.clear()
        redoStack.clear()
    }

    fun popUndo(): Action {
        return undoStack.pop()
    }

    fun popRedo(): Action {
        return redoStack.pop()
    }

    fun hasRedo(): Boolean {
        return redoStack.size != 0
    }

    fun hasUndo(): Boolean {
        return undoStack.size != 0
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
        var size = 0L
        for (entry in undoStack) {
            size += entry.size
        }
        for (entry in redoStack) {
            size += entry.size
        }
        return size
    }

    private fun dropAction(): Boolean {
        if (undoStack.size == 0 && redoStack.size == 0) return false
        if (undoStack.size >= redoStack.size) undoStack.removeAt(0) else redoStack.removeAt(0)
        return true
    }

}