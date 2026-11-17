package com.example.inventarioindustrial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Categoria
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.ui.components.BotonFiltro
import com.example.inventarioindustrial.ui.components.DialogoStock
import com.example.inventarioindustrial.ui.components.EstadoVacio
import com.example.inventarioindustrial.ui.components.TarjetaMaterial
import com.example.inventarioindustrial.ui.theme.FormaBoton

@Composable
fun PantallaAlmacen(
    textoBusqueda: String,
    alCambiarBusqueda: (String) -> Unit,
    categorias: List<Categoria>,
    categoriaSeleccionada: String?,
    alCambiarCategoria: (String?) -> Unit,
    materiales: List<Material>,
    cantidades: Map<Int, Int>,
    alAumentar: (Int) -> Unit,
    alDisminuir: (Int) -> Unit,
    alIngresarStock: (Int, Int) -> Unit
) {
    // Se muestran los materiales que coinciden con la búsqueda y con la categoría
    val materialesFiltrados = materiales.filter { material ->
        val coincideTexto = material.descripcion.contains(textoBusqueda, ignoreCase = true) ||
                material.codigo.contains(textoBusqueda, ignoreCase = true)

        val coincideCategoria = categoriaSeleccionada == null ||
                material.categoria == categoriaSeleccionada

        coincideTexto && coincideCategoria
    }

    var mostrarDialogoStock by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Almacén",
                style = MaterialTheme.typography.headlineSmall
            )

            FilledTonalButton(
                onClick = {
                    mostrarDialogoStock = true
                },
                enabled = materiales.isNotEmpty(),
                shape = FormaBoton
            ) {
                Text("Gestionar stock")
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = alCambiarBusqueda,
            label = {
                Text("Buscar materiales")
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                BotonFiltro(
                    nombre = "Todos",
                    seleccionado = categoriaSeleccionada == null,
                    alHacerClic = {
                        alCambiarCategoria(null)
                    }
                )
            }
            items(categorias) { categoria ->
                val estaSeleccionada = categoriaSeleccionada == categoria.nombre

                BotonFiltro(
                    nombre = categoria.nombre,
                    seleccionado = estaSeleccionada,
                    alHacerClic = {
                        // Tocar la categoría activa quita el filtro
                        if (estaSeleccionada) {
                            alCambiarCategoria(null)
                        } else {
                            alCambiarCategoria(categoria.nombre)
                        }
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (materialesFiltrados.isEmpty()) {
                item {
                    EstadoVacio()
                }
            } else {
                items(materialesFiltrados, key = { it.id }) { material ->
                    val cantidad = cantidades[material.id] ?: 0

                    TarjetaMaterial(
                        material = material,
                        cantidad = cantidad,
                        alAumentar = {
                            alAumentar(material.id)
                        },
                        alDisminuir = {
                            alDisminuir(material.id)
                        }
                    )
                }
            }
        }
    }

    if (mostrarDialogoStock && materiales.isNotEmpty()) {
        DialogoStock(
            materiales = materiales,
            alCerrar = {
                mostrarDialogoStock = false
            },
            alConfirmar = { idMaterial, cantidad ->
                alIngresarStock(idMaterial, cantidad)
                mostrarDialogoStock = false
            }
        )
    }
}