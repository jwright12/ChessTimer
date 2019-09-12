package com.example.chess_time

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var timerViewModel: TimerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer_card_recycler_view.layoutManager = LinearLayoutManager(this)
        timer_card_recycler_view.setHasFixedSize(true)

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

