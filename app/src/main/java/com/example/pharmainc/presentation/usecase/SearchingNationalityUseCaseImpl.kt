package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.presentation.constants.EMPTY
import com.example.core.domain.model.Patient
import kotlin.coroutines.suspendCoroutine

class SearchingNationalityUseCaseImpl : SearchingNationalityUseCase {

    override suspend fun searchingNationality(
        listPatient: List<Patient>,
        searching: String,
    ): List<Patient> {
        return suspendCoroutine { continuation ->
            continuation.resumeWith(Result.success(searchingList(searching, listPatient)))
        }
    }

    private fun searchingList(searching: String, listPatient: List<Patient>): List<Patient> {
        val listFilter: MutableList<Patient> = mutableListOf()
        return when (searching) {
            EMPTY -> listPatient
            else -> {
                listPatient.map { patient ->
                    if (patient.nationality.lowercase().contains(searching.lowercase())) {
                        listFilter.add(patient)
                    }
                }
                listFilter
            }
        }
    }
}
