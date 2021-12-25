package com.example.pharmainc.presentation.ui.fragment.home.dispatcher

import com.example.core.domain.model.Patient

interface HomeHandler {

    fun goToDetail(data: Patient)
    fun bindData(data: List<Patient>)
    fun showLoading()
    fun showError()
}