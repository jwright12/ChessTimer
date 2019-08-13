package com.example.chess_time

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.os.CountDownTimer
import kotlin.concurrent.timer

class Match : AppCompatActivity() {

    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_white_button.setOnClickListener {
            // Print text of countdown timer
            var matchTime: String = countdown_text_white.text.toString()
            println(matchTime)

        }

    }
}

