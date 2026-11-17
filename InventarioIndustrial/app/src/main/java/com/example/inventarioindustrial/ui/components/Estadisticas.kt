package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Tres tarjetas con los totales, calculados desde los datos de Room
@Composable
fun SeccionEstadisticas(total: Int, bajo: Int, agotado: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TarjetaEstadistica(
            etiqueta = "Materiales",
            valor = total.toString(),
            colorValor = MaterialTheme.colorScheme.primary
        )
        TarjetaEstadistica(
            etiqueta = "Stock bajo",
            valor = bajo.toString(),
            colorValor = MaterialTheme.colorScheme.tertiary
        )
        TarjetaEstadistica(
            etiqueta = "Sin stock",
            valor = agotado.toString(),
            colorValor = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun RowScope.TarjetaEstadistica(etiqueta: String, valor: String, colorValor: Color) {
    Card(
        modifier = Modifier.weight(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium,
                color = colorValor
            )
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}