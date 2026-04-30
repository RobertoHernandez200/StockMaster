package com.example.stockmaster.ui.screens.auth.register

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.LineTextField
import com.example.stockmaster.ui.components.PrimaryButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun generarCodigoTienda(): String {
    return (100000..999999).random().toString()
}

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

        TextButton(onClick = onBack) {
            Text("← Crear cuenta")
        }

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

            Text("Crear cuenta", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(24.dp))

            LineTextField(email, { email = it; errorMessage = "" }, "Email")

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(nombre, { nombre = it; errorMessage = "" }, "Nombre")

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(
                password,
                { password = it; errorMessage = "" },
                "Contraseña",
                true,
                passwordVisible
            ) { passwordVisible = !passwordVisible }

            Spacer(modifier = Modifier.height(16.dp))

            LineTextField(
                confirmPassword,
                { confirmPassword = it; errorMessage = "" },
                "Confirmar contraseña",
                true,
                confirmPasswordVisible
            ) { confirmPasswordVisible = !confirmPasswordVisible }

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        PrimaryButton(
            text = if (loading) "Registrando..." else "Registrarse",
            onClick = {

                val codigo = if (role == "tienda") generarCodigoTienda() else ""

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->

                        val userId = result.user?.uid ?: return@addOnSuccessListener

                        val user = hashMapOf(
                            "email" to email,
                            "nombre" to nombre,
                            "role" to role,
                            "codigo" to codigo
                        )

                        db.collection("usuarios")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                loading = false
                                onRegisterSuccess()
                            }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}