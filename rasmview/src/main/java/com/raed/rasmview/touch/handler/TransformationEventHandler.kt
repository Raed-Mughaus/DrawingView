package com.raed.rasmview.touch.handler

import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import com.raed.rasmview.touch.gesture.rotation.RotationGestureDetector
import com.raed.rasmview.touch.gesture.scale.ScaleGestureDetector
import com.raed.rasmview.touch.gesture.translation.TwoFingersTranslationDetector

class TransformationEventHandler(
    private val transformationMatrix: Matrix,
    enableRotation: Boolean,
): MotionEventHandler {

    private val fingersCenter = PointF()

    private val rotationGestureDetector = RotationGestureDetector { rotation ->
        if (enableRotation) {
            transformationMatrix.postRotate(-rotation, fingersCenter.x, fingersCenter.y)
        }
    }

    private val scaleGestureDetector = ScaleGestureDetector(object :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            val scaleFactor = detector!!.scaleFactor
            transformationMatrix.postScale(scaleFactor, scaleFactor, fingersCenter.x, fingersCenter.y)
            return true
        }
    })

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
    }

    override fun handleLastTouch(event: MotionEvent) {
        if (event.pointerCount < 2) {
            return
        }
        fingersCenter.setToCenterOf(event)
        rotationGestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        translationDetector.onTouchEvent(event)
    }

    override fun cancel() {}

}

private fun PointF.setToCenterOf(event: MotionEvent) {
    x = (event.getX(0) + event.getX(1)) / 2f
    y = (event.getY(0) + event.getY(1)) / 2f
}