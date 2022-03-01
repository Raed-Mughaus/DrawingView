package com.raed.rasmview.touch.gesture

import android.view.MotionEvent

internal class TwoFingersTranslationDetector(
    private val listener: (Float, Float) -> Unit,
) {

    private val mLastX = FloatArray(2)
    private val mLastY = FloatArray(2)

    fun onTouchEvent(event: MotionEvent) {
        val pointerCount = event.pointerCount
        if (pointerCount == 1) return
        val action = event.actionMasked
        val x0 = event.getX(0)
        val x1 = event.getX(1)
        val y0 = event.getY(0)
        val y1 = event.getY(1)
        if (action == MotionEvent.ACTION_POINTER_DOWN && pointerCount == 2) {
            mLastX[0] = x0
            mLastX[1] = x1
            mLastY[0] = y0
            mLastY[1] = y1
        } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_POINTER_DOWN) {
            val distanceX0 = x0 - mLastX[0]
            val distanceX1 = x1 - mLastX[1]
            val xTranslation: Float
            val sameXDirectionMovement =
                distanceX0 > 0 && distanceX1 > 0 || distanceX0 < 0 && distanceX1 < 0
            xTranslation = if (sameXDirectionMovement) {
                (distanceX0 + distanceX1) / 2f
            } else {
                0f
            }
            mLastX[0] = x0
            mLastX[1] = x1
            val distanceY0 = y0 - mLastY[0]
            val distanceY1 = y1 - mLastY[1]
            val yTranslation: Float
            val sameYDirectionMovement =
                distanceY0 > 0 && distanceY1 > 0 || distanceY0 < 0 && distanceY1 < 0
            yTranslation = if (sameYDirectionMovement) {
                (distanceY0 + distanceY1) / 2f
            } else {
                0f
            }
            mLastY[0] = y0
            mLastY[1] = y1
            listener(xTranslation, yTranslation)
        }
    }
}