package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.background

data class Usuario(
    val nombre: String = "",
    val email: String = "",
    val role: String = "",
    val createdBy: String = ""
)

@Composable
fun UsuariosScreen(
    onAddUser: () -> Unit,
    onBack: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->

                val lista = result.map {
                    Usuario(
                        nombre = it.getString("nombre") ?: "",
                        email = it.getString("email") ?: "",
                        role = it.getString("role") ?: "",
                        createdBy = it.getString("createdBy") ?: ""
                    )
                }

                // 🔥 1. Filtrar solo míos o creados por mí
                val filtrados = lista.filter {
                    it.createdBy == currentUserId || it.email == FirebaseAuth.getInstance().currentUser?.email
                }
                    .distinctBy { it.email }

                // 🔥 2. Quitar duplicados por email
                usuarios = filtrados.distinctBy { it.email }
            }
    }

    // 🔎 filtro de búsqueda
    val usuariosFiltrados = usuarios.filter {
        it.nombre.contains(searchText, ignoreCase = true) ||
                it.email.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔝 Header con back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text("Usuarios", style = MaterialTheme.typography.titleLarge)
            }

            IconButton(onClick = onAddUser) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }

        // 🔍 Buscador funcional
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color(0xFF6A5AE0))
                .padding(12.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))

            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar usuarios") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(usuariosFiltrados) { usuario ->
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