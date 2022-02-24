package com.raed.rasmview.brushtool.handler

import com.raed.rasmview.brushtool.model.TouchEvent
import kotlin.math.sqrt

internal class LinearInterpolationTouchHandler(
    private val step: Float,
    nextHandler: TouchHandler,
): TouchHandler {

    private val nextHandler = nextHandler.asFlexibleHandler()
    private val interpolatedTouchEvent = TouchEvent()
    private val lastTouchEvent = TouchEvent()

    override fun handleFirstTouch(event: TouchEvent) {
        lastTouchEvent.set(event)
        nextHandler.handleFirstTouch(event)
    }

    override fun handleTouch(event: TouchEvent) {
        interpolateTo(event)
    }

    override fun handleLastTouch(event: TouchEvent) {
        interpolateTo(event)
        nextHandler.flush()
    }

    //Return true if any drawing has been performed
    private fun interpolateTo(event: TouchEvent): Boolean {
        val dX = event.x - lastTouchEvent.x
        val dY = event.y - lastTouchEvent.y
        val dP = event.p - lastTouchEvent.p
        val dist = event.distanceTo(lastTouchEvent)
        if (dist < step) {
            return false
        }

        val tStep = step / dist
        var t = 0f
        while (t <= 1 - tStep) {
            interpolatedTouchEvent.x = lastTouchEvent.x + t * dX
            interpolatedTouchEvent.y = lastTouchEvent.y + t * dY
            interpolatedTouchEvent.p = lastTouchEvent.p + t * dP
            nextHandler.handleTouch(interpolatedTouchEvent)
            t += tStep
        }

        lastTouchEvent.x = lastTouchEvent.x + t * dX
        lastTouchEvent.y = lastTouchEvent.y + t * dY
        lastTouchEvent.p = lastTouchEvent.p + t * dP

        return true
    }

}
