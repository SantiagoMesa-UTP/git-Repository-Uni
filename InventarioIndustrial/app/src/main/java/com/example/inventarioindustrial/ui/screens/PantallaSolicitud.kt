package com.example.inventarioindustrial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.ui.components.FilaSolicitud
import com.example.inventarioindustrial.ui.theme.FormaBoton
import com.example.inventarioindustrial.ui.theme.Verde

@Composable
fun PantallaSolicitud(
    materiales: List<Material>,
    cantidades: Map<Int, Int>,
    alAumentar: (Int) -> Unit,
    alDisminuir: (Int) -> Unit,
    alVaciar: () -> Unit,
    alConfirmar: () -> Unit,
    alIrAlAlmacen: () -> Unit
) {
    // Solo los materiales que tienen una cantidad mayor a 0
    val materialesSolicitados = materiales.filter { material ->
        (cantidades[material.id] ?: 0) > 0
    }

    var mostrarAviso by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Mi solicitud",
            style = MaterialTheme.typography.headlineSmall
        )

        if (materialesSolicitados.isEmpty()) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aún no has agregado materiales a tu solicitud",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = alIrAlAlmacen,
                shape = FormaBoton
            ) {
                Text("Ir al almacén")
            }
        } else {
            Text(
                text = "${materialesSolicitados.size} materiales seleccionados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(materialesSolicitados, key = { it.id }) { material ->
                    FilaSolicitud(
                        material = material,
                        cantidad = cantidades[material.id] ?: 0,
                        alAumentar = {
                            alAumentar(material.id)
                        },
                        alDisminuir = {
                            alDisminuir(material.id)
                        }
                    )
                }
            }

            Button(
                onClick = {
                    alConfirmar()
                    mostrarAviso = true
                },
                shape = FormaBoton,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar pedido")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedButton(
                onClick = alVaciar,
                shape = FormaBoton,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vaciar solicitud")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }

    // Aviso que aparece después de confirmar el pedido
    if (mostrarAviso) {
        AlertDialog(
            onDismissRequest = {
                mostrarAviso = false
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Verde,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text("Solicitud realizada")
            },
            text = {
                Text("Se realizó su solicitud")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarAviso = false
                    },
                    shape = FormaBoton
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}