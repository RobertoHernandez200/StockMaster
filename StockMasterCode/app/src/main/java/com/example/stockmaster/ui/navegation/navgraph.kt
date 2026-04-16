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
            splashscreen(
                onStartClick = {
                    navController.navigate("role_selection")
                }
            )
        }

        // 🔹 Selección de rol
        composable("role_selection") {
            roleselectionscreen(
                onClienteClick = {
                    navController.navigate("login")
                },
                onTiendaClick = {
                    navController.navigate("login")
                }
            )
        }

        // 🔥 LOGIN (nuevo)
        composable("login") {
            loginscreen(
                onLoginSuccess = {
                    // luego aquí puedes mandar a home
                    // navController.navigate("home")
                }
            )
        }
    }
}