package com.example.lenaycarbon.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.mockup.listaProductos
import com.example.lenaycarbon.ui.home.components.Catalogo

@Composable
fun HomeScreen(nav: NavController){
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Productos",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "${listaProductos.size} productos disponibles",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(16.dp))
    Catalogo(listaProductos, nav)
}