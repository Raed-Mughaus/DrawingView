package com.raed.rasmview.brushtool.data

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.raed.rasmview.R
import com.raed.rasmview.brushtool.data.Brush.*
import com.raed.rasmview.brushtool.model.BrushConfig
import com.raed.rasmview.brushtool.model.BrushStamp.*

class BrushesRepository(
    private val resources: Resources,
) {

    fun get(brush: Brush): BrushConfig {
        return when (brush) {
            Pencil -> createPencilBrushConfig()
            Pen -> createPenBrushConfig()
            Calligraphy -> createCalligraphyBrushConfig()
            AirBrush -> createAirBrushBrushConfig()
            Marker -> createMarkerBrushConfig()
            HardEraser -> createHardEraserBrushConfig()
            SoftEraser -> createSoftEraserBrushConfig()
        }
    }

    private fun createPencilBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            stamp = createBrushStamp(R.drawable.stamp_pencil)
            size = 0.1f
            spacing = 0.15f
            rotationRandomness = 1f
        }
    }

    private fun createPenBrushConfig() = BrushConfig()

    private fun createCalligraphyBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            stamp = OvalStamp
            size = 0.2f
            spacing = 0.0f
            rotation = 45
        }
    }

    private fun createAirBrushBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            stamp = createBrushStamp(R.drawable.stamp_airbrush)
            size = 0.2f
            spacing = 0.1f
        }
    }

    private fun createMarkerBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            stamp = createBrushStamp(R.drawable.stamp_marker)
            size = 0.4f
            spacing = 0.15f
            flow = 0.2f
        }
    }

    private fun createHardEraserBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            size = 0.1f
            isEraser = true
        }
    }

    private fun createSoftEraserBrushConfig(): BrushConfig {
        return BrushConfig().apply {
            stamp = createBrushStamp(R.drawable.stamp_airbrush)
            size = 0.2f
            spacing = 0.15f
            flow = 0.25f
            isEraser = true
        }
    }

    private fun createBrushStamp(id: Int) = BitmapStamp(BitmapFactory.decodeResource(resources, id))

}
