package com.example.stockmaster.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun DialogCodigo(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onConfirm(codigo) }) {
                Text("Ingresar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Agregar tienda") },
        text = {
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código de tienda") }
            )
        }
    )
}