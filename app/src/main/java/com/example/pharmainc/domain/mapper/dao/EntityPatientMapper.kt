package com.example.pharmainc.domain.mapper.dao

interface EntityPatientMapper<Patient, DomainModel> {

    fun mapFromViewPatient(patient: Patient): DomainModel
}