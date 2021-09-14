package com.example.pharmainc.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pharmainc.data.db.entity.PatientEntity

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: PatientEntity): Long

    @Update
    suspend fun update(patient: PatientEntity)

    @Query("DELETE FROM patient WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM patient")
    suspend fun deleteAll()

    @Query("SELECT * FROM patient")
    fun getAll(): LiveData<List<PatientEntity>>
}