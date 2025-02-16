package com.example.stepcounterapp.features.record.presentation.screen.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.features.common.model.enums.Duration
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.colors


@Composable
fun DurationBar(
    selectedDuration: Duration,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Duration.entries.forEach { duration ->
            DurationButton(
                duration = duration,
                isSelected = duration == selectedDuration,
                onClick = { })
        }
    }
}

@Composable
fun DurationButton(
    duration: Duration,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.selectedTextButtonBackground else Color.Transparent
    val contentColor = MaterialTheme.colors.text

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }) { onClick() }
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = Paddings.large,
                vertical = Paddings.medium),
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