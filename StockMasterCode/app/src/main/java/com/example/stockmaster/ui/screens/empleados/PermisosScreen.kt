package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PermisosScreen(
    nombre: String,
    email: String,
    onNext: (Map<String, Boolean>) -> Unit,
    onBack: () -> Unit
) {

    var productos by remember { mutableStateOf(true) }
    var stock by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Permisos")
        }

        Spacer(Modifier.height(24.dp))

        // 👇 Permisos
        PermisoItem(
            titulo = "Registrar o alterar productos",
            checked = productos,
            onCheckedChange = { productos = it }
        )

        PermisoItem(
            titulo = "Gestionar stock",
            checked = stock,
            onCheckedChange = { stock = it }
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                onNext(
                    mapOf(
                        "productos" to productos,
                        "stock" to stock
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Avanzar")
        }
    }
}

@Composable
fun PermisoItem(
    titulo: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(titulo)
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}