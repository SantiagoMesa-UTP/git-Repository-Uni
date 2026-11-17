package com.example.inventarioindustrial.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.Movimiento
import com.example.inventarioindustrial.model.estadoStock
import com.example.inventarioindustrial.ui.components.EtiquetaEstadoStock
import com.example.inventarioindustrial.ui.components.FilaDetalle
import com.example.inventarioindustrial.ui.components.FilaMovimiento
import com.example.inventarioindustrial.ui.components.formatearPrecio
import com.example.inventarioindustrial.ui.components.imagenMaterial
import com.example.inventarioindustrial.ui.theme.FormaBoton

@Composable
fun PantallaDetalleMaterial(
    material: Material?,
    movimientos: List<Movimiento>,
    alVolver: () -> Unit,
    alEditar: (Material) -> Unit,
    alEliminar: (Int) -> Unit
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Detalle del material",
            style = MaterialTheme.typography.headlineSmall
        )

        if (material == null) {
            Text(
                text = "No se encontró el material",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            // DATOS DEL MATERIAL
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id = imagenMaterial(material)
                        ),
                        contentDescription = material.descripcion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = material.descripcion,
                        style = MaterialTheme.typography.titleLarge
                    )

                    EtiquetaEstadoStock(estadoStock(material))

                    FilaDetalle("Código", material.codigo)
                    FilaDetalle("Categoría", material.categoria)
                    FilaDetalle("Precio", "${formatearPrecio(material.precio)} / ${material.unidad}")
                    FilaDetalle("Fabricante", material.fabricante)
                    FilaDetalle("Stock", "${material.stock} ${material.unidad}")
                    FilaDetalle("Utilidad", material.utilidad)
                }
            }

            // EDITAR Y ELIMINAR
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilledTonalButton(
                    onClick = {
                        alEditar(material)
                    },
                    shape = FormaBoton,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                OutlinedButton(
                    onClick = {
                        mostrarConfirmacion = true
                    },
                    shape = FormaBoton,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }

            // HISTORIAL DE MOVIMIENTOS (segunda entidad de Room)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Historial de movimientos",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (movimientos.isEmpty()) {
                        Text(
                            text = "Aún no hay entradas ni salidas registradas",
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        for ((posicion, movimiento) in movimientos.withIndex()) {
                            if (posicion > 0) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            }
                            FilaMovimiento(
                                movimiento = movimiento,
                                unidad = material.unidad
                            )
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = alVolver,
            shape = FormaBoton,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }

    // CONFIRMAR ANTES DE ELIMINAR
    if (mostrarConfirmacion && material != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },
            title = {
                Text("Eliminar material")
            },
            text = {
                Text("Se eliminará ${material.descripcion} con todo su historial. Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarConfirmacion = false
                        alEliminar(material.id)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = FormaBoton
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}