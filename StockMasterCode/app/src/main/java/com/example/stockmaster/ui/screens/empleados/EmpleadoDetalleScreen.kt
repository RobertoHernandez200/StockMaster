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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(onClick = { navController.popBackStack() }) {
            Text("← Volver")
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                db.collection("usuarios")
                    .document(usuarioId)
                    .update(
                        mapOf(
                            "nombre" to nombre,
                            "email" to email
                        )
                    )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                db.collection("usuarios")
                    .document(usuarioId)
                    .delete()

                navController.navigate("home_tienda") {
                    popUpTo(0)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar usuario")
        }
    }
}