package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
import com.example.stepcounterapp.features.common.database.entity.StepRecordEntity
import com.example.stepcounterapp.features.common.database.entity.mapper.toDomain
import com.example.stepcounterapp.features.common.model.StepRecord
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class UserRecordRepository @Inject constructor(
    private val stepRecordDao: StepRecordDao
) : IUserRecordRepository {

    override val userRecord = stepRecordDao.getLatestStepRecord().map { entity ->
        entity?.toDomain() ?: StepRecord()
    }

    override suspend fun initializeTodayRecord() {
        val now = LocalDate.now()
        val latestEntity = stepRecordDao.getLatestStepRecord().firstOrNull()

        if (latestEntity == null || latestEntity.date < now) {
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

    override suspend fun getRecordsForPeriod(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<StepRecord> {
        return stepRecordDao.getStepRecords(startDate, endDate).toDomain()
    }
}