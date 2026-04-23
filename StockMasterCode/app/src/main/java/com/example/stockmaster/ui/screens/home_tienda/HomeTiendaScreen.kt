package com.example.stockmaster.ui.screens.home_tienda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.stockmaster.ui.components.PrimaryButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeTiendaScreen(
    onAddProduct: () -> Unit,
    onUsuarios: () -> Unit,
    onLogout: () -> Unit
) {

    val auth = FirebaseAuth.getInstance()

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

            Text("Inicio", fontSize = 20.sp)

            TextButton(
                onClick = {
                    auth.signOut()
                    onLogout()
                }
            ) {
                Text("Salir")
            }
        }

        // RESUMEN
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("HOY", fontSize = 14.sp)

            Text(
                text = "$0.00",
                fontSize = 32.sp
            )

            Text("Código: PDF417", fontSize = 14.sp)
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
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                CardBox(
                    title = "Productos",
                    subtitle = "Agregar nuevo producto",
                    buttonText = "Añadir producto",
                    onClick = onAddProduct
                )

                Spacer(modifier = Modifier.height(20.dp))

                CardBox(
                    title = "Usuarios",
                    subtitle = "Crear nuevo usuario",
                    buttonText = "Agregar usuario",
                    onClick = onUsuarios
                )

                Spacer(modifier = Modifier.height(20.dp))

                CardBox(
                    title = "Proveedores",
                    subtitle = "Añadir nuevo contacto",
                    buttonText = "Agregar contacto",
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun CardBox(
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ICONO
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(title, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(4.dp))

                Text(subtitle, fontSize = 14.sp, color = Color.Gray)
            }

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(50)
            ) {
                Text("+ $buttonText")
            }
        }
    }
}