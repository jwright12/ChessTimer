package com.example.chess_time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TIME = "com.example.chess_time.EXTRA_TIME"
        const val EXTRA_INC = "com.example.chess_time.EXTRA_INC"
        const val EXTRA_DELAY = "com.example.chess_time.EXTRA_DELAY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val extras = getIntent().getExtras()
        if (null != extras) {
            val time = extras.getInt(EXTRA_TIME)
            val inc = extras.getInt(EXTRA_INC)
            val delay = extras.getInt(EXTRA_DELAY)

            //The key argument here must match that used in the other activity
            match_duration_text_view.setText(time.toString())
            match_increment_text_view.setText(inc.toString())
            match_delay_text_view.setText(delay.toString())
        }
    }



}
