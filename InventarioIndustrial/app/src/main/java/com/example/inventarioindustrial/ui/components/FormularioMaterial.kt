package com.example.inventarioindustrial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.categoriasMaterial
import com.example.inventarioindustrial.ui.theme.FormaBoton

private val opcionesUnidad = listOf("m", "und", "kg")

// Formulario para registrar un material (materialInicial = null) o editar uno existente
@Composable
fun FormularioMaterial(
    materialInicial: Material?,
    codigoRepetido: Boolean,
    alCambiarCodigo: () -> Unit,
    alGuardar: (Material) -> Unit,
    alCancelar: () -> Unit
) {
    var codigo by remember { mutableStateOf(materialInicial?.codigo ?: "") }
    var descripcion by remember { mutableStateOf(materialInicial?.descripcion ?: "") }
    var unidad by remember { mutableStateOf(materialInicial?.unidad ?: "m") }
    var textoPrecio by remember { mutableStateOf(materialInicial?.precio?.toString() ?: "") }
    var fabricante by remember { mutableStateOf(materialInicial?.fabricante ?: "") }
    var textoStock by remember { mutableStateOf(materialInicial?.stock?.toString() ?: "") }
    var categoria by remember { mutableStateOf(materialInicial?.categoria ?: "Cables") }
    var utilidad by remember { mutableStateOf(materialInicial?.utilidad ?: "") }

    // Los errores solo se muestran después de presionar "Guardar"
    var intentoGuardar by remember { mutableStateOf(false) }

    val precio = textoPrecio.toIntOrNull() ?: 0
    val stock = textoStock.toIntOrNull()

    // VALIDACIONES: cada una devuelve el mensaje de error o null si está bien
    var errorCodigo: String? = null
    if (codigoRepetido) {
        errorCodigo = "Ya existe un material con ese código"
    } else if (intentoGuardar && codigo.isBlank()) {
        errorCodigo = "Ingresa el código"
    }

    var errorDescripcion: String? = null
    if (intentoGuardar && descripcion.isBlank()) {
        errorDescripcion = "Ingresa la descripción"
    }

    var errorPrecio: String? = null
    if (intentoGuardar && precio <= 0) {
        errorPrecio = "Ingresa un precio mayor a 0"
    }

    var errorStock: String? = null
    if (intentoGuardar && stock == null) {
        errorStock = "Ingresa el stock (0 o más)"
    }

    var errorFabricante: String? = null
    if (intentoGuardar && fabricante.isBlank()) {
        errorFabricante = "Ingresa el fabricante"
    }

    var errorUtilidad: String? = null
    if (intentoGuardar && utilidad.isBlank()) {
        errorUtilidad = "Describe la utilidad del material"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (materialInicial == null) "Nuevo material" else "Editar material",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = {
                codigo = it.take(12)
                alCambiarCodigo()
            },
            label = { Text("Código") },
            singleLine = true,
            isError = errorCodigo != null,
            supportingText = { if (errorCodigo != null) Text(errorCodigo) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            isError = errorDescripcion != null,
            supportingText = { if (errorDescripcion != null) Text(errorDescripcion) },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Unidad", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (opcion in opcionesUnidad) {
                BotonFiltro(
                    nombre = opcion,
                    seleccionado = unidad == opcion,
                    alHacerClic = { unidad = opcion }
                )
            }
        }

        Text("Categoría", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (opcion in categoriasMaterial) {
                BotonFiltro(
                    nombre = opcion.nombre,
                    seleccionado = categoria == opcion.nombre,
                    alHacerClic = { categoria = opcion.nombre }
                )
            }
        }

        OutlinedTextField(
            value = textoPrecio,
            onValueChange = { textoPrecio = it.filter { c -> c.isDigit() }.take(9) },
            label = { Text("Precio por unidad ($)") },
            singleLine = true,
            isError = errorPrecio != null,
            supportingText = { if (errorPrecio != null) Text(errorPrecio) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = textoStock,
            onValueChange = { textoStock = it.filter { c -> c.isDigit() }.take(7) },
            label = { Text("Stock actual") },
            singleLine = true,
            isError = errorStock != null,
            supportingText = { if (errorStock != null) Text(errorStock) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fabricante,
            onValueChange = { fabricante = it },
            label = { Text("Fabricante") },
            singleLine = true,
            isError = errorFabricante != null,
            supportingText = { if (errorFabricante != null) Text(errorFabricante) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = utilidad,
            onValueChange = { utilidad = it },
            label = { Text("Utilidad") },
            isError = errorUtilidad != null,
            supportingText = { if (errorUtilidad != null) Text(errorUtilidad) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                intentoGuardar = true

                val todoCorrecto = codigo.isNotBlank() &&
                        descripcion.isNotBlank() &&
                        fabricante.isNotBlank() &&
                        utilidad.isNotBlank() &&
                        precio > 0 &&
                        stock != null

                if (todoCorrecto) {
                    val material = Material(
                        id = materialInicial?.id ?: 0,
                        codigo = codigo.trim(),
                        descripcion = descripcion.trim(),
                        unidad = unidad,
                        precio = precio,
                        fabricante = fabricante.trim(),
                        stock = stock ?: 0,
                        categoria = categoria,
                        utilidad = utilidad.trim(),
                        claveImagen = materialInicial?.claveImagen
                    )
                    alGuardar(material)
                }
            },
            shape = FormaBoton,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (materialInicial == null) "Guardar material" else "Guardar cambios")
        }

        OutlinedButton(
            onClick = alCancelar,
            shape = FormaBoton,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}