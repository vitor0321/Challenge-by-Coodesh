package com.example.pharmainc.presentation.ui.activity.dispatcher.action

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.pharmainc.presentation.ui.activity.PharmaHandler

class PharmaActionDispatcher(private val pharmaHandler: PharmaHandler) : Dispatcher<PharmaAction> {

    override fun dispatch(item: PharmaAction) = when (item) {
        is PharmaAction.ShowActionBar -> pharmaHandler.screenItems()
        PharmaAction.HideActionBar -> pharmaHandler.screenFull()
        PharmaAction.FilterGender -> pharmaHandler.filterGender()
    }
}