package com.example.stockmaster.ui.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.example.stockmaster.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    role: String,
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (role == "tienda") "Login Tienda" else "Login Cliente"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Ingresar",
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        }
                    }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            text = "Ir a registro",
            onClick = onGoToRegister
        )
    }
}