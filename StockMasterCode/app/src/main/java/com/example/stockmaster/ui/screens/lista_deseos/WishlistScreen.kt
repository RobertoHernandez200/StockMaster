package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ClienteViewModel

@Composable
fun WishlistScreen(navController: NavController) {

    val viewModel: ClienteViewModel = viewModel()
    val listas by viewModel.listas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {

        // 🔥 HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Listas", fontSize = 18.sp)

            Text(
                "+",
                fontSize = 22.sp,
                modifier = Modifier.clickable {
                    navController.navigate("crear_lista")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (listas.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no tienes listas...", color = Color.Gray)
            }

        } else {

            LazyColumn {

                items(listas) { lista ->

                    val nombre = lista["nombre"] as? String ?: ""
                    val id = lista["id"] as? String ?: ""

                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("detalle_lista/$id")
                            }
                    ) {

                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(nombre)

                            Box {

                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "")
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {

                                    DropdownMenuItem(
                                        text = { Text("Eliminar") },
                                        onClick = {
                                            expanded = false
                                            viewModel.eliminarLista(id)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}