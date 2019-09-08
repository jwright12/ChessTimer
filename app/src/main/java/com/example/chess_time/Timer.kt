package com.example.chess_time

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.os.CountDownTimer
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.concurrent.timer

@Entity(tableName = "timer_table")
class Timer(
    var title: String,

    var Duration: Int,

    var Description: String,

    var Increment: Long,

    var Delay: Long,

    var Type: String,

    var playCount: Int

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}




