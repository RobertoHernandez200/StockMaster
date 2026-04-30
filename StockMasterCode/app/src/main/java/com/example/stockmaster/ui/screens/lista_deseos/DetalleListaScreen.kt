package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ClienteViewModel
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState

@Composable
fun DetalleListaScreen(
    listaId: String,
    navController: NavController
) {

    val viewModel: ClienteViewModel = viewModel()

    val productos by viewModel.productosLista.collectAsState()

    LaunchedEffect(listaId) {
        viewModel.cargarProductosDeLista(listaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {

        Text("Productos de la lista", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        if (productos.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos en esta lista", color = Color.Gray)
            }

        } else {

            LazyColumn {

                items(productos) { producto ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {

                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(producto.nombre)
                                Text("$${producto.valor}", color = Color.Gray)
                            }

                            TextButton(
                                onClick = {
                                    viewModel.eliminarProductoDeLista(
                                        listaId,
                                        producto.id
                                    )
                                }
                            ) {
                                Text("Eliminar", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 AGREGAR MÁS PRODUCTOS
        Button(
            onClick = {
                viewModel.tiendaSeleccionada?.let {
                    navController.navigate("seleccionar_productos/${it}/${listaId}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A5AE0)
            )
        ) {
            Text("Agregar productos")
        }
    }
}