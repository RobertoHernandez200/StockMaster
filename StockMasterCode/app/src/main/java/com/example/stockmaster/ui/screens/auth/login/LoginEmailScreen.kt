package com.example.stockmaster.ui.screens.auth.login

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.ui.components.LineTextField
import com.example.stockmaster.ui.components.PrimaryButton

@Composable
fun LoginEmailScreen(
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        // 🔹 HEADER
        TextButton(onClick = onBack) {
            Text("← Ingresar")
        }

        // 🔹 CONTENIDO CENTRADO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Ingresa tu correo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔥 INPUT MODERNO
            LineTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                label = "Email"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔴 ERROR
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 🔘 BOTÓN
        PrimaryButton(
            text = "Avanzar",
            onClick = {

                when {
                    email.isBlank() -> {
                        errorMessage = "Ingresa un correo"
                        return@PrimaryButton
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        errorMessage = "Correo inválido"
                        return@PrimaryButton
                    }

                    else -> {
                        onNext(email)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}