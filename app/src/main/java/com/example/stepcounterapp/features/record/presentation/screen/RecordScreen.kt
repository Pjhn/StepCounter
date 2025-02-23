package com.example.stepcounterapp.features.record.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.model.enums.Duration
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.features.record.presentation.input.IRecordViewModelInput
import com.example.stepcounterapp.features.record.presentation.output.RecordState
import com.example.stepcounterapp.features.record.presentation.screen.components.CategorySection
import com.example.stepcounterapp.features.record.presentation.screen.components.ChartSection
import com.example.stepcounterapp.features.record.presentation.screen.components.DurationButtonSection
import com.example.stepcounterapp.features.record.presentation.screen.components.RecordTopAppBar
import com.example.stepcounterapp.features.record.presentation.screen.components.TotalSection
import com.example.stepcounterapp.ui.theme.Paddings


@Composable
fun RecordScreen(
    recordStateHolder: State<RecordState>,
    selectedCategory: State<RecordCategories>,
    selectedDuration: State<Duration>,
    chartRecords: State<List<StepRecord>>,
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
        }
    }
}