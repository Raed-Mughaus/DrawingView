package com.raed.rasmview.touch.gesture

import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.atan

class RotationGestureDetector(
    private val listener: (Float) -> Unit,
) {

    private val currentLine = Array(2) { FloatArray(2) }
    private val lastLine = Array(2) { FloatArray(2) }
    private var firstPointerID = 0
    private var secondPointerID = 0

    fun onTouchEvent(event: MotionEvent) {
        if (event.pointerCount != 2) return
        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                firstPointerID = event.getPointerId(0)
                secondPointerID = event.getPointerId(1)
                lastLine[0][0] = event.getX(0)
                lastLine[0][1] = event.getY(0)
                lastLine[1][0] = event.getX(1)
                lastLine[1][1] = event.getY(1)
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                if (firstPointerID != event.getPointerId(0) || secondPointerID != event.getPointerId(1)) return
                currentLine[0][0] = event.getX(0)
                currentLine[0][1] = event.getY(0)
                currentLine[1][0] = event.getX(1)
                currentLine[1][1] = event.getY(1)
                listener(angleBetweenLines(lastLine, currentLine))
                lastLine[0][0] = currentLine[0][0]
                lastLine[0][1] = currentLine[0][1]
                lastLine[1][0] = currentLine[1][0]
                lastLine[1][1] = currentLine[1][1]
            }
        }
    }

}

private fun angleBetweenLines(line1: Array<FloatArray>, line2: Array<FloatArray>): Float {
    var denominator = (line1[1][0] - line1[0][0]).toDouble()
    denominator = if (denominator == 0.0) 0.000000001 else denominator
    val m1 = (line1[1][1] - line1[0][1]) / denominator
    denominator = (line2[1][0] - line2[0][0]).toDouble()
    denominator = if (denominator == 0.0) 0.000000001 else denominator
    val m2 = (line2[1][1] - line2[0][1]) / denominator
    return if (m1 * m2 == -1.0) {
        90f
    } else {
        (if (m2 > m1) -1 else 1) * Math.toDegrees(atan(abs((m1 - m2) / (1 + m1 * m2)))).toFloat()
    }
}