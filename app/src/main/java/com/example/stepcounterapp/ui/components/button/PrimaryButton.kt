package com.example.stepcounterapp.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme

private val PRIMARY_BUTTON_PADDING_VERTICAL = Paddings.xlarge

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit,
    colors: ButtonColors
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
        colors = colors,
        contentPadding = PaddingValues(PRIMARY_BUTTON_PADDING_VERTICAL)
    ) {
        Text(
            text = id?.let { stringResource(id = id) } ?: text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    StepCounterAppTheme {
        PrimaryButton(
            text = "Preview",
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