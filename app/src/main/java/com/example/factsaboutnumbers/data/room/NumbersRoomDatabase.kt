package com.example.factsaboutnumbers.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.factsaboutnumbers.model.Number

@Database(entities = [Number::class], version = NumbersRoomDatabase.VERSION, exportSchema = false)
abstract class NumbersRoomDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
    }

    abstract fun numbersDao(): NumbersDao
}
