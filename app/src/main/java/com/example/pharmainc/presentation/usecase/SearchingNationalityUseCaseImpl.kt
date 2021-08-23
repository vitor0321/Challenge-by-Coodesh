package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.presentation.model.Patient
import kotlin.coroutines.suspendCoroutine

class SearchingNationalityUseCaseImpl : SearchingNationalityUseCase {

    override suspend fun searchingNationality(
        listPatient: List<Patient>,
        searching: String?,
    ): List<Patient> {
        return suspendCoroutine { continuation ->
            val listFilter: MutableList<Patient> = mutableListOf()
            searching?.let {
                listPatient.map { patient ->
                    if (patient.nationality.lowercase().contains(searching.lowercase())
                    ) {
                        listFilter.add(patient)
                    }
                }
                continuation.resumeWith(Result.success(listFilter))
            } ?: run {
                continuation.resumeWith(Result.success(listPatient))
            }

        }
    }
}
