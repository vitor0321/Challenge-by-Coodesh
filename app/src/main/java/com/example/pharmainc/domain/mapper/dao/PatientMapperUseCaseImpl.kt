package com.example.pharmainc.domain.mapper.dao

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.model.Patient

class PatientMapperUseCaseImpl :
    PatientMapper<PatientEntity, Patient>,
    PatientMapperUseCase {

    override fun mapFromViewPatient(patient: PatientEntity): Patient {
        return Patient(
            id = patient.id,
            idIdentification = patient.idIdentification,
            title = patient.title,
            name = patient.name,
            lastName = patient.lastName,
            email = patient.email,
            image = patient.image,
            gender = patient.gender,
            birthdate = patient.birthdate,
            phone = patient.phone,
            nationality = patient.nationality,
            street = patient.street,
            numberHouse = patient.numberHouse,
            city = patient.city,
            state = patient.state,
            country = patient.country,
        )
    }

    override fun fromViewList(initial: List<PatientEntity>): List<Patient> {
        return initial.map { mapFromViewPatient(it) }
    }
}