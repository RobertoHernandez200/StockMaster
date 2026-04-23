package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.entry.EntryTiendaScreen
import com.example.stockmaster.ui.screens.auth.login.LoginEmailScreen
import com.example.stockmaster.ui.screens.auth.login.LoginPasswordScreen
import com.example.stockmaster.ui.screens.auth.register.RegisterScreen
import com.example.stockmaster.ui.screens.home_cliente.HomeClienteScreen
import com.example.stockmaster.ui.screens.home_tienda.HomeTiendaScreen
import com.example.stockmaster.ui.screens.products.ProductScreen
import com.google.firebase.auth.FirebaseAuth

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
                    navController.navigate("entry/cliente")
                },
                onTiendaClick = {
                    navController.navigate("entry/tienda")
                }
            )
        }

        // 🔹 ENTRADA DE TIENDA
        composable("entry/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            EntryTiendaScreen(
                onLoginClick = {
                    navController.navigate("login_email/$role")
                },
                onRegisterClick = {
                    navController.navigate("register/$role")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 🔹 LOGIN CORREO
        composable("login_email/{role}") { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginEmailScreen(
                onNext = { email ->
                    navController.navigate("login_password/$email/$role")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // 🔹 LOGIN CONTRASEÑA
        composable("login_password/{email}/{role}") { backStackEntry ->

            val email = backStackEntry.arguments?.getString("email") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginPasswordScreen(
                email = email,
                onLogin = { password, callback ->

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {

                            callback(true, null)

                            if (role == "cliente") {
                                navController.navigate("home_cliente") {
                                    popUpTo(0)
                                }
                            } else {
                                navController.navigate("home_tienda") {
                                    popUpTo(0)
                                }
                            }
                        }
                        .addOnFailureListener {

                            callback(false, "Correo o contraseña incorrectos")
                        }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 🔹 REGISTER
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
                },
                onLogout = {
                    navController.navigate("role_selection") {
                        popUpTo(0) // 🔥 limpia toda la navegación
                    }
                }
            )
        }

        // 🔹 PRODUCTOS
        composable("productos") {
            ProductScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}