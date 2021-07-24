package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientRepository
import com.example.pharmainc.presentation.constants.ERROR_400
import com.example.pharmainc.presentation.constants.ERROR_401
import com.example.pharmainc.presentation.constants.ERROR_500
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData

class HomeViewModel(
    private val apiRepository: PatientRepository,
    private val mapper: ResultNetworkMapper,
    private val checkGender: ItemCheckGenderData
) : ViewModel() {

    val returnApiLiveData: MutableLiveData<Pair<Int?, List<Patient>?>> = MutableLiveData()
    private val patientList: MutableList<Patient> = mutableListOf()

    fun getPatient() {
        apiRepository.getPatient { result: PatientResult ->
            when (result) {
                is PatientResult.Success -> {
                    mapper.fromEntityApiList(result.patient).apply {
                        returnApiLiveData.value = Pair(null, onClickedCheckBox(this))
                    }
                }
                is PatientResult.ApiError -> when (result.statusCode) {
                    401 -> returnApiLiveData.value = Pair(ERROR_401, null)
                    else -> returnApiLiveData.value = Pair(ERROR_400, null)
                }
                is PatientResult.ServerError -> returnApiLiveData.value = Pair(ERROR_500, null)
            }
        }
    }

    private fun onClickedCheckBox(list: List<Patient>): List<Patient> {
        list.map { patientList.add(it) }
        checkGender.female
        checkGender.male
        patientList
        return patientList
    }
}