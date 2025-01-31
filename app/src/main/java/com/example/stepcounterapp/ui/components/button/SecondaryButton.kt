package com.example.stepcounterapp.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stepcounterapp.ui.theme.Paddings
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit
){
    Button(
        modifier = modifier.wrapContentSize()
            .padding(
                top = Paddings.medium,
                bottom = Paddings.medium,
                start = Paddings.large,
                end = Paddings.large
            ),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.outline,
            disabledContentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.outline
        )
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = id?.let { stringResource(id = id)} ?: text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
fun SecondaryButtonPreview(){
    StepCounterAppTheme {
        SecondaryButton(
            text = "PreView"
        ) {
        }
    }
}