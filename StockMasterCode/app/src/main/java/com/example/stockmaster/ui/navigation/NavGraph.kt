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

        // 🔹 SPLASH
        composable("splash") {
            SplashScreen(
                onStartClick = {
                    navController.navigate("role_selection")
                }
            )
        }

        // 🔹 SELECCIÓN DE ROL
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

        // 🔹 LOGIN
        composable("login/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginScreen(
                role = role,
                onLoginSuccess = {
                    if (role == "cliente") {
                        navController.navigate("home_cliente") {
                            popUpTo("login/{role}") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home_tienda") {
                            popUpTo("login/{role}") { inclusive = true }
                        }
                    }
                },
                onGoToRegister = {
                    navController.navigate("register/$role")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 🔹 REGISTER (🔥 AQUÍ NAVEGA AL HOME)
        composable("register/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {
                    if (role == "cliente") {
                        navController.navigate("home_cliente") {
                            popUpTo("register/{role}") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home_tienda") {
                            popUpTo("register/{role}") { inclusive = true }
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 🔹 HOME CLIENTE
        composable("home_cliente") {
            HomeClienteScreen()
        }

        // 🔹 HOME TIENDA
        composable("home_tienda") {
            HomeTiendaScreen(
                onAddProduct = {
                    navController.navigate("productos")
                }
            )
        }

        // 🔹 PRODUCTOS
        composable("productos") {
            ProductScreen()
        }
    }
}