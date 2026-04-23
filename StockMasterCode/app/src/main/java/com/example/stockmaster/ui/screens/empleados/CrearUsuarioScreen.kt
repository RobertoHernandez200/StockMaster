package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CrearUsuarioScreen(
    onNext: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(24.dp)
    ) {

        TextButton(onClick = onBack) { Text("← Nuevo usuario") }

        Spacer(Modifier.height(40.dp))

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.weight(1f))

        Button(onClick = { onNext(nombre, email) }, modifier = Modifier.fillMaxWidth()) {
            Text("Avanzar")
        }
    }
}