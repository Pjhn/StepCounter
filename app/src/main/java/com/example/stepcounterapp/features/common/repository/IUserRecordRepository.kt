package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.model.StepRecord
import kotlinx.coroutines.flow.Flow

interface IUserRecordRepository {
    val userRecord: Flow<StepRecord>
    suspend fun saveUserRecord(record: StepRecord)

    suspend fun initializeTodayRecord()
}