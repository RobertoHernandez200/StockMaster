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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ProductosViewModel

@Composable
fun SeleccionarProductosScreen(
    navController: NavController,
    tiendaId: String,
    nombreLista: String
) {

    val viewModel: ProductosViewModel = viewModel()

    val productos by viewModel.productos.collectAsState()

    var seleccionados by remember { mutableStateOf(setOf<String>()) }

    // 🔥 CARGAR PRODUCTOS DE LA TIENDA
    LaunchedEffect(Unit) {
        viewModel.cargarProductosDeTienda(tiendaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {

        Text("Seleccionar productos", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 SI NO HAY PRODUCTOS
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

                    val seleccionado = seleccionados.contains(producto.id)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                seleccionados = if (seleccionado) {
                                    seleccionados - producto.id
                                } else {
                                    seleccionados + producto.id
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

                // 🔥 AQUÍ LUEGO GUARDAS EN FIREBASE

                navController.navigate("wishlist") {
                    popUpTo("wishlist") { inclusive = true }
                }
            },
            enabled = seleccionados.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A5AE0)
            )
        ) {
            Text("Guardar lista")
        }
    }
}