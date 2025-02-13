package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import com.example.stepcounterapp.features.common.model.StepRecord
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class UserRecordRepository @Inject constructor(
    private val stepRecordDao: StepRecordDao
) : IUserRecordRepository {

    override val userRecord = stepRecordDao.getLatestStepRecord().map { entity ->
        entity?.let {
            val stepCount = it.stepCount
            val distance = it.stepCount * STEP_LENGTH
            val calories = it.stepCount * CALORIE_PER_STEP

            StepRecord(
                stepCount = stepCount,
                distance = distance,
                calories = calories
            )
        } ?: StepRecord()
    }

    override suspend fun saveUserRecord(record: StepRecord) {
        val entity = StepRecordEntity(
            date = LocalDate.now(),
            stepCount = record.stepCount ?: 0
        )
        stepRecordDao.upsert(entity)
    }

    companion object {
        private const val STEP_LENGTH = 0.8
        private const val CALORIE_PER_STEP = 1.0
    }
}