package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.presentation.input.IRecordViewModelInput
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors

private val DURATION_SECTION_SPACED_BY = Paddings.medium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DurationButtonSection(
    selectedDuration: State<Duration>,
    showAvg: Boolean,
    showDur: Boolean,
    onToggleAvg: () -> Unit,
    onToggleDur: () -> Unit,
    input: IRecordViewModelInput
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(DURATION_SECTION_SPACED_BY)
        ) {
            Duration.entries.forEach { duration ->
                DurationButton(
                    duration = duration,
                    isSelected = duration == selectedDuration.value,
                    onClick = { input.selectDuration(duration) })
            }
        }

        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(DURATION_SECTION_SPACED_BY)
        ){
            FilterChip(
                selected = showAvg,
                onClick = onToggleAvg,
                label = { Text(text = "avg", style = MaterialTheme.typography.labelLarge) },
                shape = MaterialTheme.shapes.medium,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colors.background,
                    labelColor = MaterialTheme.colors.text,
                    selectedContainerColor = MaterialTheme.colors.primary,
                    selectedLabelColor = MaterialTheme.colors.onPrimary
                    ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = MaterialTheme.colors.outlinedButton,
                    borderWidth = 1.dp
                    ),
                )

            FilterChip(
                selected = showDur,
                onClick = onToggleDur,
                label = { Text(text = "dur", style = MaterialTheme.typography.labelLarge) },
                shape = MaterialTheme.shapes.medium,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colors.background,
                    labelColor = MaterialTheme.colors.text,
                    selectedContainerColor = MaterialTheme.colors.primary,
                    selectedLabelColor = MaterialTheme.colors.onPrimary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = MaterialTheme.colors.outlinedButton,
                    borderWidth = 1.dp
                )
            )
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