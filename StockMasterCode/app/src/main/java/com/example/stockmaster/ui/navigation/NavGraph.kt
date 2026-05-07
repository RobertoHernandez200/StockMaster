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
import com.example.stockmaster.ui.screens.cliente.TiendasClienteScreen
import com.example.stockmaster.ui.screens.cliente.ClienteProductosScreen

// 🔥 FINANZAS
import com.example.stockmaster.ui.screens.finanzas.ClientesStatsScreen
import com.example.stockmaster.ui.screens.finanzas.FinanzasScreen
import com.example.stockmaster.ui.screens.finanzas.InventarioStatsScreen
import com.example.stockmaster.ui.screens.finanzas.TendenciasScreen
import com.example.stockmaster.ui.screens.finanzas.ReabastecerStockScreen
import com.example.stockmaster.ui.screens.finanzas.informes.CrearInformeScreen
import com.example.stockmaster.ui.screens.finanzas.informes.InformesListScreen

// LISTAS
import com.example.stockmaster.ui.screens.lista_deseos.*
import com.example.stockmaster.viewmodel.EmpleadoViewModel
import com.example.stockmaster.viewmodel.ClienteViewModel
import com.example.stockmaster.viewmodel.InformeViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

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
        composable("entry/{role}") { backStackEntry ->

            val role =
                backStackEntry.arguments?.getString("role")
                    ?: "cliente"

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

        // LOGIN EMAIL
        composable("login_email/{role}") { backStackEntry ->

            val role =
                backStackEntry.arguments?.getString("role")
                    ?: "cliente"

            LoginEmailScreen(

                onNext = { email ->

                    navController.navigate(
                        "login_password/$email/$role"
                    )
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // LOGIN PASSWORD
        composable("login_password/{email}/{role}") { backStackEntry ->

            val email =
                backStackEntry.arguments?.getString("email")
                    ?: ""

            val role =
                backStackEntry.arguments?.getString("role")
                    ?: "cliente"

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

                            callback(
                                false,
                                "Correo o contraseña incorrectos"
                            )
                        }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // REGISTER
        composable("register/{role}") { backStackEntry ->

            val role =
                backStackEntry.arguments?.getString("role")
                    ?: "cliente"

            RegisterScreen(
                role = role,
                onRegisterSuccess = {

                    if (role == "cliente") {

                        navController.navigate("home_cliente") {

                            popUpTo("register/{role}") {
                                inclusive = true
                            }
                        }

                    } else {

                        navController.navigate("home_tienda") {

                            popUpTo("register/{role}") {
                                inclusive = true
                            }
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // HOME CLIENTE
        composable("home_cliente") {
            HomeClienteScreen(navController)
        }

        // TIENDAS CLIENTE
        composable("mis_tiendas") {

            val viewModel: ClienteViewModel = viewModel()

            val tiendas by viewModel.tiendas.collectAsState()

            TiendasClienteScreen(
                tiendas = tiendas,
                onDelete = { viewModel.eliminarTienda(it.id) },
                viewModel = viewModel,
                navController = navController
            )
        }

        // PRODUCTOS TIENDA
        composable("productos_tienda/{tiendaId}") { backStack ->

            val tiendaId =
                backStack.arguments?.getString("tiendaId")
                    ?: ""

            ClienteProductosScreen(
                tiendaId = tiendaId,
                onBack = {
                    navController.popBackStack()
                }
            )
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

                onProveedores = {
                    navController.navigate("proveedores")
                },

                onFinanzas = {
                    navController.navigate("finanzas")
                },

                onLogout = {
                    navController.navigate("role_selection") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("proveedores") {
            ProveedoresScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // FINANZAS
        composable("finanzas") {

            FinanzasScreen(
                navController = navController,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("informes") {

            val viewModel: InformeViewModel = viewModel()

            InformesListScreen(
                viewModel = viewModel,
                onCrearInforme = {
                    navController.navigate("crear_informe")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("crear_informe") {

            val parentEntry = remember {
                navController.getBackStackEntry("informes")
            }

            val viewModel: InformeViewModel = viewModel(parentEntry)

            CrearInformeScreen(
                viewModel = viewModel,
                onSuccess = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("inventarioStats"){
            InventarioStatsScreen(navController)
        }

        // CLIENTES
        composable("clienteStats") {
            ClientesStatsScreen(navController)
        }

        // TENDENCIAS
        composable("tendencias") {
            TendenciasScreen(navController)
        }

        // 🔥 NUEVA PANTALLA REABASTECER
        composable("reabastecer/{producto}") { backStack ->

            val producto =
                backStack.arguments?.getString("producto")
                    ?: ""

            ReabastecerStockScreen(
                navController = navController,
                productoNombre = producto
            )
        }

        // PRODUCTOS
        composable("productos") {
            ProductScreen {
                navController.popBackStack()
            }
        }

        // USUARIOS
        composable("usuarios") {

            UsuariosScreen(
                navController = navController,

                onAddUser = {
                    navController.navigate("crear_usuario")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("detalle_usuario/{id}") { backStack ->

            val id =
                backStack.arguments?.getString("id")!!

            EmpleadoDetalleScreen(
                usuarioId = id,
                navController = navController
            )
        }

        composable("crear_usuario") {

            CrearUsuarioScreen(

                onNext = { nombre, email ->

                    navController.navigate(
                        "permisos/$nombre/$email"
                    )
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("permisos/{nombre}/{email}") { backStack ->

            val nombre =
                backStack.arguments?.getString("nombre")!!

            val email =
                backStack.arguments?.getString("email")!!

            PermisosScreen(

                nombre,
                email,

                onNext = { permisos ->

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("permisos", permisos)

                    navController.navigate(
                        "password/$nombre/$email"
                    )
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("password/{nombre}/{email}") { backStack ->

            val nombre =
                backStack.arguments?.getString("nombre")!!

            val email =
                backStack.arguments?.getString("email")!!

            val permisos =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Map<String, Boolean>>("permisos")
                    ?: emptyMap()

            val viewModel: EmpleadoViewModel = viewModel()

            PasswordEmpleadoScreen(

                onCreate = { password ->

                    viewModel.crearEmpleado(
                        nombre,
                        email,
                        password,
                        permisos,

                        onSuccess = {
                            navController.navigate("success")
                        },

                        onError = {}
                    )
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("loading") {
            LoadingScreen()
        }

        composable("success") {

            SuccessScreen {

                navController.navigate("usuarios") {

                    popUpTo("usuarios") {
                        inclusive = false
                    }
                }
            }
        }

        // WISHLIST
        composable("wishlist") {
            WishlistScreen(navController)
        }

        composable("crear_lista") {
            CrearListaScreen(navController)
        }

        composable("seleccionar_tienda/{nombreLista}") { backStack ->

            val nombreLista =
                backStack.arguments?.getString("nombreLista")
                    ?: ""

            SeleccionarTiendaScreen(
                navController = navController,
                nombreLista = nombreLista
            )
        }

        composable("seleccionar_productos/{tiendaId}/{nombreLista}") { backStack ->

            val tiendaId =
                backStack.arguments?.getString("tiendaId")
                    ?: ""

            val nombreLista =
                backStack.arguments?.getString("nombreLista")
                    ?: ""

            SeleccionarProductosScreen(
                navController = navController,
                tiendaId = tiendaId,
                nombreLista = nombreLista
            )
        }

        composable("detalle_lista/{listaId}/{nombre}/{tiendaId}") { backStack ->

            val listaId =
                backStack.arguments?.getString("listaId")
                    ?: ""

            val nombre =
                backStack.arguments?.getString("nombre")
                    ?: ""

            val tiendaId =
                backStack.arguments?.getString("tiendaId")
                    ?: ""

            DetalleListaScreen(
                navController = navController,
                listaId = listaId,
                nombreLista = nombre,
                tiendaId = tiendaId
            )
        }
    }
}