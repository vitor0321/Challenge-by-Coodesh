package com.example.pharmainc.presentation.extensions

import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData

fun onClickedCheckBox(
    listPatient: List<Patient>,
    checkGenderData: ItemCheckGenderData,
    callbackList: (callBack: List<Patient>) -> Unit
) {
    val checkGender = checkGenderData.getCheckGenderData()
    when {
        checkGender?.female == TRUE_GENDER && checkGender.male != TRUE_GENDER ->
            getListWithGender(listPatient, FEMALE, NULL) { list ->
                callbackList(list)
            }
        checkGender?.female != TRUE_GENDER && checkGender?.male == TRUE_GENDER ->
            getListWithGender(listPatient, MALE, NULL){ list ->
                callbackList(list)
            }
        checkGender?.female == TRUE_GENDER && checkGender.male == TRUE_GENDER ->
            getListWithGender(listPatient, NULL, TRUE_GENDER){ list ->
                callbackList(list)
            }
        else -> getListWithGender(listPatient, NULL, FALSE_GENDER){ list ->
            callbackList(list)
        }
    }
}

private fun getListWithGender(
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

fun searchingNationality(
    listPatient: List<Patient>,
    searching: String,
    checkGenderData: ItemCheckGenderData,
    callbackList: (callBack: List<Patient>) -> Unit
) {
    val listFilter: MutableList<Patient> = mutableListOf()
    for (item: Patient in listPatient) {
        if (item.nationality.lowercase().contains(searching.lowercase())) {
            listFilter.add(item)
        }
    }
    onClickedCheckBox(listFilter, checkGenderData) {
        callbackList(it)
    }
}



