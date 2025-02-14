package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import com.example.stepcounterapp.features.common.model.StepRecord
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UserRecordRepository @Inject constructor(
    private val stepRecordDao: StepRecordDao
) : IUserRecordRepository {

    override val userRecord = stepRecordDao.getLatestStepRecord().map { entity ->
        entity?.let {
            val stepCount = it.stepCount
            val distance = it.stepCount * STEP_LENGTH
            val calories = it.stepCount * CALORIE_PER_STEP
            val measurementTime = (it.stepCount * TIME_PER_STEP).seconds

            StepRecord(
                stepCount = stepCount,
                distance = distance,
                calories = calories,
                measurementTime = measurementTime
            )
        } ?: StepRecord()
    }

    override suspend fun initializeTodayRecord() {
        val now = LocalDate.now()
        val latestEntity = stepRecordDao.getLatestStepRecord().firstOrNull()

        if (latestEntity == null || latestEntity.date < now){
            val entity = StepRecordEntity(
                date = now,
                stepCount = 0
            )
            stepRecordDao.upsert(entity)
        }
    }

    override suspend fun saveUserRecord(record: StepRecord) {
        val now = LocalDate.now()
        val entity = StepRecordEntity(
            date = now,
            stepCount = record.stepCount ?: 0
        )
        stepRecordDao.upsert(entity)
    }

    companion object {
        private const val STEP_LENGTH = 0.8
        private const val CALORIE_PER_STEP = 0.04
        private const val TIME_PER_STEP = 0.6
    }
}