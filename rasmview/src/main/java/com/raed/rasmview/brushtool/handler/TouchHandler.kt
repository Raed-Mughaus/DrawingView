package com.raed.rasmview.brushtool.handler

import com.raed.rasmview.brushtool.model.TouchEvent

internal interface TouchHandler {

    /**
     * No method should be called before calling this method.
     */
    fun handleFirstTouch(event: TouchEvent)

    fun handleTouch(event: TouchEvent)

    /**
     * No method should be called after calling this method.
     */
    fun handleLastTouch(event: TouchEvent)

}
