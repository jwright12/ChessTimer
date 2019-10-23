package com.example.chess_time

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_edit_timer.*
import kotlinx.android.synthetic.main.timer_item.*

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
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_timer)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            // If the intent was already assigned an ID, we know we are trying to edit an existing timer
            title = "Edit Match Settings"
            title_text.setText(intent.getStringExtra(EXTRA_TITLE))

            white_duration_edit_text.setText(intent.getStringExtra(EXTRA_TIME_WHITE))
            black_duration_edit_text.setText(intent.getStringExtra(EXTRA_TIME_BLACK))

            white_increment_edit_text.setText(intent.getStringExtra(EXTRA_INC_WHITE))
            black_increment_edit_text.setText(intent.getStringExtra(EXTRA_INC_BLACK))

            white_delay_edit_text.setText(intent.getStringExtra(EXTRA_DELAY_WHITE))
            black_delay_edit_text.setText(intent.getStringExtra(EXTRA_DELAY_BLACK))

        } else {
            title = "Create New Match"
        }




    }

    private fun saveTimer() {

        // If title or duration has no entry, then prevent the save
        if (title_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Enter a title before saving", Toast.LENGTH_SHORT).show()
            return

        } else if (white_duration_edit_text.text.toString().trim().isBlank() && black_duration_edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Provide the match duration", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {  }


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