package com.example.inventarioindustrial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Categoria
import com.example.inventarioindustrial.model.EstadoStock
import com.example.inventarioindustrial.model.FiltroStock
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.estadoStock
import com.example.inventarioindustrial.ui.components.BarraFiltroStock
import com.example.inventarioindustrial.ui.components.BotonFiltro
import com.example.inventarioindustrial.ui.components.EstadoVacio
import com.example.inventarioindustrial.ui.components.SeccionEstadisticas
import com.example.inventarioindustrial.ui.components.TarjetaConsulta
import com.example.inventarioindustrial.ui.theme.FormaBoton

@Composable
fun PantallaInicio(
    categorias: List<Categoria>,
    materiales: List<Material>,
    categoriaSeleccionada: String?,
    alCambiarCategoria: (String?) -> Unit,
    filtroStock: FiltroStock,
    alCambiarFiltro: (FiltroStock) -> Unit,
    alSolicitar: () -> Unit,
    alAbrirMaterial: (Int) -> Unit,
    alNuevoMaterial: () -> Unit
) {
    // Totales para las tarjetas de estadísticas
    val cantidadBajo = materiales.count { estadoStock(it) == EstadoStock.BAJO }
    val cantidadAgotado = materiales.count { estadoStock(it) == EstadoStock.AGOTADO }

    // Materiales que se muestran: cumplen la categoría y el filtro de stock elegidos
    val materialesFiltrados = materiales.filter { material ->
        val cumpleCategoria = categoriaSeleccionada == null ||
                material.categoria == categoriaSeleccionada

        val estado = estadoStock(material)
        val cumpleStock = when (filtroStock) {
            FiltroStock.TODOS -> true
            FiltroStock.BAJO -> estado == EstadoStock.BAJO
            FiltroStock.AGOTADO -> estado == EstadoStock.AGOTADO
        }

        cumpleCategoria && cumpleStock
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ENCABEZADO Y ESTADÍSTICAS
            item {
                Text(
                    text = "Detalles de existencias en almacén",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            item {
                SeccionEstadisticas(
                    total = materiales.size,
                    bajo = cantidadBajo,
                    agotado = cantidadAgotado
                )
            }

            // SOLICITAR AL ALMACÉN
            item {
                Button(
                    onClick = alSolicitar,
                    shape = FormaBoton,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solicitar al almacén")
                }
            }

            // CATEGORÍAS
            item {
                Text(
                    text = "Categorías",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
            }

            // FILTRO POR ESTADO DEL STOCK
            item {
                BarraFiltroStock(
                    seleccionado = filtroStock,
                    alCambiar = alCambiarFiltro
                )
            }

            // MATERIALES (vienen de Room)
            if (materialesFiltrados.isEmpty()) {
                item {
                    if (materiales.isEmpty()) {
                        EstadoVacio("Aún no hay materiales. Toca Nuevo material para registrar el primero.")
                    } else {
                        EstadoVacio("No hay materiales con ese filtro")
                    }
                }
            } else {
                items(materialesFiltrados, key = { it.id }) { material ->
                    TarjetaConsulta(
                        material = material,
                        alHacerClic = {
                            alAbrirMaterial(material.id)
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = alNuevoMaterial,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "+  Nuevo material",
                modifier = Modifier.padding(horizontal = 18.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}