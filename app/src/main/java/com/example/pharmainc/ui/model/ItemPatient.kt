package com.example.pharmainc.ui.model

data class ItemPatient(
    val idName: String = "",
    val title: String = "",
    val name: String = "",
    val lastName: String = "",
    val image: String = "",
    val gender: String = "",
    val birthdate: String = "",
    val phone: String = "",
    val nationality: String = "",
    val street: String = "",
    val numberStreet: Int? = null,
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val email: String = ""
)
