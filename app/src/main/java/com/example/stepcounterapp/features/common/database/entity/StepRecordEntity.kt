package com.example.stepcounterapp.features.common.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "step_record")
data class StepRecordEntity(
    @PrimaryKey val date: LocalDateTime,
    @ColumnInfo("step_count") val stepCount: Int
)
