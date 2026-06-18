package com.example.lenaycarbon.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lenaycarbon.ui.detail.components.DetailCantidad
import com.example.lenaycarbon.ui.detail.components.DetailHeader
import com.example.lenaycarbon.ui.navigation.Routes
import com.example.lenaycarbon.viewmodel.CarritoViewModel
import com.example.lenaycarbon.viewmodel.DetailViewModel

@Composable
fun DetailScreen(productoId: Int?, nav: NavController, carritoViewModel: CarritoViewModel) {
    val viewModel: DetailViewModel = viewModel()
    val productos by viewModel.productos.collectAsStateWithLifecycle()
    val categorias by viewModel.categorias.collectAsStateWithLifecycle()

    val producto = productos.find { it.id == productoId }
    val categoria = categorias.find { it.id == producto?.idCategoria }
    var cantidad by remember { mutableIntStateOf(1) }
    if (productos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    if (producto == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado")
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailHeader(producto = producto)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = categoria?.nombre ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            DetailCantidad(
                precio = producto.precio,
                cantidad = cantidad,
                onIncrementar = { cantidad++ },
                onDecrementar = { if (cantidad > 1) cantidad-- })
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    carritoViewModel.agregarProducto(producto, cantidad)
                    nav.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = producto.disponible,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (producto.disponible) "Agregar al carrito" else "No disponible",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}