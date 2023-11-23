package com.example.factsaboutnumbers.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.factsaboutnumbers.model.Number
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNumber(number: Number): Long

    @get:Query("SELECT * from numbers_table ORDER BY id DESC")
    val numbersSortedByRequestHistory: Flow<List<Number>>

    @get:Query("SELECT * from numbers_table ORDER BY number ASC")
    val numbersSortedAscending: Flow<List<Number>>

    @Query("SELECT * from numbers_table WHERE id = :id")
    suspend fun getNumberSelectedFromList(id: Int): Number
}