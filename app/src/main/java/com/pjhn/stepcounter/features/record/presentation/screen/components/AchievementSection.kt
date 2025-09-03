package com.pjhn.stepcounter.features.record.presentation.screen.components

import AchievementIndicator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.ui.theme.colors

@Composable
fun AchievementSection(
    modifier: Modifier = Modifier,
    stepRecord: State<StepRecord>,
    stepGoal: State<Int?>,
    indicatorSize: Dp = 64.dp
) {
    val stepCount = stepRecord.value.stepCount ?: 0
    val stepGoal = stepGoal.value ?: 0
    val progress = if (stepCount > 0 && stepGoal > 0) {
        stepCount / stepGoal.toFloat()
    } else 0f

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(start = 10.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AchievementIndicator(
                progress = progress,
                size = indicatorSize,
                stroke = 8.dp
            )

            Spacer(Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Daily Achievement",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colors.defaultTextButton
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${stepCount.toString()} / ${stepGoal.toString()}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
