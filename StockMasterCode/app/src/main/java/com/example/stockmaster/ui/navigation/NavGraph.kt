package com.example.stockmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmaster.ui.screens.empleados.UsuariosScreen
import com.example.stockmaster.ui.screens.empleados.EmpleadoDetalleScreen
import com.example.stockmaster.ui.screens.splash.SplashScreen
import com.example.stockmaster.ui.screens.role_selection.RoleSelectionScreen
import com.example.stockmaster.ui.screens.entry.EntryTiendaScreen
import com.example.stockmaster.ui.screens.auth.login.LoginEmailScreen
import com.example.stockmaster.ui.screens.auth.login.LoginPasswordScreen
import com.example.stockmaster.ui.screens.auth.register.RegisterScreen
import com.example.stockmaster.ui.screens.home_cliente.HomeClienteScreen
import com.example.stockmaster.ui.screens.home_tienda.HomeTiendaScreen
import com.example.stockmaster.ui.screens.products.ProductScreen
import com.example.stockmaster.ui.screens.empleados.CrearUsuarioScreen
import com.example.stockmaster.ui.screens.empleados.PermisosScreen
import com.example.stockmaster.ui.screens.empleados.PasswordEmpleadoScreen
import com.example.stockmaster.ui.screens.empleados.LoadingScreen
import com.example.stockmaster.ui.screens.empleados.SuccessScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.EmpleadoViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen {
                navController.navigate("role_selection")
            }
        }

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

        composable("entry/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            EntryTiendaScreen(
                onLoginClick = {
                    navController.navigate("login_email/$role")
                },
                onRegisterClick = {
                    navController.navigate("register/$role")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("login_email/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "cliente"

            LoginEmailScreen(
                onNext = { email ->
                    navController.navigate("login_password/$email/$role")
                },
                onBack = { navController.popBackStack() }
            )
        }

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
                onBack = { navController.popBackStack() }
            )
        }

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
                onBack = { navController.popBackStack() }
            )
        }

        composable("home_cliente") {
            HomeClienteScreen()
        }

        // HOME TIENDA
        composable("home_tienda") {
            HomeTiendaScreen(
                onAddProduct = {
                    navController.navigate("productos")
                },
                onUsuarios = {
                    navController.navigate("usuarios")
                },
                onLogout = {
                    navController.navigate("role_selection") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("productos") {
            ProductScreen {
                navController.popBackStack()
            }
        }

        // LISTA DE USUARIOS
        composable("usuarios") {
            UsuariosScreen(
                navController = navController,
                onAddUser = {
                    navController.navigate("crear_usuario")
                },
                onBack = {
                    navController.navigate("home_tienda") {
                        popUpTo("home_tienda") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // DETALLE USUARIO (EDITAR / ELIMINAR)
        composable("detalle_usuario/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!

            EmpleadoDetalleScreen(
                usuarioId = id,
                navController = navController
            )
        }

        composable("crear_usuario") {
            CrearUsuarioScreen(
                onNext = { nombre, email ->
                    navController.navigate("permisos/$nombre/$email")
                },
                onBack = {
                    navController.navigate("usuarios") {
                        popUpTo("usuarios") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("permisos/{nombre}/{email}") { backStack ->
            val nombre = backStack.arguments?.getString("nombre")!!
            val email = backStack.arguments?.getString("email")!!

            PermisosScreen(
                nombre,
                email,
                onNext = { permisos ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("permisos", permisos)

                    navController.navigate("password/$nombre/$email")
                },
                onBack = {
                    navController.popBackStack("crear_usuario", false)
                }
            )
        }

        composable("password/{nombre}/{email}") { backStack ->

            val nombre = backStack.arguments?.getString("nombre")!!
            val email = backStack.arguments?.getString("email")!!

            val permisos = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Map<String, Boolean>>("permisos") ?: emptyMap()

            val viewModel: EmpleadoViewModel = viewModel()

            PasswordEmpleadoScreen(
                onCreate = { password ->

                    navController.navigate("loading")

                    viewModel.crearEmpleado(
                        nombre,
                        email,
                        password,
                        permisos,
                        onSuccess = {
                            navController.navigate("success") {
                                popUpTo("usuarios") { inclusive = false }
                            }
                        },
                        onError = {
                            navController.popBackStack()
                        }
                    )
                },
                onBack = {
                    navController.popBackStack("permisos/$nombre/$email", false)
                }
            )
        }

        composable("loading") { LoadingScreen() }

        composable("success") {
            SuccessScreen {
                navController.navigate("usuarios")
            }
        }
    }
}