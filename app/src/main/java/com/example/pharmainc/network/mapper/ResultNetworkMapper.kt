package com.example.pharmainc.network.mapper

import com.example.pharmainc.domain.mapper.ResultMapper
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.model.Result

class ResultNetworkMapper : ResultMapper<Result, Patient> {
    override fun mapFromEntityApi(entityApi: Result): Patient {
        return Patient(
            idIdentification = entityApi.id.name,
            title = entityApi.name.title,
            name = entityApi.name.first,
            lastName = entityApi.name.last,
            image = entityApi.picture.large,
            gender = entityApi.gender,
            birthdate = entityApi.dob.date,
            phone = entityApi.phone,
            nationality = entityApi.nat,
            street = entityApi.location.street.name,
            numberHouse = entityApi.location.street.number,
            city = entityApi.location.city,
            state = entityApi.location.state,
            country = entityApi.location.country,
        )
    }

    fun fromEntityApiList(initial: List<Result>): List<Patient> {
        return initial.map { mapFromEntityApi(it) }
    }
}