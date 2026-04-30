package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.stockmaster.ui.components.MenuDrawer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()
    val listas by viewModel.listas.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var listaEditar by remember { mutableStateOf<Map<String, Any>?>(null) }
    var nombreEditado by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuDrawer(
                onInicio = {
                    scope.launch { drawerState.close() }
                    navController.navigate("home_cliente")
                },
                onTiendas = {
                    scope.launch { drawerState.close() }
                    navController.navigate("mis_tiendas")
                },
                onLista = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "☰",
                    fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        scope.launch { drawerState.open() }
                    }
                )

                Text("Listas", fontSize = 18.sp)

                Text(
                    "+",
                    fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("crear_lista")
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (listas.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no tienes listas...", color = Color.Gray)
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(listas) { lista ->

                        val nombre = lista["nombre"] as? String ?: ""

                        var expanded by remember { mutableStateOf(false) }

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEDEDED)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(nombre, fontSize = 16.sp)

                                Box {

                                    IconButton(onClick = {
                                        expanded = true
                                    }) {
                                        Icon(Icons.Default.MoreVert, contentDescription = "")
                                    }

                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {

                                        DropdownMenuItem(
                                            text = { Text("Editar") },
                                            onClick = {
                                                expanded = false
                                                listaEditar = lista
                                                nombreEditado = nombre
                                            }
                                        )

                                        DropdownMenuItem(
                                            text = { Text("Eliminar", color = Color.Red) },
                                            onClick = {
                                                expanded = false
                                                // 🔥 aquí luego borras en firebase
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 🔥 DIALOG EDITAR
    listaEditar?.let {

        AlertDialog(
            onDismissRequest = { listaEditar = null },
            title = { Text("Editar lista") },
            text = {
                OutlinedTextField(
                    value = nombreEditado,
                    onValueChange = { nombreEditado = it },
                    label = { Text("Nombre") }
                )
            },
            confirmButton = {
                Button(onClick = {

                    // 🔥 AQUÍ luego actualizas en Firebase

                    listaEditar = null
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { listaEditar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}