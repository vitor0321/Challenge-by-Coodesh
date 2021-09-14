package com.example.pharmainc.presentation.ui.activity

sealed interface PharmaHandler {

    fun screenItems()
    fun screenFull()
    fun filterGender()
}