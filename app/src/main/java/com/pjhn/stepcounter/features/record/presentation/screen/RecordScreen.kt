package com.pjhn.stepcounter.features.record.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories
import com.pjhn.stepcounter.features.record.presentation.input.IRecordViewModelInput
import com.pjhn.stepcounter.features.record.presentation.output.RecordState
import com.pjhn.stepcounter.features.record.presentation.screen.components.AchievementSection
import com.pjhn.stepcounter.features.record.presentation.screen.components.CategorySection
import com.pjhn.stepcounter.features.record.presentation.screen.components.ChartSection
import com.pjhn.stepcounter.features.record.presentation.screen.components.DurationButtonSection
import com.pjhn.stepcounter.features.record.presentation.screen.components.RecordTopAppBar
import com.pjhn.stepcounter.features.record.presentation.screen.components.TotalSection
import com.pjhn.stepcounter.ui.theme.Paddings


@Composable
fun RecordScreen(
    recordStateHolder: State<RecordState>,
    selectedCategory: State<RecordCategories>,
    selectedDuration: State<Duration>,
    chartRecords: State<List<StepRecord>>,
    stepRecord: State<StepRecord>,
    stepGoal: State<Int>,
    input: IRecordViewModelInput,
) {
    Scaffold(
        topBar = {
            RecordTopAppBar(input)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CategorySection(
                selectedCategory = selectedCategory.value,
                input = input
            )
            Spacer(modifier = Modifier.padding(Paddings.medium))
            DurationButtonSection(
                selectedDuration = selectedDuration.value,
                input = input
            )
            Spacer(modifier = Modifier.padding(Paddings.medium))
            ChartSection(
                modifier = Modifier.padding(horizontal = Paddings.xxlarge),
                selectedCategory = selectedCategory.value,
                selectedDuration = selectedDuration.value,
                records = chartRecords.value
            )
            Spacer(modifier = Modifier.padding(Paddings.large))
            TotalSection(
                modifier = Modifier.padding(horizontal = Paddings.xxlarge),
                records = chartRecords.value, selectedDuration = selectedDuration.value
            )
            Spacer(modifier = Modifier.padding(Paddings.medium))
            AchievementSection(
                modifier = Modifier.padding(horizontal = Paddings.xxlarge),
                stepCount = stepRecord.value.stepCount ?: 0,
                stepGoal = stepGoal.value,
            )
        }
    }
}

