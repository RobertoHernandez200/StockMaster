package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var telefono by remember { mutableStateOf("") }

    var editNombre by remember { mutableStateOf(false) }
    var editEmail by remember { mutableStateOf(false) }
    var editTelefono by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        db.collection("usuarios").document(usuarioId).get()
            .addOnSuccessListener {
                nombre = it.getString("nombre") ?: ""
                email = it.getString("email") ?: ""
                telefono = it.getString("telefono") ?: ""
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔙 VOLVER
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("← Usuarios")
        }

        Spacer(Modifier.height(10.dp))

        // 🔵 FOTO (INICIALES)
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nombre.take(2).uppercase(),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(email, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(30.dp))

        // 🔹 NOMBRE
        CampoEditable(
            label = nombre,
            editing = editNombre,
            onEdit = { editNombre = true },
            onValueChange = { nombre = it }
        )

        // 🔹 EMAIL
        CampoEditable(
            label = email,
            editing = editEmail,
            onEdit = { editEmail = true },
            onValueChange = { email = it }
        )

        // 🔹 TELÉFONO
        CampoEditable(
            label = telefono.ifEmpty { "Agregar teléfono" },
            editing = editTelefono,
            onEdit = { editTelefono = true },
            onValueChange = { telefono = it }
        )

        Spacer(Modifier.height(20.dp))

        // 🔘 CONFIRMAR
        Button(
            onClick = {
                db.collection("usuarios")
                    .document(usuarioId)
                    .update(
                        mapOf(
                            "nombre" to nombre,
                            "email" to email,
                            "telefono" to telefono
                        )
                    )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar")
        }

        Spacer(Modifier.height(10.dp))

        // 🗑 ELIMINAR
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

@Composable
fun CampoEditable(
    label: String,
    editing: Boolean,
    onEdit: () -> Unit,
    onValueChange: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (editing) {
            OutlinedTextField(
                value = label,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
            )
        } else {
            Text(label)
        }

        TextButton(onClick = onEdit) {
            Text("Editar", color = Color(0xFF6A5AE0))
        }
    }
}