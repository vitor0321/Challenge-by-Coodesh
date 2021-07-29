package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientRepository
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.extensions.onClickedCheckBox

class HomeViewModel(
    private val apiRepository: PatientRepository,
    private val mapper: ResultNetworkMapper,
    private val checkGenderData: ItemCheckGenderData,
) : ViewModel() {

    private val _apiLiveData = MutableLiveData<Pair<Int?, List<Patient>?>>()
    val apiLiveData: MutableLiveData<Pair<Int?, List<Patient>?>> get() = _apiLiveData

    private val _checkBoxGenderLiveData = MutableLiveData<List<Patient>>()
    val checkBoxGenderLiveData: MutableLiveData<List<Patient>> get() = _checkBoxGenderLiveData

    private val _filterLiveData = MutableLiveData<List<Patient>>()
    val filterLiveData: MutableLiveData<List<Patient>> get() = _filterLiveData

    private val _loadingLiveData = MutableLiveData<Int>()
    val loadingLiveData: MutableLiveData<Int> get() = _loadingLiveData

    private var controlApiLiveData: Boolean = ACTIVE

    fun getPatient() {
        when (controlApiLiveData) {
            ACTIVE -> {
                controlAccessApi(INACTIVE)
                apiRepository.getPatient { result: PatientResult ->
                    when (result) {
                        is PatientResult.Success -> {
                            mapper.fromEntityApiList(result.patient).apply {
                                _apiLiveData.value = Pair(NULL, this)
                                controlAccessApi(ACTIVE)
                            }
                        }
                        is PatientResult.ApiError -> when (result.statusCode) {
                            401 -> _apiLiveData.value = Pair(ERROR_401, NULL)
                            else -> _apiLiveData.value = Pair(ERROR_400, NULL)
                        }
                        is PatientResult.ServerError -> _apiLiveData.value =
                            Pair(ERROR_500, NULL)
                    }
                }
            }
        }
    }

    fun filter(searching: String, listPatient: List<Patient>) {
        searching.setSetting()
        val listFilter: MutableList<Patient> = mutableListOf()
        for (item: Patient in listPatient) {
            if (item.nationality.lowercase().contains(searching.lowercase())) {
                listFilter.add(item)
            }
        }
        _filterLiveData.value = listFilter
    }

    private fun String.setSetting() {
        _loadingLiveData.value = HIDE
        when {
            this != EMPTY -> controlAccessApi(INACTIVE)
            else -> controlAccessApi(ACTIVE)
        }
    }

    fun checkBoxGender(listPatient: List<Patient>) {
        checkList(listPatient) { list ->
            _checkBoxGenderLiveData.value = list
            controlAccessApi(ACTIVE)
        }
    }

    private fun checkList(
        listPatient: List<Patient>,
        callbackList: (callBack: List<Patient>) -> Unit
    ) {
        onClickedCheckBox(listPatient, checkGenderData) { list ->
            callbackList(list)
        }
    }

    private fun controlAccessApi(control: Boolean) {
        controlApiLiveData = control
    }
}

