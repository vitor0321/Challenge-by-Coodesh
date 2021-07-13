package com.example.pharmainc.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.constants.ERROR_400
import com.example.pharmainc.constants.ERROR_401
import com.example.pharmainc.constants.ERROR_500
import com.example.pharmainc.dataApi.PatientResult
import com.example.pharmainc.dataApi.repository.PatientRepository

class HomeViewModel(
    private val apiRepository: PatientRepository
) : ViewModel() {

    init {
        getPatient()
    }

    val errorLiveData: MutableLiveData<Int> = MutableLiveData()

    private fun getPatient() {
        apiRepository.getPatient { result: PatientResult ->
            when (result) {
                is PatientResult.Success -> result.patient
                is PatientResult.ApiError -> when (result.statusCode) {
                    401 -> errorLiveData.value = ERROR_401
                    else -> errorLiveData.value = ERROR_400
                }
                is PatientResult.ServerError -> errorLiveData.value = ERROR_500
            }
        }
    }

}