package com.example.pharmainc.domain.mapper

interface ResultMapper<EntityApi, DomainModel> {
    fun mapFromEntityApi(entityApi: EntityApi): DomainModel
}