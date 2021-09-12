package com.example.pharmainc.presentation.ui.fragment.home.data

import com.example.pharmainc.presentation.common.viewModel.Dispatcher
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.PatientHandler

class PatientsDataDispatcher(private val handler: PatientHandler) : Dispatcher<List<Patient>> {

    override fun dispatch(item:List<Patient>) = handler.bindData(item)
}