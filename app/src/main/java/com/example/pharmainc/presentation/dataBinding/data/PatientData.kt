package com.example.pharmainc.presentation.dataBinding.data

import androidx.lifecycle.MutableLiveData
import com.example.pharmainc.data.db.entity.PatientEntity

class PatientData(
    private var patient: PatientEntity = PatientEntity(),
    val idName: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.idIdentification
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
    val email: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.email
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
    val numberHouse: MutableLiveData<Int> = MutableLiveData<Int>().also {
        it.value = patient.numberHouse
    },
    val city: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.city
    },
    val state: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.state
    },
    val country: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = patient.country
    }
) {
    fun setItemPatientData(patient: PatientEntity?) {
        patient?.let { this.patient = patient }
        patient?.idIdentification?.let { idName.postValue(this.patient.idIdentification) }
        patient?.title?.let { title.postValue(this.patient.title) }
        patient?.name?.let { name.postValue(this.patient.name) }
        patient?.lastName?.let { lastName.postValue(this.patient.lastName) }
        patient?.email?.let { email.postValue(this.patient.email) }
        patient?.image?.let { image.postValue(this.patient.image) }
        patient?.gender?.let { gender.postValue(this.patient.gender) }
        patient?.birthdate?.let { birthdate.postValue(this.patient.birthdate) }
        patient?.phone?.let { phone.postValue(this.patient.phone) }
        patient?.nationality?.let { nationality.postValue(this.patient.nationality) }
        patient?.street?.let { street.postValue(this.patient.street) }
        patient?.numberHouse?.let { numberHouse.postValue(this.patient.numberHouse) }
        patient?.city?.let { city.postValue(this.patient.city) }
        patient?.state?.let { state.postValue(this.patient.state) }
        patient?.country?.let { country.postValue(this.patient.country) }
    }
}