package com.raed.rasmview.brushtool

class BrushToolStatus {

    var active = false
        set(value) {
            field = value
            notifyListeners()
        }

    private val listeners = mutableSetOf<(Boolean) -> Unit>()

    fun addOnChangeListener(listener: (Boolean) -> Unit) = listeners.add(listener)

    fun removeOnChangeListener(listener: (Boolean) -> Unit) = listeners.remove(listener)

    private fun notifyListeners() {
        for (listener in listeners) {
            listener(active)
        }
    }

}