package com.example.stepcounterapp.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.ui.theme.colors

private val ICON_BUTTON_SIZE = 36.dp
private val ICON_BUTTON_SHAPE = 6.dp
private val BUTTON_BORDER_WIDTH = 1.dp

@Composable
fun CustomIconButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
){
    OutlinedIconButton(
        modifier = Modifier.size(ICON_BUTTON_SIZE),
        onClick = onClick,
        border = BorderStroke(BUTTON_BORDER_WIDTH, MaterialTheme.colors.outlinedButton),
        shape = RoundedCornerShape(ICON_BUTTON_SHAPE)
    ) {
        icon()
    }
}