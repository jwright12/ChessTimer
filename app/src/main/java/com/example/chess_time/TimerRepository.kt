package com.example.chess_time

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import kotlin.concurrent.timer

class TimerRepository (application: Application) {

    private var timerDao: Timer_DAO
    private var allTimers: LiveData<List<Timer>>

    init {
        val database: TimerDatabase = TimerDatabase.getInstance(application.applicationContext)!!
        timerDao = database.timerDAO()
        allTimers = timerDao.getAllTimers()
    }

    fun insert(timer: Timer) {

        val insertTimer = InsertTimerAsyncTask(timerDao).execute(timer)
    }

    fun update(timer: Timer) {

        val updateTimer = UpdateTimerAsyncTask(timerDao).execute(timer)
    }

    fun delete(timer: Timer) {

        val deleteTimer = DeleteTimerAsyncTask(timerDao).execute(timer)
    }

    fun deleteAllTimers() {

        val deleteAllTimers = DeleteAllTimersAsyncTask(timerDao).execute()

    }

    fun getAllTimers(): LiveData<List<Timer>> {
        return allTimers
    }

    companion object {
        private class InsertTimerAsyncTask(timerDao: Timer_DAO) : AsyncTask<Timer, Unit, Unit>() {

            val timerDao = timerDao

            override fun doInBackground(vararg p0: Timer?) {
                timerDao.insert(p0[0]!!)
            }
        }

        private class UpdateTimerAsyncTask(timerDao: Timer_DAO) : AsyncTask<Timer, Unit, Unit>() {

            val timerDao = timerDao

            override fun doInBackground(vararg p0: Timer?) {
                timerDao.update(p0[0]!!)
            }
        }

        private class DeleteTimerAsyncTask(timerDao: Timer_DAO) : AsyncTask<Timer, Unit, Unit>() {

            val timerDao = timerDao

            override fun doInBackground(vararg p0: Timer?) {
                timerDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllTimersAsyncTask(timerDao: Timer_DAO) : AsyncTask<Timer, Unit, Unit>() {

            val timerDao = timerDao

            override fun doInBackground(vararg p0: Timer?) {
                timerDao.deleteAllTimers()
            }
        }
    }
}