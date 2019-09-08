package com.example.chess_time

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TimerRepository = TimerRepository(application)
    private var allTimers: LiveData<List<Timer>> = repository.getAllTimers()

    fun insert(timer: Timer) {
        repository.insert(timer)
    }

    fun update(timer: Timer) {
        repository.update(timer)
    }

    fun delete(timer: Timer) {
        repository.delete(timer)
    }

    fun deleteAll() {
        repository.deleteAllTimers()
    }

   fun getAllTimers() : LiveData<List<Timer>> {
       return allTimers
   }
}