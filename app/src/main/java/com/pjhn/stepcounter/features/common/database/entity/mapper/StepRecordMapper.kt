package com.pjhn.stepcounter.features.common.database.entity.mapper

import com.pjhn.stepcounter.features.common.config.StepRecordConfig.CALORIE_PER_STEP
import com.pjhn.stepcounter.features.common.config.StepRecordConfig.STEP_LENGTH
import com.pjhn.stepcounter.features.common.config.StepRecordConfig.TIME_PER_STEP
import com.pjhn.stepcounter.features.common.database.entity.StepRecordEntity
import com.pjhn.stepcounter.features.common.model.StepRecord
import kotlin.time.Duration.Companion.seconds

object StepRecordMapper {
    fun toDomain(entities: List<StepRecordEntity>?): List<StepRecord>? {
        return entities?.map { stepRecordEntity ->
            stepRecordEntity.toDomain()
        }
    }

    fun toDomain(entity: StepRecordEntity?): StepRecord {
        return entity?.let {
            StepRecord(
                date = entity.date,
                stepCount = entity.stepCount,
                distance = entity.stepCount * STEP_LENGTH,
                calories = entity.stepCount * CALORIE_PER_STEP,
                measurementTime = (entity.stepCount * TIME_PER_STEP).seconds
            )
        } ?: StepRecord()
    }
}

fun StepRecordEntity.toDomain(): StepRecord = StepRecordMapper.toDomain(this)

fun List<StepRecordEntity>?.toDomain(): List<StepRecord> {
    return this?.let { StepRecordMapper.toDomain(it) } ?: emptyList()
}
