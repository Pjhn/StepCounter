package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.MonthlyBarChart
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.WeeklyBarChart
import com.pjhn.stepcounter.features.record.presentation.screen.components.chart.YearlyBarChart

@Composable
fun ChartSection(
    modifier: Modifier,
    selectedCategory: RecordCategories,
    selectedDuration: Duration,
    records: List<StepRecord>
) {
    when (selectedDuration) {
        Duration.WEEK -> {
            WeeklyBarChart(
                modifier = modifier,
                records = records,
                selectedCategory = selectedCategory
            )
        }

        Duration.MONTH -> {
            MonthlyBarChart(
                modifier = modifier,
                records = records,
                selectedCategory = selectedCategory
            )
        }

        Duration.YEAR -> {
            YearlyBarChart(
                modifier = modifier,
                records = records,
                selectedCategory = selectedCategory
            )
        }
    }
}