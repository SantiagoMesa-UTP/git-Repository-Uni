package com.example.inventarioindustrial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    // Los más recientes primero
    @Query("SELECT * FROM movimientos WHERE idMaterial = :idMaterial ORDER BY fecha DESC, id DESC")
    fun observarPorMaterial(idMaterial: Int): Flow<List<MovimientoEntidad>>

    @Insert
    suspend fun insertar(movimiento: MovimientoEntidad)
}