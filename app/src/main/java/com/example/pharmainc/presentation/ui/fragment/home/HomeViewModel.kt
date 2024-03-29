package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCase
import com.example.pharmainc.presentation.common.viewModel.ActionViewModel
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action.HomeAction
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val clickedCheckBoxUseCase: ClickedCheckBoxUseCase,
    private val patientRepositoryUseCase: PatientRepositoryUseCase,
    private val searchingNationalityUseCase: SearchingNationalityUseCase,
) : ActionViewModel<HomeAction, Patient>() {

    private var controlApi: Boolean = ACTIVE
    private var searchingNat: String? = NULL
    private var listPatient: MutableList<Patient> = mutableListOf()

    fun getPatientDao() = viewModelScope.launch {
        patientRepositoryUseCase.getAllPatientDao().apply {
            when (this) {
                is ResultType.Success -> {
                    if (this.data.isEmpty()) {
                        getResultAPI()
                    } else {
                        setListAndFilter(this.data)
                    }
                }
                is ResultType.Error -> {
                    dispatchAction(HomeAction.ShowLoading)
                    Log.e(TAG_DAO_VIEW_MODEL, this.error.toString())
                }
            }
        }
    }

    private fun getResultAPI() = viewModelScope.launch {
        if (controlApi) {
            controlApi = INACTIVE
            patientRepositoryUseCase.getResultApi().apply {
                when (this) {
                    is ResultType.Success -> savePatientDao(this.data)
                    is ResultType.Error -> {
                        dispatchAction(HomeAction.ShowError)
                        controlApi = ACTIVE
                        Log.e(TAG_API_VIEW_MODEL, this.error.toString())
                    }
                }
            }
        }
    }

    private fun savePatientDao(patients: List<Patient>) {
        viewModelScope.launch {
            patientRepositoryUseCase.addPatientDao(patients).apply {
                when (this) {
                    is ResultType.Success -> getPatientDao()
                    is ResultType.Error -> Log.e(TAG_API_VIEW_MODEL, this.error.toString())
                }
            }
        }
    }

    private fun setListAndFilter(patient: List<Patient>) {
        setListPatient(patient)
        filterSearching(EMPTY)
    }

    private fun setListPatient(list: List<Patient>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }

    fun filterSearching(searching: String) = viewModelScope.launch {
        searchingNat = searching
        searchingNationalityUseCase.searchingNationality(listPatient, searching).run {
            clickedCheckBoxUseCase.onClickedCheckBox(this).run {
                dispatchData(this)
            }
        }
    }

    fun scrollLoading(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (searchingNat == EMPTY) {
            controlApi = ACTIVE
            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                dispatchAction(HomeAction.ShowLoading)
                Handler(Looper.getMainLooper()).postDelayed({
                    getResultAPI()
                }, LOADING_TIME_OUT)
            }
        }
    }

    fun goToDetail(itemPatient: Patient) {
        dispatchAction(HomeAction.GoToDetail(itemPatient))
    }
}