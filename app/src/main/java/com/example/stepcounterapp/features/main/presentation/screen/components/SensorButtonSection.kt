package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.SensorState
import com.example.stepcounterapp.ui.components.button.SecondaryButton
import com.example.stepcounterapp.ui.theme.Paddings

@Composable
fun SensorButtonSection(
    sensorState: SensorState,
    input: IMainViewModelInput
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.sensor_delay),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Spacer(Modifier.padding(Paddings.small))
        SecondaryButton(
            text = stringResource(
                id = when (sensorState) {
                    SensorState.DelayHigh -> R.string.high
                    SensorState.DelayLow -> R.string.low
                }
            ),
            onClick = { input.updateSensorDelay() }
        )
    }
}