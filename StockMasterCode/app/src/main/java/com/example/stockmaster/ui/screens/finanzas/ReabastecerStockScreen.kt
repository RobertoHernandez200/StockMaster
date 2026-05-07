package com.example.stockmaster.ui.screens.finanzas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ReabastecerStockScreen(
    navController: NavController,
    productoNombre: String
) {

    var cantidad by remember {
        mutableStateOf("")
    }

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {

        // ===== TOP BAR =====

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = PrincipalPurple
                )
            }

            Text(
                text = "Reabastecer Stock",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrincipalPurple
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = productoNombre,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = {
                cantidad = it
            },
            label = {
                Text("Cantidad a agregar")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                val userId = auth.currentUser?.uid ?: return@Button

                val cantidadInt = cantidad.toIntOrNull() ?: 0

                db.collection("usuarios")
                    .document(userId)
                    .collection("productos")
                    .whereEqualTo("nombre", productoNombre)
                    .get()
                    .addOnSuccessListener { result ->

                        for (document in result.documents) {

                            val stockActual =
                                (document.getLong("stock") ?: 0).toInt()

                            val nuevoStock =
                                stockActual + cantidadInt

                            db.collection("usuarios")
                                .document(userId)
                                .collection("productos")
                                .document(document.id)
                                .update(
                                    "stock",
                                    nuevoStock
                                )
                        }

                        navController.popBackStack()
                    }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = PrincipalPurple
            )
        ) {

            Text(
                "Guardar Stock",
                fontSize = 18.sp
            )
        }
    }
}