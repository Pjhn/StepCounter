package com.pjhn.stepcounter.features.record.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.pjhn.stepcounter.features.record.presentation.screen.components.AchievementCalendarSection
import com.pjhn.stepcounter.features.record.presentation.screen.components.DayDetailBottomsheet
import com.pjhn.stepcounter.ui.theme.Paddings
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    recordStateHolder: State<RecordState>,
    selectedCategory: State<RecordCategories>,
    selectedDuration: State<Duration>,
    chartRecords: State<List<StepRecord>>,
    recordsProgress: State<List<Pair<LocalDate, Float>>>,
    stepRecord: State<StepRecord>,
    stepGoal: State<Int>,
    input: IRecordViewModelInput,
) {
    var sheetDate by remember { mutableStateOf<LocalDate?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            RecordTopAppBar(input)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Paddings.xxlarge)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
        ) {
            CategorySection(
                selectedCategory = selectedCategory,
                input = input
            )
            DurationButtonSection(
                selectedDuration = selectedDuration,
                input = input
            )
            ChartSection(
                modifier = Modifier.padding(bottom = Paddings.small),
                selectedCategory = selectedCategory,
                selectedDuration = selectedDuration,
                records = chartRecords,
            )
            TotalSection(
                records = chartRecords, selectedDuration = selectedDuration
            )
            AchievementSection(
                stepRecord = stepRecord,
                stepGoal = stepGoal,
            )
            AchievementCalendarSection(
                recordsProgress = recordsProgress
            ) { clickedDate ->
                sheetDate = clickedDate
            }
            Spacer(modifier = Modifier.padding(0.dp))
        }
    }
    DayDetailBottomsheet(
        selectedDate = sheetDate,
        sheetState = sheetState,
        record = stepRecord.value,
        onDismiss = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    sheetDate = null
                }
            }
        }
    )

}

