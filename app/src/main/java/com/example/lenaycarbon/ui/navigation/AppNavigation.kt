package com.example.lenaycarbon.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.lenaycarbon.ui.detail.DetailScreen
import com.example.lenaycarbon.ui.home.HomeScreen
import com.example.lenaycarbon.ui.login.LoginScreen
import com.example.lenaycarbon.ui.login.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

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
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }
            composable(Routes.LOGIN) { LoginScreen(onLoginSuccess = {navController.navigate(Routes.HOME)}) }
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
        }
    }
}
