package com.example.pharmainc.presentation.ui.fragment.home.action

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.pharmainc.presentation.ui.PatientHandler

class PatientActionDispatcher(private val handler: PatientHandler) : Dispatcher<PatientAction> {

    override fun dispatch(item: PatientAction) = when (item) {
        is PatientAction.GoToDetail -> handler.goToDetail(item.item)
        PatientAction.ShowLoading -> handler.showLoading()
        PatientAction.ShowError -> handler.showError()
    }
}