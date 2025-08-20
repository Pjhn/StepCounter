package com.pjhn.stepcounter.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.StepCounterAppTheme
import com.pjhn.stepcounter.ui.theme.colors

private val SECONDARY_BUTTON_PADDING_VERTICAL = Paddings.medium
private val SECONDARY_BUTTON_PADDING_HORIZONTAL = Paddings.medium
private val BUTTON_BORDER_WIDTH = 1.dp

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        border = BorderStroke(BUTTON_BORDER_WIDTH, MaterialTheme.colors.outlinedButton),
        contentPadding = PaddingValues(
            horizontal = SECONDARY_BUTTON_PADDING_HORIZONTAL,
            vertical = SECONDARY_BUTTON_PADDING_VERTICAL
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = id?.let { stringResource(id = id) } ?: text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    StepCounterAppTheme {
        SecondaryButton(
            text = "PreView"
        ) {
        }
    }
}