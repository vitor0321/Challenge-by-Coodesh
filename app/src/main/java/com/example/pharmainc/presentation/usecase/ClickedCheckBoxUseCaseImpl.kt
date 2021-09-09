package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.presentation.constants.FEMALE
import com.example.pharmainc.presentation.constants.MALE
import com.example.pharmainc.presentation.constants.NULL
import com.example.pharmainc.presentation.constants.TRUE_GENDER
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.data.db.entity.PatientEntity
import kotlin.coroutines.suspendCoroutine

class ClickedCheckBoxUseCaseImpl(
    private val checkGenderData: ItemCheckGenderData
) : ClickedCheckBoxUseCase {

    override suspend fun onClickedCheckBox(listPatient: List<PatientEntity>): List<PatientEntity> {
        return suspendCoroutine { continuation ->
            val checkGender = checkGenderData.getCheckGenderData()
            checkGender?.let {
                when {
                    checkGender.female == TRUE_GENDER && checkGender.male != TRUE_GENDER -> {
                        continuation.resumeWith(Result.success(checkList(listPatient, FEMALE)))
                    }
                    checkGender.female != TRUE_GENDER && checkGender.male == TRUE_GENDER -> {
                        continuation.resumeWith(Result.success(checkList(listPatient, MALE)))
                    }
                    checkGender.female != TRUE_GENDER && checkGender.male != TRUE_GENDER -> {
                        continuation.resumeWith(Result.success(checkList(listPatient, NULL)))
                    }
                    else -> continuation.resumeWith(Result.success(listPatient))
                }
            }
        }
    }

    private fun checkList(
        listPatient: List<PatientEntity>,
        gender: String?
    ): MutableList<PatientEntity> {
        val listMutable: MutableList<PatientEntity> = mutableListOf()
        when (gender) {
            NULL -> listMutable
            else -> listPatient.map { patient ->
                if (gender == patient.gender) {
                    listMutable.add(patient)
                }
            }
        }
        return listMutable
    }
}