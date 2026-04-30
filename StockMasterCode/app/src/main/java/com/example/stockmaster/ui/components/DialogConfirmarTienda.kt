package com.example.stockmaster.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.stockmaster.model.Tienda

@Composable
fun DialogConfirmarTienda(
    tienda: Tienda,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text("Agregar tienda")
        },
        text = {
            Text("¿Quieres agregar la tienda \"${tienda.nombre}\"?")
        }
    )
}