package com.example.pharmainc.presentation.ui

import com.example.pharmainc.presentation.model.Patient

interface PatientHandler {

    fun goToDetail(data: Patient)
    fun bindData(data: List<Patient>)
    fun showLoading()
    fun showError()
}