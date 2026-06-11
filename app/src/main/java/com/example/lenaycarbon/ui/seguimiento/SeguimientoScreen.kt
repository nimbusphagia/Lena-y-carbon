package com.example.lenaycarbon.ui.seguimiento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.dto.EstadoPedido

@Composable
fun SeguimientoScreen(pedidoId: Int?, navController: NavController) {
    // Datos de prueba simulando el DTO Pedido de tu grupo
    val codigoPedido = pedidoId ?: 123
    val estadoActual = EstadoPedido.EN_PREPARACION // Estado del enum de tu grupo
    val montoTotal = "S/ 72.90"

    // Mapeamos los enums reales de tu grupo a textos amigables en español
    val listaEstados = listOf(
        EstadoPedido.REGISTRADO to "Registrado",
        EstadoPedido.CONFIRMADO to "Confirmado",
        EstadoPedido.EN_PREPARACION to "En preparación",
        EstadoPedido.EN_REPARTO to "En reparto",
        EstadoPedido.ENTREGADO to "Entregado"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Código de Orden: #$codigoPedido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Traducimos el estado actual del enum para mostrarlo arriba
                val textoEstadoActual = listaEstados.find { it.first == estadoActual }?.second ?: "Desconocido"
                Text(
                    text = "Estado: $textoEstadoActual",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Línea de tiempo del pedido:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dibujamos el flujo basado en el enum de tu grupo
                listaEstados.forEachIndexed { index, parEstado ->
                    val enumEstado = parEstado.first
                    val textoEstado = parEstado.second

                    val esEstadoActual = (enumEstado == estadoActual)
                    val yaPaso = index <= listaEstados.indexOf(listaEstados.find { it.first == estadoActual })

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .background(
                                    color = when {
                                        esEstadoActual -> MaterialTheme.colorScheme.primary
                                        yaPaso -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                        else -> MaterialTheme.colorScheme.outline
                                    },
                                    shape = CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = textoEstado,
                            fontSize = 16.sp,
                            fontWeight = if (esEstadoActual) FontWeight.Bold else FontWeight.Normal,
                            color = if (esEstadoActual) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Pagado:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = montoTotal, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(text = "Volver al Inicio", fontWeight = FontWeight.Bold)
        }
    }
}