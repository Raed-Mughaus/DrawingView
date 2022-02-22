package com.raed.rasmview.state

import com.raed.rasmview.RasmContext


/**
 * getActionSize() should return the size of the memory expensive variables(if any) that is used in
 *  your action.
 * Assuming you are in state A
 *    Creating the action is straightforward, just write some code that moves you from state A to state B
 *    Creating the OppositeActionRecord is a little bit tricky, you need to think about State B
 *      and what would you do to return to State A. But remember that generateOppositeActionRecord()
 *          is going to be called before moving to state B.
 */

/**
 * getActionSize() should return the size of the memory expensive variables(if any) that is used in
 * your action.
 * Assuming you are in state A
 * Creating the action is straightforward, just write some code that moves you from state A to state B
 * Creating the OppositeActionRecord is a little bit tricky, you need to think about State B
 * and what would you do to return to State A. But remember that generateOppositeActionRecord()
 * is going to be called before moving to state B.
 */
/**
 * Keep in mind that this class is designed to be used in this way:
 * generateOppositeActionRecord() is called before calling performAction()
 * You can use performAction() without calling generateOppositeActionRecord(), but do not call
 * generateOppositeActionRecord() after calling performAction().
 * Assuming you are in state A,
 * calling performAction moves you to state B
 * calling generateOppositeActionRecord() returns an ActionRecord, and if the returned ActionRecord's
 * performAction() method is called it will move you from state B to state A
 */
internal class ActionRecord(
    private val action: (RasmContext) -> Unit,
    private val oppositeActionRecordGenerator: (RasmContext) -> ActionRecord,
    val actionSize: Long = 0
) {

    fun generateOppositeActionRecord(
        context: RasmContext,
    ) = oppositeActionRecordGenerator(context)

    fun performAction(context: RasmContext) = action(context)

}
