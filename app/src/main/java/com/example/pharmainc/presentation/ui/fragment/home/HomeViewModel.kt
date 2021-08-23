package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.constants.*
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

    private val _apiErrorLiveData = MutableLiveData<Int>()
    val apiErrorLiveData: LiveData<Int> get() = _apiErrorLiveData

    private val _childLiveData = MutableLiveData<Int>()
    val childLiveData: LiveData<Int> get() = _childLiveData

    private val _loadingRecycle = MutableLiveData<Int>()
    val loadingRecycle: LiveData<Int> get() = _loadingRecycle

    private val _listPatientLiveData = MutableLiveData<List<Patient>>()
    val listPatientLiveData: LiveData<List<Patient>> get() = _listPatientLiveData

    private var controlApi: Boolean = ACTIVE
    private var searchingNat: String? = NULL
    private var listPatient: MutableList<Patient> = mutableListOf()

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
                _apiErrorLiveData.value = ERROR_401
                controlApi = ACTIVE
            }
        }
    }

    fun scrollLoading(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if(searchingNat == EMPTY){
            controlApi = ACTIVE
            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                _loadingRecycle.value = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    getPatients()
                    _loadingRecycle.value = View.GONE
                }, LOADING_TIME_OUT)
            }
        }
    }

    fun filterSearching(searching: String) = viewModelScope.launch {
        searchingNat = searching
        searchingNationality.searchingNationality(listPatient, searching).run {
            clickedCheckBox.onClickedCheckBox(this).run {
                _listPatientLiveData.value = this
            }
        }
    }

    fun viewFlipperControl(child: Int) {
        when (child) {
            CHILD_FIRST -> {
                _childLiveData.value = child
            }
            CHILD_SECOND -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    _childLiveData.value = child
                }, LOADING_TIME_OUT)
            }
        }
    }

    private fun setList(list: List<Patient>) {
        list.map { patient ->
            listPatient.add(patient)
        }
    }
}