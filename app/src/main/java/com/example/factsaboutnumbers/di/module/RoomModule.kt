package com.example.factsaboutnumbers.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.factsaboutnumbers.data.room.NumbersDao
import com.example.factsaboutnumbers.data.room.NumbersRoomDatabase
import com.example.factsaboutnumbers.di.util.AppScope
import com.example.factsaboutnumbers.util.log
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    private val DATABASE_NAME = "numbers_database"

    @AppScope
    @Provides
    fun provideNumbersRoomDatabase(context: Context): NumbersRoomDatabase {
        return Room.databaseBuilder(context.applicationContext, NumbersRoomDatabase::class.java, DATABASE_NAME).build();
    }

    @AppScope
    @Provides
    fun provideNumbersDao(numbersRoomDatabase: NumbersRoomDatabase): NumbersDao {
        return numbersRoomDatabase.numbersDao()
    }
}
