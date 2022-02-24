package com.raed.rasmview.brushtool.model

import kotlin.math.sqrt

data class TouchEvent(
    var x: Float = 0f,
    var y: Float = 0f,
    var p: Float = 0f,
) {

    fun set(event: TouchEvent) = set(event.x, event.y, event.p)

    fun set(x: Float, y: Float, p: Float) {
        this.x = x
        this.y = y
        this.p = p
    }

    fun distanceTo(anotherEvent: TouchEvent): Float {
        val dx = x - anotherEvent.x
        val dy = y - anotherEvent.y
        return sqrt(dx * dx + dy * dy)
    }

}
