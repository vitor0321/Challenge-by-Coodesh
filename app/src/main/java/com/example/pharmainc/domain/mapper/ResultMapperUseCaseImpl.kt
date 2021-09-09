package com.example.pharmainc.domain.mapper

import android.annotation.SuppressLint
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.model.modelnetworl.Result
import java.text.SimpleDateFormat

class ResultMapperUseCaseImpl : ResultMapper<Result, PatientEntity>, ResultMapperUseCase {
    override fun mapFromEntityApi(entityApi: Result): PatientEntity {
        val valueDate: String = changeDate(entityApi.dob.date)
        return PatientEntity(
            id = 0,
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

    override fun fromEntityApiList(initial: List<Result>): List<PatientEntity> {
        return initial.map { mapFromEntityApi(it) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun changeDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(parser.parse(date))
    }
}