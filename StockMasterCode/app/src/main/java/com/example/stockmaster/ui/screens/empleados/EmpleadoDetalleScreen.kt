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

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var editNombre by remember { mutableStateOf(false) }
    var editEmail by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

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

        // 🔵 AVATAR
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
            value = nombre,
            editing = editNombre,
            onEdit = { editNombre = true },
            onValueChange = { nombre = it }
        )

        // 🔹 EMAIL
        CampoEditable(
            value = email,
            editing = editEmail,
            onEdit = { editEmail = true },
            onValueChange = { email = it }
        )

        Spacer(Modifier.height(20.dp))

        // 🔐 CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Nueva contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // 🔘 CONFIRMAR
        Button(
            onClick = {
                if (password == confirmPassword || password.isEmpty()) {

                    db.collection("usuarios")
                        .document(usuarioId)
                        .update(
                            mapOf(
                                "nombre" to nombre,
                                "email" to email
                            )
                        )

                    // ⚠️ NOTA: cambiar contraseña en Firebase Auth es más complejo
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar")
        }

        Spacer(Modifier.height(10.dp))

        // 🗑 BOTÓN ELIMINAR
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar usuario")
        }
    }

    // ⚠️ DIÁLOGO CONFIRMACIÓN
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        db.collection("usuarios")
                            .document(usuarioId)
                            .delete()

                        navController.navigate("home_tienda") {
                            popUpTo(0)
                        }
                    }
                ) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Eliminar usuario") },
            text = { Text("¿Seguro que quieres eliminar este usuario?") }
        )
    }
}

@Composable
fun CampoEditable(
    value: String,
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
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
            )
        } else {
            Text(value)
        }

        TextButton(onClick = onEdit) {
            Text("Editar", color = Color(0xFF6A5AE0))
        }
    }
}