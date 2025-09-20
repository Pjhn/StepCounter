package com.pjhn.stepcounter.features.common.repository

import com.pjhn.stepcounter.features.common.model.StepRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface IUserRecordRepository {
    val userRecord: Flow<StepRecord>

    val userRecordsProgress: Flow<List<Pair<LocalDate, Float>>>

    suspend fun isNewDay(): Boolean

    suspend fun saveUserRecord(record: StepRecord)

    suspend fun initializeTodayRecord()

    suspend fun getRecordsForPeriod(startDate: LocalDate, endDate: LocalDate): List<StepRecord>
}