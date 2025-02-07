package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.UserRecord
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.colors
import kotlin.time.Duration

@Composable
fun RecordDetailSection(
    mainStateHolder: State<MainState>,
    userRecord: UserRecord
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(start = Paddings.extra, end = Paddings.extra),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CaloriesRow(mainStateHolder, userRecord)
            TimeRow(mainStateHolder, userRecord)
        }
    }
}

@Composable
private fun CaloriesRow(
    mainStateHolder: State<MainState>,
    userRecord: UserRecord
) {
    val isMeasuring = mainStateHolder.value is MainState.Measuring

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_cal),
            tint = if (isMeasuring) MaterialTheme.colors.brandColor else MaterialTheme.colors.primary,
            contentDescription = if (isMeasuring) "활성화 칼로리 아이콘" else "칼로리 아이콘",
        )
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = userRecord.calories.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Spacer(modifier = Modifier.padding(Paddings.xsmall))
        Text(
            text = stringResource(id = R.string.calories_unit),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
private fun TimeRow(
    mainStateHolder: State<MainState>,
    userRecord: UserRecord
) {
    val isMeasuring = mainStateHolder.value is MainState.Measuring

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_time),
            contentDescription = if (isMeasuring) "활성화 시계 아이콘" else "시계 아이콘",
            tint = if (isMeasuring) MaterialTheme.colors.brandColor else MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = formatDuration(userRecord.measurementTime),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

fun formatDuration(duration: Duration): String {
    val totalSeconds = duration.inWholeSeconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}