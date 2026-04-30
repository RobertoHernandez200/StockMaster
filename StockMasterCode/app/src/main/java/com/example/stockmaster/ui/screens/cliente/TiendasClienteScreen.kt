package com.example.stockmaster.ui.screens.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.model.Tienda

@Composable
fun TiendasClienteScreen(
    tiendas: List<Tienda>,
    onClick: (Tienda) -> Unit,
    onDelete: (Tienda) -> Unit,
    onBack: () -> Unit
) {

    var search by remember { mutableStateOf("") }

    // 🔥 NUEVO: control del diálogo
    var tiendaAEliminar by remember { mutableStateOf<Tienda?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(onClick = onBack) {
                Text("← Volver")
            }

            Text("Tiendas", fontSize = 20.sp)

            IconButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        }

        // BUSCADOR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFF6A5AE0), Color(0xFF7F67F8))
                    ),
                    shape = RoundedCornerShape(50)
                )
                .padding(12.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Default.Search, contentDescription = "", tint = Color.White)

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Buscar tienda", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // LISTA
        LazyColumn {

            items(tiendas.filter {
                it.nombre.contains(search, ignoreCase = true)
            }) { tienda ->

                TiendaItem(
                    tienda = tienda,
                    onClick = onClick,
                    onDelete = {
                        tiendaAEliminar = tienda
                    }
                )
            }
        }
    }

    // 🔥 DIÁLOGO DE CONFIRMACIÓN
    tiendaAEliminar?.let { tienda ->

        AlertDialog(
            onDismissRequest = {
                tiendaAEliminar = null
            },
            title = {
                Text("Eliminar tienda")
            },
            text = {
                Text("¿Seguro que deseas eliminar \"${tienda.nombre}\"?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete(tienda)
                        tiendaAEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        tiendaAEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TiendaItem(
    tienda: Tienda,
    onClick: (Tienda) -> Unit,
    onDelete: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onClick(tienda) }
            ) {

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF6A5AE0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tienda.nombre.first().toString(),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    tienda.nombre,
                    fontSize = 18.sp
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }

        Divider(
            thickness = 1.dp,
            color = Color(0xFF6A5AE0),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}