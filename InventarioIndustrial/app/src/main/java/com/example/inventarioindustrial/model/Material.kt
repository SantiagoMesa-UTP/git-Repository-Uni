package com.example.inventarioindustrial.model

// Un material con stock entre 1 y este número se considera con stock bajo
const val LIMITE_STOCK_BAJO = 500

// Datos de un material del almacén
data class Material(
    val id: Int,
    val codigo: String,
    val descripcion: String,
    val unidad: String,
    val precio: Int,
    val fabricante: String,
    val stock: Int,
    val categoria: String,
    val utilidad: String,
    val claveImagen: String? = null
)

data class Categoria(
    val nombre: String
)

// Estado de un material según su stock
enum class EstadoStock { DISPONIBLE, BAJO, AGOTADO }

// Filtro de la pantalla de inicio
enum class FiltroStock { TODOS, BAJO, AGOTADO }

fun estadoStock(material: Material): EstadoStock {
    if (material.stock == 0) {
        return EstadoStock.AGOTADO
    }
    if (material.stock <= LIMITE_STOCK_BAJO) {
        return EstadoStock.BAJO
    }
    return EstadoStock.DISPONIBLE
}

// Categorías del almacén
val categoriasMaterial = listOf(
    Categoria("Cables"),
    Categoria("Protección"),
    Categoria("Aislantes")
)