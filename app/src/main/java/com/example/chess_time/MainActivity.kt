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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hold time input by user
        var input_time_string: String = ""
        var input_time_long: Long = 0
        var default_interval: Long = 1

        // Set match time with user input
        set_match_time_button.setOnClickListener {

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
            format_white_countdown(input_time_long)
            format_black_countdown(input_time_long)
            close_keyboard()
            format_interface()

            // Enable the black toggle button to start the match
            black_toggle.isClickable = true
            black_toggle.isChecked = true
            Toast.makeText(this@MainActivity, "Black clicks their clock to start", Toast.LENGTH_SHORT).show()
        }

        restart_game_button.setOnClickListener {

            // Update both sides clock states and display text/images
            white_isCancelled = true
            black_isCancelled = true
            white_isPaused = false
            black_isPaused = false

            //it.isEnabled = false
            pause_resume_game_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)

            black_toggle.setText("00:00")
            white_toggle.setText("00:00")

            match_time_input.isVisible = true
            set_match_time_button.isVisible = true
        }

        pause_resume_game_button.setOnClickListener {

            // Pause and resume both white and black clocks
            if (white_isPaused == false && black_isPaused == false) {
                white_isPaused = true
                black_isPaused = true
                pause_resume_game_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            } else {
                timer_white(white_resumeFromMillis, default_interval).start()
                timer_black(black_resumeFromMillis, default_interval).start()
                white_isPaused = false
                black_isPaused = false
                pause_resume_game_button.setImageResource(R.drawable.ic_pause_black_24dp)
            }

        }

        black_toggle.setOnClickListener {

            black_move_count += 1

            // On the first click, black is initiating the game for white. The second click should pause blacks turn
            // and resume whites timer
            if (black_move_count <= 1) {

                // Assign blacks beginning time as its resume time here. Otherwise we pass the initial match time to the
                // countdown object everytime white completes a move.
                black_resumeFromMillis = input_time_long
                white_resumeFromMillis = input_time_long

                // start white timer
                timer_white(white_resumeFromMillis, default_interval).start()

                // Update floating action button
                pause_resume_game_button.setImageResource(R.drawable.ic_pause_black_24dp)

                // Enable white button & disable black
                white_toggle.isChecked = true
                white_toggle.isClickable = true
                black_toggle.isChecked = false
                black_toggle.isClickable = false
            }

            if (black_move_count > 1) {

                timer_white(white_resumeFromMillis, default_interval).start()

                // Now, after blacks first move, we update the state to paused. The first move initiates the game.
                black_isPaused = true
                black_isCancelled = false

                // Re enable whites clock state
                white_isPaused = false

                // Enable white button & disable black
                white_toggle.isChecked = true
                white_toggle.isClickable = true
                black_toggle.isChecked = false
                black_toggle.isClickable = false
            }
        }

        white_toggle.setOnClickListener {

            // Update move counter
            white_move_count +=1

            if (white_move_count <=1 ) {

                // Pause whites time
                white_isPaused = true
                white_isCancelled = false

                // Start blacks clock
                timer_black(black_resumeFromMillis, default_interval).start()

                // Enable black button & disable white
                white_toggle.isChecked = false
                white_toggle.isClickable = false
                black_toggle.isChecked = true
                black_toggle.isClickable = true
            }

            if (white_move_count > 1) {

                // Start blacks clock
                timer_black(black_resumeFromMillis, default_interval).start()

                // Resume blacks clock state
                black_isPaused = false

                // Enable black button & disable white
                white_toggle.isChecked = false
                white_toggle.isClickable = false
                black_toggle.isChecked = true
                black_toggle.isClickable = true
            }

        }
    }


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

        //white toggle button text
        white_toggle.setText(formatted_time)
        white_toggle.textOff = formatted_time
        white_toggle.textOn = formatted_time
    }

    private fun format_black_countdown(input_time_long: Long) {
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

        // Set the beginning time for all toggle button states for now. Unless something else makes more sense later.
        black_toggle.setText(formatted_time)
        black_toggle.textOff = formatted_time
        black_toggle.textOn = formatted_time

    }

    private fun format_interface() {
        // Once time is set, hide the input text and set button
        match_time_input.isVisible = false
        set_match_time_button.isVisible = false
    }

    private fun timer_white(time: Long, interval: Long): CountDownTimer {
        return object : CountDownTimer(time, interval) {
            override fun onTick(white_time_to_finish: Long) {

                val white_timeRemaining = white_time_to_finish

                if (white_isPaused) {
                    white_resumeFromMillis = white_time_to_finish
                    cancel()
                } else if (white_isCancelled) {
                    cancel()
                } else {
                    format_white_countdown(white_timeRemaining)
                }
            }

            override fun onFinish() {
                println("Done")
            }
        }
    }

    private fun timer_black(time: Long, interval: Long): CountDownTimer {
        return object : CountDownTimer(time, interval) {
            override fun onTick(black_time_to_finish: Long) {

                val black_timeRemaining = black_time_to_finish

                if (black_isPaused) {
                    black_resumeFromMillis = black_time_to_finish
                    cancel()
                } else if (black_isCancelled) {
                    cancel()
                } else {
                    format_black_countdown(black_timeRemaining)
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

