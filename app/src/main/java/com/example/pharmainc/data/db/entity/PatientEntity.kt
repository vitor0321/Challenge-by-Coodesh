package com.example.pharmainc.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "patientEntity")
@Parcelize
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
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
): Parcelable
