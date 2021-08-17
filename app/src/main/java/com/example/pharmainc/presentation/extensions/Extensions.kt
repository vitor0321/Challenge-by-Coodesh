package com.example.pharmainc.presentation.extensions

import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData

fun getListWithGender(
    listPatient: List<Patient>,
    gender: String?,
    check: Boolean?,
    callbackList: (callBack: List<Patient>) -> Unit
) {
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
        callbackList(patientList)
    }
}




