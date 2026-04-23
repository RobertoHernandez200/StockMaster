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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

<<<<<<< HEAD
        // 🔙 Volver
        TextButton(onClick = onBack) {
            Text("← Contraseña")
        }
=======
        TextButton(onClick = onBack) { Text("← Contraseña") }
>>>>>>> 715ec2d0b71fefb2ba330664d9626531e260ef20

        Spacer(Modifier.height(40.dp))

        // 🔐 Contraseña
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
<<<<<<< HEAD
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔐 Confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                error = false
            },
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            isError = error,
            visualTransformation = if (visibleConfirm) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visibleConfirm = !visibleConfirm }) {
                    Icon(
                        imageVector = if (visibleConfirm) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ❌ Mensaje error
        if (error) {
            Text(
                text = "Las contraseñas no coinciden",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 🔘 Botón
        Button(
            onClick = {
                if (password != confirmPassword || password.isEmpty()) {
                    error = true
                    return@Button
                }

                loading = true
                onCreate(password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Crear usuario")
=======
>>>>>>> 715ec2d0b71fefb2ba330664d9626531e260ef20
            }
        )

        Spacer(Modifier.weight(1f))

        Button(onClick = { onCreate(password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Crear usuario")
        }
    }
}