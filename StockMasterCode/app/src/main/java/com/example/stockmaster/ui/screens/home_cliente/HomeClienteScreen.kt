package com.example.stockmaster.ui.screens.home_cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockmaster.viewmodel.ClienteViewModel
import com.example.stockmaster.ui.components.DialogCodigo

@Composable
fun HomeClienteScreen(
    navController: NavController
) {

    val viewModel: ClienteViewModel = viewModel()

    val tiendaId by viewModel.tiendaId.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    // 🔥 Dialog
    if (showDialog) {
        DialogCodigo(
            onConfirm = {
                viewModel.ingresarCodigo(it)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    // 🔥 Navegación automática cuando encuentra tienda
    LaunchedEffect(tiendaId) {
        tiendaId?.let {
            navController.navigate("productos_tienda/$it")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Inicio Cliente",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { showDialog = true }
        ) {
            Text("+ Agregar código")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 Mostrar error si el código no existe
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}