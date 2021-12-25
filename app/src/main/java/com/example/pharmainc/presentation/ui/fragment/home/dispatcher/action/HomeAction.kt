package com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action

import com.example.core.domain.model.Patient

sealed class HomeAction {

    data class GoToDetail(val item: Patient) : HomeAction()
    object ShowLoading : HomeAction()
    object ShowError : HomeAction()
}