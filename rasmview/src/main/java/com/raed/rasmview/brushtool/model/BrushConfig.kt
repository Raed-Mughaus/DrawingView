package com.raed.rasmview.brushtool.model

import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.math.roundToInt

data class BrushConfig(
    var stamp: BrushStamp = BrushStamp.CircleStamp,
    var size: Float = 0.5f,
    var spacing: Float = 0.01f,
    var opacity: Float = 1f,
    var flow: Float = 1f,
    var rotation: Int = 0,
    var rotationRandomness: Float = 0f,
    var isEraser: Boolean = false,
) {

    internal val step get() = max(round(sizeInPixels * spacing), 1f)

    val sizeInPixels: Int
    get() {
        return when (val stamp = stamp) {
            is BrushStamp.BitmapStamp -> {
                val maxSize = min(stamp.stamp.width, stamp.stamp.height)
                return lerp(1, maxSize, size)
            }
            else -> {
                lerp(1, 120, size)
            }
        }
    }

}

private fun lerp(a: Int, b: Int, t: Float) = (a + t * (b - a)).roundToInt()
