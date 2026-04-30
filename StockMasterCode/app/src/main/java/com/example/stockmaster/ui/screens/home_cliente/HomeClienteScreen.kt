package com.example.stockmaster.ui.screens.home_cliente

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

    var showDialog by remember { mutableStateOf(false) }

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
            Text("Inicio", color = Color.Blue)
            Text("Salir", color = Color.Blue)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // 🔹 TOTAL
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total")
                Text("$0.00", fontSize = 24.sp)
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
                    .padding(20.dp)
            ) {

                Column {

                    // 🔹 LISTA DE DESEOS
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text("Lista de deseos")
                                Text("Crea tu lista de deseos", fontSize = 12.sp)
                            }

                            Button(onClick = { }) {
                                Text("+ Agregar lista")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 🔹 TIENDAS
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text("Tiendas")
                                Text("Agrega una nueva tienda", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    // 🔥 AQUÍ VA TU LÓGICA DEL CÓDIGO
                                    showDialog = true
                                }
                            ) {
                                Text("+ Agregar código")
                            }
                        }
                    }
                }
            }
        }
    }
}