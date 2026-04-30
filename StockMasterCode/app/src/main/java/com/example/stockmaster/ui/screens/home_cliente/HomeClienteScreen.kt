package com.example.stockmaster.ui.screens.home_cliente

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeClienteScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {

        // 🔹 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Inicio", color = Color.Black)

            Text(
                "Salir",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    navController.navigate("role_selection") {
                        popUpTo("role_selection") { inclusive = true }
                    }
                }
            )
        }

        // 🔹 TOTAL
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("HOY", fontSize = 12.sp)
            Text("$0.00", fontSize = 26.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 FONDO MORADO
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A5AE0),
                            Color(0xFF7F67F8)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(16.dp)
        ) {

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // 🔹 CARD LISTA DE DESEOS
                CardItem(
                    title = "Lista de deseos",
                    subtitle = "Crear lista",
                    buttonText = "+ Agregar lista"
                )

                // 🔹 CARD TIENDAS
                CardItem(
                    title = "Tiendas",
                    subtitle = "Agregar nueva tienda",
                    buttonText = "+ Agregar código",
                    onClick = {
                        // aquí va tu lógica de código
                    }
                )
            }
        }
    }
}

@Composable
fun CardItem(
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🔹 ICONO (placeholder)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }

            Button(onClick = onClick) {
                Text(buttonText)
            }
        }
    }
}