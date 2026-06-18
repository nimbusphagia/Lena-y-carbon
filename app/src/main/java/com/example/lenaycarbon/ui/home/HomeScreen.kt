package com.example.lenaycarbon.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lenaycarbon.ui.home.components.Catalogo
import com.example.lenaycarbon.ui.navigation.Routes
import com.example.lenaycarbon.viewmodel.CarritoViewModel
import com.example.lenaycarbon.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    nav: NavController, homeViewModel: HomeViewModel, carritoViewModel: CarritoViewModel
) {

    val productos by homeViewModel.productos.collectAsStateWithLifecycle()
    val categoriaId by homeViewModel.categoriaSeleccionada.collectAsStateWithLifecycle()
    val busqueda by homeViewModel.busqueda.collectAsStateWithLifecycle()
    val categorias by homeViewModel.categorias.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { nav.navigate(Routes.seguimientoRoute(12345)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "PROBAR MI PANTALLA DE SEGUIMIENTO")
        }

        Catalogo(
            catalogo = productos,
            categorias = categorias,
            categoriaSeleccionadaId = categoriaId,
            busqueda = busqueda,
            onCategoriaChange = { id -> homeViewModel.seleccionarCategoria(id) },
            onSearchChange = { query -> homeViewModel.buscarPorNombre(query) },
            nav = nav
        )
    }
}