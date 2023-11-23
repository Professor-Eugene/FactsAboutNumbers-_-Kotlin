package com.example.factsaboutnumbers.repository.usecases

import com.example.factsaboutnumbers.model.Number
import com.example.factsaboutnumbers.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNumbersSortedAscending_UseCase @Inject constructor(
    private val numbersRepository: NumbersRepository
) {

    fun execute(): Flow<List<Number>> {
        return numbersRepository.getNumbersSortedAscending()
    }

}