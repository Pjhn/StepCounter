package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.ui.components.button.SecondaryButton
import com.example.stepcounterapp.ui.theme.Paddings

@Composable
fun StepGoalSection(
    stepRecord: StepRecord,
    stepGoal: Int,
    input: IMainViewModelInput
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = Paddings.xxlarge),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stepRecord.stepCount.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Spacer(Modifier.padding(Paddings.small))
        Text(
            text = "/",
        )
        Spacer(Modifier.padding(Paddings.small))
        SecondaryButton(
            text = stepGoal.toString(),
            onClick = {  }
        )
    }
}