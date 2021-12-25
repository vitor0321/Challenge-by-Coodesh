package com.example.pharmainc.presentation.ui.fragment.home.dispatcher.data

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.core.domain.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.HomeHandler

class HomeDataDispatcher(private val handler: HomeHandler) : Dispatcher<List<Patient>> {

    override fun dispatch(item:List<Patient>) = handler.bindData(item)
}