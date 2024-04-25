package com.google.jetstream.presentation.screens.liveTv.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.jetstream.data.models.ProgramRowItems


@Composable
fun RecordDialogContent(
    showDialog: Boolean,
    program: ProgramRowItems,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(program.programName) },
            text = { Text(
                "From ${program.programStart} to ${program.programEnd} ")},
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.DarkGray
                    )
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}