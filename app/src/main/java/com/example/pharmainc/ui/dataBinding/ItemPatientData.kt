package com.example.pharmainc.ui.dataBinding

import androidx.lifecycle.MutableLiveData
import com.example.pharmainc.ui.model.ItemPatient

class ItemPatientData(
    private var patient: ItemPatient = ItemPatient(),
    val id: MutableLiveData<Int> = MutableLiveData<Int>().also {
        it.value = patient.id
    },
    val title: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.title
    },
    val name: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.name
    },
    val lastName: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.lastName
    },
    val image: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.image
    },
    val gender: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.gender
    },
    val birthdate: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.birthdate
    },
    val phone: MutableLiveData<Int> = MutableLiveData<Int>().also {
        it.value = patient.phone
    },
    val nationality: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.nationality
    },
    val address: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.address
    }
) {
    fun setItemPatientData(patient: ItemPatient?) {
        patient?.let { this.patient = patient }
        patient?.id?.let { id.postValue(this.patient.id) }
        patient?.title?.let { title.postValue(this.patient.title) }
        patient?.name?.let { name.postValue(this.patient.name) }
        patient?.lastName?.let { lastName.postValue(this.patient.lastName) }
        patient?.image?.let { image.postValue(this.patient.image) }
        patient?.gender?.let { gender.postValue(this.patient.gender) }
        patient?.birthdate?.let { birthdate.postValue(this.patient.birthdate) }
        patient?.phone?.let { phone.postValue(this.patient.phone) }
        patient?.nationality?.let { nationality.postValue(this.patient.nationality) }
        patient?.address?.let { address.postValue(this.patient.address) }

    }
}