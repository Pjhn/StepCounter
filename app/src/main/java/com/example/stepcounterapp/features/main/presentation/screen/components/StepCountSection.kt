package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.ui.theme.Paddings

@Composable
fun StepCountSection(
    stepRecord: StepRecord,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.small)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stepRecord.stepCount}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.padding(Paddings.small))
                Text(
                    text = "걸음",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.alignByBaseline()
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${stepRecord.distance}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.padding(Paddings.xsmall))
                Text(
                    text = stringResource(id = R.string.distance_unit),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
    }
}