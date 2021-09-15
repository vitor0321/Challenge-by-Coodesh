package com.example.pharmainc.domain.mapper.dao

interface PatientMapper<Patient, DomainModel> {

    fun mapFromViewPatient(patient: Patient): DomainModel
}