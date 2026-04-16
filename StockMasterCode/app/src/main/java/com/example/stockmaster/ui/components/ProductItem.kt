package com.example.stockmaster.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockmaster.model.Producto

@Composable
fun ProductItem(
    producto: Producto,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(producto.nombre)
            Text("💰 ${producto.valor}")
            Text("📦 ${producto.stock}")

            Spacer(modifier = Modifier.height(8.dp))

            Row {

                Button(onClick = onEdit) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onDelete) {
                    Text("Eliminar")
                }
            }
        }
    }
}