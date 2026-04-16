package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

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

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen {
                navController.navigate("role_selection")
            }
        }

        composable("role_selection") {
            RoleSelectionScreen(
                onClienteClick = { navController.navigate("login/cliente") },
                onTiendaClick = { navController.navigate("login/tienda") }
            )
        }

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
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("register/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

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