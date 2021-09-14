package com.example.pharmainc.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.data.db.entity.PatientEntity

@Database(entities = [PatientEntity::class], version = 1)
abstract class PatientDatabase : RoomDatabase() {

    abstract val patientDao: PatientDao

    companion object {

        @Volatile
        private var INSTANCE: PatientDatabase? = null

        fun getInstance(context: Context): PatientDatabase {
            synchronized(this) {
                var instance: PatientDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        PatientDatabase::class.java,
                        "patient_database"
                    ).build()
                }
                return instance
            }
        }
    }
}