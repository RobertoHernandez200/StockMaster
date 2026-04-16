package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.auth.login.LoginScreen
import com.example.stockmaster.ui.screens.auth.register.RegisterScreen

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

        // 🔥 LOGIN con role
        composable("login/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginScreen(
                role = role,
                onLoginSuccess = {
                    // luego puedes redirigir según rol
                    // if (role == "cliente") {
                    //     navController.navigate("home_cliente")
                    // } else {
                    //     navController.navigate("home_tienda")
                    // }
                },
                onGoToRegister = {
                    navController.navigate("register/$role")
                }
            )
        }

        // 🔥 REGISTER con role
        composable("register/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {
                    navController.navigate("login/$role")
                }
            )
        }
    }
}