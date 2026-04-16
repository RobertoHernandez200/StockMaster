package com.example.stockmaster.ui.screens.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.stockmaster.ui.components.PrimaryButton

@Composable
fun RegisterScreen(
    role: String,
    onRegisterSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (role == "tienda") "Registro Tienda" else "Registro Cliente"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Registrarse",
            onClick = {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            val userId = auth.currentUser?.uid

                            val user = hashMapOf(
                                "email" to email,
                                "tipo" to role
                            )

                            if (userId != null) {
                                db.collection("usuarios")
                                    .document(userId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        onRegisterSuccess()
                                    }
                            }
                        }
                    }
            }
        )
    }
}