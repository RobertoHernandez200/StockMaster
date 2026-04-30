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

@Composable
fun HomeClienteScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()

    val tiendaId by viewModel.tiendaId.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    // 🔥 NAVEGACIÓN
    LaunchedEffect(tiendaId) {
        tiendaId?.let {
            navController.navigate("productos_tienda/$it")
        }
    }

    // 🔥 DIALOG CON ERROR
    if (showDialog) {
        DialogCodigo(
            error = error, // 👈 PASAMOS EL ERROR
            onConfirm = {
                viewModel.ingresarCodigo(it)
            },
            onDismiss = {
                showDialog = false
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

        // 🔥 TOTAL CENTRADO CORRECTO
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total", fontSize = 14.sp)
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
                        showDialog = true
                    }
                )
            }
        }
    }
}