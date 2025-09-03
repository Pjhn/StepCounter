package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.presentation.input.IRecordViewModelInput
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors

private val DURATION_SECTION_SPACED_BY = Paddings.medium

@Composable
fun DurationButtonSection(
    selectedDuration: State<Duration>,
    input: IRecordViewModelInput
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(DURATION_SECTION_SPACED_BY)
    ) {
        Duration.entries.forEach { duration ->
            DurationButton(
                duration = duration,
                isSelected = duration == selectedDuration.value,
                onClick = { input.selectDuration(duration) })
        }
    }
}

@Composable
fun DurationButton(
    duration: Duration,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colors.selectedTextButtonBackground else Color.Transparent
    val contentColor = MaterialTheme.colors.text

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }) { onClick() }
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(
                horizontal = Paddings.large,
                vertical = Paddings.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = duration.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            color = contentColor
        )
    }
}