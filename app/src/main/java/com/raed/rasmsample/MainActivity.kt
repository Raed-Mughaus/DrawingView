package com.raed.rasmview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()

        val linearLayout = findViewById<LinearLayout>()
    }

    override fun onBackPressed() {
        rasmView.rasmContext!!.state.undo()
    }

}