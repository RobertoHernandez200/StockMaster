package com.example.stockmaster.ui.screens.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.stockmaster.ui.components.PrimaryButton
import com.example.stockmaster.ui.components.BackButton

@Composable
fun RegisterScreen(
    role: String,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        BackButton(onClick = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Crear cuenta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 ESTE BOTÓN DEBE NAVEGAR SÍ O SÍ
        PrimaryButton(
            text = "Registrar cuenta",
            onClick = {
                onRegisterSuccess()
            }
        )
    }
}