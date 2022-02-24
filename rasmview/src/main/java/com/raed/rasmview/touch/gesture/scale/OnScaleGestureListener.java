package com.raed.rasmview.touch.gesture.scale;

public class OnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private final ScaleListener mOnScale;

    public OnScaleGestureListener(ScaleListener onScale) {
        mOnScale = onScale;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return mOnScale.onScale(detector);
    }
}
