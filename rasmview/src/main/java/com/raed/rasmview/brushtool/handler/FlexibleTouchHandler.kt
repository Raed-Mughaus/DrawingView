package com.raed.rasmview.brushtool.handler

import com.raed.rasmview.brushtool.model.TouchEvent

/**
 * This wrapper delays handling an event until a newer event is available. This allows you to
 * delegate an event when you are unsure whether it is the last event or not. If you realized
 * that [handleFirstTouch] or [handleTouch] has already been called with the last event, just call
 * [flush] and there is no need to call [handleLastTouch].
 *
 * In other words, FlexibleTouchHandler allows you to end your touch events sequence with a single
 * call to [handleLastTouch] or [flush]
 */
internal class FlexibleTouchHandler(
    private val nextHandler: TouchHandler,
): TouchHandler {

    private var unhandledEvent: TouchEvent? = null
    private val firstEvent = TouchEvent()

    override fun handleFirstTouch(event: TouchEvent) {
        nextHandler.handleFirstTouch(event)
        firstEvent.set(event)
    }

    override fun handleTouch(event: TouchEvent) {
        if (unhandledEvent != null) {
            nextHandler.handleTouch(unhandledEvent!!)
        } else {
            unhandledEvent = TouchEvent()
        }
        unhandledEvent!!.set(event)
    }

    override fun handleLastTouch(event: TouchEvent) {
        if (unhandledEvent != null) {
            nextHandler.handleTouch(unhandledEvent!!)
        }
        nextHandler.handleLastTouch(event)
    }

    fun flush() {
        nextHandler.handleLastTouch(unhandledEvent ?: firstEvent)
    }

}

internal fun TouchHandler.asFlexibleHandler() = FlexibleTouchHandler(this)
