package com.example.chess_time

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_edit_timer.*
import java.util.*

class AddEditTimerRequest : AppCompatActivity(), MatchTimePicker.NoticeDialogListener {

    companion object {
        const val EXTRA_ID = "com.example.chess_time.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.chess_time.EXTRA_TITLE"
        const val EXTRA_WTIME = "com.example.chess_time.EXTRA_WTIME"
        const val EXTRA_WINC = "com.example.chess_time.EXTRA_WINC"
        const val EXTRA_WDELAY = "com.example.chess_time.EXTRA_WDELAY"
        const val EXTRA_BTIME = "com.example.chess_time.EXTRA_BTIME"
        const val EXTRA_BINC = "com.example.chess_time.EXTRA_BINC"
        const val EXTRA_BDELAY = "com.example.chess_time.EXTRA_BDELAY"
    }

    // Interface output
    var wTime: String = "00:00:00"
    var bTime: String = "00:00:00"
    var wInc: String = "00:00"
    var bInc: String = "00:00"
    var wDelay: String = "00:00"
    var bDelay: String = "00:00"

    // Integers for activity result
    var wT = 0
    var bT = 0
    var wI = 0
    var bI = 0
    var wD = 0
    var bD = 0


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_timer)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_grey_24dp)

        if (intent.hasExtra(EXTRA_ID)) {
            // If the intent was already assigned an ID, we know we are trying to edit an existing timer
            title = "Edit Match Settings"
            /*
            matchNameTextField.
            duration_edit_text.setText(intent.getStringExtra(EXTRA_TIME))

            increment_edit_text.setText(intent.getStringExtra(EXTRA_INC))

            delay_edit_text.setText(intent.getStringExtra(EXTRA_DELAY))
            */


        } else {
            title = "Create New Match"
        }

        same_for_black_switch.setOnClickListener {
            if(same_for_black_switch.isChecked) {
                bTime = wTime
                bInc = wInc
                bDelay = wDelay

                bTimeTextField.setHint(bTime)
                bIncrementTextField.setHint(bInc)
                bDelayTextField.setHint(bDelay)
            }
        }

        wTimeText!!.showSoftInputOnFocus = false
        wIncrementText!!.showSoftInputOnFocus = false
        wDelayText!!.showSoftInputOnFocus = false
        bTimeText!!.showSoftInputOnFocus = false
        bIncrementText!!.showSoftInputOnFocus = false
        bDelayText!!.showSoftInputOnFocus = false

        wTimeText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                wTimeTextField.setHint("White")
                show(1)
            } else {
                wTimeTextField.setHint(wTime)
            }

        }

        wIncrementText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                wIncrementTextField.setHint("White")
                show(2)
            } else wIncrementTextField.setHint(wInc)

        }

        wDelayText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                wDelayTextField.setHint("White")
                show(3)
            } else wDelayTextField.setHint(wDelay)
        }

        bTimeText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                bTimeTextField.setHint("Black")
                show(4)
            } else bTimeTextField.setHint(bTime)

        }

        bIncrementText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                bIncrementTextField.setHint("Black")
                show(5)
            } else bIncrementTextField.setHint(bInc)
        }

        bDelayText.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                bDelayTextField.setHint("Black")
                show(6)
            } else bDelayTextField.setHint(bDelay)
        }
    }

    private fun saveTimer() {

        if (matchNameText.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Enter a title before saving", Toast.LENGTH_SHORT).show()
            return
        } else if (wT == 0 || bT == 0) {
            Toast.makeText(this, "Set a duration for black and white", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, matchNameText.text.toString())
            putExtra(EXTRA_WTIME, wT)
            putExtra(EXTRA_WINC, wI)
            putExtra(EXTRA_WDELAY, wD)
            putExtra(EXTRA_BTIME, bT)
            putExtra(EXTRA_BINC, bI)
            putExtra(EXTRA_BDELAY, bD)
            // Helps distinguish between timers that are being edited
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
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

     fun show(id: Int) {
        val dialog = MatchTimePicker(id)
        dialog.show(supportFragmentManager, "Success")
     }

    override fun onDialogPositiveClick(value: Int, int: Int) {
        var dialogResult = pretty_print(value)
        var id = int

        if(same_for_black_switch.isChecked) {
            if (id == 1) {
                wTime = dialogResult
                wT = value
                bTime = dialogResult
                bT = value
                bTimeTextField.setHint(bTime)
                wTimeText.clearFocus()
            } else if (id == 2) {
                wInc = dialogResult
                wI = value
                bInc = dialogResult
                bI = value
                bIncrementTextField.setHint(bInc)
                wIncrementText.clearFocus()
            } else if (id == 3) {
                wDelay = dialogResult
                wD = value
                bDelay = dialogResult
                bD = value
                bDelayTextField.setHint(bDelay)
                wDelayText.clearFocus()
            }
        } else {
            if (id == 1) {
                wTime = dialogResult
                wT = value
                wTimeText.clearFocus()
            } else if (id == 2) {
                wInc = dialogResult
                wI = value
                wIncrementText.clearFocus()
            } else if (id == 3) {
                wDelay = dialogResult
                wD = value
                wDelayText.clearFocus()
            }
            else if (id == 4) {
                bTime = dialogResult
                bT = value
                bTimeText.clearFocus()
            } else if (id == 5) {
                bInc = dialogResult
                bI = value
                bIncrementText.clearFocus()
            } else if (id == 6) {
                bDelay = dialogResult
                bD = value
                bDelayText.clearFocus()
            }
        }
    }

    override fun onDialogNegativeClick(int: Int) {
        val id = int
        if (id == 1) {
            wTimeText.clearFocus()
        } else if (id == 2) {
            wIncrementText.clearFocus()
        } else if (id == 3) {
            wDelayText.clearFocus()
        } else if (id == 4) {
            bTimeText.clearFocus()
        } else if (id == 5) {
            bIncrementText.clearFocus()
        } else if (id == 6) {
            bDelayText.clearFocus()
        }
    }

    fun pretty_print (time: Int): String {
        val hours = (time/1000) / 3600
        val minutes = (time/1000) / 60
        val seconds = (time/1000) % 60
        val formatted_time: String

        if (hours > 0) {
            formatted_time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else if (minutes > 0){
            formatted_time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            formatted_time = String.format(Locale.getDefault(), "%02d"+"s", seconds)
        }

        return formatted_time
    }
}