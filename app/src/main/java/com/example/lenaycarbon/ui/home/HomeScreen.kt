package com.example.lenaycarbon.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.mockup.listaProductos
import com.example.lenaycarbon.ui.home.components.Catalogo
import com.example.lenaycarbon.ui.navigation.Routes

@Composable
fun HomeScreen(nav: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {nav.navigate(Routes.seguimientoRoute(12345))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text="PROBAR MI PANTALLA DE SEGUIMIENTO")
        }
        Catalogo(listaProductos, nav)
    }
}
