package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.R
import com.example.stepcounterapp.ui.components.button.SecondaryButton
import com.example.stepcounterapp.ui.theme.Paddings

@Composable
fun SensorButtonSection(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "센서 민감도",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(Modifier.padding(Paddings.small))
            SecondaryButton(
                text = stringResource(id = R.string.high_sensitivity),
                modifier = Modifier.wrapContentSize(),
                onClick = {}
            )
        }
    }
}