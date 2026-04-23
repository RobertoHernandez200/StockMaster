package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.background

data class Usuario(
    val nombre: String = "",
    val email: String = "",
    val role: String = ""
)

@Composable
fun UsuariosScreen(
    onAddUser: () -> Unit,
    onBack: () -> Unit
) {

    val db = FirebaseFirestore.getInstance()
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                usuarios = result.map {
                    Usuario(
                        nombre = it.getString("nombre") ?: "",
                        email = it.getString("email") ?: "",
                        role = it.getString("role") ?: ""
                    )
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔝 Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Usuarios", style = MaterialTheme.typography.titleLarge)

            IconButton(onClick = onAddUser) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }

        // 🔍 Buscador (solo visual)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color(0xFF6A5AE0))
                .padding(12.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Buscar usuarios", color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(usuarios) { usuario ->
                UsuarioItem(usuario)
            }
        }
    }
}

@Composable
fun UsuarioItem(usuario: Usuario) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(usuario.nombre)
            Text(usuario.email, style = MaterialTheme.typography.bodySmall)
        }

        Text(usuario.role)
    }
}