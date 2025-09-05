package com.pjhn.stepcounter.features.main.presentation.screen.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.R
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.main.presentation.output.MainState
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors
import java.util.Locale
import kotlin.time.Duration

private val SECTION_PADDING_HORIZONTAL = Paddings.extra
private val CONTENT_PADDING = Paddings.medium
private val CONTENT_BOX_SHAPE = 12.dp
private val ICON_BOX_SIZE = 36.dp
private val ICON_BOX_SHAPE = 6.dp

@Composable
fun RecordDetailSection(
    mainState: MainState,
    stepRecord: State<StepRecord>
) {
    val isMeasuring = mainState is MainState.Measuring
    val scale = remember { Animatable(1f) }
    var prevMeasuringState by remember { mutableStateOf(isMeasuring) }

    LaunchedEffect(isMeasuring) {
        if (prevMeasuringState != isMeasuring) {
            scale.animateTo(
                1.18f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )
            scale.animateTo(1f)
        }
        prevMeasuringState = isMeasuring
    }

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
                CaloriesRow(mainState == MainState.Measuring, stepRecord.value, scale)
                TimeRow(mainState == MainState.Measuring, stepRecord.value, scale)
            }
        }
    }
}

@Composable
private fun CaloriesRow(
    isMeasuring: Boolean,
    stepRecord: StepRecord,
    scale: Animatable<Float, *>
) {
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
                tint = Color.Unspecified,
                modifier = Modifier.graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
            )
        }
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = String.format(Locale.getDefault(),"%.2f", stepRecord.calories),
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
    isMeasuring: Boolean,
    stepRecord: StepRecord,
    scale: Animatable<Float, *>
) {
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
                tint = Color.Unspecified,
                modifier = Modifier.graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
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
    return "${hours}h ${minutes}m ${seconds}s"
}