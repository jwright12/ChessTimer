package com.example.chess_time

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class AddEditTimerRequest : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.chess_time.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.chess_time.EXTRA_TITLE"
        const val EXTRA_TIME_WHITE = "com.example.chess_time.EXTRA_TIME_WHITE"
        const val EXTRA_TIME_BLACK = "com.example.chess_time.EXTRA_TIME_BLACK"
        const val EXTRA_INC_WHITE = "com.example.chess_time.EXTRA_INC_WHITE"
        const val EXTRA_INC_BLACK = "com.example.chess_time.EXTRA_INC_BLACK"
        const val EXTRA_DELAY_WHITE = "com.example.chess_time.EXTRA_DELAY_WHITE"
        const val EXTRA_DELAY_BLACK = "com.example.chess_time.EXTRA_DELAY_BLACK"
        const val EXTRA_TYPE = "com.example.chess_time.EXTRA_TYPE"
        const val EXTRA_PLAY_COUNT = "com.example.chess_time.EXTRA_PLAY_COUNT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_timer)


        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

    }

    private fun saveTimer() {

        
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_timer -> {
                saveTimer()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }





}