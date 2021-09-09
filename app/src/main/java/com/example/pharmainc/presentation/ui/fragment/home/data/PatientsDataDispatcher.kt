package com.example.pharmainc.presentation.ui.fragment.home.data

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.ui.PatientHandler

class PatientsDataDispatcher(private val handler: PatientHandler) : Dispatcher<List<PatientEntity>> {

    override fun dispatch(item:List<PatientEntity>) = handler.bindData(item)
}