package com.example.pharmainc.domain.mapper.network

import android.annotation.SuppressLint
import com.example.pharmainc.domain.model.modelnetwork.Result
import com.example.pharmainc.presentation.model.Patient
import java.text.SimpleDateFormat

class ResultMapperUseCaseImpl : ResultMapper<Result, Patient>, ResultMapperUseCase {

    override fun mapFromEntityApi(entityApi: Result): Patient {
        val valueDate: String = changeDate(entityApi.dob.date)
        return Patient(
            idIdentification = entityApi.id.value,
            title = entityApi.name.title,
            name = entityApi.name.first,
            lastName = entityApi.name.last,
            email = entityApi.email,
            image = entityApi.picture.large,
            gender = entityApi.gender,
            birthdate = valueDate,
            phone = entityApi.phone,
            nationality = entityApi.nat,
            street = entityApi.location.street.name,
            numberHouse = entityApi.location.street.number,
            city = entityApi.location.city,
            state = entityApi.location.state,
            country = entityApi.location.country,
        )
    }

    override fun fromEntityApiList(initial: List<Result>): List<Patient> {
        return initial.map { mapFromEntityApi(it) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun changeDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(parser.parse(date))
    }
}