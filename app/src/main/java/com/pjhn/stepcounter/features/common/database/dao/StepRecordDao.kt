package com.pjhn.stepcounter.features.common.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.pjhn.stepcounter.features.common.database.entity.StepRecordEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface StepRecordDao {
    @Upsert
    suspend fun upsert(record: StepRecordEntity)

    @Query("SELECT * FROM step_record ORDER BY date DESC LIMIT 1")
    fun getLatestStepRecord(): Flow<StepRecordEntity?>

    @Query("SELECT * FROM step_record WHERE date BETWEEN :startDate AND :endDate GROUP BY date ORDER BY date ASC")
    suspend fun getStepRecords(startDate: LocalDate, endDate: LocalDate): List<StepRecordEntity>?
}