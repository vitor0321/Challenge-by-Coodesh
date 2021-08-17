package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.domain.mapper.ResultNetworkMapper
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.constants.ACTIVE
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.ERROR_401
import com.example.pharmainc.presentation.constants.INACTIVE
import com.example.pharmainc.presentation.extensions.CheckListPatient
import com.example.pharmainc.presentation.model.Patient
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPatientUseCase: GetPatientUseCase,
    private val mapper: ResultNetworkMapper,
    private val checkListPatient: CheckListPatient,
) : ViewModel() {

    private val _apiErrorLiveData = MutableLiveData<Int?>()
    val apiErrorLiveData: MutableLiveData<Int?> get() = _apiErrorLiveData

    private val _apiListLiveData = MutableLiveData<List<Patient>>()
    val apiListLiveData: MutableLiveData<List<Patient>> get() = _apiListLiveData

    private val _filterLiveData = MutableLiveData<List<Patient>>()
    val filterLiveData: MutableLiveData<List<Patient>> get() = _filterLiveData

    private var controlApiLiveData: Boolean = ACTIVE
    private var listPatient: MutableList<Patient> = mutableListOf()

    fun getPatients() = viewModelScope.launch {
        if (controlApiLiveData) {
            controlApiLiveData = INACTIVE
            try {
                getPatientUseCase().run {
                    mapper.fromEntityApiList(this).apply {
                        setList(this)
                        checkBoxGender()
                    }
                }
            } catch (e: Exception) {
                _apiErrorLiveData.value = ERROR_401
            }
        }
    }

    private fun setList(list: List<Patient>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }

    fun filter(searching: String) {
        searching.setSetting()
        checkListPatient.searchingNationality(listPatient, searching) { list ->
            _filterLiveData.value = list
        }
    }

    fun checkBoxGender() {
        listPatient.checkList { list ->
            _apiListLiveData.value = list
            controlApiLiveData = ACTIVE
        }
    }

    private fun String.setSetting() {
        controlApiLiveData = when {
            this != EMPTY -> INACTIVE
            else -> ACTIVE
        }
    }

    private fun List<Patient>.checkList(
        callbackList: (callBack: List<Patient>) -> Unit
    ) {
        checkListPatient.onClickedCheckBox(this) { list ->
            callbackList(list)
        }
    }
}