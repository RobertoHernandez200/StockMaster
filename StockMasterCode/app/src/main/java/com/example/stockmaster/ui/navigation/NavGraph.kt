package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.stockmaster.ui.screens.home_cliente.HomeClienteScreen
import com.example.stockmaster.ui.screens.home_tienda.HomeTiendaScreen
import com.example.stockmaster.ui.screens.products.ProductScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_tienda") {

        composable("home_cliente") {
            HomeClienteScreen()
        }

        composable("home_tienda") {
            HomeTiendaScreen(
                onAddProduct = {
                    navController.navigate("productos")
                }
            )
        }

        composable("productos") {
            ProductScreen()
        }
    }
}