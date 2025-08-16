package com.elpablo.fscslutsky.core.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FSCSlutskyAlertDialog(
    modifier: Modifier = Modifier,
    onConfirmation: () -> Unit = {  },
    onDismissRequest: () -> Unit = {  },
    title: String = "Ошибка",
    buttonText: String = "Закрыть",
    dialogText: String
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(text = buttonText)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                modifier = Modifier.verticalScroll(state = rememberScrollState()),
                text = dialogText
            )
        }
    )
}