package com.example.core.data.repository

import androidx.paging.PagingSource
import com.example.core.domain.model.Patient


interface PatientRepository {

    fun getPatient(query: String): PagingSource<Int, Patient>
}