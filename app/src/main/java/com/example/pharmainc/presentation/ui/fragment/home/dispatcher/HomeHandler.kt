package com.example.pharmainc.presentation.ui.fragment.home.dispatcher

import com.example.pharmainc.presentation.model.Patient

interface HomeHandler {

    fun goToDetail(data: Patient)
    fun bindData(data: List<Patient>)
    fun showLoading()
    fun showError()
}