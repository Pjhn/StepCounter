package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Box
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

@Composable
fun PrimaryButtonSection(
    mainStateHolder: State<MainState>,
    input: IMainViewModelInput
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.xlarge)
    ) {
        when(mainStateHolder.value){
            is MainState.Measuring -> {
                PrimaryButton(
                    text = stringResource(id = R.string.pause),
                    modifier = Modifier.align(Alignment.Center),
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
                    text = stringResource(id = R.string.start_measurement),
                    modifier = Modifier.align(Alignment.Center),
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