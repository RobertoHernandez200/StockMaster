package com.example.stockmaster.ui.screens.proveedores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.model.Proveedor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ProveedorViewModel

data class Proveedor(
    val nombre: String,
    val contacto: String
)

@Composable
fun ProveedoresScreen(
    onBack: () -> Unit
) {

    val viewModel: ProveedorViewModel = viewModel()
    val proveedores by viewModel.proveedores.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }


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

                Text("Proveedores", fontSize = 20.sp)
            }

            IconButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CONTENEDOR MORADO
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
                    items(proveedores) { proveedor ->
                        ProveedorItem(proveedor)
                    }
                }
            }
        }
    }
    if (showDialog) {

        AlertDialog(

            onDismissRequest = {
                showDialog = false
            },

            title = {
                Text("Agregar proveedor")
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                        },
                        label = {
                            Text("Nombre")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                        },
                        label = {
                            Text("Correo")
                        }
                    )
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        if (
                            nombre.isNotBlank() &&
                            correo.isNotBlank()
                        ) {

                            viewModel.agregarProveedor(
                                nombre,
                                correo
                            )

                            nombre = ""
                            correo = ""

                            showDialog = false
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// CARD PROVEEDOR
@Composable
fun ProveedorItem(proveedor: Proveedor) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
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

            Column {

                Text(proveedor.nombre, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    proveedor.correo,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}