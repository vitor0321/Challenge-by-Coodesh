package com.example.core.domain.model

data class Patient(
    val id:Long = 0L,
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
    var numberHouse: Int = 0,
    val city: String = "",
    val state: String = "",
    val country: String = "",
)
