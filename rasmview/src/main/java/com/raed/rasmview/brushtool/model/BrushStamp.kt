package com.raed.rasmview.brushtool.model

import android.graphics.Bitmap

sealed class BrushStamp {
    class BitmapStamp(val stamp: Bitmap): BrushStamp()
    object CircleStamp: BrushStamp()
    object OvalStamp: BrushStamp()
}
