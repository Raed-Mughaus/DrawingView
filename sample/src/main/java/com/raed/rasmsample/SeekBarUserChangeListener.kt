package com.raed.rasmsample

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

class SeekBarUserChangeListener(
    private val onProgressChangeListener: (Int) -> Unit,
    private val onStartTrackingTouchListener: (() -> Unit)?,
    private val onStopTrackingTouchListener: (() -> Unit)?,
) : OnSeekBarChangeListener {

    constructor(
        onProgressChangeListener: (Int) -> Unit,
    ): this(onProgressChangeListener, null, null)

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (!fromUser) return
        onProgressChangeListener(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        onStartTrackingTouchListener?.invoke()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        onStopTrackingTouchListener?.invoke()
    }

}
