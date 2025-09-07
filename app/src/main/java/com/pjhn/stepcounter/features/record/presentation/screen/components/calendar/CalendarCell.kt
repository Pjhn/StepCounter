package com.pjhn.stepcounter.features.record.presentation.screen.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.ui.theme.colors
import java.time.LocalDate

@Composable
fun CalendarCell(
    date: LocalDate,
    isMarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
    ){

        if (isMarked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shape = CircleShape,
                        color = Color(0xFF76E447)
                    )
            )
        }
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isMarked) MaterialTheme.colorScheme.outline else
                MaterialTheme.colors.defaultTextButton,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}