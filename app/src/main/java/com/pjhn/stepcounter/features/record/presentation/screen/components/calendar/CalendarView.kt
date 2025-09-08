package com.pjhn.stepcounter.features.record.presentation.screen.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.R
import com.pjhn.stepcounter.ui.components.button.CustomIconButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarView(
    month: LocalDate,
    date: List<Pair<LocalDate, Float>>?,
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (displayPrev) {
                CustomIconButton(
                    onClick = onClickPrev,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_left_arrow),
                            contentDescription = "Add Widget Icon", tint = Color.Unspecified
                        )
                    }
                )
            }
            Text(
                text = month.formatToMonthString(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (displayNext) {
                CustomIconButton(
                    onClick = onClickNext,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_right_arrow),
                            contentDescription = "Add Widget Icon", tint = Color.Unspecified
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        if (!date.isNullOrEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun LocalDate.formatToMonthString(): String =
    DateTimeFormatter.ofPattern("MMM", Locale.US).format(this)