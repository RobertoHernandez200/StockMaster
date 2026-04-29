package com.example.stockmaster.ui.screens.empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.automirrored.filled.ArrowBack

// MODELO
data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val email: String = "",
    val role: String = "",
    val createdBy: String = ""
)

@Composable
fun UsuariosScreen(
    navController: NavController,
    onAddUser: () -> Unit,
    onBack: () -> Unit
) {

    val db = FirebaseFirestore.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    // CARGA DE DATOS
    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->

                val lista = result.map {
                    Usuario(
                        id = it.id,
                        nombre = it.getString("nombre") ?: "",
                        email = it.getString("email") ?: "",
                        role = it.getString("role") ?: "",
                        createdBy = it.getString("createdBy") ?: ""
                    )
                }

                usuarios = lista
                    .filter {
                        it.createdBy == currentUserId || it.id == currentUserId
                    }
                    .distinctBy { it.id }
            }
    }

    val filtrados = usuarios.filter {
        it.nombre.contains(searchText, ignoreCase = true) ||
                it.email.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }

                Text("Usuarios", fontSize = 20.sp)
            }

            IconButton(onClick = onAddUser) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }

        // BUSCADOR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color(0xFF6A5AE0), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
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

        Spacer(modifier = Modifier.height(16.dp))

        // FONDO MORADO + LISTA
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A5AE0),
                            Color(0xFF8E7CFF)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                LazyColumn {
                    items(filtrados) { usuario ->
                        UsuarioItem(
                            usuario = usuario,
                            onClick = {
                                navController.navigate("detalle_usuario/${usuario.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

// ITEM USUARIO (CARD)
@Composable
fun UsuarioItem(
    usuario: Usuario,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(usuario.nombre, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    usuario.email,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(
                usuario.role,
                color = Color.Gray
            )
        }
    }
}