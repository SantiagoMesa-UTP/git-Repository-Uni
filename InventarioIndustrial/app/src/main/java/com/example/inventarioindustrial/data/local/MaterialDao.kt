package com.example.inventarioindustrial.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DAO: Data Access Object
/*
* CRUD
* C - Crear (insertar)
* R - Leer (consultar)
* U - Actualizar
* D - Eliminar
* */

@Dao
interface MaterialDao {
    @Query("SELECT * FROM materiales ORDER BY codigo ASC")
    fun observarTodos(): Flow<List<MaterialEntidad>>

    @Query("SELECT * FROM materiales WHERE id = :idMaterial LIMIT 1")
    fun observarPorId(idMaterial: Int): Flow<MaterialEntidad?>

    @Query("SELECT * FROM materiales WHERE id = :idMaterial LIMIT 1")
    suspend fun buscarPorId(idMaterial: Int): MaterialEntidad?

    @Query("SELECT * FROM materiales WHERE codigo = :codigo LIMIT 1")
    suspend fun buscarPorCodigo(codigo: String): MaterialEntidad?

    @Insert
    suspend fun insertar(material: MaterialEntidad): Long

    @Update
    suspend fun actualizar(material: MaterialEntidad)

    @Delete
    suspend fun eliminar(material: MaterialEntidad)
}