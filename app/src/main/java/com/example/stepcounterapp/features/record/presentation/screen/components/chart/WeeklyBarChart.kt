package com.example.stepcounterapp.features.record.presentation.screen.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.ui.components.chart.DefaultBarChart
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun WeeklyBarChart(
    modifier: Modifier = Modifier,
    selectedCategory: RecordCategories,
    records: List<StepRecord>
) {
    val today = LocalDate.now()
    val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

    val days = (0..6).map { weekStart.plusDays(it.toLong()) }

    val xLabels = listOf("Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat")

    val yValues = days.map { day ->
        if (day <= today) {
            val record = records.find { it.date == day }
            when (selectedCategory) {
                RecordCategories.STEP -> record?.stepCount?.toFloat() ?: 0f
                RecordCategories.CALORIES -> record?.calories?.toFloat() ?: 0f
                RecordCategories.DISTANCE -> (record?.distance?.toFloat() ?: 0f)/1000
                RecordCategories.TIME -> record?.measurementTime?.inWholeSeconds?.toFloat()
                    ?: 0f
            }
        } else 0f
    }

    DefaultBarChart(
        modifier = modifier,
        xLabels = xLabels,
        yValues = yValues
    )
}