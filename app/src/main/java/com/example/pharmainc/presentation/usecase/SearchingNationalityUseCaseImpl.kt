package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.data.db.entity.PatientEntity
import kotlin.coroutines.suspendCoroutine

class SearchingNationalityUseCaseImpl : SearchingNationalityUseCase {

    override suspend fun searchingNationality(
        listPatient: List<PatientEntity>,
        searching: String,
    ): List<PatientEntity> {
        return suspendCoroutine { continuation ->
            continuation.resumeWith(Result.success(searchingList(searching, listPatient)))
        }
    }

    private fun searchingList(searching: String, listPatient: List<PatientEntity>): List<PatientEntity> {
        val listFilter: MutableList<PatientEntity> = mutableListOf()
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
