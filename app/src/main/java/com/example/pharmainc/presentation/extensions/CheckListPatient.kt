package com.example.pharmainc.presentation.extensions

import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.model.Patient

class CheckListPatient(private val checkGenderData: ItemCheckGenderData) {

    fun searchingNationality(
        listPatient: List<Patient>,
        searching: String,
        callbackList: (callBack: List<Patient>) -> Unit
    ) {
        val listFilter: MutableList<Patient> = mutableListOf()
        for (item: Patient in listPatient) {
            if (item.nationality.lowercase().contains(searching.lowercase())) {
                listFilter.add(item)
            }
        }
        onClickedCheckBox(listFilter) {
            callbackList(it)
        }
    }

    fun onClickedCheckBox(
        listPatient: List<Patient>,
        callbackList: (callBack: List<Patient>) -> Unit
    ) {
        val checkGender = checkGenderData.getCheckGenderData()
        when {
            checkGender?.female == TRUE_GENDER && checkGender.male != TRUE_GENDER ->
                getListWithGender(listPatient, FEMALE, NULL) { list ->
                    callbackList(list)
                }
            checkGender?.female != TRUE_GENDER && checkGender?.male == TRUE_GENDER ->
                getListWithGender(listPatient, MALE, NULL) { list ->
                    callbackList(list)
                }
            checkGender?.female == TRUE_GENDER && checkGender.male == TRUE_GENDER ->
                getListWithGender(listPatient, NULL, TRUE_GENDER) { list ->
                    callbackList(list)
                }
            else -> getListWithGender(listPatient, NULL, FALSE_GENDER) { list ->
                callbackList(list)
            }
        }
    }
}