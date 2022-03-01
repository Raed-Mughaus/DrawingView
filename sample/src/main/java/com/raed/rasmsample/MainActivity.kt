package com.raed.rasmsample

import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.switchmaterial.SwitchMaterial
import com.raed.rasmview.RasmContext
import com.raed.rasmview.RasmView
import com.raed.rasmview.brushtool.data.Brush
import com.raed.rasmview.brushtool.data.BrushesRepository
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var rasmView: RasmView
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var flowSeekBar: SeekBar
    private lateinit var opacitySeekBar: SeekBar
    private lateinit var rotationSwitch: SwitchMaterial

    private val rasmContext get() = rasmView.rasmContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewResId())

        rasmView = findViewById(R.id.rasmView)

        val spinner = findViewById<Spinner>(R.id.brush_spinner)
        spinner.adapter = ArrayAdapter(this, R.layout.text_view, Brush.values())
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val brushesRepository = BrushesRepository(resources)
                val brush = spinner.adapter.getItem(p2) as Brush
                rasmView.rasmContext.brushConfig = brushesRepository.get(brush)
                updateUI()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {  }
        }

        val undoButton = findViewById<Button>(R.id.undoButton)
        undoButton.setOnClickListener {
            rasmView.rasmContext
                .state
                .undo()
        }

        val redoButton = findViewById<Button>(R.id.redoButton)
        redoButton.setOnClickListener {
            rasmView.rasmContext
                .state
                .redo()
        }

        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            rasmView.rasmContext.clear()
        }

        val resetTransformationButton = findViewById<Button>(R.id.resetTransformation)
        resetTransformationButton.setOnClickListener {
            rasmView.resetTransformation()
        }

        rasmView.rasmContext
            .state
            .addOnStateChangedListener { state ->
                redoButton.isEnabled = state.canCallRedo()
                undoButton.isEnabled = state.canCallUndo()
            }

        val brushColorsContainer = findViewById<ViewGroup>(R.id.brushColorViews)
        for (i in 0 until brushColorsContainer.childCount) {
            brushColorsContainer.getChildAt(i).setOnClickListener { view ->
                rasmView.rasmContext.brushColor = (view.background as ColorDrawable).color
            }
        }

        val backgroundColorsContainer = findViewById<ViewGroup>(R.id.backgroundColorViews)
        for (i in 0 until backgroundColorsContainer.childCount) {
            backgroundColorsContainer.getChildAt(i).setOnClickListener { view ->
                rasmView.rasmContext.setBackgroundColor(
                    (view.background as ColorDrawable).color
                )
            }
        }

        sizeSeekBar = findViewById(R.id.brushSizeSeekBar)
        sizeSeekBar.setOnSeekBarChangeListener(SeekBarUserChangeListener { v ->
            rasmView.rasmContext
                .brushConfig
                .size = (v + 1) / 100f
        })

        flowSeekBar = findViewById(R.id.brushFlowSeekBar)
        flowSeekBar.setOnSeekBarChangeListener(SeekBarUserChangeListener { v ->
            rasmView.rasmContext
                .brushConfig
                .flow = (v + 1) / 100f
        })

        opacitySeekBar = findViewById(R.id.brushOpacitySeekBar)
        opacitySeekBar.setOnSeekBarChangeListener(SeekBarUserChangeListener { v ->
            rasmView.rasmContext
                .brushConfig
                .opacity = (v + 1) / 100f
        })

        rotationSwitch = findViewById(R.id.rotationSwitch)
        rotationSwitch.setOnCheckedChangeListener { _, checked ->
            rasmContext.rotationEnabled = checked
            updateUI()
        }
        updateUI()
    }

    override fun onBackPressed() {
        rasmContext.setRasm(BitmapFactory.decodeResource(resources, R.drawable.image))
    }

    private fun getContentViewResId(): Int {
        val (screenWidth, screenHeight) = getScreenSize()
        return if (screenWidth > screenHeight) {
            R.layout.activity_main_horizontal
        } else {
            R.layout.activity_main_vertical
        }
    }

    private fun getScreenSize(): Pair<Int, Int> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            Pair(bounds.width(), bounds.height())
        } else {
            val point = Point()
            windowManager.defaultDisplay.getRealSize(point)
            Pair(point.x, point.y)
        }
    }

    private fun updateUI() {
        val brushConfig = rasmContext.brushConfig
        sizeSeekBar.progress = (99 * brushConfig.size).roundToInt()
        flowSeekBar.progress = (99 * brushConfig.flow).roundToInt()
        opacitySeekBar.progress = (99 * brushConfig.opacity).roundToInt()
        rotationSwitch.isChecked = rasmContext.rotationEnabled
    }

}
