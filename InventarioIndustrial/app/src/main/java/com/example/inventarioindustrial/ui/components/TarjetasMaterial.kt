package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.estadoStock
import com.example.inventarioindustrial.ui.theme.EstiloCodigo
import com.example.inventarioindustrial.ui.theme.FormaBoton

// Tarjeta de la pantalla Almacén: permite agregar el material a la solicitud
@Composable
fun TarjetaMaterial(
    material: Material,
    cantidad: Int,
    alAumentar: () -> Unit,
    alDisminuir: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(
                    id = imagenMaterial(material)
                ),
                contentDescription = material.descripcion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Descripción
            Text(
                text = material.descripcion,
                style = MaterialTheme.typography.titleMedium
            )

            // Código
            Text(
                text = "Código: ${material.codigo}",
                style = EstiloCodigo,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EtiquetaEstadoStock(estadoStock(material))
                Text(
                    text = "Stock: ${material.stock} ${material.unidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Precio y botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "${formatearPrecio(material.precio)} / ${material.unidad}",
                    style = MaterialTheme.typography.titleMedium
                )

                SelectorCantidad(
                    cantidad = cantidad,
                    maximo = material.stock,
                    alAumentar = alAumentar,
                    alDisminuir = alDisminuir
                )
            }
        }
    }
}

// Tarjeta de la pantalla Inicio: al tocarla se abre el detalle del material
@Composable
fun TarjetaConsulta(
    material: Material,
    alHacerClic: () -> Unit
) {
    Card(
        onClick = alHacerClic,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = imagenMaterial(material)
                ),
                contentDescription = material.descripcion,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = material.descripcion,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Código: ${material.codigo}",
                    style = EstiloCodigo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EtiquetaEstadoStock(estadoStock(material))
                    Text(
                        text = "${material.stock} ${material.unidad}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                FilledTonalButton(
                    onClick = alHacerClic,
                    shape = FormaBoton
                ) {
                    Text("Ver detalles")
                }
            }
        }
    }
}

// Fila de un material dentro de la pantalla Solicitud
@Composable
fun FilaSolicitud(
    material: Material,
    cantidad: Int,
    alAumentar: () -> Unit,
    alDisminuir: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = imagenMaterial(material)
                ),
                contentDescription = material.descripcion,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = material.descripcion,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Código: ${material.codigo}",
                    style = EstiloCodigo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Cantidad: $cantidad ${material.unidad}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                SelectorCantidad(
                    cantidad = cantidad,
                    maximo = material.stock,
                    alAumentar = alAumentar,
                    alDisminuir = alDisminuir
                )
            }
        }
    }
}