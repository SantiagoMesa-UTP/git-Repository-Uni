package com.example.inventarioindustrial.ui.components

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val colombia = Locale.forLanguageTag("es-CO")

// 45522 -> $45.522
fun formatearPrecio(valor: Int): String {
    return "$" + NumberFormat.getIntegerInstance(colombia).format(valor)
}

// Fecha en milisegundos -> 15/11/2026 13:28
fun formatearFecha(fecha: Long): String {
    return SimpleDateFormat("dd/MM/yyyy HH:mm", colombia).format(Date(fecha))
}