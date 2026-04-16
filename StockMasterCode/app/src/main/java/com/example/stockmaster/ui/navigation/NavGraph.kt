package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.auth.login.LoginScreen
import com.example.stockmaster.ui.screens.auth.register.RegisterScreen
import com.example.stockmaster.ui.screens.home_cliente.HomeClienteScreen
import com.example.stockmaster.ui.screens.home_tienda.HomeTiendaScreen
import com.example.stockmaster.ui.screens.products.ProductScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // 🔹 Splash
        composable("splash") {
            SplashScreen(
                onStartClick = {
                    navController.navigate("role_selection")
                }
            )
        }

        // 🔹 Selección de rol
        composable("role_selection") {
            RoleSelectionScreen(
                onClienteClick = {
                    navController.navigate("login/cliente")
                },
                onTiendaClick = {
                    navController.navigate("login/tienda")
                }
            )
        }

        // 🔹 Login
        composable("login/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginScreen(
                role = role,
                onLoginSuccess = {
                    if (role == "cliente") {
                        navController.navigate("home_cliente")
                    } else {
                        navController.navigate("home_tienda")
                    }
                },
                onGoToRegister = {
                    navController.navigate("register/$role")
                }
            )
        }

        // 🔹 Register
        composable("register/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {
                    if (role == "cliente") {
                        navController.navigate("home_cliente")
                    } else {
                        navController.navigate("home_tienda")
                    }
                }
            )
        }

        // 🔹 Home Cliente
        composable("home_cliente") {
            HomeClienteScreen()
        }

        // 🔹 Home Tienda (con botón funcional)
        composable("home_tienda") {
            HomeTiendaScreen(
                onAddProduct = {
                    navController.navigate("productos")
                }
            )
        }

        // 🔹 Pantalla Productos
        composable("productos") {
            ProductScreen()
        }
    }
}