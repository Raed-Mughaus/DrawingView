package com.raed.rasmview.touch.gesture.rotation;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.toDegrees;

import android.view.MotionEvent;

public class RotationGestureDetector {

    private OnRotationListener mListener;

    public RotationGestureDetector(OnRotationListener listener) {
        mListener = listener;
    }

    public static float angleBetweenLines(float[][] line1, float[][] line2) {

        double denominator = (line1[1][0] - line1[0][0]);
        denominator = denominator == 0 ? 0.000000001 : denominator;
        double m1 = (line1[1][1] - line1[0][1])/denominator;

        denominator = (line2[1][0] - line2[0][0]);
        denominator = denominator == 0 ? 0.000000001 : denominator;
        double m2 = (line2[1][1] - line2[0][1])/denominator;

        if (m1 * m2 == -1)
            return 90;

        return  (m2 > m1 ? -1 : 1)  * (float) toDegrees(atan(abs((m1 - m2)/(1 + m1 * m2))));
    }

    private float[][] mCurrentLine = new float[2][2];
    private float[][] mLastLine = new float[2][2];
    private int m1stPointerID;
    private int m2ndPointerID;
    public void onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() != 2)
            return;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                m1stPointerID = event.getPointerId(0);
                m2ndPointerID = event.getPointerId(1);
                mLastLine[0][0] = event.getX(0);
                mLastLine[0][1] = event.getY(0);
                mLastLine[1][0] = event.getX(1);
                mLastLine[1][1] = event.getY(1);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (m1stPointerID != event.getPointerId(0) || m2ndPointerID != event.getPointerId(1))
                    return;
                mCurrentLine[0][0] = event.getX(0);
                mCurrentLine[0][1] = event.getY(0);
                mCurrentLine[1][0] = event.getX(1);
                mCurrentLine[1][1] = event.getY(1);
                mListener.onRotation(angleBetweenLines(mLastLine, mCurrentLine));
                mLastLine[0][0] = mCurrentLine[0][0];
                mLastLine[0][1] = mCurrentLine[0][1];
                mLastLine[1][0] = mCurrentLine[1][0];
                mLastLine[1][1] = mCurrentLine[1][1];
                break;
        }
    }

}