package com.example.stockmaster.ui.screens.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockmaster.model.Tienda
import com.example.stockmaster.viewmodel.ClienteViewModel
import com.example.stockmaster.ui.components.MenuDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendasClienteScreen(
    tiendas: List<Tienda>,
    onDelete: (Tienda) -> Unit,
    viewModel: ClienteViewModel,
    navController: NavController
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var search by remember { mutableStateOf("") }
    var tiendaAEliminar by remember { mutableStateOf<Tienda?>(null) }
    var showDialogCodigo by remember { mutableStateOf(false) }

    val tienda by viewModel.tienda.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

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
                },
                onLista = {
                    scope.launch { drawerState.close() }
                    navController.navigate("wishlist")
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
        ) {

            // 🔥 HEADER EXACTO COMO HOME
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "☰",
                    fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        scope.launch { drawerState.open() }
                    }
                )

                Text("Tiendas", fontSize = 20.sp)

                Text("") // para centrar
            }

            // 🔥 BUSCADOR
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

            // 🔥 LISTA
            LazyColumn {

                items(tiendas.filter {
                    it.nombre.contains(search, ignoreCase = true)
                }) { tiendaItem ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color(0xFF6A5AE0), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    tiendaItem.nombre.first().toString(),
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(tiendaItem.nombre, fontSize = 18.sp)
                        }

                        IconButton(
                            onClick = {
                                tiendaAEliminar = tiendaItem
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                        }
                    }

                    Divider(color = Color(0xFF6A5AE0))
                }
            }
        }
    }

    // 🔥 ELIMINAR
    tiendaAEliminar?.let {
        AlertDialog(
            onDismissRequest = { tiendaAEliminar = null },
            title = { Text("Eliminar tienda") },
            text = { Text("¿Seguro que deseas eliminar ${it.nombre}?") },
            confirmButton = {
                Button(onClick = {
                    onDelete(it)
                    tiendaAEliminar = null
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { tiendaAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // 🔥 AGREGAR TIENDA
    if (showDialogCodigo) {
        com.example.stockmaster.ui.components.DialogCodigo(
            error = error,
            onConfirm = { viewModel.buscarTienda(it) },
            onDismiss = {
                showDialogCodigo = false
                viewModel.limpiar()
            }
        )
    }

    tienda?.let {
        com.example.stockmaster.ui.components.DialogConfirmarTienda(
            tienda = it,
            onConfirm = { viewModel.confirmarTienda() },
            onDismiss = { viewModel.limpiar() }
        )
    }

    if (success) {
        AlertDialog(
            onDismissRequest = {
                viewModel.limpiar()
                showDialogCodigo = false
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.limpiar()
                    showDialogCodigo = false
                }) {
                    Text("OK")
                }
            },
            title = { Text("¡Listo!") },
            text = { Text("Tienda agregada correctamente") }
        )
    }
}