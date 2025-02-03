package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.ui.components.button.PrimaryButton
import com.example.stepcounterapp.ui.theme.Paddings

@Composable
fun PrimaryButtonSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.xlarge)
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.start_measurement),
            modifier = Modifier.align(Alignment.Center),
            onClick = {}
        )
    }
}