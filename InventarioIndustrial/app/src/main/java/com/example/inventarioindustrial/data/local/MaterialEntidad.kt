package com.example.inventarioindustrial.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Tabla "materiales": no puede haber dos materiales con el mismo código
@Entity(
    tableName = "materiales",
    indices = [Index(value = ["codigo"], unique = true)]
)
data class MaterialEntidad(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val codigo: String,

    val descripcion: String,

    val unidad: String,

    val precio: Int,

    val fabricante: String,

    val stock: Int,

    val categoria: String,

    val utilidad: String,

    // Nombre de la foto del material (vacío si no tiene)
    val claveImagen: String? = null
)