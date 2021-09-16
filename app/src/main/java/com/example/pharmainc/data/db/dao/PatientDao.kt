package com.example.pharmainc.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pharmainc.data.db.entity.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity): Long

    @Query("SELECT * FROM patientDB")
    fun getAll(): Flow<List<PatientEntity>>
}