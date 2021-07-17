package com.example.pharmainc.ui.dataBinding

import androidx.lifecycle.MutableLiveData
import com.example.pharmainc.ui.model.ItemPatient

class ItemPatientData(
    private var patient: ItemPatient = ItemPatient(),
    val idName: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.idName
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
    val phone: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.phone
    },
    val nationality: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.nationality
    },
    val street: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.street
    },
    val city: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.city
    },
    val state: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.state
    },
    val country: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.country
    },
    val email: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.email
    }
) {
    fun setItemPatientData(patient: ItemPatient?) {
        patient?.let { this.patient = patient }
        patient?.idName?.let { idName.postValue(this.patient.idName) }
        patient?.title?.let { title.postValue(this.patient.title) }
        patient?.name?.let { name.postValue(this.patient.name) }
        patient?.lastName?.let { lastName.postValue(this.patient.lastName) }
        patient?.image?.let { image.postValue(this.patient.image) }
        patient?.gender?.let { gender.postValue(this.patient.gender) }
        patient?.birthdate?.let { birthdate.postValue(this.patient.birthdate) }
        patient?.phone?.let { phone.postValue(this.patient.phone) }
        patient?.nationality?.let { nationality.postValue(this.patient.nationality) }
        patient?.street?.let { street.postValue(this.patient.street) }
        patient?.city?.let { city.postValue(this.patient.city) }
        patient?.state?.let { state.postValue(this.patient.state) }
        patient?.country?.let { state.postValue(this.patient.country) }
        patient?.email?.let { email.postValue(this.patient.email) }
    }
}