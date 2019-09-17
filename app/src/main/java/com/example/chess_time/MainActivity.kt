package com.example.chess_time

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_TIMER_REQUEST = 1
        const val EDIT_TIMER_REQUEST = 2
    }
    private lateinit var timerViewModel: TimerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pass data back from add timer activity
        buttonAddTimer.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditTimerRequest::class.java),
                ADD_TIMER_REQUEST
            )
        }


        // Layout manager handles recycling and dimensions of the recycler view
        timer_card_recycler_view.layoutManager = LinearLayoutManager(this)
        timer_card_recycler_view.setHasFixedSize(true)

        // Adapter deals with binding and inflating actual data to recycler view cards
        var adapter = TimerAdapter()
        timer_card_recycler_view.adapter = adapter

        // Android will destroy the view when the activity is not needed anymore
        // Adds capability to persist orientation and life cycle changes, we're passing a class using livedata
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)

        // This observer manages live data through life cycle changes and more
        timerViewModel.getAllTimers().observe(this, Observer<List<Timer>> {
            adapter.submitList(it)

        })


    }
}

