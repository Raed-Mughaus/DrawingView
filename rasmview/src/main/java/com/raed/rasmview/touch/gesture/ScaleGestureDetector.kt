package com.raed.rasmview.touch.gesture

import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.hypot

/**
 * Detects scaling transformation gestures using the supplied [MotionEvent]s.
 * The [OnScaleGestureListener] callback will notify users when a particular
 * gesture event has occurred.
 *
 * This class should only be used with [MotionEvent]s reported via touch.
 *
 * To use this class:
 *
 *  * Create an instance of the `ScaleGestureDetector` for your
 * [View]
 *  * In the [View.onTouchEvent] method ensure you call
 * [.onTouchEvent]. The methods defined in your
 * callback will be executed when the events occur.
 *
 */
internal class ScaleGestureDetector(
    private val listener: (Float) -> Unit,
) {

    private var mCurrSpan = 0f
    private var mPrevSpan = 0f
    private var mInitialSpan = 0f
    private var mInProgress = false
    private val mSpanSlop = 0
    private val mMinSpan = 0

    /**
     * Accepts MotionEvents and dispatches events to a [OnScaleGestureListener]
     * when appropriate.
     *
     *
     * Applications should pass a complete and consistent event stream to this method.
     * A complete and consistent event stream involves all MotionEvents from the initial
     * ACTION_DOWN to the final ACTION_UP or ACTION_CANCEL.
     *
     * @param event The event to process
     * @return true if the event was processed and the detector wants to receive the
     * rest of the MotionEvents in this event stream.
     */
    fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val count = event.pointerCount
        if (action == MotionEvent.ACTION_DOWN) {
            mInProgress = false
            mInitialSpan = 0f
        }
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            return true
        }
        val configChanged =
            action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_POINTER_DOWN
        val pointerUp = action == MotionEvent.ACTION_POINTER_UP
        val skipIndex = if (pointerUp) event.actionIndex else -1

        // Determine focal point
        var sumX = 0f
        var sumY = 0f
        val div = if (pointerUp) count - 1 else count
        for (i in 0 until count) {
            if (skipIndex == i) continue
            sumX += event.getX(i)
            sumY += event.getY(i)
        }
        val focusX = sumX / div
        val focusY = sumY / div

        // Determine average deviation from focal point
        var devSumX = 0f
        var devSumY = 0f
        for (i in 0 until count) {
            if (skipIndex == i) continue

            // Convert the resulting diameter into a radius.
            devSumX += abs(event.getX(i) - focusX)
            devSumY += abs(event.getY(i) - focusY)
        }
        val devX = devSumX / div
        val devY = devSumY / div

        // Span is the average distance between touch points through the focal point;
        // i.e. the diameter of the circle with a radius of the average deviation from
        // the focal point.
        val spanX = devX * 2
        val spanY = devY * 2
        val span = hypot(spanX.toDouble(), spanY.toDouble()).toFloat()

        // Dispatch begin/end events as needed.
        // If the configuration changes, notify the app to reset its current state by beginning
        // a fresh scale event stream.
        val wasInProgress = mInProgress
        if (mInProgress && (span < mMinSpan || configChanged)) {
            mInProgress = false
            mInitialSpan = span
        }
        if (configChanged) {
            mCurrSpan = span
            mPrevSpan = mCurrSpan
            mInitialSpan = mPrevSpan
        }
        val minSpan = mMinSpan
        if (!mInProgress && span >= minSpan &&
            (wasInProgress || abs(span - mInitialSpan) > mSpanSlop)
        ) {
            mCurrSpan = span
            mPrevSpan = mCurrSpan
            mInProgress = true
        }

        // Handle motion; focal point and span/scale factor are changing.
        if (action == MotionEvent.ACTION_MOVE) {
            mCurrSpan = span
            if (mInProgress) {
                val scale = if (mPrevSpan > 0) mCurrSpan / mPrevSpan else 1f
                listener(scale)
            }
            mPrevSpan = mCurrSpan
        }
        return true
    }

}