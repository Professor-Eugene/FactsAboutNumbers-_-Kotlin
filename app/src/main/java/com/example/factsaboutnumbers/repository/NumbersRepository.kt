package com.example.factsaboutnumbers.repository

import com.example.factsaboutnumbers.data.rest.NumbersApiService
import com.example.factsaboutnumbers.data.room.NumbersDao
import com.example.factsaboutnumbers.di.util.AppScope
import com.example.factsaboutnumbers.model.Number
import retrofit2.Response
import javax.inject.Inject

@AppScope
class NumbersRepository @Inject constructor(
    private val numbersApiService: NumbersApiService,
    private val numbersDao: NumbersDao
) {

    fun getNumbersSortedByRequestHistory() = numbersDao.numbersSortedByRequestHistory
    fun getNumbersSortedAscending() = numbersDao.numbersSortedAscending

    suspend fun requestNewFact(number: Int): Response<String> {
        return numbersApiService.requestNewFact(number)
    }

    suspend fun getNumberSelectedFromList(id: Int): Number {
        return numbersDao.getNumberSelectedFromList(id);

    }

    suspend fun insertNumber(number: Number): Long {
        return numbersDao.insertNumber(number)
    }
}