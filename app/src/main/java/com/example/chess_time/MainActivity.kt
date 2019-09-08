package com.example.chess_time

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import kotlinx.android.synthetic.main.content_main.*
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android will destroy the view when the activity is not needed anymore
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)

        // This observer manages live data through life cycle changes and more
        timerViewModel.getAllTimers().observe(this, Observer<List<Timer>> {

        })


    }
}

