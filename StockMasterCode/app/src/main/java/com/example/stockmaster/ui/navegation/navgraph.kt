package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.auth.login.LoginScreen

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

        // 🔥 Login con role
        composable("login/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginScreen(
                role = role,
                onLoginSuccess = {
                    if (role == "cliente") {
                        // navController.navigate("home_cliente")
                    } else {
                        // navController.navigate("home_tienda")
                    }
                }
            )
        }
    }
}