package com.raed.rasmview.brushtool.handler

import com.raed.rasmview.brushtool.model.TouchEvent
import kotlin.math.ceil

internal class CubicInterpolationTouchHandler(
    private val mStep: Float,
    private val nextHandler: TouchHandler,
): TouchHandler {

    private val event0 = TouchEvent()
    private val event1 = TouchEvent()
    private val event2 = TouchEvent()

    private val interpolatedEvent = TouchEvent()

    override fun handleFirstTouch(event: TouchEvent) {
        event1.set(event)
        event0.set(event)
        nextHandler.handleFirstTouch(event1)
    }

    override fun handleTouch(event: TouchEvent) {
        event2.x = (event1.x + event.x) / 2f
        event2.y = (event1.y + event.y) / 2f
        event2.p = (event1.p + event.p) / 2f
        val pointCount = ceil(event0.distanceTo(event2) / mStep).toInt()
        for (n in 1 until pointCount) {
            val t = n.toFloat() / pointCount.toFloat()
            val tSqr = t * t
            val tPrime = 1 - t
            val tPrimeSqr = tPrime * tPrime
            interpolatedEvent.x = tSqr * event2.x + 2 * t * tPrime * event1.x + tPrimeSqr * event0.x
            interpolatedEvent.y = tSqr * event2.y + 2 * t * tPrime * event1.y + tPrimeSqr * event0.y
            interpolatedEvent.p = tSqr * event2.p + 2 * t * tPrime * event1.p + tPrimeSqr * event0.p
            nextHandler.handleTouch(interpolatedEvent)
        }
        nextHandler.handleTouch(event2)
        event0.set(event2)
        event1.set(event)
    }

    override fun handleLastTouch(event: TouchEvent) {
        val pointCount = ceil(event0.distanceTo(event) / mStep).toInt()
        for (n in 1 until pointCount) {
            val t = n.toFloat() / pointCount.toFloat()
            val tSqr = t * t
            val tPrime = 1 - t
            val tPrimeSqr = tPrime * tPrime
            interpolatedEvent.x = tSqr * event.x + 2 * t * tPrime * event1.x + tPrimeSqr * event0.x
            interpolatedEvent.y = tSqr * event.y + 2 * t * tPrime * event1.y + tPrimeSqr * event0.y
            interpolatedEvent.p = tSqr * event.p + 2 * t * tPrime * event1.p + tPrimeSqr * event0.p
            nextHandler.handleTouch(interpolatedEvent)
        }
        nextHandler.handleLastTouch(event)
    }

}
