package com.pjhn.stepcounter.features.record.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.R
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
import com.pjhn.stepcounter.ui.components.button.SecondaryWideButton
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors


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
            SecondaryWideButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                        contentDescription = "Play Icon",
                        tint = MaterialTheme.colors.defaultTextButton
                    )
                },
                id = R.string.rate_on_play_store
            ) { }
        }
    }
}

