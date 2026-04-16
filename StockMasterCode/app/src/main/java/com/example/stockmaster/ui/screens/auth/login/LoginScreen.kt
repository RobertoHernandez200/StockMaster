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

        BackButton(onClick = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Login $role")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Ingresar",
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                db.collection("usuarios").document(userId).get()
                                    .addOnSuccessListener { document ->
                                        val tipo = document.getString("tipo")
                                        if (tipo == role) {
                                            onLoginSuccess()
                                        } else {
                                            errorMessage = "Tipo incorrecto"
                                        }
                                    }
                            }
                        } else {
                            errorMessage = "Credenciales incorrectas"
                        }
                    }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            text = "Registrarse",
            onClick = onGoToRegister
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
    }
}