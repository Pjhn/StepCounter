package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.MonthlyBarChart
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.WeeklyBarChart
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.YearlyBarChart

@Composable
fun ChartSection(
    modifier: Modifier = Modifier,
    selectedCategory: State<RecordCategories>,
    selectedDuration: State<Duration>,
    showAvg: Boolean,
    showDur: Boolean,
    records: State<List<StepRecord>>
) {
    when (selectedDuration.value) {
        Duration.WEEK -> {
            WeeklyBarChart(
                modifier = modifier,
                records = records.value,
                selectedCategory = selectedCategory.value,
                showAvg = showAvg,
                showDur = showDur
            )
        }

        Duration.MONTH -> {
            MonthlyBarChart(
                modifier = modifier,
                records = records.value,
                selectedCategory = selectedCategory.value,
                showAvg = showAvg,
                showDur = showDur
            )
        }

        Duration.YEAR -> {
            YearlyBarChart(
                modifier = modifier,
                records = records.value,
                selectedCategory = selectedCategory.value,
                showAvg = showAvg,
                showDur = showDur
            )
        }
    }
}