package com.example.lenaycarbon.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.lenaycarbon.data.mockup.listaProductos
import com.example.lenaycarbon.ui.home.components.Catalogo

@Composable
fun HomeScreen(nav: NavController) {
    Catalogo(listaProductos, nav)
}
