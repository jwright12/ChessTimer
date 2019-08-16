package com.example.chess_time

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import kotlinx.android.synthetic.main.content_main.*
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isPaused = false
    private var isCancelled = false
    private var resumeFromMillis:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define here, needed by functions
        var input_time_string: String = ""
        var input_time_long: Long = 0
        var default_interval: Long = 1

        set_match_time_button.setOnClickListener {
            // Takes the initial input and formats it for display

            input_time_string = match_time_input.text.toString()

            // Do not accept empty values
            if (input_time_string.length == 0) {
                Toast.makeText(this@MainActivity, "This field cannot be empty", Toast.LENGTH_SHORT).show()
            }

            // Parse the input string into a long, and then multiply the minutes to convert it to millseconds
            input_time_long = input_time_string.toLong() * 60000

            // Do not accept 0 or negative numbers
            if (input_time_long <= 0) {
                Toast.makeText(this@MainActivity, "Enter a positive number", Toast.LENGTH_SHORT).show()
            }

            // Display the countdown in 00:00:00 format, update buttons, hide irrelevant objects
            format_countdown_text(input_time_long)
            close_keyboard()
            format_interface()
        }

        restart_game_button.setOnClickListener {
            // Activate time setting input and button, clear display countdown text
            match_time_input.isVisible = true
            set_match_time_button.isVisible = true
            countdown_text_white.setText("00:00")

        }

        start_white_button.setOnClickListener {
            // Start the countdown timer, update floating action buttons
            timer_white(input_time_long, default_interval).start()
            it.isEnabled = false
            pause_game_button.setImageResource(R.drawable.ic_pause_black_24dp)
            isCancelled = false
            isPaused = false
        }

        pause_game_button.setOnClickListener {
            isPaused = true
            isCancelled = false
            it.isEnabled = false
            pause_game_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)

        }
    }


    private fun format_countdown_text(input_time_long: Long) {
        // Format and display the countdown text, close keyboard and input text
        // Convert to hours, minutes, seconds

        val hours = (input_time_long / 1000) / 3600
        val minutes = ((input_time_long / 1000) % 3600) / 60
        val seconds = (input_time_long / 1000) % 60
        val formatted_time: String

        if (hours > 0) {
            formatted_time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            formatted_time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }

        countdown_text_white.setText(formatted_time)
    }

    private fun format_interface() {
        // Once time is set, hide the input text and set button
        match_time_input.isVisible = false
        set_match_time_button.isVisible = false
    }

    private fun timer_white(time: Long, interval: Long): CountDownTimer {
        return object : CountDownTimer(time, interval) {
            override fun onTick(time_to_finish: Long) {

                val timeRemaining = time_to_finish

                if (isPaused) {
                    resumeFromMillis = time_to_finish
                    cancel()
                } else if (isCancelled) {
                    cancel()
                } else {
                    format_countdown_text(timeRemaining)
                }
            }

            override fun onFinish() {
                println("Done")
            }
        }
    }

    private fun close_keyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}

