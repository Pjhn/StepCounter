package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import com.example.stepcounterapp.features.common.model.StepRecord
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class UserRecordRepository @Inject constructor(
    private val stepRecordDao: StepRecordDao
) : IUserRecordRepository {

    override val userRecord = stepRecordDao.getLatestStepRecord().map { entity ->
        entity?.let {
            val stepCount = it.stepCount
            val distance = it.stepCount * 0.8
            val calories = it.stepCount * 1.0

            StepRecord(
                stepCount = stepCount,
                distance = distance,
                calories = calories
            )
        } ?: StepRecord()
    }

    override suspend fun saveUserRecord(record: StepRecord) {
        val entity = StepRecordEntity(
            date = LocalDateTime.now(),
            stepCount = record.stepCount ?: 0
        )
        stepRecordDao.insert(entity)
    }
}