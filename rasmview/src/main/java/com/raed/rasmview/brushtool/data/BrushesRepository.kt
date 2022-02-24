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
        return BrushConfig(
            createBrushStamp(R.drawable.stamp_pencil),
            0.1f,
            0.15f,
            rotationRandomness = 1f,
        )
    }

    private fun createPenBrushConfig() = BrushConfig()

    private fun createCalligraphyBrushConfig(): BrushConfig {
        return BrushConfig(
            OvalStamp,
            1f,
            0.0f,
        )
    }

    private fun createAirBrushBrushConfig(): BrushConfig {
        return BrushConfig(
            createBrushStamp(R.drawable.stamp_airbrush),
            1f,
            0.1f,
        )
    }

    private fun createMarkerBrushConfig(): BrushConfig {
        return BrushConfig(
            createBrushStamp(R.drawable.stamp_marker),
            1f,
            0.15f,
            flow = 0.2f,
        )
    }

    private fun createHardEraserBrushConfig(): BrushConfig {
        return BrushConfig(isEraser = true)
    }

    private fun createSoftEraserBrushConfig(): BrushConfig {
        return BrushConfig(
            createBrushStamp(R.drawable.stamp_airbrush),
            1f,
            0.15f,
            flow = 0.25f,
            isEraser = true
        )
    }

    private fun createBrushStamp(id: Int) = BitmapStamp(BitmapFactory.decodeResource(resources, id))

}
