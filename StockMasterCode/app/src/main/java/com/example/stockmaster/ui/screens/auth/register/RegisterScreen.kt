package com.example.stockmaster.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        BackButton(onClick = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Crear cuenta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") })
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirmar contraseña") })

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Crear cuenta",
            onClick = {

                if (email.isEmpty() || nombre.isEmpty() || password.isEmpty()) {
                    errorMessage = "Completa todos los campos"
                    return@PrimaryButton
                }

                if (password != confirmPassword) {
                    errorMessage = "Las contraseñas no coinciden"
                    return@PrimaryButton
                }

                // 🔥 1. CREA USUARIO
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {

                        val userId = auth.currentUser?.uid

                        // 🔥 2. GUARDA EN FIRESTORE
                        val user = hashMapOf(
                            "email" to email,
                            "nombre" to nombre,
                            "tipo" to role
                        )

                        if (userId != null) {
                            db.collection("usuarios")
                                .document(userId)
                                .set(user)
                        }

                        // 🔥 3. NAVEGA SIEMPRE (CLAVE)
                        Toast.makeText(context, "Cuenta creada", Toast.LENGTH_SHORT).show()
                        onRegisterSuccess()
                    }
                    .addOnFailureListener {
                        errorMessage = "Error en registro"
                    }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
    }
}