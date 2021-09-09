package com.example.pharmainc.presentation.ui

import com.example.pharmainc.data.db.entity.PatientEntity

interface PatientHandler {

    fun goToDetail(data: PatientEntity)
    fun bindData(data: List<PatientEntity>)
    fun showLoading()
    fun showError()
}