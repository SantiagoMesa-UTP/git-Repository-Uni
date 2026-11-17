package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Movimiento
import com.example.inventarioindustrial.model.TipoMovimiento
import com.example.inventarioindustrial.ui.theme.Verde

// Una línea del historial: Entrada (verde, +) o Salida (rojo, -)
@Composable
fun FilaMovimiento(movimiento: Movimiento, unidad: String) {
    val esEntrada = movimiento.tipo == TipoMovimiento.ENTRADA

    val color = if (esEntrada) Verde else MaterialTheme.colorScheme.error
    val titulo = if (esEntrada) "Entrada" else "Salida"
    val signo = if (esEntrada) "+" else "-"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
            Text(
                text = formatearFecha(movimiento.fecha),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "$signo${movimiento.cantidad} $unidad",
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}