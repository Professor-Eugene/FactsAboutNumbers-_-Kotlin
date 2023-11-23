package com.example.factsaboutnumbers.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.factsaboutnumbers.model.Number
import com.example.factsaboutnumbers.repository.NumbersRepository
import com.example.factsaboutnumbers.repository.usecases.GetNumbersSortedAscending_UseCase
import com.example.factsaboutnumbers.repository.usecases.GetNumbersSortedByRequestHistory_UseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    val getNumbersSortedAscending_Usecase: GetNumbersSortedAscending_UseCase,
    val getNumbersSortedByRequestHistory_Usecase: GetNumbersSortedByRequestHistory_UseCase
) : ViewModel() {

    private val _numbersLiveData = MutableLiveData<List<Number>>()
    val numbersLiveData = _numbersLiveData as LiveData<List<Number>>

    private val updateListLiveData: MutableLiveData<LiveData<List<Number>>> = MutableLiveData()

    fun getUpdateListLiveData(): LiveData<LiveData<List<Number>>> {
        return updateListLiveData;
    }

    private var sortByRequestHistory = true;

    private var job: Job? = null

    init {
        selectDataSource()
    }

    private fun selectDataSource() {
        job?.cancel()

        if (sortByRequestHistory) {
            job = viewModelScope.launch {
                getNumbersSortedByRequestHistory_Usecase.execute()
                    .collect { numbers -> _numbersLiveData.setValue(numbers) }
            }
        } else {
                job = viewModelScope.launch {
                    getNumbersSortedAscending_Usecase.execute()
                        .collect {numbers -> _numbersLiveData.setValue(numbers)};
                }
            }
    }

    fun toggleSortOrder() {
        sortByRequestHistory = !sortByRequestHistory;
        selectDataSource()
    }
}