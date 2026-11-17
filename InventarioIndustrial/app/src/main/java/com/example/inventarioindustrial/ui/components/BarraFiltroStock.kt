package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.FiltroStock

// Tres botones: Todos, Stock bajo y Sin stock
@Composable
fun BarraFiltroStock(seleccionado: FiltroStock, alCambiar: (FiltroStock) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        BotonFiltro(
            nombre = "Todos",
            seleccionado = seleccionado == FiltroStock.TODOS,
            alHacerClic = { alCambiar(FiltroStock.TODOS) }
        )
        BotonFiltro(
            nombre = "Stock bajo",
            seleccionado = seleccionado == FiltroStock.BAJO,
            alHacerClic = { alCambiar(FiltroStock.BAJO) }
        )
        BotonFiltro(
            nombre = "Sin stock",
            seleccionado = seleccionado == FiltroStock.AGOTADO,
            alHacerClic = { alCambiar(FiltroStock.AGOTADO) }
        )
    }
}