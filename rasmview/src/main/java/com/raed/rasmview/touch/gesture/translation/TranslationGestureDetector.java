package com.raed.rasmview.touch.gesture.translation;

import android.view.MotionEvent;

public abstract class TranslationGestureDetector {

    private final TranslationListener mListener;

    TranslationGestureDetector(TranslationListener listener) {
        mListener = listener;
    }

    public abstract void onTouchEvent(MotionEvent event);

    TranslationListener getListener() {
        return mListener;
    }
}
