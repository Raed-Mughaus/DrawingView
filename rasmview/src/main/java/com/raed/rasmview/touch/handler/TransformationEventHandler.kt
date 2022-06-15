package com.raed.rasmview.touch.handler

import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import com.raed.rasmview.touch.gesture.RotationGestureDetector
import com.raed.rasmview.touch.gesture.ScaleGestureDetector
import com.raed.rasmview.touch.gesture.TwoFingersTranslationDetector

internal class TransformationEventHandler(
    private val transformationMatrix: Matrix,
    enableRotation: Boolean,
): MotionEventHandler {

    private val fingersCenter = PointF()

    private val rotationGestureDetector = RotationGestureDetector { rotation ->
        if (enableRotation) {
            transformationMatrix.postRotate(-rotation, fingersCenter.x, fingersCenter.y)
        }
    }

    private val scaleGestureDetector = ScaleGestureDetector { scale ->
        transformationMatrix.postScale(scale, scale, fingersCenter.x, fingersCenter.y)
    }

    private val translationDetector = TwoFingersTranslationDetector { xTranslation, yTranslation ->
        transformationMatrix.postTranslate(xTranslation, yTranslation)
    }

    override fun handleFirstTouch(event: MotionEvent) {
        if (event.pointerCount < 2) {
            return
        }
        fingersCenter.setToCenterOf(event)
        rotationGestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        translationDetector.onTouchEvent(event)
    }

    override fun handleTouch(event: MotionEvent) {
        if (event.pointerCount < 2) {
            return
        }
        fingersCenter.setToCenterOf(event)
        rotationGestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        translationDetector.onTouchEvent(event)
        //TODO: if the drawing is outside the view, get it inside
    }

    override fun handleLastTouch(event: MotionEvent) {
        if (event.pointerCount < 2) {
            return
        }
        fingersCenter.setToCenterOf(event)
        rotationGestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        translationDetector.onTouchEvent(event)
        //TODO: if the drawing is outside the view, get it inside
    }

    override fun cancel() {}

}

private fun PointF.setToCenterOf(event: MotionEvent) {
    x = (event.getX(0) + event.getX(1)) / 2f
    y = (event.getY(0) + event.getY(1)) / 2f
}