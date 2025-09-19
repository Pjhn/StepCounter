package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.ui.components.button.PrimaryButton
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors
import com.pjhn.stepcounter.util.TimeFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailBottomsheet(
    selectedDate: LocalDate?,
    sheetState: SheetState,
    record: StepRecord?,
    onDismiss: () -> Unit
) {
    selectedDate?.let { selectedDate ->
        val steps = record?.stepCount ?: 0
        val date = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.xlarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colors.surface)
                        .padding(Paddings.xlarge),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Steps  %d".format(steps),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Calories  " + (record?.calories?.let { "%.1f kcal".format(it) } ?: "-"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Distance  " + (record?.distance?.let { "%.2f km".format(it / 1000.0) } ?: "-"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Time  " + (record?.measurementTime?.inWholeSeconds?.let { TimeFormatter.formatTime(it) } ?: "-"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss,
                    text = "Close",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}