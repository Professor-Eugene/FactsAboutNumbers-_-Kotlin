package com.example.factsaboutnumbers.repository.usecases

import com.example.factsaboutnumbers.model.Number
import com.example.factsaboutnumbers.repository.NumbersRepository
import javax.inject.Inject

class GetNumberSelectedFromList_UseCase @Inject constructor(
    private val numbersRepository: NumbersRepository
) {

    suspend fun execute(id: Int): Number {
        return numbersRepository.getNumberSelectedFromList(id)
    }

}