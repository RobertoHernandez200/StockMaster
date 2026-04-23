package com.example.stockmaster.ui.screens.auth.register

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
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

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            TextButton(onClick = onBack) {
                Text("← Crear cuenta")
            }

            Spacer(modifier = Modifier.height(20.dp))
        // 🔹 CONTENIDO CENTRADO
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🔷 Avatar
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

            Spacer(modifier = Modifier.height(24.dp))

                Text("Crear cuenta", fontSize = 22.sp)

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

        Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        errorMessage = ""
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

        Spacer(modifier = Modifier.height(12.dp))

                // 🔐 PASSWORD
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = ""
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // 🔐 CONFIRM PASSWORD
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = ""
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

        Spacer(modifier = Modifier.height(16.dp))

                Text("*Mínimo 6 caracteres", fontSize = 12.sp)

                Spacer(modifier = Modifier.height(8.dp))

                // 🔴 ERROR
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    when {
                        email.isBlank() || nombre.isBlank() ||
                                password.isBlank() || confirmPassword.isBlank() -> {
                            errorMessage = "Todos los campos son obligatorios"
                            return@Button
                        }

                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            errorMessage = "Correo inválido"
                            return@Button
                        }

                        password.length < 6 -> {
                            errorMessage = "Mínimo 6 caracteres"
                            return@Button
                        }

                        password != confirmPassword -> {
                            errorMessage = "Las contraseñas no coinciden"
                            return@Button
                        }
                    }

                    loading = true
                    errorMessage = ""

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->

                            val userId = result.user?.uid

                            if (userId == null) {
                                loading = false
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
                                    loading = false
                                    onRegisterSuccess()
                                }
                                .addOnFailureListener {
                                    loading = false
                                    errorMessage = "Error guardando datos"
                                }
                        }
                        .addOnFailureListener {
                            loading = false
                            errorMessage = "Error: ${it.message}"
                        }
                },
                enabled = !loading,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A5AE0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(if (loading) "Registrando..." else "Registrarse")
            }
        }
    }
}
}