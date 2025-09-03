package com.pjhn.stepcounter.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors

private val SECONDARY_BUTTON_PADDING_VERTICAL = Paddings.xlarge
private val SECONDARY_BUTTON_PADDING_HORIZONTAL = Paddings.medium
private val BUTTON_BORDER_WIDTH = 2.dp

@Composable
fun SecondaryWideButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    @StringRes id: Int? = null,
    text: String = "",
    height: Dp = 58.dp,
    onClick: () -> Unit
){
    OutlinedButton(
        modifier = modifier.fillMaxWidth()
            .height(height),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
        border = BorderStroke(BUTTON_BORDER_WIDTH, MaterialTheme.colors.wideTextButton),
        contentPadding = PaddingValues(
            horizontal = SECONDARY_BUTTON_PADDING_HORIZONTAL,
            vertical = SECONDARY_BUTTON_PADDING_VERTICAL
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = id?.let { stringResource(id = id) } ?: text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}