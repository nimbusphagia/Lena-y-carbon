package com.example.lenaycarbon.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
    val pedidoActivo by homeViewModel.pedidoActivo.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxWidth()) {

        //BOTON CONDICIONAL
        if (pedidoActivo != null) {
            Button(
                onClick = {
                    nav.navigate(Routes.seguimientoRoute(pedidoActivo!!.id, pedidoActivo!!.total))
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Ver estado de mi pedido activo (#${pedidoActivo!!.codigo})")
            }
        }

        /** BORRO ESTE BOTON PORQUE NO DEBERIA DE APARECER SEGUIMIENTO DE PEDIDO HASTA QUE SE HAYA CONFIRMADO
         * Button(
            onClick = { nav.navigate(Routes.seguimientoRoute(12345)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "PROBAR MI PANTALLA DE SEGUIMIENTO")
        }**/

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