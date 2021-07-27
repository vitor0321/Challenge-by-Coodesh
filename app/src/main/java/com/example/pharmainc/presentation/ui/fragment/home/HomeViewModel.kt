package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientRepository
import com.example.pharmainc.presentation.constants.ERROR_400
import com.example.pharmainc.presentation.constants.ERROR_401
import com.example.pharmainc.presentation.constants.ERROR_500
import com.example.pharmainc.presentation.constants.NULL
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.ui.onClickedCheckBox

class HomeViewModel(
    private val apiRepository: PatientRepository,
    private val mapper: ResultNetworkMapper,
    val checkGenderData: ItemCheckGenderData,
) : ViewModel() {

    val returnApiLiveData: MutableLiveData<Pair<Int?, List<Patient>?>> = MutableLiveData()
    val returnCheckBoxGender: MutableLiveData<List<Patient>> = MutableLiveData()

    fun getPatient() {
        apiRepository.getPatient { result: PatientResult ->
            when (result) {
                is PatientResult.Success -> {
                    mapper.fromEntityApiList(result.patient).apply {
                        returnApiLiveData.value = Pair(NULL, this)
                    }
                }
                is PatientResult.ApiError -> when (result.statusCode) {
                    401 -> returnApiLiveData.value = Pair(ERROR_401, NULL)
                    else -> returnApiLiveData.value = Pair(ERROR_400, NULL)
                }
                is PatientResult.ServerError -> returnApiLiveData.value = Pair(ERROR_500, NULL)
            }
        }
    }

    fun checkBoxGender(listPatient: List<Patient>) {
        returnCheckBoxGender.value =
            onClickedCheckBox(listPatient, checkGenderData.female.value, checkGenderData.male.value)
    }
}

