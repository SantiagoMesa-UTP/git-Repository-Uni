package com.example.inventarioindustrial.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Tabla "movimientos": cada movimiento pertenece a un material
// y se borra automáticamente cuando se borra ese material
@Entity(
    tableName = "movimientos",
    foreignKeys = [
        ForeignKey(
            entity = MaterialEntidad::class,
            parentColumns = ["id"],
            childColumns = ["idMaterial"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idMaterial"])]
)
data class MovimientoEntidad(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val idMaterial: Int,

    // "ENTRADA" o "SALIDA"
    val tipo: String,

    val cantidad: Int,

    // Fecha y hora en milisegundos
    val fecha: Long
)