package com.raed.rasmview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.raed.rasmview.renderer.RasmRendererFactory
import com.raed.rasmview.renderer.Renderer
import com.raed.rasmview.state.RasmState
import com.raed.rasmview.touch.handler.RasmViewEventHandlerFactory
import com.raed.rasmview.touch.handler.MotionEventHandler

class RasmView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
): View(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet? = null): this(context, attrs, 0)

    val rasmContext = RasmContext()

    init {
        rasmContext.state.addOnStateChangedListener(::onRasmStateChanged)
        rasmContext.brushToolStatus.addOnChangeListener {
            updateRenderer()
        }
    }

    private val eventHandlerFactory = RasmViewEventHandlerFactory()
    private var touchHandler: MotionEventHandler? = null
    private var rendererFactory = RasmRendererFactory()
    private var render: Renderer? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (rasmContext.hasRasm || w == 0 || h == 0) {
            return
        }
        rasmContext.setRasm(w, h)
        updateRenderer()
        resetTransformation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        render?.render(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchHandler = eventHandlerFactory.create(rasmContext)
                touchHandler!!.handleFirstTouch(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (event.actionMasked == MotionEvent.ACTION_UP) {
                    touchHandler!!.handleLastTouch(event)
                } else {
                    touchHandler!!.cancel()
                }
                touchHandler = null
            }
            else -> {
                touchHandler!!.handleTouch(event)
            }
        }
        invalidate()
        return true
    }

    fun resetTransformation() {
        rasmContext.resetTransformation(width, height)
        invalidate()
    }

    private fun updateRenderer() {
        render = rendererFactory.createOnscreenRenderer(rasmContext)
        invalidate()
    }

    private fun onRasmStateChanged(rasmState: RasmState) {
        render = rendererFactory.createOnscreenRenderer(rasmContext)
        invalidate()
    }

}
