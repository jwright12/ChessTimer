package com.example.chess_time

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.security.AccessControlContext

@Database(entities = [Timer::class], version = 1)
abstract class TimerDatabase: RoomDatabase() {

    abstract fun timerDAO(): Timer_DAO

    companion object {
        // Elvis operator, returns null if an object is not instantiated
        private var instance: TimerDatabase? = null

        // Get instance method, create one if it does not exist. Return the instance

        fun getInstance(context: Context): TimerDatabase? {
            // If null, we haven't created one yet
            if (instance == null) {

                // Synchronized will not create a new instance if attempted by a second thread,
                synchronized(TimerDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimerDatabase::class.java, "timer_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        . build()
                }
            }

            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: TimerDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val timerDao = db?.timerDAO()

        override fun doInBackground(vararg p0: Unit?) {
            timerDao?.insert(Timer("Blitz", 10, 10, incrementWhite = 0, incrementBlack = 0,
                delayWhite = 0, delayBlack = 0, Type ="Blitz", playCount = 0))
            timerDao?.insert(Timer("Rapid", 20, 20, incrementWhite = 0, incrementBlack = 0,
                delayWhite = 0, delayBlack = 0, Type ="Rapid", playCount = 0))
            timerDao?.insert(Timer("Bullet", 1, 1, incrementWhite = 5, incrementBlack = 5,
                delayWhite = 2, delayBlack = 2, Type ="Bullet", playCount = 0))
            timerDao?.insert(Timer("Classical", 60, 60, incrementWhite = 0, incrementBlack = 0,
                delayWhite = 0, delayBlack = 0, Type ="Classical", playCount = 0))
        }
    }
}

// Need to create a static instance so that we are accessing the same database everytime
// Notice the database annotation takes the entity we defined earlier
// Notice this is an abstract class which means it is never directly accessed
// Read about what a sychronized method is - not allowed to be created on multiple threads
// We will define the access to our DAO here
// This method will return a DAO object so we can access the operations we defined
// Instantiate database object
// Companion object works like a static method in java. Allows us to access members of this class without
// creating a brand new class. So if we create a database instance onCreate, and want to access it later, we can
// go through this companion object which will return the same instance and allow us to do operations on it