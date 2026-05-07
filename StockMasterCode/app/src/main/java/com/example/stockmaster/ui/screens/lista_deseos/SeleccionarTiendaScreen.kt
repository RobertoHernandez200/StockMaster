package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ClienteViewModel

@Composable
fun SeleccionarTiendaScreen(
    navController: NavController,
    nombreLista: String
) {

    val viewModel: ClienteViewModel = viewModel()

    val tiendas by viewModel.tiendas.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔙 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Seleccionar tienda", fontSize = 16.sp)
        }

        // 🔥 TÍTULO MORADO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6A5AE0))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Selecciona la tienda",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        // 🏪 LISTA DE TIENDAS
        LazyColumn {

            items(tiendas) { tienda ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            // 🔥 AQUÍ VAS A PRODUCTOS
                            navController.navigate(
                                "seleccionar_productos/${tienda.id}/$nombreLista"
                            )
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = tienda.nombre,
                            tint = Color(0xFF6A5AE0),
                            modifier = Modifier.size(26.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(tienda.nombre)
                    }

                    Text(">", color = Color.Gray)
                }

                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}