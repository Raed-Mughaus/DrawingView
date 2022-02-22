package com.raed.brushtool.data

import com.raed.brushtool.data.Brush.*
import com.raed.brushtool.model.BrushConfig

class BrushesRepository {

    fun get(brush: Brush): BrushConfig {
        return when (brush) {
            Pencil -> createPencilBrushConfig()
            Pen -> createPenBrushConfig()
            Calligraphy -> createCalligraphyBrushConfig()
            AirBrush -> createAirBrushBrushConfig()
            HardEraser -> createHardEraserBrushConfig()
            SoftEraser -> createSoftEraserBrushConfig()
        }
    }

    private fun createPencilBrushConfig(): BrushConfig {
        TODO()
    }

    private fun createPenBrushConfig() = BrushConfig()

    private fun createCalligraphyBrushConfig(): BrushConfig {
        TODO()
    }

    private fun createAirBrushBrushConfig(): BrushConfig {
        TODO()
    }

    private fun createHardEraserBrushConfig(): BrushConfig {
        TODO()
    }

    private fun createSoftEraserBrushConfig(): BrushConfig {
        TODO()
    }

}
