package com.example.pharmainc.domain.mapper.dao

interface PatientEntityMapper<EntityDao, DomainModel> {

    fun mapFromEntityDao(entityDao: EntityDao): DomainModel
}