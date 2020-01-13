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

        //val stringOne = intent.getIntExtra(EXTRA_TIME,150)

        val extras = getIntent().getExtras()
        if (null != extras) {
            val value = extras.getInt(EXTRA_TIME)
            //The key argument here must match that used in the other activity
            match_duration_text_view.setText(value.toString())
        }
    }



}
