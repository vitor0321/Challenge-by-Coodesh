package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCase
import com.example.pharmainc.presentation.common.viewModel.ActionViewModel
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientAction
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val resultMapperUseCase: ResultMapperUseCase,
    private val searchingNationalityUseCase: SearchingNationalityUseCase,
    private val clickedCheckBoxUseCase: ClickedCheckBoxUseCase,
    private val patientRepositoryUseCase: PatientRepositoryUseCase
) : ActionViewModel<PatientAction, Patient>() {

    private var controlApi: Boolean = ACTIVE
    private var searchingNat: String? = NULL
    private var listPatient: MutableList<Patient> = mutableListOf()

    init {
        getResultAPI()
    }

    private fun getResultAPI() = viewModelScope.launch {
        if (controlApi) {
            controlApi = INACTIVE
            patientRepositoryUseCase.getResultApi().apply {
                when (this) {
                    is ResultType.Success ->
                        resultMapperUseCase.fromEntityApiList(this.data).apply {
                            setList(this)
                            filterSearching(EMPTY)
                        }
                    is ResultType.Error -> {
                        dispatchAction(PatientAction.ShowError)
                        controlApi = ACTIVE
                        Log.e(TAG_HOME_VIEW_MODEL, this.error.toString())
                    }
                }
            }
        }
    }

    fun scrollLoading(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (searchingNat == EMPTY) {
            controlApi = ACTIVE
            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                dispatchAction(PatientAction.ShowLoading)
                Handler(Looper.getMainLooper()).postDelayed({
                    getResultAPI()
                }, LOADING_TIME_OUT)
            }
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

    private fun setList(list: List<Patient>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }

    fun goToDetail(itemPatient: Patient) {
        dispatchAction(PatientAction.GoToDetail(itemPatient))
    }
}