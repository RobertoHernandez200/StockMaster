package com.example.stockmaster.ui.screens.cliente

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.model.Tienda

@Composable
fun TiendasClienteScreen(
    tiendas: List<Tienda>,
    onClick: (Tienda) -> Unit,
    onBack: () -> Unit
) {

    Column {

        TextButton(onClick = onBack) {
            Text("← Volver")
        }

        Text("Tiendas", fontSize = 22.sp)

        LazyColumn {
            items(tiendas) { tienda ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable { onClick(tienda) }
                ) {
                    Text(
                        tienda.nombre,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}