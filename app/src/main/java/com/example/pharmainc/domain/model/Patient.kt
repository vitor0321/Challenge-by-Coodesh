package com.example.pharmainc.domain.model

data class Patient(
    val idIdentification: String? = "",
    val title: String = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val gender: String = "",
    val birthdate: String = "",
    val phone: String = "",
    val nationality: String = "",
    val street: String = "",
    val numberHouse: Int? = null,
    val city: String = "",
    val state: String = "",
    val country: String = "",
)
