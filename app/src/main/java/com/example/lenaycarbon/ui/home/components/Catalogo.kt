package com.example.lenaycarbon.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.dto.CategoriaProducto
import com.example.lenaycarbon.data.dto.Producto
import com.example.lenaycarbon.data.mockup.listaCategorias

@Composable
fun Catalogo(catalogo: List<Producto>, nav: NavController) {
    var query by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<CategoriaProducto?>(null) }

    val productosFiltrados = catalogo.filter { producto ->
        val coincideBusqueda = producto.nombre.contains(query, ignoreCase = true)
        val coincideCategoria =
            categoriaSeleccionada == null || producto.idCategoria == categoriaSeleccionada!!.id
        coincideBusqueda && coincideCategoria
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            placeholder = { Text("Buscar producto...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            item {
                FilterChip(
                    selected = categoriaSeleccionada == null,
                    onClick = { categoriaSeleccionada = null },
                    label = { Text("Todos") })
            }
            items(listaCategorias) { categoria ->
                FilterChip(selected = categoriaSeleccionada?.id == categoria.id, onClick = {
                    categoriaSeleccionada =
                        if (categoriaSeleccionada == categoria) null else categoria
                }, label = {
                    Text( categoria.nombre)
                })
            }
        }

        if (productosFiltrados.isEmpty()) {
            Text(
                text = "No se encontraron productos",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 24.dp)
            )
        } else {
            LazyColumn {
                items(productosFiltrados) { producto ->
                    ProductoCard(
                        producto = producto, onClick = { nav.navigate("detail/${producto.id}") })
                }
            }
        }
    }
}