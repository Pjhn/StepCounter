package com.pjhn.stepcounter.features.main.presentation.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.pjhn.stepcounter.R
import com.pjhn.stepcounter.features.main.presentation.input.IMainViewModelInput
import com.pjhn.stepcounter.ui.components.button.CustomIconButton
import com.pjhn.stepcounter.ui.theme.Paddings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val TOP_APP_BAR_VERTICAL = Paddings.small
private val TOP_APP_BAR_HORIZONTAL = Paddings.xxlarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    title: String,
    input: IMainViewModelInput,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(
            horizontal = TOP_APP_BAR_HORIZONTAL,
            vertical = TOP_APP_BAR_VERTICAL
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            CustomIconButton(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_modal),
                        contentDescription = "settings"
                    )
                },
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        },
        actions = {
            CustomIconButton(
                onClick = { input.openRecord() },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_chart),
                        contentDescription = "record"
                    )
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
    )
}