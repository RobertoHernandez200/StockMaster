package com.example.stockmaster.ui.screens.auth.register

import android.util.Log
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
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {

        BackButton(onClick = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Crear cuenta")

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

                if (nombre.isEmpty()) {
                    errorMessage = "Ingresa tu nombre"
                    return@PrimaryButton
                }

                if (password != confirmPassword) {
                    errorMessage = "Las contraseñas no coinciden"
                    return@PrimaryButton
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {

                        val userId = auth.currentUser?.uid

                        Log.d("REGISTER", "UID: $userId")

                        if (userId == null) {
                            errorMessage = "Error obteniendo usuario"
                            return@addOnSuccessListener
                        }

                        val user = hashMapOf(
                            "email" to email,
                            "nombre" to nombre,
                            "tipo" to role
                        )

                        db.collection("usuarios")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {

                                Log.d("REGISTER", "GUARDADO EN FIRESTORE")

                                Toast.makeText(context, "Cuenta creada", Toast.LENGTH_SHORT).show()

                                onRegisterSuccess()
                            }
                            .addOnFailureListener { e ->

                                Log.e("REGISTER", "ERROR FIRESTORE", e)

                                errorMessage = "Error guardando en Firestore"
                            }

                    }
                    .addOnFailureListener { e ->

                        Log.e("REGISTER", "ERROR AUTH", e)

                        errorMessage = "Error en registro"
                    }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(errorMessage, color = MaterialTheme.colorScheme.error)
    }
}