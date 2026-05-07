package com.example.stockmaster.ui.screens.home_cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.stockmaster.viewmodel.ClienteViewModel
import com.example.stockmaster.ui.components.DialogCodigo
import com.example.stockmaster.ui.components.DialogConfirmarTienda
import com.example.stockmaster.ui.components.MenuDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeClienteScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()

    val tienda by viewModel.tienda.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

    var showDialogCodigo by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuDrawer(
                onInicio = {
                    scope.launch { drawerState.close() }
                },
                onTiendas = {
                    scope.launch { drawerState.close() }
                    navController.navigate("mis_tiendas")
                },
                onLista = {
                    scope.launch { drawerState.close() }
                    navController.navigate("wishlist")
                }
            )
        }
    ) {

        // 🔥 DIALOG CÓDIGO
        if (showDialogCodigo) {
            DialogCodigo(
                error = error,
                onConfirm = {
                    viewModel.buscarTienda(it)
                },
                onDismiss = {
                    showDialogCodigo = false
                    viewModel.limpiar()
                }
            )
        }

        // 🔥 CONFIRMAR TIENDA
        tienda?.let {
            DialogConfirmarTienda(
                tienda = it,
                onConfirm = {
                    viewModel.confirmarTienda()
                },
                onDismiss = {
                    viewModel.limpiar()
                }
            )
        }

        // 🔥 ÉXITO
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
        ) {

            // HEADER CON MENÚ REAL
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

                Text(
                    "Salir",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate("role_selection") {
                            popUpTo("role_selection") { inclusive = true }
                        }
                    }
                )
            }

            // TOTAL
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total")
                    Text("$0.00", fontSize = 26.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FONDO MORADO
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF6A5AE0), Color(0xFF7F67F8))
                        ),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .padding(16.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    CardItem(
                        title = "Lista de deseos",
                        subtitle = "Crear lista",
                        buttonText = "+ Agregar lista",
                        icon = Icons.Default.Favorite,
                        onClick = {
                            navController.navigate("wishlist")
                        }
                    )


                    CardItem(
                        title = "Tiendas",
                        subtitle = "Agregar nueva tienda",
                        buttonText = "+ Agregar código",
                        icon = Icons.Default.Store,
                        onClick = {
                            showDialogCodigo = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CardItem(
    title: String,
    subtitle: String,
    buttonText: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF6A5AE0),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }

            Button(onClick = onClick) {
                Text(buttonText)
            }
        }
    }
}