package com.pjhn.stepcounter.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.ui.components.button.SecondaryButton
import com.pjhn.stepcounter.ui.theme.Paddings

@Composable
fun StepGoalSection(
    stepRecord: State<StepRecord>,
    stepGoal: State<Int>,
    buttonOnClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = Paddings.xxlarge),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stepRecord.value.stepCount.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Spacer(Modifier.padding(Paddings.small))
        Text(
            text = "/",
        )
        Spacer(Modifier.padding(Paddings.small))
        SecondaryButton(
            text = "${stepGoal.value}",
            onClick = { buttonOnClick() }
        )
    }
}