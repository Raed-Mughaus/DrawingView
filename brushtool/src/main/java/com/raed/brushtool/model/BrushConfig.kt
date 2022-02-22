package com.raed.brushtool.model

import android.graphics.Bitmap

data class BrushConfig(
    var stamp: Bitmap? = null,
    var size: Int = 16,
    var spacing: Float = 0.01f,
    var opacity: Float = 1f,
    var flow: Float = 1f,
    var rotationRandomness: Float = 0f,
    var isEraser: Boolean = false,
)
