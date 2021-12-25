package com.example.core.data.repository

interface PatientRemoteDataSource<T> {

    suspend fun fetchCharacter(queries: Map<String, String>): T
}