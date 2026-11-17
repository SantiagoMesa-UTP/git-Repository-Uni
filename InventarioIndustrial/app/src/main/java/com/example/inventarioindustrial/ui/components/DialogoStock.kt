package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.ui.theme.FormaBoton

// Ventana para registrar la llegada de un nuevo pedido al almacén
@Composable
fun DialogoStock(
    materiales: List<Material>,
    alCerrar: () -> Unit,
    alConfirmar: (Int, Int) -> Unit
) {
    var idSeleccionado by remember { mutableStateOf(materiales.first().id) }
    var textoCantidad by remember { mutableStateOf("") }
    var menuAbierto by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

    // Material elegido en la lista desplegable
    var materialSeleccionado = materiales.first()
    for (material in materiales) {
        if (material.id == idSeleccionado) {
            materialSeleccionado = material
        }
    }

    val cantidad = textoCantidad.toIntOrNull() ?: 0

    AlertDialog(
        onDismissRequest = alCerrar,
        title = {
            Text("Gestión de stock")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Registra el ingreso de un nuevo pedido al almacén",
                    style = MaterialTheme.typography.bodyMedium
                )

                Box {
                    OutlinedButton(
                        onClick = {
                            menuAbierto = true
                        },
                        shape = FormaBoton,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = materialSeleccionado.descripcion,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = {
                            menuAbierto = false
                        }
                    ) {
                        for (material in materiales) {
                            DropdownMenuItem(
                                text = {
                                    Text("${material.codigo} - ${material.descripcion}")
                                },
                                onClick = {
                                    idSeleccionado = material.id
                                    menuAbierto = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Stock actual: ${materialSeleccionado.stock} ${materialSeleccionado.unidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = textoCantidad,
                    onValueChange = { nuevoTexto ->
                        // Solo se aceptan números
                        textoCantidad = nuevoTexto.filter { it.isDigit() }.take(7)
                        mostrarError = false
                    },
                    label = {
                        Text("Cantidad que ingresa (${materialSeleccionado.unidad})")
                    },
                    singleLine = true,
                    isError = mostrarError,
                    supportingText = {
                        if (mostrarError) {
                            Text("Ingresa una cantidad mayor a 0")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (cantidad > 0) {
                        alConfirmar(idSeleccionado, cantidad)
                    } else {
                        mostrarError = true
                    }
                },
                shape = FormaBoton
            ) {
                Text("Registrar ingreso")
            }
        },
        dismissButton = {
            TextButton(onClick = alCerrar) {
                Text("Cancelar")
            }
        }
    )
}