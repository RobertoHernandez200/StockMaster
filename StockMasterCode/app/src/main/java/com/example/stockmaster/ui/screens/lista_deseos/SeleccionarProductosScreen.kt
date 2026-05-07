package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ProductosViewModel
import com.example.stockmaster.viewmodel.ClienteViewModel

@Composable
fun SeleccionarProductosScreen(
    navController: NavController,
    listaId: String?,
    tiendaId: String,
    nombreLista: String
) {

    val productosViewModel: ProductosViewModel = viewModel()
    val clienteViewModel: ClienteViewModel = viewModel()

    val productos by productosViewModel.productos.collectAsState()

    // GUARDADO CON NOMBRE
    var seleccionados by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(listaId) {

        if (listaId != null && listaId != "null") {

            val lista = clienteViewModel.listas.value.find {
                it["id"] == listaId
            }

            val productosGuardados = lista?.get("productos")
                ?.split(",")
                ?.filter { it.isNotBlank() }
                ?.toSet()
                ?: emptySet()

            seleccionados = productosGuardados
        }
    }

    // CARGA DE PRODUCTOS DE TIENDA
    LaunchedEffect(Unit) {
        productosViewModel.cargarProductosDeTienda(tiendaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {

        Text("Seleccionar productos", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        if (productos.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Esta tienda no tiene productos", color = Color.Gray)
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(productos) { producto ->

                    // 🔥 CAMBIO AQUÍ
                    val seleccionado = seleccionados.contains(producto.nombre)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                seleccionados = if (seleccionado) {
                                    seleccionados - producto.nombre
                                } else {
                                    seleccionados + producto.nombre
                                }
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(producto.nombre)
                            Text("$${producto.valor}", color = Color.Gray)
                        }

                        Checkbox(
                            checked = seleccionado,
                            onCheckedChange = null
                        )
                    }

                    Divider(color = Color.LightGray)
                }
            }
        }

        // 🔥 BOTÓN GUARDAR
        Button(
            onClick = {

                if (listaId == null || listaId == "null") {

                    clienteViewModel.guardarLista(
                        nombreLista,
                        tiendaId,
                        seleccionados.toList()
                    )

                } else {

                    clienteViewModel.actualizarProductosLista(
                        listaId,
                        seleccionados.toList()
                    )
                }

                navController.navigate("wishlist") {
                    popUpTo("wishlist") { inclusive = true }
                }
            },
            enabled = seleccionados.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .offset(y = 10.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A5AE0)
            )
        ) {
            Text("Guardar lista")
        }
    }
}