package com.example.stockmaster.ui.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class ProductoCliente(
    val nombre: String = "",
    val valor: Double = 0.0,
    val stock: Int = 0
)

@Composable
fun ClienteProductosScreen(
    tiendaId: String,
    onBack: () -> Unit
) {

    var productos by remember { mutableStateOf<List<ProductoCliente>>(emptyList()) }

    LaunchedEffect(Unit) {

        val db = FirebaseFirestore.getInstance()

        val result = db.collection("usuarios")
            .document(tiendaId)
            .collection("productos")
            .get()
            .await()

        productos = result.documents.map {
            ProductoCliente(
                nombre = it.getString("nombre") ?: "",
                valor = it.getDouble("valor") ?: 0.0,
                stock = (it.getLong("stock") ?: 0).toInt()
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        TextButton(onClick = onBack) {
            Text("← Volver")
        }

        Text("Productos", fontSize = 22.sp)

        LazyColumn {

            items(productos) { producto ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(producto.nombre, fontSize = 18.sp)

                        Text("Precio: $${producto.valor}")

                        Text("Stock: ${producto.stock}")
                    }
                }
            }
        }
    }
}