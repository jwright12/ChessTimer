package com.example.chess_time

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment

class MatchTimePicker(textID: Int): DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(matchTimeSetting: Int, id: Int)
        fun onDialogNegativeClick(id: Int)
    }

    var matchSettingID: Int =  textID

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Get the view to inflate
            val view: View = inflater.inflate(R.layout.time_picker, null)

            val hour: NumberPicker = view.findViewById(R.id.hourPicker)
            hour.maxValue = 10
            hour.minValue = 0
            hour.wrapSelectorWheel = false

            val minute: NumberPicker = view.findViewById(R.id.minutePicker)
            minute.maxValue = 59
            minute.minValue = 0
            minute.wrapSelectorWheel = false

            val second: NumberPicker = view.findViewById(R.id.secondPicker)
            second.maxValue = 59
            second.minValue = 0
            second.wrapSelectorWheel = false

            builder.setView(view)
                .setPositiveButton(R.string.save,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Pass total milliseconds of match setting
                        var hoursInMili = (hour.value * 3600000)
                        var minutesInMili = (minute.value * 60000)
                        var secondsInMili = (second.value * 1000)
                        val matchComponent = hoursInMili+minutesInMili+secondsInMili
                        listener.onDialogPositiveClick(matchComponent, matchSettingID)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick(matchSettingID)
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}