package com.example.factsaboutnumbers.repository.usecases

import com.example.factsaboutnumbers.repository.NumbersRepository
import retrofit2.Response
import javax.inject.Inject

class RequestNewFact_UseCase @Inject constructor(
    private val numbersRepository: NumbersRepository
) {

    suspend fun execute(number: Int): Response<String> {
        return numbersRepository.requestNewFact(number)
    }

}