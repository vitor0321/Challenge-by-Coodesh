package com.example.pharmainc.domain.mapper.network

interface ResultMapper<EntityApi, DomainModel> {
    fun mapFromEntityApi(entityApi: EntityApi): DomainModel
}