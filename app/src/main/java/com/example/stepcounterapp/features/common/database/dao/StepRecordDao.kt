package com.example.stepcounterapp.features.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepRecordDao {
    @Upsert
    suspend fun upsert(record: StepRecordEntity)

    @Query("SELECT * FROM step_record ORDER BY date DESC LIMIT 1")
    fun getLatestStepRecord(): Flow<StepRecordEntity?>
}