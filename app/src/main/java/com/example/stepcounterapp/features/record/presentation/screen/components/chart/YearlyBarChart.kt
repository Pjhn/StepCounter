package com.example.stepcounterapp.features.record.presentation.screen.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.ui.components.chart.DefaultBarChart
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun YearlyBarChart(
    modifier: Modifier = Modifier,
    selectedCategory: RecordCategories,
    records: List<StepRecord>
) {
    val currentMonthStart = LocalDate.now().withDayOfMonth(1)

    val previousMonths = (1..11)
        .map { currentMonthStart.minusMonths(it.toLong()) }
        .sorted()

    val monthStarts = listOf(currentMonthStart) + previousMonths

    val filteredRecords = records.filter {
        it.date != null && monthStarts.contains(it.date!!.withDayOfMonth(1))
    }

    val monthlyData = filteredRecords.groupBy { record ->
        record.date!!.withDayOfMonth(1)
    }.mapValues { entry ->
        entry.value.sumOf { record ->
            when (selectedCategory) {
                RecordCategories.STEP -> (record.stepCount ?: 0).toDouble()
                RecordCategories.CALORIES -> record.calories ?: 0.0
                RecordCategories.DISTANCE -> record.distance ?: 0.0
                RecordCategories.TIME -> record.measurementTime?.inWholeSeconds?.toDouble()
                    ?: 0.0
            }
        }.toFloat()
    }

    val formatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)
    val xLabels =
        monthStarts.map { it.format(formatter) }
    val yValues = monthStarts.map { monthlyData[it] ?: 0f }


    DefaultBarChart(
        modifier = modifier,
        xLabels = xLabels,
        yValues = yValues
    )
}