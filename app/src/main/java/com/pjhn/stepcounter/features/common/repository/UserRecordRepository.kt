package com.pjhn.stepcounter.features.common.repository

import com.pjhn.stepcounter.features.common.database.dao.StepRecordDao
import com.pjhn.stepcounter.features.common.database.entity.StepRecordEntity
import com.pjhn.stepcounter.features.common.database.entity.mapper.toDomain
import com.pjhn.stepcounter.features.common.model.StepRecord
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class UserRecordRepository @Inject constructor(
    private val stepRecordDao: StepRecordDao,
    private val stepGoalRepository: StepGoalRepository
) : IUserRecordRepository {

    override val userRecord = stepRecordDao.getLatestStepRecord().map { entity ->
        entity?.toDomain() ?: StepRecord()
    }

    override val userRecordsProgress =
        stepRecordDao.getStepRecords().map { list ->
            list.orEmpty()
                .map { entity ->
                    val progress = if (entity.stepGoal > 0) entity.stepCount / entity.stepGoal.toFloat() else 0f
                    entity.date to progress.coerceIn(0f, 1f)
                }
        }

    override suspend fun isNewDay(): Boolean {
        val latestEntity = stepRecordDao.getLatestStepRecord().firstOrNull()
        val now = LocalDate.now()

        if (latestEntity == null) return true
        val daysGap =
            ChronoUnit.DAYS.between(latestEntity.date, now)
        return daysGap >= 1
    }

    override suspend fun initializeTodayRecord() {
        val now = LocalDate.now()

        if (isNewDay()) {
            val entity = StepRecordEntity(
                date = now,
                stepCount = 0,
                stepGoal = stepGoalRepository.getGoal()
            )
            stepRecordDao.upsert(entity)
        }
    }

    override suspend fun saveUserRecord(record: StepRecord) {
        val now = LocalDate.now()

        if (isNewDay()) {
            val entity = StepRecordEntity(
                date = now,
                stepCount = 0,
                stepGoal = stepGoalRepository.getGoal()
            )
            stepRecordDao.upsert(entity)
        }else {
            val entity = StepRecordEntity(
                date = now,
                stepCount = record.stepCount ?: 0,
                stepGoal = stepGoalRepository.getGoal()
            )
            stepRecordDao.upsert(entity)
        }
    }

    override suspend fun getRecordsForPeriod(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<StepRecord> {
        return stepRecordDao.getStepRecords(startDate, endDate).toDomain()
    }
}