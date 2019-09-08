package com.example.chess_time

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MatchFragment {

    // White clock state variables
    private var white_isPaused = false
    private var white_isCancelled = false
    private var white_resumeFromMillis:Long = 0
    private var white_move_count: Int = 0

    // Black
    private var black_isPaused = false
    private var black_isCancelled = false
    private var black_resumeFromMillis:Long = 0
    private var black_move_count: Int = 0

    // Hold time input by user
    var input_time_string: String = ""
    var input_time_long: Long = 0
    var default_interval: Long = 1

    private fun format_white_countdown(input_time_long: Long) {
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

    }


}