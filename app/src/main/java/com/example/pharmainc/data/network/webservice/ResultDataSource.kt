package com.example.pharmainc.data.network.webservice

import com.example.pharmainc.domain.model.modelnetwork.Result

interface ResultDataSource {

    suspend fun getResult(): List<Result>
}