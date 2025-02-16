package com.example.stepcounterapp.features.record.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.record.presentation.input.IRecordViewModelInput
import com.example.stepcounterapp.ui.theme.Paddings

private val TOP_APP_BAR_VERTICAL = Paddings.small
private val TOP_APP_BAR_HORIZONTAL = Paddings.xxlarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordTopAppBar(
    input: IRecordViewModelInput
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(
            horizontal = TOP_APP_BAR_HORIZONTAL,
            vertical = TOP_APP_BAR_VERTICAL
        ),
        title = {
            Text(text = "Record", style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(indication = null, interactionSource = remember {
                    MutableInteractionSource()
                }) { input.goBackToMain() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "back button"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}