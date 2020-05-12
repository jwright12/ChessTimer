package com.example.chess_time

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Timer_DAO {

    @Insert
    fun insert(timer: Timer)

    @Update
    fun update(timer: Timer)

    @Update()
    fun incrementPlayCount(timer: Timer)

    @Delete
    fun delete(timer: Timer)

    @Query("DELETE FROM timer_table")
    fun deleteAllTimers()

    @Query("SELECT * FROM timer_table ORDER BY playCount DESC")
    fun getAllTimers(): LiveData<List<Timer>>

}