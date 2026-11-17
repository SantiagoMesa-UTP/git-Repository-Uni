package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.EstadoStock
import com.example.inventarioindustrial.ui.theme.Verde

// Color de cada estado: verde = disponible, ámbar = bajo, rojo = agotado
@Composable
fun colorEstadoStock(estado: EstadoStock): Color {
    return when (estado) {
        EstadoStock.DISPONIBLE -> Verde
        EstadoStock.BAJO -> MaterialTheme.colorScheme.tertiary
        EstadoStock.AGOTADO -> MaterialTheme.colorScheme.error
    }
}

fun textoEstadoStock(estado: EstadoStock): String {
    return when (estado) {
        EstadoStock.DISPONIBLE -> "Disponible"
        EstadoStock.BAJO -> "Stock bajo"
        EstadoStock.AGOTADO -> "Agotado"
    }
}

// Etiqueta redondeada con el estado del stock
@Composable
fun EtiquetaEstadoStock(estado: EstadoStock, modifier: Modifier = Modifier) {
    val color = colorEstadoStock(estado)

    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.14f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = textoEstadoStock(estado),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}