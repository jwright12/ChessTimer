package com.example.chess_time

import android.os.Bundle
import android.util.Log.d
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*

class Clock : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_white_button.setOnClickListener {
            d("start white button", "pressed")

        }
    }
}