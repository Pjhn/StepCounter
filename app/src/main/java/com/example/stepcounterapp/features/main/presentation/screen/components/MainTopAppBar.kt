package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.ui.theme.Paddings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val TOP_APP_BAR_VERTICAL = Paddings.small
private val TOP_APP_BAR_HORIZONTAL = Paddings.xxlarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    date: LocalDateTime,
    button: @Composable (() -> Unit) = {}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(
            horizontal = TOP_APP_BAR_HORIZONTAL,
            vertical = TOP_APP_BAR_VERTICAL
        ),
        title = {
            Text(
                text = date.format(
                    DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US)
                ),
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            button()
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
    )
}