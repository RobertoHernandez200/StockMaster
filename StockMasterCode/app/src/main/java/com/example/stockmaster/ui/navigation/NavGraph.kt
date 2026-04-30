package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth

// SCREENS
import com.example.stockmaster.ui.screens.empleados.*
import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.entry.EntryTiendaScreen
import com.example.stockmaster.ui.screens.auth.login.*
import com.example.stockmaster.ui.screens.auth.register.RegisterScreen
import com.example.stockmaster.ui.screens.home_cliente.HomeClienteScreen
import com.example.stockmaster.ui.screens.home_tienda.HomeTiendaScreen
import com.example.stockmaster.ui.screens.proveedores.ProveedoresScreen
import com.example.stockmaster.ui.screens.products.ProductScreen
import com.example.stockmaster.ui.screens.cliente.*
import com.example.stockmaster.ui.screens.lista_deseos.*

import com.example.stockmaster.viewmodel.*

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        // SPLASH
        composable("splash") {
            SplashScreen {
                navController.navigate("role_selection") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        // ROLE
        composable("role_selection") {
            RoleSelectionScreen(
                onClienteClick = { navController.navigate("entry/cliente") },
                onTiendaClick = { navController.navigate("entry/tienda") }
            )
        }

        // ENTRY
        composable("entry/{role}") { backStack ->
            val role = backStack.arguments?.getString("role") ?: "cliente"

            EntryTiendaScreen(
                onLoginClick = { navController.navigate("login_email/$role") },
                onRegisterClick = { navController.navigate("register/$role") },
                onBack = { navController.popBackStack() }
            )
        }

        // LOGIN EMAIL
        composable("login_email/{role}") { backStack ->
            val role = backStack.arguments?.getString("role") ?: "cliente"

            LoginEmailScreen(
                onNext = { email ->
                    navController.navigate("login_password/$email/$role")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // LOGIN PASSWORD
        composable("login_password/{email}/{role}") { backStack ->

            val email = backStack.arguments?.getString("email") ?: ""
            val role = backStack.arguments?.getString("role") ?: "cliente"

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
                            callback(false, "Error login")
                        }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // REGISTER
        composable("register/{role}") { backStack ->
            val role = backStack.arguments?.getString("role") ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {
                    if (role == "cliente") {
                        navController.navigate("home_cliente") {
                            popUpTo(0)
                        }
                    } else {
                        navController.navigate("home_tienda") {
                            popUpTo(0)
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // HOME CLIENTE
        composable("home_cliente") {
            HomeClienteScreen(navController)
        }

        // 🔥 TIENDAS CLIENTE (ARREGLADO)
        composable("mis_tiendas") {

            val viewModel: ClienteViewModel = viewModel()
            val tiendas by viewModel.tiendas.collectAsState()

            TiendasClienteScreen(
                tiendas = tiendas,
                onDelete = { tienda ->
                    viewModel.eliminarTienda(tienda.id) // ✅ CORREGIDO
                },
                viewModel = viewModel,
                navController = navController
            )
        }

        // HOME TIENDA
        composable("home_tienda") {
            HomeTiendaScreen(
                onAddProduct = { navController.navigate("productos") },
                onUsuarios = { navController.navigate("usuarios") },
                onProveedores = { navController.navigate("proveedores") },
                onLogout = { navController.navigate("role_selection") }
            )
        }

        // PRODUCTOS
        composable("productos") {
            ProductScreen {
                navController.popBackStack()
            }
        }

        // PROVEEDORES
        composable("proveedores") {
            ProveedoresScreen {
                navController.popBackStack()
            }
        }

        // 🔥 LISTAS
        composable("wishlist") {
            WishlistScreen(navController)
        }

        composable("crear_lista") {
            CrearListaScreen(navController)
        }

        composable("seleccionar_tienda/{nombreLista}") { backStack ->
            val nombre = backStack.arguments?.getString("nombreLista") ?: ""

            SeleccionarTiendaScreen(
                navController = navController,
                nombreLista = nombre
            )
        }

        composable("seleccionar_productos/{tiendaId}/{nombreLista}") { backStack ->

            val tiendaId = backStack.arguments?.getString("tiendaId") ?: ""
            val nombreLista = backStack.arguments?.getString("nombreLista") ?: ""

            SeleccionarProductosScreen(
                navController = navController,
                tiendaId = tiendaId,
                nombreLista = nombreLista
            )
        }
    }
}