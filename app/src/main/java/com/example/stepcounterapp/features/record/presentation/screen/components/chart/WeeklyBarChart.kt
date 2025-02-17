package com.example.stepcounterapp.features.record.presentation.screen.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.ui.components.chart.DefaultBarChart
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun WeeklyBarChart(
    modifier: Modifier = Modifier,
    selectedCategory: RecordCategories,
    records: List<StepRecord>
) {
    val today = LocalDate.now()
    val previousDays = (1 until 7).map { today.minusDays(it.toLong()) }.sorted()
    val days = listOf(today) + previousDays

    val formatter = DateTimeFormatter.ofPattern("MM/dd")
    val xLabels = days.map { it.format(formatter) }
        .toMutableList().also { it[0] = "Today" }

    val yValues = days.map { date ->
        val record = records.find { it.date == date }
        when (selectedCategory) {
            RecordCategories.STEP -> record?.stepCount?.toFloat() ?: 0f
            RecordCategories.CALORIES -> record?.calories?.toFloat() ?: 0f
            RecordCategories.DISTANCE -> record?.distance?.toFloat() ?: 0f
            RecordCategories.DURATION -> record?.measurementTime?.inWholeSeconds?.toFloat() ?: 0f
        }
    }

    DefaultBarChart(
        modifier = modifier,
        xLabels = xLabels,
        yValues = yValues
    )
}