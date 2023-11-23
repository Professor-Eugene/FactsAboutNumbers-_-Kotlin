package com.example.factsaboutnumbers.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factsaboutnumbers.R
import com.example.factsaboutnumbers.model.Number
import com.example.factsaboutnumbers.repository.usecases.GetNumberSelectedFromList_UseCase
import com.example.factsaboutnumbers.repository.usecases.RequestNewFact_UseCase
import com.example.factsaboutnumbers.repository.usecases.SaveNumberToDatabase_UseCase
import com.example.factsaboutnumbers.util.log
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val saveNumberToDatabase_UseCase: SaveNumberToDatabase_UseCase,
    private val requestNewFact_UseCase: RequestNewFact_UseCase,
    private val getNumberSelectedFromList_UseCase: GetNumberSelectedFromList_UseCase
) : ViewModel() {

    private val _numberLiveData = MutableLiveData<Number>()
    val numberLiveData = _numberLiveData as LiveData<Number>

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading as LiveData<Boolean>

    private val _numbersLoadError = MutableLiveData<Boolean>()
    val numbersLoadError = _numbersLoadError as LiveData<Boolean>

    private val _toast = MutableLiveData<Int>()
    val toast = _toast as LiveData<Int>


    fun requestNewFact(intNumberRequested: Int) {
        hideErrorMessage()
        showLoadingProgressBar()

        viewModelScope.launch {
            try {
                val response = requestNewFact_UseCase.execute(intNumberRequested)

                if (response.isSuccessful) {
                    hideLoadingProgressBar()

                    val fact = response.body()!!
                    val number = Number(intNumberRequested, fact)

                    showNumber(number)
                    saveNumberToDatabase(number)
                } else {
                    hideLoadingProgressBar()
                    showErrorMessage()

                    log("Unsuccessful request, response code ${response.code()}")
                }
            } catch (e: Exception) {
                hideLoadingProgressBar()
                showErrorMessage()

                log("Network error \n ${e.stackTrace}")
            }
        }
    }

    fun getNumberSelectedFromList(id: Int) {
        hideErrorMessage()

        viewModelScope.launch {
            try {
                val number = getNumberSelectedFromList_UseCase.execute(id)
                showNumber(number)
            } catch (e: Exception) {
                log("Database error \n ${e.stackTrace}")
            }
        }
    }

    suspend fun saveNumberToDatabase(number: Number) {
        val saveResult = saveNumberToDatabase_UseCase.execute(number)
        if (saveResult == -1L)
            showToast(R.string.present_fact)
    }

    fun showLoadingProgressBar() {
        _loading.value = true
    }

    fun hideLoadingProgressBar() {
        _loading.value = false
    }

    fun showErrorMessage() {
        _numbersLoadError.value = true
    }

    fun hideErrorMessage() {
        _numbersLoadError.value = false
    }

    fun showNumber(number: Number) {
        _numberLiveData.value = number
    }

    fun showToast(resource: Int) {
        _toast.value = resource;
    }
}