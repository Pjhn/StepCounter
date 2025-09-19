package com.pjhn.stepcounter.features.record.presentation.screen.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.ui.theme.colors
import java.time.LocalDate

@Composable
fun CalendarCell(
    date: LocalDate,
    progress: Float = 0f,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(
                enabled = progress > 0f,
                onClick = onClick
            )
    ) {

        if (progress > 0) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress.coerceIn(0f, 1f),
                strokeWidth = 2.dp,
                color = MaterialTheme.colors.indicator,
                trackColor = MaterialTheme.colors.surface
            )
        }
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (progress > 0) MaterialTheme.colorScheme.outline else
                MaterialTheme.colors.defaultTextButton,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}