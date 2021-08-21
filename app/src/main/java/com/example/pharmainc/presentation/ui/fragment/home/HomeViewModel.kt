package com.example.pharmainc.presentation.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.constants.ACTIVE
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.ERROR_401
import com.example.pharmainc.presentation.constants.INACTIVE
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPatientUseCase: GetPatientUseCase,
    private val mapper: ResultMapperUseCase,
    private val searchingNationality: SearchingNationalityUseCase,
    private val clickedCheckBox: ClickedCheckBoxUseCase
) : ViewModel() {

    private val _apiErrorLiveData = MutableLiveData<Int?>()
    val apiErrorLiveData: LiveData<Int?> get() = _apiErrorLiveData

    private val _listPatientLiveData = MutableLiveData<List<Patient>>()
    val listPatientLiveData: LiveData<List<Patient>> get() = _listPatientLiveData

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

    fun filterSearching(searching: String) = viewModelScope.launch {
        searching.setSetting()
        searchingNationality.searchingNationality(listPatient, searching).run {
            clickedCheckBox.onClickedCheckBox(this).run {
                _listPatientLiveData.value = this
            }
        }
    }

    fun checkBoxGender() {
        checkListGender { list ->
            _listPatientLiveData.value = list
            controlApiLiveData = ACTIVE
        }
    }

    private fun checkListGender(
        callbackList: (callBack: List<Patient>) -> Unit
    ) = viewModelScope.launch {
        clickedCheckBox.onClickedCheckBox(listPatient).run {
            callbackList(this)
        }
    }

    private fun setList(list: List<Patient>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }

    private fun String.setSetting() {
        controlApiLiveData = when {
            this != EMPTY -> INACTIVE
            else -> ACTIVE
        }
    }
}