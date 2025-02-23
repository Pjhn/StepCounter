package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.colors
import kotlin.time.Duration

private val SECTION_PADDING_HORIZONTAL = Paddings.extra
private val CONTENT_PADDING = Paddings.medium
private val CONTENT_BOX_SHAPE = 12.dp
private val ICON_BOX_SIZE = 36.dp
private val ICON_BOX_SHAPE = 6.dp

@Composable
fun RecordDetailSection(
    mainStateHolder: State<MainState>,
    stepRecord: StepRecord
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = SECTION_PADDING_HORIZONTAL),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(CONTENT_BOX_SHAPE))
                .background(MaterialTheme.colors.surface)
                .padding(CONTENT_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CaloriesRow(mainStateHolder, stepRecord)
                TimeRow(mainStateHolder, stepRecord)
            }
        }
    }
}

@Composable
private fun CaloriesRow(
    mainStateHolder: State<MainState>,
    stepRecord: StepRecord
) {
    val isMeasuring = mainStateHolder.value is MainState.Measuring

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(ICON_BOX_SIZE)
                .clip(RoundedCornerShape(ICON_BOX_SHAPE))
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = if (isMeasuring) painterResource(id = R.drawable.ic_cal_active) else
                    painterResource(id = R.drawable.ic_cal),
                contentDescription = if (isMeasuring) "Active Calories Icon" else "Calories Icon",
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = stepRecord.calories.toString(),
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
    stepRecord: StepRecord
) {
    val isMeasuring = mainStateHolder.value is MainState.Measuring

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(ICON_BOX_SIZE)
                .clip(RoundedCornerShape(ICON_BOX_SHAPE))
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isMeasuring) ImageVector.vectorResource(id = R.drawable.ic_time_active) else
                    ImageVector.vectorResource(id = R.drawable.ic_time),
                contentDescription = if (isMeasuring) "Active Time Icon" else "Time Icon",
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = formatDuration(stepRecord.measurementTime),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.padding(Paddings.small))
    }
}

fun formatDuration(duration: Duration?): String {
    val totalSeconds = duration?.inWholeSeconds ?: 0
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "${hours}h ${minutes}m ${seconds}s".format(hours, minutes, seconds)
}