package com.example.chess_time

import android.app.Activity
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
        const val EXTRA_TIME = "com.example.chess_time.EXTRA_TIME"
        const val EXTRA_INC = "com.example.chess_time.EXTRA_INC"
        const val EXTRA_DELAY = "com.example.chess_time.EXTRA_DELAY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Assigns layout from resource folder
        setContentView(R.layout.add_edit_timer)
        // Sets icon in left menu corner to the close icon
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            // If the intent was already assigned an ID, we know we are trying to edit an existing timer
            title = "Edit Match Settings"
            title_text.setText(intent.getStringExtra(EXTRA_TITLE))

            duration_edit_text.setText(intent.getStringExtra(EXTRA_TIME))

            increment_edit_text.setText(intent.getStringExtra(EXTRA_INC))

            delay_edit_text.setText(intent.getStringExtra(EXTRA_DELAY))

        } else {
            title = "Create New Match"
        }




    }

    private fun saveTimer() {

        // If title or duration has no entry, then prevent the save
        if (title_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Enter a title before saving", Toast.LENGTH_SHORT).show()
            return

        } else if (duration_edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Provide the match duration", Toast.LENGTH_SHORT).show()
            return
        } else if (increment_edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Provide the match increment", Toast.LENGTH_SHORT).show()
            return
        } else if (delay_edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Provide the match delay", Toast.LENGTH_SHORT).show()
            return
        }


        // Creating a object to return to the main activity. Main activity started intent for result
        // This is it
        // Uses the extras defined above

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, title_text.text.toString())
            putExtra(EXTRA_TIME, duration_edit_text.text.toString().toInt())
            putExtra(EXTRA_INC, increment_edit_text.text.toString().toInt())
            putExtra(EXTRA_DELAY, delay_edit_text.text.toString().toInt())

            // Helps distinguish between timers that are being edited
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        // Returns the data and result code
        setResult(Activity.RESULT_OK, data)
        // Close the activity
        finish()
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