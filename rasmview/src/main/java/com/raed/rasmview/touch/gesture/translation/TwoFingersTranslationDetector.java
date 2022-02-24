package com.raed.rasmview.touch.gesture.translation;

import android.view.MotionEvent;

public class TwoFingersTranslationDetector extends TranslationGestureDetector{

    private final float[] mLastX = new float[2];
    private final float[] mLastY = new float[2];

    public TwoFingersTranslationDetector(TranslationListener listener) {
        super(listener);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (pointerCount == 1)
            return;
        int action = event.getActionMasked();
        float x0 = event.getX(0);
        float x1 = event.getX(1);
        float y0 = event.getY(0);
        float y1 = event.getY(1);
        if (action == MotionEvent.ACTION_POINTER_DOWN && pointerCount == 2) {
            mLastX[0] = x0;
            mLastX[1] = x1;
            mLastY[0] = y0;
            mLastY[1] = y1;
        } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_POINTER_DOWN) {
            float distanceX0 = x0 - mLastX[0];
            float distanceX1 = x1 - mLastX[1];
            float xTranslation;
            boolean sameXDirectionMovement = (distanceX0 > 0 && distanceX1 > 0) || (distanceX0 < 0 && distanceX1 < 0);
            if (sameXDirectionMovement) {
                xTranslation = (distanceX0 + distanceX1) / 2f;
            }else {
                xTranslation = 0;
            }
            mLastX[0] = x0;
            mLastX[1] = x1;

            float distanceY0 = y0 - mLastY[0];
            float distanceY1 = y1 - mLastY[1];
            float yTranslation;
            boolean sameYDirectionMovement = (distanceY0 > 0 && distanceY1 > 0) || (distanceY0 < 0 && distanceY1 < 0);
            if (sameYDirectionMovement) {
                yTranslation = (distanceY0 + distanceY1) / 2f;
            }else {
                yTranslation = 0;
            }
            mLastY[0] = y0;
            mLastY[1] = y1;

            getListener().onTranslation(xTranslation, yTranslation);
        }
    }
}
