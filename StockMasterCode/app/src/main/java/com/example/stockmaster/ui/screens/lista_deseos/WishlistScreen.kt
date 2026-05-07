package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

@Composable
fun WishlistScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()
    val listas by viewModel.listas.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var expandedId by remember { mutableStateOf<String?>(null) }

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    var nombreEditado by remember {
        mutableStateOf("")
    }

    var listaSeleccionada by remember {
        mutableStateOf("")
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var listaEliminar by remember {
        mutableStateOf("")
    }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "☰",
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "Listas",
                        fontSize = 18.sp
                    )
                }

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                modifier = Modifier.clickable {
                    navController.navigate("crear_lista")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(listas) { lista ->

                val id = lista["id"] ?: ""
                val nombre = lista["nombre"] ?: ""
                val tiendaId = lista["tiendaId"] ?: ""

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            navController.navigate("detalle_lista/$id/$nombre/$tiendaId")
                        }
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(nombre)

                        Box {

                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    expandedId = id
                                }
                            )

                            DropdownMenu(
                                expanded = expandedId == id,
                                onDismissRequest = { expandedId = null }
                            ) {

                                DropdownMenuItem(
                                    text = { Text("Editar") },
                                    onClick = {
                                        listaSeleccionada = id
                                        nombreEditado = nombre

                                        showEditDialog = true
                                        expandedId = null
                                    }
                                )

                                DropdownMenuItem(
                                    text = { Text("Eliminar") },
                                    onClick = {

                                        listaEliminar = id
                                        showDeleteDialog = true

                                        expandedId = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showEditDialog) {

            AlertDialog(
                onDismissRequest = {
                    showEditDialog = false
                },

                title = {
                    Text("Editar lista")
                },

                text = {

                    OutlinedTextField(
                        value = nombreEditado,
                        onValueChange = {
                            nombreEditado = it
                        },
                        singleLine = true
                    )
                },

                confirmButton = {

                    Button(
                        onClick = {

                            if (nombreEditado.isNotBlank()) {

                                viewModel.editarNombreLista(
                                    listaSeleccionada,
                                    nombreEditado.trim()
                                )

                                showEditDialog = false
                            }

                            showEditDialog = false
                        }
                    ) {
                        Text("Guardar")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            showEditDialog = false
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (showDeleteDialog) {

            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                },

                title = {
                    Text("Eliminar lista")
                },

                text = {
                    Text(
                        "¿Seguro que deseas eliminar esta lista?"
                    )
                },

                confirmButton = {

                    Button(
                        onClick = {

                            viewModel.eliminarLista(listaEliminar)

                            showDeleteDialog = false
                        }
                    ) {
                        Text("Eliminar")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
        }
    }
}