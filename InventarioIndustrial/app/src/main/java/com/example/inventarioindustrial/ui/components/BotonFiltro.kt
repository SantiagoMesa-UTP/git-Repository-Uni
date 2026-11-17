package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// Botón redondeado para filtrar o elegir una opción
@Composable
fun BotonFiltro(nombre: String, seleccionado: Boolean, alHacerClic: () -> Unit) {
    FilterChip(
        selected = seleccionado,
        onClick = alHacerClic,
        label = {
            Text(
                text = nombre,
                style = MaterialTheme.typography.labelLarge
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.primary
        ),
        border = null
    )
}