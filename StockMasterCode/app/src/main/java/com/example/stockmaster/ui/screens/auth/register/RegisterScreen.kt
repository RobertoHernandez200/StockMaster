package com.example.stockmaster.ui.screens.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.stockmaster.ui.components.PrimaryButton
import com.example.stockmaster.ui.components.BackButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(
    role: String,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        BackButton(onClick = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Crear cuenta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔴 Mostrar errores
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        PrimaryButton(
            text = if (loading) "Registrando..." else "Registrar cuenta",
            onClick = {

                // 🔥 VALIDACIONES
                when {
                    email.isEmpty() || nombre.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                        errorMessage = "Todos los campos son obligatorios"
                        return@PrimaryButton
                    }

                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        errorMessage = "Correo inválido"
                        return@PrimaryButton
                    }

                    password.length < 6 -> {
                        errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        return@PrimaryButton
                    }

                    password != confirmPassword -> {
                        errorMessage = "Las contraseñas no coinciden"
                        return@PrimaryButton
                    }
                }

                // limpiar error y activar loading
                errorMessage = ""
                loading = true

                // 🔥 FIREBASE AUTH
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->

                        val userId = result.user?.uid

                        if (userId == null) {
                            errorMessage = "Error creando usuario"
                            return@addOnSuccessListener
                        }

                        val user = hashMapOf(
                            "email" to email,
                            "nombre" to nombre,
                            "role" to role
                        )

                        db.collection("usuarios")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                onRegisterSuccess() // 🔥 SOLO aquí
                            }
                            .addOnFailureListener {
                                errorMessage = "Error guardando en Firestore: ${it.message}"
                            }
                    }
                    .addOnFailureListener {
                        errorMessage = "Error en Auth: ${it.message}"
                    }
            }
        )
    }
}