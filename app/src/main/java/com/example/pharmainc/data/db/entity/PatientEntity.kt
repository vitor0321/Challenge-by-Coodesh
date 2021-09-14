package com.example.pharmainc.data.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "patient")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "idIdentification")
    val idIdentification: String? = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "lastName")
    val lastName: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "gender")
    val gender: String = "",
    @ColumnInfo(name = "birthdate")
    val birthdate: String = "",
    @ColumnInfo(name = "phone")
    val phone: String = "",
    @ColumnInfo(name = "nationality")
    val nationality: String = "",
    @ColumnInfo(name = "street")
    val street: String = "",
    @ColumnInfo(name = "numberHouse")
    val numberHouse: Int = 0,
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "state")
    val state: String = "",
    @ColumnInfo(name = "country")
    val country: String = "",
) : Parcelable