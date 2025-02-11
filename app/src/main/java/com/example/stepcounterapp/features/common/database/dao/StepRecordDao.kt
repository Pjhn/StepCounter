package com.example.stepcounterapp.features.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: StepRecordEntity)

    @Query("SELECT * FROM step_record ORDER BY date DESC LIMIT 1")
    fun getLatestStepRecord(): Flow<StepRecordEntity?>
}