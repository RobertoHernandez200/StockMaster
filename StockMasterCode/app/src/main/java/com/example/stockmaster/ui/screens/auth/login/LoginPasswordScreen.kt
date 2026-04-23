package com.example.stockmaster.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.LineTextField
import com.example.stockmaster.ui.components.PrimaryButton

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        // HEADER
        TextButton(onClick = onBack) {
            Text("← Contraseña")
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
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = email,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // INPUT MODERNO
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "*Mínimo 6 caracteres",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ERROR
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¿Olvidaste la contraseña?",
                fontSize = 12.sp,
                color = Color(0xFF6A5AE0)
            )
        }

        //BOTÓN
        PrimaryButton(
            text = if (loading) "Entrando..." else "Entrar",
            onClick = {

                when {
                    password.isBlank() -> {
                        errorMessage = "Ingresa la contraseña"
                        return@PrimaryButton
                    }

                    password.length < 6 -> {
                        errorMessage = "Mínimo 6 caracteres"
                        return@PrimaryButton
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
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}