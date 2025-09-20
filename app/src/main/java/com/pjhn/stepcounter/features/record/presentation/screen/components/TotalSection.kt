package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.runtime.State
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.R
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.common.model.enums.Duration.*
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories.*
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors
import com.pjhn.stepcounter.util.TimeFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters


private val TOTAL_SECTION_PADDING = Paddings.medium
private val CONTENT_BOX_SHAPE = 12.dp
private val ICON_BOX_SIZE = 36.dp
private val ICON_BOX_SHAPE = 6.dp

@Composable
fun TotalSection(
    modifier: Modifier = Modifier,
    records: State<List<StepRecord>>,
    selectedDuration: State<Duration>
) {
    val today = LocalDate.now()
    val filteredRecords = when (selectedDuration.value) {
        WEEK -> records.value.filter { it.date != null && it.date >= today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) }
        MONTH -> records.value.filter { it.date != null && it.date >= today.withDayOfMonth(1) }
        YEAR -> records.value.filter { it.date != null && it.date >= today.withDayOfYear(1) }
    }

    val totalCalories = filteredRecords.sumOf { it.calories ?: 0.0 }
    val totalSteps = filteredRecords.sumOf { it.stepCount ?: 0 }
    val totalDistance = filteredRecords.sumOf { it.distance ?: 0.0 }
    val totalTimeSeconds = filteredRecords.sumOf { it.measurementTime?.inWholeSeconds ?: 0 }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CONTENT_BOX_SHAPE))
            .background(MaterialTheme.colors.surface)
            .padding(TOTAL_SECTION_PADDING)
            .wrapContentHeight()
    ) {
        IconRow(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_cal_active),
            description = "Calories icon",
            category = CALORIES,
            text = "${"%.1f".format(totalCalories)} Kcal"
        )
        Spacer(modifier = Modifier.padding(Paddings.medium))
        IconRow(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_time_active),
            description = "Time icon",
            category = TIME,
            text = TimeFormatter.formatTime(totalTimeSeconds)
        )
        Spacer(modifier = Modifier.padding(Paddings.medium))
        IconRow(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_step),
            description = "Steps icon",
            category = STEP,
            text = "$totalSteps Steps"
        )
        Spacer(modifier = Modifier.padding(Paddings.medium))
        IconRow(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_distance),
            description = "Distance icon",
            category = DISTANCE,
            text = "${"%.2f".format(totalDistance)} m"
        )
    }
}

@Composable
fun IconRow(
    imageVector: ImageVector,
    description: String,
    category: RecordCategories,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                imageVector = imageVector,
                contentDescription = description,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.padding(Paddings.small))
        Text(
            text = category.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.padding(Paddings.small))
    }
}
