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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.LineTextField
import com.example.stockmaster.ui.components.PrimaryButton
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        // HEADER
        TextButton(onClick = onBack) {
            Text("← Crear cuenta")
        }

        // CONTENIDO CENTRADO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Crear cuenta",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // INPUTS TIPO LÍNEA
            LineTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                label = "Email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorMessage = ""
                },
                label = "Nombre"
            )

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                label = "Contraseña",
                isPassword = true,
                visible = passwordVisible,
                onToggleVisibility = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                },
                label = "Confirmar contraseña",
                isPassword = true,
                visible = confirmPasswordVisible,
                onToggleVisibility = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("*Mínimo 6 caracteres", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // BOTÓN
        PrimaryButton(
            text = if (loading) "Registrando..." else "Registrarse",
            onClick = {
                when {
                    email.isBlank() || nombre.isBlank() ||
                            password.isBlank() || confirmPassword.isBlank() -> {
                        errorMessage = "Todos los campos son obligatorios"
                        return@PrimaryButton
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        errorMessage = "Correo inválido"
                        return@PrimaryButton
                    }

                    password.length < 6 -> {
                        errorMessage = "Mínimo 6 caracteres"
                        return@PrimaryButton
                    }

                    password != confirmPassword -> {
                        errorMessage = "Las contraseñas no coinciden"
                        return@PrimaryButton
                    }
                }

                loading = true
                errorMessage = ""

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->

                        val userId = result.user?.uid ?: return@addOnSuccessListener

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
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}