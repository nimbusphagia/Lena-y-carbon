package com.example.lenaycarbon.ui.carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lenaycarbon.ui.navigation.Routes
import com.example.lenaycarbon.viewmodel.CarritoViewModel

@Composable
fun CarritoScreen(nav: NavController, carritoViewModel: CarritoViewModel) {
    val items by carritoViewModel.items.collectAsStateWithLifecycle()
    val total = items.sumOf { it.subtotal }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi carrito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "Cantidad: ${item.cantidad}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            "S/ ${"%.2f".format(item.subtotal)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HorizontalDivider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Total: S/ ${"%.2f".format(total)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { nav.navigate(Routes.CONFIRMACION) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Confirmar compra")
            }
        }
    }
}