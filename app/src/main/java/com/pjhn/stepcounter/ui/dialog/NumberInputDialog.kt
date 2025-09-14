package com.pjhn.stepcounter.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun NumberInputDialog(
    initialNumber: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var text by rememberSaveable(initialNumber) { mutableStateOf(initialNumber.toString()) }
    val isValid = text.toIntOrNull() != null

    AlertDialog(
        title = { Text(text = "Set step goal") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { new -> text = new.filter { it.isDigit() } },
                label = { Text("Step goal") },
                supportingText = { Text("Enter a number") },
                singleLine = true,
            )
        },
        confirmButton = {
            TextButton(enabled = isValid, onClick = { onConfirm(text.toInt()) }) {
                Text(
                    text = "Setting",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background
    )
}