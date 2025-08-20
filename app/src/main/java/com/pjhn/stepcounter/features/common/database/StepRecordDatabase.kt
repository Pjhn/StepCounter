package com.pjhn.stepcounter.features.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pjhn.stepcounter.features.common.database.dao.StepRecordDao
import com.pjhn.stepcounter.features.common.database.entity.StepRecordEntity
import com.pjhn.stepcounter.util.Converters

@Database(
    entities = [StepRecordEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StepRecordDatabase : RoomDatabase() {
    abstract fun stepRecordDao(): StepRecordDao
}