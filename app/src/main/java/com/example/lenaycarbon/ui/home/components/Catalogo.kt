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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.local.entity.CategoriaProducto
import com.example.lenaycarbon.data.local.entity.Producto

@Composable
fun Catalogo(
    catalogo: List<Producto>,
    categorias: List<CategoriaProducto>,
    categoriaSeleccionadaId: Int? = null,
    busqueda: String = "",
    onCategoriaChange: (Int?) -> Unit,
    onSearchChange: (String) -> Unit,
    nav: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = busqueda,
            onValueChange = onSearchChange,
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
                    selected = categoriaSeleccionadaId == null,
                    onClick = { onCategoriaChange(null) },
                    label = { Text("Todos") })
            }
            items(categorias) { categoria ->
                FilterChip(selected = categoriaSeleccionadaId == categoria.id, onClick = {
                    val nuevaId =
                        if (categoriaSeleccionadaId == categoria.id) null else categoria.id
                    onCategoriaChange(nuevaId)
                }, label = { Text(categoria.nombre) })
            }
        }
        if (catalogo.isEmpty()) {
            Text(
                text = "No se encontraron productos",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 24.dp)
            )
        } else {
            LazyColumn {
                items(catalogo) { producto ->
                    ProductoCard(
                        producto = producto,
                        categoria = categorias.find { it.id == producto.idCategoria },
                        onClick = { nav.navigate("detail/${producto.id}") })
                }
            }
        }
    }
}