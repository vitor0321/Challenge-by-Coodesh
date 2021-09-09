package com.example.pharmainc.presentation.ui.fragment.home.action

import com.example.pharmainc.data.db.entity.PatientEntity

sealed class PatientAction {

    data class GoToDetail(val item: PatientEntity) : PatientAction()
    object ShowLoading : PatientAction()
    object ShowError : PatientAction()
}