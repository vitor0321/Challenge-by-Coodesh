package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.usecase.dao.PatientDAOUseCase
import com.example.pharmainc.domain.usecase.network.GetResultUseCase
import com.example.pharmainc.presentation.common.viewModel.ActionViewModel
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientAction
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getResultUseCase: GetResultUseCase,
    private val resultMapperUseCase: ResultMapperUseCase,
    private val searchingNationalityUseCase: SearchingNationalityUseCase,
    private val clickedCheckBoxUseCase: ClickedCheckBoxUseCase,
    private val patientDAOUseCase: PatientDAOUseCase
) : ActionViewModel<PatientAction, PatientEntity>() {

    private var controlApi: Boolean = ACTIVE
    private var searchingNat: String? = NULL
    private var listPatient: MutableList<PatientEntity> = mutableListOf()

    init {
        getPatientsSalveBD()
    }

    private fun getPatientsSalveBD() = viewModelScope.launch {
        if (controlApi) {
            controlApi = INACTIVE
            try {
                getResultUseCase().run {
                    resultMapperUseCase.fromEntityApiList(this).apply {
                        this.map { patient ->
                            patientDAOUseCase.insertPatient(patient)
                        }
                    }
                }
            } catch (e: Exception) {
                dispatchAction(PatientAction.ShowError)
                controlApi = ACTIVE
                Log.e(TAG_HOME_VIEW_MODEL, e.toString())
            }
        }
    }

    private fun getPatients() = viewModelScope.launch {
        if (controlApi) {
            controlApi = INACTIVE
            try {
                getResultUseCase().run {
                    resultMapperUseCase.fromEntityApiList(this).apply {
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
                    getPatientsSalveBD()
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

    private fun setList(list: List<PatientEntity>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }

    fun goToDetail(itemPatient: PatientEntity) {
        dispatchAction(PatientAction.GoToDetail(itemPatient))
    }
}