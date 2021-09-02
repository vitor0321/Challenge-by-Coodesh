package com.example.pharmainc.presentation.ui.fragment.home.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.common.viewModel.ActionViewModel
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientAction
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPatientUseCase: GetPatientUseCase,
    private val mapper: ResultMapperUseCase,
    private val searchingNationality: SearchingNationalityUseCase,
    private val clickedCheckBox: ClickedCheckBoxUseCase
) : ActionViewModel<PatientAction, Patient>() {

    private var controlApi: Boolean = ACTIVE
    private var searchingNat: String? = NULL
    private var listPatient: MutableList<Patient> = mutableListOf()

    init {
        getPatients()
    }

    fun getPatients() = viewModelScope.launch {
        if (controlApi) {
            controlApi = INACTIVE
            try {
                getPatientUseCase().run {
                    mapper.fromEntityApiList(this).apply {
                        setList(this)
                        filterSearching(EMPTY)
                    }
                }
            } catch (e: Exception) {
                dispatchAction(PatientAction.ShowError)
                controlApi = ACTIVE
            }
        }
    }

    fun scrollLoading(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if(searchingNat == EMPTY){
            controlApi = ACTIVE
            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                dispatchAction(PatientAction.ShowLoading)
                Handler(Looper.getMainLooper()).postDelayed({
                    getPatients()
                }, LOADING_TIME_OUT)
            }
        }
    }

    fun filterSearching(searching: String) = viewModelScope.launch {
        searchingNat = searching
        searchingNationality.searchingNationality(listPatient, searching).run {
            clickedCheckBox.onClickedCheckBox(this).run {
                this?.let { patients ->
                    dispatchData(patients)
                }
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