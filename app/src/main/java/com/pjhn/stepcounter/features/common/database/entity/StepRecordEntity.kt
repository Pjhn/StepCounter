package com.pjhn.stepcounter.features.common.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "step_record")
data class StepRecordEntity(
    @PrimaryKey val date: LocalDate,
    @ColumnInfo("step_count") val stepCount: Int,
    @ColumnInfo("step_goal") val stepGoal: Int
)
