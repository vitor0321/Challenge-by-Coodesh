package com.example.pharmainc.domain.mapper.dao

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.model.Patient

class PatientEntityMapperUseCaseImpl :
    PatientEntityMapper<Patient, PatientEntity>,
    PatientEntityMapperUseCase {

    override fun mapFromEntityDao(entityDao: Patient): PatientEntity {
        return PatientEntity(
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

    override fun fromEntityDaoList(initial: List<Patient>): List<PatientEntity> {
        return initial.map { mapFromEntityDao(it) }
    }
}