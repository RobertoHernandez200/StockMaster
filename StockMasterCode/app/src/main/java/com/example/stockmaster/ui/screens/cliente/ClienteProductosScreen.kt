package com.example.stockmaster.ui.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

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

    var productos by remember {
        mutableStateOf<List<ProductoCliente>>(emptyList())
    }

    // ==================== FIREBASE REALTIME ====================

    LaunchedEffect(Unit) {

        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios")
            .document(tiendaId)
            .collection("productos")
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {

                    productos = snapshot.documents.map {

                        ProductoCliente(
                            nombre = it.getString("nombre") ?: "",
                            valor = it.getDouble("valor") ?: 0.0,
                            stock = (it.getLong("stock") ?: 0).toInt()
                        )
                    }
                }
            }
    }

    // ==================== UI ====================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ===== TOP BAR =====

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            IconButton(onClick = onBack) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Productos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ==================== LISTA ====================

        LazyColumn {

            items(productos) { producto ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = producto.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Precio: $${producto.valor}",
                            color = Color.Gray
                        )

                        Text(
                            text = "Stock: ${producto.stock}",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}