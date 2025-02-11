package com.example.stepcounterapp.features.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import com.example.stepcounterapp.util.Converters

@Database(
    entities = [StepRecordEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StepRecordDatabase : RoomDatabase() {
    abstract fun stepRecordDao(): StepRecordDao
}