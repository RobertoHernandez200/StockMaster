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
import kotlinx.coroutines.launch
import com.example.stockmaster.ui.components.MenuDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(navController: NavController) {

    var listas by remember { mutableStateOf(listOf<String>()) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuDrawer(
                onInicio = {
                    scope.launch { drawerState.close() }
                    navController.navigate("home_cliente")
                },
                onTiendas = {
                    scope.launch { drawerState.close() }
                    navController.navigate("mis_tiendas")
                },
                onLista = {
                    scope.launch { drawerState.close() }
                    navController.navigate("wishlist")
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {

            // 🔥 HEADER CON MENÚ
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "☰",
                    fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        scope.launch { drawerState.open() }
                    }
                )

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
                    Text("Aún no tienes listas de deseos...", color = Color.Gray)
                }
            } else {
                LazyColumn {
                    items(listas) { lista ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Text(
                                lista,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}