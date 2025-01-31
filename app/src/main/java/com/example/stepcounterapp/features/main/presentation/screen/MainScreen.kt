package com.example.stepcounterapp.features.main.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.ui.components.button.SecondaryButton
import com.example.stepcounterapp.ui.theme.Paddings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val TOP_APP_BAR_PADDING = Paddings.small

@Composable
fun MainScreen() {
    val currentDate = remember {
        LocalDateTime.now()
    }

    Scaffold(
        topBar = {
            MainTopAppBar(
                date = currentDate,
                button = {
                    SecondaryButton(
                        text = stringResource(id = R.string.view_records),
                        modifier = Modifier.wrapContentSize(),
                        onClick = {}
                    )
                }
            )
        }
    ) { paddingValues ->
        //더미 데이터
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "메인 화면", style = MaterialTheme.typography.bodyLarge)
        }
    }
}


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
