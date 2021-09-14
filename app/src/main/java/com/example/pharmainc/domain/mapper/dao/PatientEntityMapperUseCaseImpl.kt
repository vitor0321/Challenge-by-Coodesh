package com.example.pharmainc.domain.mapper.dao

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.model.Patient

class PatientEntityMapperUseCaseImpl :
    PatientEntityMapper<PatientEntity, Patient>,
    PatientEntityMapperUseCase {

    override fun mapFromEntityDao(entityDao: PatientEntity): Patient {
        return Patient(
            idIdentification = entityDao.idIdentification,
            title = entityDao.title,
            name = entityDao.name,
            lastName = entityDao.lastName,
            email = entityDao.email,
            image = entityDao.image,
            gender = entityDao.gender,
            birthdate = entityDao.birthdate,
            phone = entityDao.phone,
            nationality = entityDao.nationality,
            street = entityDao.street,
            numberHouse = entityDao.numberHouse,
            city = entityDao.city,
            state = entityDao.state,
            country = entityDao.country,
        )
    }

    override fun fromEntityDaoList(initial: List<PatientEntity>): List<Patient> {
        return initial.map { mapFromEntityDao(it) }
    }
}