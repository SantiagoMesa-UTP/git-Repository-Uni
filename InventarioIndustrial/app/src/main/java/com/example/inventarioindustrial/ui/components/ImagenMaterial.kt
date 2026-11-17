package com.example.inventarioindustrial.ui.components

import androidx.annotation.DrawableRes
import com.example.inventarioindustrial.R
import com.example.inventarioindustrial.model.Material

// Devuelve la foto del material o, si no tiene, una imagen genérica de su categoría
@DrawableRes
fun imagenMaterial(material: Material): Int {
    return when (material.claveImagen) {
        "cable_1" -> R.drawable.cable_1
        "cable_2" -> R.drawable.cable_2
        "cable_3" -> R.drawable.cable_3
        "cable_4" -> R.drawable.cable_4
        "cable_5" -> R.drawable.cable_5
        "cable_6" -> R.drawable.cable_6
        "descargador_psm3" -> R.drawable.descargador_psm3
        "nomex_013" -> R.drawable.nomex_013
        else -> imagenGenerica(material.categoria)
    }
}

@DrawableRes
private fun imagenGenerica(categoria: String): Int {
    return when (categoria) {
        "Protección" -> R.drawable.generic_protection
        "Aislantes" -> R.drawable.generic_insulation
        else -> R.drawable.generic_cable
    }
}