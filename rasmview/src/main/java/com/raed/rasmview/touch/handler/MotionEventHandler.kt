package com.raed.rasmview.touch.handler

import android.view.MotionEvent

internal interface MotionEventHandler {

    /**
     * No method should be called before calling this method.
     */
    fun handleFirstTouch(event: MotionEvent)

    fun handleTouch(event: MotionEvent)

    /**
     * No method should be called after calling this method.
     */
    fun handleLastTouch(event: MotionEvent)

    /**
     * No method should be called after calling this method.
     */
    fun cancel()

}
