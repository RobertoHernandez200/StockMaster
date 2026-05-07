package com.example.stockmaster.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.ui.components.ProductItem
import com.example.stockmaster.viewmodel.ProductosViewModel

@Composable
fun ProductScreen(
    viewModel: ProductosViewModel = viewModel(),
    onBack: () -> Unit
) {

    val productos by viewModel.productos.collectAsState()

    val nombre by viewModel.nombre.collectAsState()
    val valor by viewModel.valor.collectAsState()
    val stock by viewModel.stock.collectAsState()
    val categoria by viewModel.categoria.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Inventario",
                fontSize = 20.sp
            )

            TextButton(
                onClick = onBack
            ) {

                Text("← Volver")
            }
        }

        // CONTENEDOR MORADO

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A5AE0),
                            Color(0xFF8E7CFF)
                        )
                    ),

                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                // FORMULARIO

                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Agregar producto",
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        OutlinedTextField(
                            value = nombre,
                            onValueChange = {
                                viewModel.onNombreChange(it)
                            },
                            label = {
                                Text("Nombre")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        OutlinedTextField(
                            value = categoria,
                            onValueChange = {
                                viewModel.onCategoriaChange(it)
                            },
                            label = {
                                Text("Categoría")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        OutlinedTextField(
                            value = valor,
                            onValueChange = {
                                viewModel.onValorChange(it)
                            },
                            label = {
                                Text("Valor")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        OutlinedTextField(
                            value = stock,
                            onValueChange = {
                                viewModel.onStockChange(it)
                            },
                            label = {
                                Text("Stock")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.crearProducto()
                            },

                            shape = RoundedCornerShape(50),

                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text("+ Guardar producto")
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // LISTA PRODUCTOS

                LazyColumn {

                    items(productos) { producto ->

                        ProductItem(
                            producto = producto,

                            onDelete = {
                                viewModel.eliminarProducto(producto.id)
                            },

                            onEdit = {
                                viewModel.cargarProducto(producto)
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                    }
                }
            }
        }
    }
}