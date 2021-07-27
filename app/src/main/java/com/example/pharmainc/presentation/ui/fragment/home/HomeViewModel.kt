package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.domain.flag.ControlApi
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientRepository
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.extensions.onClickedCheckBox
import java.lang.Boolean.FALSE

class HomeViewModel(
    private val apiRepository: PatientRepository,
    private val mapper: ResultNetworkMapper,
    val checkGenderData: ItemCheckGenderData,
) : ViewModel() {

    val returnApiLiveData: MutableLiveData<Pair<Int?, List<Patient>?>> = MutableLiveData()
    val returnCheckBoxGender: MutableLiveData<List<Patient>> = MutableLiveData()
    private var flagControlApi = ControlApi()

    fun getPatient() {
        when (flagControlApi.enabled) {
            TRUE -> {
                flagControlApi.enabled = FALSE
                apiRepository.getPatient { result: PatientResult ->
                    when (result) {
                        is PatientResult.Success -> {
                            mapper.fromEntityApiList(result.patient).apply {
                                returnApiLiveData.value = Pair(NULL, this)
                                flagControlApi.enabled = TRUE
                            }
                        }
                        is PatientResult.ApiError -> when (result.statusCode) {
                            401 -> returnApiLiveData.value = Pair(ERROR_401, NULL)
                            else -> returnApiLiveData.value = Pair(ERROR_400, NULL)
                        }
                        is PatientResult.ServerError -> returnApiLiveData.value =
                            Pair(ERROR_500, NULL)
                    }
                }
            }
        }
    }

    fun checkBoxGender(listPatient: List<Patient>) {
        returnCheckBoxGender.value =
            onClickedCheckBox(listPatient, checkGenderData.female.value, checkGenderData.male.value)
    }
}

