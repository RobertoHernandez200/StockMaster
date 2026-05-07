package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun DetalleListaScreen(
    navController: NavController,
    listaId: String,
    nombreLista: String,
    tiendaId: String
) {

    val viewModel: ClienteViewModel = viewModel()

    val listas by viewModel.listas.collectAsState()

    val lista = listas.find { it["id"] == listaId }

    val productos = lista?.get("productos")
        ?.split(",")
        ?.filter { it.isNotBlank() } ?: emptyList()

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

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Text(nombreLista, fontSize = 18.sp)

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                modifier = Modifier.clickable {
                    navController.navigate(
                        "seleccionar_productos/$listaId/$tiendaId/$nombreLista"
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (productos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay productos en la lista", color = Color.Gray)
            }
        } else {
            LazyColumn {

                items(productos) { producto ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(producto)

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                viewModel.eliminarProductoDeLista(
                                    listaId,
                                    producto
                                )
                            }
                        )
                    }

                    Divider()
                }
            }
        }
    }
}