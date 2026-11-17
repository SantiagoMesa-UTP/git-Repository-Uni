package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.ui.theme.FormaBoton

/*
* Si el stock es 0, muestra "Sin stock"
* Si la cantidad es 0, muestra "Agregar"
* Al presionar Agregar, la cantidad pasa a 1
* Cuando la cantidad es mayor que 0, aparecen - y +
* Al presionar +, la cantidad aumenta (hasta el stock disponible)
* Al presionar -, la cantidad disminuye
* Cuando llega a 0, vuelve a aparecer "Agregar"
* */
@Composable
fun SelectorCantidad(
    cantidad: Int,
    maximo: Int,
    alAumentar: () -> Unit,
    alDisminuir: () -> Unit
) {
    if (maximo == 0) {
        Button(
            onClick = {},
            enabled = false,
            shape = FormaBoton
        ) {
            Text("Sin stock")
        }
    } else if (cantidad == 0) {
        Button(
            onClick = alAumentar,
            shape = FormaBoton
        ) {
            Text("Agregar")
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = alDisminuir,
                shape = FormaBoton,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Text("-")
            }

            Text(
                text = cantidad.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedButton(
                onClick = alAumentar,
                enabled = cantidad < maximo,
                shape = FormaBoton,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Text("+")
            }
        }
    }
}