package com.example.chess_time

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_match.*
import java.util.*


/*
    Match Activity:
    -- Controls turn changes. When one player completes a turn, the others begins, and vice versa
    -- Manages the clock time for each player using an inner class and CountDown Timer
    -- Responds to button clicks
    -- Implements data passed from the main activity
 */
class MatchActivity : AppCompatActivity() {

    private var click: MediaPlayer? = null
    private var chime: MediaPlayer? = null

    companion object {
        const val EXTRA_TIME_WHITE = "com.example.chess_time.EXTRA_TIME_WHITE"
        const val EXTRA_INC_WHITE = "com.example.chess_time.EXTRA_INC_WHITE"
        const val EXTRA_DELAY_WHITE = "com.example.chess_time.EXTRA_DELAY_WHITE"
        const val EXTRA_TIME_BLACK = "com.example.chess_time.EXTRA_TIME_BLACK"
        const val EXTRA_INC_BLACK = "com.example.chess_time.EXTRA_INC_BLACK"
        const val EXTRA_DELAY_BLACK = "com.example.chess_time.EXTRA_DELAY_BLACK"
    }

    // Timer objects for each player, initialized during onCreate
    var white: countdown_timer?= null
    var black: countdown_timer?= null

    // Boolean helps us make UI logic
    var match_enabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        click = MediaPlayer.create(this, R.raw.click)
        chime = MediaPlayer.create(this, R.raw.chime)

        // Disable nav & status bar during a match
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        // Receive data and initialize view and timers
        val extras = getIntent().getExtras()
        if (null != extras) {

            val wT = extras.getInt(EXTRA_TIME_WHITE).toLong()
            val wI = extras.getInt(EXTRA_INC_WHITE).toLong()
            val wD = extras.getInt(EXTRA_DELAY_WHITE).toLong()
            val bT = extras.getInt(EXTRA_TIME_BLACK).toLong()
            val bI = extras.getInt(EXTRA_INC_BLACK).toLong()
            val bD = extras.getInt(EXTRA_DELAY_BLACK).toLong()

            white_duration.setText(pretty_print(wT))
            black_duration.setText(pretty_print(bT))
            white_increment_text_view.setText(pretty_print(wI))
            black_increment_text_view.setText(pretty_print(bI))
            white_delay_text_view.setText(pretty_print(wD))
            black_delay_text_view.setText(pretty_print(bD))

            white = countdown_timer(wT, wI, wD, true)
            black = countdown_timer(bT, bI, bD, true)
        }

        // Rotate view
        rotate_button.setOnClickListener{
            if(constraint_view.rotation == 180.0F) constraint_view.rotation = 0.0F
            else constraint_view.rotation = 180.0F
        }

        white_tile_image.setOnClickListener{
            // End white, start black, save resume time
            // What if this is clicked when it is not whites turn?
            if(white_tile_image.isClickable) {
                click?.start()
                if (match_enabled) {
                    white!!.end_turn()
                    black!!.start_timer()
                }
            }
        }

        // Black tile can call start match if the match is not enabled
        black_tile_image.setOnClickListener{
            click?.start()
            if(!match_enabled) {
                start_match()
            } else if (black!!.active){
                black!!.end_turn()
                white!!.start_timer()
            }
        }

        // Enable white tile, change FABs, star white timer
        play_button.setOnClickListener {
            if(!match_enabled) {
                start_match()
            } else {
                resume_match()
            }
        }

        pause_button.setOnClickListener {
            pause_match()
        }

        reset_button.setOnClickListener {
            reset_match()
        }

    }

    fun pretty_print (time: Long): String {
        val hours = time / 3600000
        var minutes = time / 60000
        val seconds = (time/1000) % 60
        val formatted_time: String

        if (hours >= 1) {
            if (minutes >= 60) minutes = minutes % 60
            formatted_time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else if (minutes > 0){
            formatted_time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            formatted_time = String.format(Locale.getDefault(), "%02d"+"s", seconds)
        }

        return formatted_time
    }

    fun end_match () {
        // Back to main activity
        var i = 0
        while (i < 3) {
            chime?.start()
            i++
        }
    }

    fun reset_match () {
        // Not white or blacks turn anymore
        // cancel clocks, reset timers to initial values, set FABs, animations, sounds
        white?.reset_timer()
        black?.reset_timer()
        //white_moves_text.setText("0")
        //black_moves_text.setText("0")
        black_tile_image.isClickable = true
        match_enabled = false
    }

    // Change layout back to constraint view
    fun pause_match () {
        fab_layout_paused.isVisible = true
        pause_button.isVisible = false
        white_tile_image.isClickable = false
        black_tile_image.isClickable = false

        if(black!!.my_turn) {
            black!!.pause_timer()
        } else white!!.pause_timer()

    }

    // Start white timer, white tile clickable, update FABs
    fun start_match() {
        fab_layout_paused.isVisible = false
        pause_button.isVisible = true
        white_tile_image.isClickable = true
        match_enabled = true
        white!!.start_timer()
    }

    fun resume_match() {
        fab_layout_paused.isVisible = false
        pause_button.isVisible = true
        if(black!!.my_turn) {
            black_tile_image.isClickable = true
            black!!.start_timer()
        } else {
            white_tile_image.isClickable = true
            white!!.start_timer()
        }

    }

    /*
           Timer:
           -- Implements a CountDown Timers for delay and match time functions
           -- Starts, pauses, and cancels a timer based on user input
           -- Respects match increments and delays
           -- Signals if it is one players turn or the others
     */

    inner class countdown_timer (var duration: Long, var increment: Long, var delay: Long,
                                 isWhite: Boolean) {
        // Match params
        private var match_duration: Long
        private var match_increment: Long
        private var match_delay: Long
        // Core timer object, only initialized when the timer needs to start
        private lateinit var timer: CountDownTimer
        private lateinit var delay_timer: CountDownTimer
        private var resume_from: Long? = null
        // Internal to open access to outer class. Controls pause/resume logic in outer class
        internal var my_turn = false
        internal var active = false
        // Controls which TextView a timer writes to
        private var isWhite: Boolean
        // Helps us prevent cancelling a timer before it is instantiated
        private var is_delay_running = false
        private var delay_time: Long
        // Move count
        private var moves = 0

        init {
            match_duration = duration
            match_increment = increment
            match_delay = delay
            resume_from = match_duration
            delay_time = match_delay
            this.isWhite = isWhite
        }

        fun start_timer() {
            my_turn = true
            active = true

            if(isWhite) white_tile_image.isClickable = true
            else black_tile_image.isClickable = true

            //Instantiate main timer. Start it after any delay timer
            timer = object: CountDownTimer(resume_from!!,250) {

                override fun onFinish() {
                    // Alarm players, call end game function
                    end_match()
                }

                override fun onTick(resume_time: Long) {
                    // Store remaining time, update
                    //resume_from = resume_time

                    Log.i("test","ms="+resume_time+" till finished="+resume_from )
                    if (resume_time != resume_from) {
                        resume_from = resume_time
                        output_time(resume_time)
                    }
                }
            }

            if(delay_time >= 100) {
                delay_timer = object: CountDownTimer(delay_time,250) {

                    override fun onFinish() {
                        // Cancel current delay, start main timer, reset delay time.
                        timer.start()
                        is_delay_running = false
                    }

                    override fun onTick(resume_time: Long) {
                        // Decrement the delay time so we resume correctly from paused state
                        delay_time = resume_time

                        if (resume_time != delay_time) {
                            delay_time = resume_time
                        }
                    }
                }.start()
                is_delay_running = true
            } else {
                // If no delay than start main timer
                timer.start()
            }
        }

        fun reset_timer() {
            // If the game has started and reset FAB pressed, if were in delay time, cancel that timer
            // If we are not in delay time but in match time, cancel the match timer.
            if(active) {
                cancel_delay()
                stop_timer()
                moves = 0
                delay_time = match_delay
                resume_from = match_duration
                output_time(match_duration)
            }
        }

        fun stop_timer() {
            // What if this function is called while the delay timer is running?
            timer.cancel()
        }

        fun pause_timer() {
            // White will always be set to active. Possible to pause match before black is activated.
            // Maybe this could be more specific.
            if(isWhite) {
                // What if this function is called while the delay timer is running?
                if(is_delay_running) {
                    cancel_delay()
                } else {
                    white!!.stop_timer()
                }

            } else if(black!!.active) {
                if(is_delay_running) {
                    cancel_delay()
                } else {
                 black!!.stop_timer()
                 }
            }
        }

        // Needs to cancel the timer and reset the delay time
        fun cancel_delay() {
            delay_timer.cancel()
        }


        fun end_turn() {

            my_turn = false
            if (isWhite) white_tile_image.isClickable = false
            else black_tile_image.isClickable = false

            if(is_delay_running) {
                // If end turn is called during delay time, we still give increment and move.
                // Also reset the delay timer to the full delay time
                cancel_delay()

                resume_from = resume_from?.plus(match_increment)
                output_time(resume_from!!)
                moves++

                if (isWhite) white_moves_text_view.setText(moves.toString())
                else black_moves_text_view.setText(moves.toString())

                delay_time = match_delay
            } else {
                // Add increment, update display time
                resume_from = resume_from?.plus(match_increment)
                output_time(resume_from!!)

                delay_time = match_delay

                // Increase move count, check if FIDE mode, check if Bronstein delay
                moves++
                if (isWhite) white_moves_text_view.setText(moves.toString())
                else black_moves_text_view.setText(moves.toString())

                stop_timer()
            }
        }

        fun output_time(time: Long) {
           var formatted_time = pretty_print(time)
            if(isWhite) {
                white_duration.setText(formatted_time)
            }
            else {
                black_duration.setText(formatted_time)
            }
        }

    }
}

