package com.example.pharmainc.presentation.ui.fragment.home.action

import com.example.pharmainc.presentation.model.Patient

sealed class PatientAction {

    data class GoToDetail(val item: Patient) : PatientAction()
    object ShowLoading : PatientAction()
    object ShowError : PatientAction()
}