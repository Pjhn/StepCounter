package com.example.stepcounterapp.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme

private val PRIMARY_BUTTON_PADDING_VERTICAL = Paddings.small
private val PRIMARY_BUTTON_PADDING_HORIZONTAL = 76.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit,
    colors: ButtonColors
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
        colors = colors
    ) {
        Row(
            modifier = modifier.padding(
                vertical = PRIMARY_BUTTON_PADDING_VERTICAL,
                horizontal = PRIMARY_BUTTON_PADDING_HORIZONTAL
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = id?.let { stringResource(id = id) } ?: text,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    StepCounterAppTheme {
        PrimaryButton(
            text = "미리보기",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.secondary,
                disabledContainerColor = MaterialTheme.colorScheme.outline
            ),
            onClick = {}
        )
    }
}