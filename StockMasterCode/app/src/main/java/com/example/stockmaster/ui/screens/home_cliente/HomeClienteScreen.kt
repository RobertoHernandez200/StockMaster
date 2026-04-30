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
import com.example.stockmaster.viewmodel.ClienteViewModel
import com.example.stockmaster.ui.components.DialogCodigo
import com.example.stockmaster.ui.components.DialogConfirmarTienda

@Composable
fun HomeClienteScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()

    val tienda by viewModel.tienda.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState() //  NUEVO

    var showDialogCodigo by remember { mutableStateOf(false) }

    //  DIALOG INGRESAR CÓDIGO
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

    //  DIALOG CONFIRMAR TIENDA
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

    //  MENSAJE DE ÉXITO (como tu imagen)
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
            title = {
                Text("¡Listo!")
            },
            text = {
                Text("Tienda agregada correctamente")
            }
        )
    }

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Inicio")

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
                    buttonText = "+ Agregar lista"
                )

                CardItem(
                    title = "Tiendas",
                    subtitle = "Agregar nueva tienda",
                    buttonText = "+ Agregar código",
                    onClick = {
                        showDialogCodigo = true
                    }
                )
            }
        }
    }
}

@Composable
fun CardItem(
    title: String,
    subtitle: String,
    buttonText: String,
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

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, RoundedCornerShape(10.dp))
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