package com.raed.rasmview.brushtool.model

import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.math.roundToInt

class BrushConfig {

    var stamp: BrushStamp = BrushStamp.CircleStamp

    var size: Float = 0.1f
        set (value) {
            if (value !in 0.0..1.0) {
                throw IllegalArgumentException("size must be between 0 and 1")
            }
            field = value
        }

    var spacing: Float = 0f
        set (value) {
            if (value !in 0.0..1.0) {
                throw IllegalArgumentException("spacing must be between 0 and 1")
            }
            field = value
        }

    var opacity: Float = 1f
        set (value) {
            if (value <= 0 || value > 1.0) {
                throw IllegalArgumentException("opacity must be between > 0 and <= 1")
            }
            field = value
        }

    var flow: Float = 1f
        set (value) {
            if (value <= 0 || value > 1.0) {
                throw IllegalArgumentException("flow must be between > 0 and <= 1")
            }
            field = value
        }

    var rotation: Int = 0

    var rotationRandomness: Float = 0f
        set (value) {
            if (value !in 0.0..1.0) {
                throw IllegalArgumentException("rotationRandomness must be between 0 and 1")
            }
            field = value
        }

    var isEraser: Boolean = false

    internal val step get() = max(round(sizeInPixels * spacing), 1f)

    internal val sizeInPixels: Int
        get() {
            return when (val stamp = stamp) {
                is BrushStamp.BitmapStamp -> {
                    val maxSize = min(stamp.stamp.width, stamp.stamp.height)
                    return lerp(1, maxSize, size)
                }
                else -> {
                    lerp(1, 80, size)
                }
            }
        }

}

private fun lerp(a: Int, b: Int, t: Float) = (a + t * (b - a)).roundToInt()
