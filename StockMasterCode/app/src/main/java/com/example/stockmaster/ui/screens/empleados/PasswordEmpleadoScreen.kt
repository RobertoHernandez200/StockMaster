package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun PasswordEmpleadoScreen(
    onCreate: (String) -> Unit,
    onBack: () -> Unit
) {

    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(24.dp)
    ) {

        TextButton(onClick = onBack) { Text("← Contraseña") }

        Spacer(Modifier.height(40.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(Modifier.weight(1f))

        Button(onClick = { onCreate(password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Crear usuario")
        }
    }
}