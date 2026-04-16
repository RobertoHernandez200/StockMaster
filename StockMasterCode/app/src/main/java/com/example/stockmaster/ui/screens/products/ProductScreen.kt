package com.example.stockmaster.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.ui.components.ProductItem
import com.example.stockmaster.viewmodel.ProductosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductosViewModel = viewModel(),
    onBack: () -> Unit
) {

    val productos by viewModel.productos.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val valor by viewModel.valor.collectAsState()
    val stock by viewModel.stock.collectAsState()

    Scaffold(

        // 🔥 BARRA SUPERIOR
        topBar = {
            TopAppBar(
                title = { Text("Inventario") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.crearProducto() }
            ) {
                Text("+")
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre") }
            )

            OutlinedTextField(
                value = valor,
                onValueChange = { viewModel.onValorChange(it) },
                label = { Text("Valor") }
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { viewModel.onStockChange(it) },
                label = { Text("Stock") }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                }
            }
        }
    }
}