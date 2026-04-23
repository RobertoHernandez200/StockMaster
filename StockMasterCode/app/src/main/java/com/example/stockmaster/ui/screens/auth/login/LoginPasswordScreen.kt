package com.example.stockmaster.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R

@Composable
fun LoginPasswordScreen(
    email: String,
    onLogin: (String, (Boolean, String?) -> Unit) -> Unit,
    onBack: () -> Unit
) {

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            TextButton(onClick = onBack) {
                Text("← Contraseña")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(email, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Contraseña") },
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
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("*Mínimo 6 caracteres", fontSize = 12.sp)

                Spacer(modifier = Modifier.height(8.dp))

                // 🔴 Mostrar error
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "¿Olvidaste la contraseña?",
                    fontSize = 12.sp,
                    color = Color(0xFF6A5AE0)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    when {
                        password.isBlank() -> {
                            errorMessage = "Ingresa la contraseña"
                            return@Button
                        }

                        password.length < 6 -> {
                            errorMessage = "Mínimo 6 caracteres"
                            return@Button
                        }
                    }

                    loading = true

                    onLogin(password) { success, error ->

                        loading = false

                        if (!success) {
                            errorMessage = error ?: "Error al iniciar sesión"
                        }
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
                Text(if (loading) "Entrando..." else "Entrar")
            }
        }
    }
}