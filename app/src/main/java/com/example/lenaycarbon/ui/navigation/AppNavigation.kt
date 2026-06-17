package com.example.lenaycarbon.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.*
import com.example.lenaycarbon.ui.confirmacion.ConfirmacionScreen
import com.example.lenaycarbon.ui.detail.DetailScreen
import com.example.lenaycarbon.ui.home.HomeScreen
import com.example.lenaycarbon.ui.login.LoginScreen
import com.example.lenaycarbon.ui.login.SplashScreen
import com.example.lenaycarbon.viewmodel.AuthViewModel
import com.example.lenaycarbon.ui.login.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    Scaffold(
        topBar = {
            when {
                currentRoute == Routes.HOME -> {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Leña y Carbón",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "¿Qué vas a pedir hoy?",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                authViewModel.logout(showMessage = true) {
                                    navController.navigate(Routes.LOGIN) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Cerrar sesión"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
                currentRoute?.startsWith("detail") == true -> {
                    TopAppBar(
                        title = { Text("Detalle del producto") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }

                currentRoute?.startsWith("deliveryType") == true -> {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Leña y Carbón",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "¿Cómo te llevarás tu pedido?",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }

                // ---> (FABRIZIO) Aqui el integrado de "estado del pedido})
                currentRoute?.startsWith("seguimiento") == true -> {
                    TopAppBar(
                        title = { Text("Estado de tu Pedido") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
                // ------------------- Hasta aca -------------------
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SPLASH) {
                SplashScreen(onSplashFinished = {
                    authViewModel.logout(showMessage = false) {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                })
            }
            composable(Routes.LOGIN) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(Routes.REGISTER)
                    }
                )
            }
            composable(Routes.REGISTER) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = {
                        authViewModel.logout(showMessage = false) {
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.REGISTER) { inclusive = true }
                            }
                        }
                    },
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Routes.HOME) { HomeScreen(navController) }

            composable(Routes.DETAIL) { backStackEntry ->
                val productoId = backStackEntry.arguments
                    ?.getString("productoId")
                    ?.toIntOrNull()
                DetailScreen(
                    productoId = productoId,
                    nav = navController
                )
            }

            composable(Routes.CONFIRMACION){ backStackEntry ->
                val pedidoId = backStackEntry.arguments
                    ?.getString("pedidoSubtotal")
                    ?.toIntOrNull()
                ConfirmacionScreen(
                    pedidoId = pedidoId,
                    nav = navController,
                )
            }

            // (FABRIZIO) > Aqui registro la pantalla de seguimiento
            composable(Routes.SEGUIMIENTO) { backStackEntry ->
                val pedidoId = backStackEntry.arguments
                    ?.getString("pedidoId")
                    ?.toIntOrNull()

                // (FABRIZIO) Llamo al archivo SeguimientoScreen
                com.example.lenaycarbon.ui.seguimiento.SeguimientoScreen(
                    pedidoId = pedidoId,
                    navController = navController
                )
            }
            // ---> -------------------------  <---
        }
    }
}