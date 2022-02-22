package com.raed.rasmview.state

import android.graphics.Bitmap
import android.graphics.Rect

internal fun changeBackgroundColor(newColor: Int): ActionRecord {
    return ActionRecord(
        { context ->
            context.backgroundColor = newColor
        },
        { context ->
            changeBackgroundColor(context.backgroundColor)
        }
    )
}

internal fun draw(srcBitmap: Bitmap, srcRect: Rect, dstRect: Rect): ActionRecord {
    TODO()
}

internal fun clear(): ActionRecord {
    TODO()
}
