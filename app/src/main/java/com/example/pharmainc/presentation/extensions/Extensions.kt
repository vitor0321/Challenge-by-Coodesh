package com.example.pharmainc.presentation.extensions

import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.presentation.constants.*

fun onClickedCheckBox(
    listPatient: List<Patient>,
    female: Boolean?,
    male: Boolean?
): List<Patient> {
    return when {
        female == TRUE_GENDER && male != TRUE_GENDER ->
            getListWithGender(listPatient, FEMALE, NULL)
        female != TRUE_GENDER && male == TRUE_GENDER ->
            getListWithGender(listPatient, MALE, NULL)
        female == TRUE_GENDER && male == TRUE_GENDER ->
            getListWithGender(listPatient, NULL, TRUE_GENDER)
        else -> getListWithGender(listPatient, NULL, FALSE_GENDER)
    }
}

private fun getListWithGender(
    listPatient: List<Patient>,
    gender: String?,
    check: Boolean?
): List<Patient> {
    val patientList: MutableList<Patient> = mutableListOf()
    listPatient.map { patient ->
        when (patient.gender) {
            gender -> patientList.add(patient)
            else -> {
                when (check) {
                    TRUE_GENDER -> patientList.add(patient)
                    else -> patientList
                }
            }
        }
    }
    return patientList
}

