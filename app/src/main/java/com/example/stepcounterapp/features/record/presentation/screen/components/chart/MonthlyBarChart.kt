package com.example.stepcounterapp.features.record.presentation.screen.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.ui.components.chart.DefaultBarChart
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun MonthlyBarChart(
    modifier: Modifier = Modifier,
    selectedCategory: RecordCategories,
    records: List<StepRecord>
) {
    val currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val previousWeeks = (0..3)
        .map { currentWeekStart.minusWeeks(it.toLong()) }
        .sorted()

    val formatter = DateTimeFormatter.ofPattern("MM/dd")
    val xLabels =
        previousWeeks.map { it.format(formatter) }

    val weeklyData = records.filter { it.date != null }
        .groupBy { record ->
            record.date!!.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        }
        .mapValues { entry ->
            entry.value.sumOf { record ->
                when (selectedCategory) {
                    RecordCategories.STEP -> (record.stepCount ?: 0).toDouble()
                    RecordCategories.CALORIES -> record.calories ?: 0.0
                    RecordCategories.DISTANCE -> (record.distance ?: 0.0)/1000.0
                    RecordCategories.TIME -> record.measurementTime?.inWholeSeconds?.toDouble()
                        ?: 0.0
                }
            }.toFloat()
        }

    val yValues = previousWeeks.map { weeklyData[it] ?: 0f }

    DefaultBarChart(
        modifier = modifier,
        xLabels = xLabels,
        yValues = yValues
    )
}