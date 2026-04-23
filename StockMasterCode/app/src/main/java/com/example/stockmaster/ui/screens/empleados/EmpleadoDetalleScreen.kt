package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EmpleadoDetalleScreen(
    usuarioId: String,
    navController: NavController
) {

    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("usuarios").document(usuarioId).get()
            .addOnSuccessListener {
                nombre = it.getString("nombre") ?: ""
                email = it.getString("email") ?: ""
            }
    }

    Column(Modifier.padding(20.dp)) {

        TextButton(onClick = {
            navController.popBackStack("usuarios", false)
        }) {
            Text("← Volver")
        }

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Button(
            onClick = {
                db.collection("usuarios").document(usuarioId)
                    .update(
                        mapOf(
                            "nombre" to nombre,
                            "email" to email
                        )
                    )
            }
        ) {
            Text("Actualizar")
        }

        Button(
            onClick = {
                db.collection("usuarios")
                    .document(usuarioId)
                    .delete()

                navController.popBackStack("usuarios", false)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar")
        }
    }
}