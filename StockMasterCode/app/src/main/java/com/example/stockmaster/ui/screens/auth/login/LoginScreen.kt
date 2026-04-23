package com.example.stockmaster.ui.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.stockmaster.ui.components.PrimaryButton
import com.example.stockmaster.ui.components.BackButton

@Composable
fun LoginScreen(
    role: String,
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 🔹 HEADER
        BackButton(onClick = onBack)

        // 🔹 CONTENIDO CENTRADO
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Login $role",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Ingresar",
                onClick = {

                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Completa todos los campos."
                        return@PrimaryButton
                    }

                    errorMessage = "Cargando..."

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {

                            val userId = auth.currentUser?.uid

                            if (userId == null) {
                                errorMessage = "Error obteniendo usuario"
                                return@addOnSuccessListener
                            }

                            db.collection("usuarios")
                                .document(userId)
                                .get()
                                .addOnSuccessListener { document ->

                                    if (document.exists()) {

                                        val tipo = document.getString("role")

                                        if (tipo == role) {
                                            errorMessage = ""
                                            onLoginSuccess()
                                        } else {
                                            errorMessage = "Este usuario no es $role"
                                        }

                                    } else {
                                        errorMessage = "No se encontraron datos del usuario"
                                    }
                                }
                                .addOnFailureListener {
                                    errorMessage = "Error al obtener datos"
                                }
                        }
                        .addOnFailureListener {
                            errorMessage = "Credenciales incorrectas"
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryButton(
                text = "Registrarse",
                onClick = onGoToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}