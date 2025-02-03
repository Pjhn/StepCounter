package com.example.stepcounterapp.features.main.presentation.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stepcounterapp.ui.theme.Paddings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val TOP_APP_BAR_PADDING = Paddings.small

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    date: LocalDateTime,
    button: @Composable (() -> Unit) = {}
) {
    TopAppBar(
        modifier = Modifier.padding(
            start = TOP_APP_BAR_PADDING,
            end = TOP_APP_BAR_PADDING,
            top = TOP_APP_BAR_PADDING
        ),
        title = {
            Text(
                text = date.format(
                    DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN)
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