package com.raed.rasmview.touch.gesture.translation;

import android.view.MotionEvent;


public class OneFingerTranslationDetector extends TranslationGestureDetector{

    //if true, touch events will be ignored until an event with the action 'ACTION_DOWN' is received
    private boolean mIgnoreEvent;

    public OneFingerTranslationDetector(TranslationListener listener) {
        super(listener);
    }

    private float mLastX;
    private float mLastY;
    @Override
    public void onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (mIgnoreEvent) {
            if (action == MotionEvent.ACTION_DOWN) {
                mIgnoreEvent = false;
            }else {
                return;
            }
        }else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            mIgnoreEvent = true;
            return;
        }

        //translation logic
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float xTranslation = x - mLastX;
                mLastX = x;

                float yTranslation = y - mLastY;
                mLastY = y;

                getListener().onTranslation(xTranslation, yTranslation);
                break;
        }
    }

}
