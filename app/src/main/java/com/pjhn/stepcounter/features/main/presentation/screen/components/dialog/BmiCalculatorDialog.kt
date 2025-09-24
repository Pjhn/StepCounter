package com.pjhn.stepcounter.features.main.presentation.screen.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pjhn.stepcounter.ui.components.button.PrimaryButton
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors
import kotlin.math.pow

@Composable
fun BmiCalculatorDialog(
    title: String,
    weight: String,
    height: String,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val calculatedBMI = remember(weight, height) {
        val weightToDouble = weight.toDoubleOrNull()
        val heightToDouble = height.toDoubleOrNull()
        val isInputValid =
            weightToDouble != null && heightToDouble != null && weightToDouble > 0 && heightToDouble > 0

        if (isInputValid) calculateBMI(weightToDouble!!, heightToDouble!!) else 0.0
    }
    var recommendedStepCount by remember(calculatedBMI) {
        mutableStateOf(
            recommendSteps(
                calculatedBMI
            )
        )
    }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.padding(Paddings.medium))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = weight,
                    onValueChange = { new ->
                        onWeightChange(new.filter { it.isDigit() })
                    },
                    label = { Text(text = "Weight(kg)") },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                )
                Spacer(Modifier.padding(Paddings.small))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = height,
                    onValueChange = { new ->
                        onHeightChange(new.filter { it.isDigit() })
                    },
                    label = { Text(text = "Height(cm)") },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                )
                Spacer(Modifier.padding(Paddings.medium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colors.outlinedButton,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BMI: %.1f".format(calculatedBMI),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.padding(Paddings.medium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colors.outlinedButton,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily recommend steps: $recommendedStepCount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.padding(Paddings.medium))
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss,
                    text = "Close",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}

fun calculateBMI(weight: Double, height: Double): Double {
    val heightToMeter = height / 100
    return weight / heightToMeter.pow(2)
}

private fun recommendSteps(bmi: Double): String = when {
    bmi <= 0.0 -> "0"
    bmi < 17.5 -> "6000"
    bmi < 18.5 -> "6500"
    bmi < 21.0 -> "7500"
    bmi < 23.0 -> "9000"
    bmi < 25.0 -> "9500"
    bmi < 27.5 -> "10000"
    bmi < 30.0 -> "10500"
    else -> "11500+"
}