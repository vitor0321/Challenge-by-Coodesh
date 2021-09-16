package com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.HomeHandler

class HomeActionDispatcher(private val handler: HomeHandler) : Dispatcher<HomeAction> {

    override fun dispatch(item: HomeAction) = when (item) {
        is HomeAction.GoToDetail -> handler.goToDetail(item.item)
        HomeAction.ShowLoading -> handler.showLoading()
        HomeAction.ShowError -> handler.showError()
    }
}