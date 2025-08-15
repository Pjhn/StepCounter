package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.ui.components.button.PrimaryButton
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.colors

private val SECTION_PADDING_HORIZONTAL = Paddings.extra
private val SECTION_PADDING_VERTICAL = Paddings.xlarge

@Composable
fun PrimaryButtonSection(
    mainState: MainState,
    input: IMainViewModelInput
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SECTION_PADDING_HORIZONTAL,
                vertical = SECTION_PADDING_VERTICAL
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (mainState) {
            is MainState.Measuring -> {
                PrimaryButton(
                    text = stringResource(id = R.string.pause),
                    onClick = {
                        input.pauseMeasurement()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.pauseButton,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            else -> {
                PrimaryButton(
                    text = stringResource(id = R.string.start),
                    onClick = {
                        input.startMeasurement()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}