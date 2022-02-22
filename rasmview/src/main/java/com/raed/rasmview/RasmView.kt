package com.raed.rasmview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.raed.rasmview.renderer.DrawingRendererFactory
import com.raed.rasmview.renderer.Renderer
import com.raed.rasmview.touch.DrawingViewEventHandlerFactory
import com.raed.rasmview.touch.MotionEventHandler

class RasmView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): View(context, attrs, defStyleAttr) {

    var rasmContext: RasmContext? = null
        set(value) {
            field = value
            updateRenderer()
        }

    private val eventHandlerFactory = DrawingViewEventHandlerFactory()
    private var touchHandler: MotionEventHandler? = null
    private var rendererFactory = DrawingRendererFactory()
    private var render: Renderer? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        render?.render(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        initializeDrawingContextIfNeeded()
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchHandler = eventHandlerFactory.create(rasmContext!!)
                touchHandler!!.handleFirstTouch(event)
                updateRenderer()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (event.actionMasked == MotionEvent.ACTION_UP) {
                    touchHandler!!.handleLastTouch(event)
                } else {
                    touchHandler!!.cancel()
                }
                touchHandler = null
                updateRenderer()
            }
            else -> {
                touchHandler!!.handleTouch(event)
            }
        }
        invalidate()
        return true
    }

    private fun initializeDrawingContextIfNeeded() {
        if (rasmContext != null) {
            return
        }
        rasmContext = RasmContext(width, height)
        render = rendererFactory.create(rasmContext!!)
    }

    private fun updateRenderer() {
        render = if (rasmContext != null) {
            rendererFactory.create(rasmContext!!)
        } else {
            null
        }
    }

}
