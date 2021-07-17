package com.example.pharmainc.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.constants.ERROR_400
import com.example.pharmainc.constants.ERROR_401
import com.example.pharmainc.constants.ERROR_500
import com.example.pharmainc.dataApi.PatientResult
import com.example.pharmainc.dataApi.model.Result
import com.example.pharmainc.dataApi.repository.PatientRepository

class HomeViewModel(
    private val apiRepository: PatientRepository
) : ViewModel() {

    init {
        getPatient()
    }

    val errorLiveData: MutableLiveData<Pair<Int?, List<Result>?>> = MutableLiveData()

    private fun getPatient() {
        apiRepository.getPatient { result: PatientResult ->
            when (result) {
                is PatientResult.Success -> Pair(null, result.patient)
                is PatientResult.ApiError -> when (result.statusCode) {
                    401 -> errorLiveData.value = Pair(ERROR_401, null)
                    else -> errorLiveData.value = Pair(ERROR_400, null)
                }
                is PatientResult.ServerError -> errorLiveData.value = Pair(ERROR_500, null)
            }
        }
    }

}