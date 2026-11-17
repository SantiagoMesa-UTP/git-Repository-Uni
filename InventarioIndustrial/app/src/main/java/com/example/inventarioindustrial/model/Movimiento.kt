package com.example.inventarioindustrial.model

// Una línea del historial de un material: una entrada o una salida
data class Movimiento(
    val id: Int,
    val idMaterial: Int,
    val tipo: TipoMovimiento,
    val cantidad: Int,
    val fecha: Long
)

enum class TipoMovimiento { ENTRADA, SALIDA }