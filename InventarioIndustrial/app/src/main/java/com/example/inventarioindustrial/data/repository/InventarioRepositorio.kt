package com.example.inventarioindustrial.data.repository

import androidx.room.withTransaction
import com.example.inventarioindustrial.data.local.InventarioBaseDatos
import com.example.inventarioindustrial.data.local.MaterialEntidad
import com.example.inventarioindustrial.data.local.MovimientoEntidad
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.Movimiento
import com.example.inventarioindustrial.model.TipoMovimiento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// El repositorio es el único que habla con la base de datos
class InventarioRepositorio(
    private val baseDatos: InventarioBaseDatos
) {

    private val materialDao = baseDatos.materialDao()
    private val movimientoDao = baseDatos.movimientoDao()

    // Lista de materiales: se actualiza sola cuando cambia la base de datos
    val materiales: Flow<List<Material>> =
        materialDao.observarTodos().map { lista ->
            lista.map { entidad -> entidad.aMaterial() }
        }

    fun observarMaterial(idMaterial: Int): Flow<Material?> =
        materialDao.observarPorId(idMaterial).map { entidad ->
            entidad?.aMaterial()
        }

    fun observarMovimientos(idMaterial: Int): Flow<List<Movimiento>> =
        movimientoDao.observarPorMaterial(idMaterial).map { lista ->
            lista.map { entidad -> entidad.aMovimiento() }
        }

    // CREAR: devuelve false si ya existe un material con ese código
    suspend fun agregarMaterial(material: Material): Boolean {
        val codigo = material.codigo.trim().uppercase()

        if (materialDao.buscarPorCodigo(codigo) != null) {
            return false
        }

        val entidad = material.aEntidad().copy(id = 0, codigo = codigo)
        materialDao.insertar(entidad)
        return true
    }

    // ACTUALIZAR: devuelve false si el código ya lo tiene otro material
    suspend fun actualizarMaterial(material: Material): Boolean {
        val codigo = material.codigo.trim().uppercase()
        val materialConEseCodigo = materialDao.buscarPorCodigo(codigo)

        if (materialConEseCodigo != null && materialConEseCodigo.id != material.id) {
            return false
        }

        materialDao.actualizar(material.aEntidad().copy(codigo = codigo))
        return true
    }

    // ELIMINAR: sus movimientos se borran junto con él
    suspend fun eliminarMaterial(idMaterial: Int) {
        val entidad = materialDao.buscarPorId(idMaterial)

        if (entidad != null) {
            materialDao.eliminar(entidad)
        }
    }

    // Llega un nuevo pedido al almacén: sube el stock y queda una ENTRADA en el historial
    suspend fun ingresarStock(idMaterial: Int, cantidad: Int) {
        if (cantidad <= 0) {
            return
        }

        // Las dos operaciones se hacen juntas: o se guardan ambas o ninguna
        baseDatos.withTransaction {
            val entidad = materialDao.buscarPorId(idMaterial)

            if (entidad != null) {
                materialDao.actualizar(entidad.copy(stock = entidad.stock + cantidad))
                movimientoDao.insertar(
                    MovimientoEntidad(
                        idMaterial = idMaterial,
                        tipo = TipoMovimiento.ENTRADA.name,
                        cantidad = cantidad,
                        fecha = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    // Se confirma la solicitud: baja el stock y queda una SALIDA por cada material
    suspend fun confirmarSolicitud(cantidades: Map<Int, Int>) {
        baseDatos.withTransaction {
            for ((idMaterial, cantidadPedida) in cantidades) {
                val entidad = materialDao.buscarPorId(idMaterial)

                if (entidad != null) {
                    // Nunca se descuenta más de lo que hay en stock
                    var cantidad = cantidadPedida
                    if (cantidad > entidad.stock) {
                        cantidad = entidad.stock
                    }

                    if (cantidad > 0) {
                        materialDao.actualizar(entidad.copy(stock = entidad.stock - cantidad))
                        movimientoDao.insertar(
                            MovimientoEntidad(
                                idMaterial = idMaterial,
                                tipo = TipoMovimiento.SALIDA.name,
                                cantidad = cantidad,
                                fecha = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
        }
    }
}

// Conversiones entre lo que se guarda (Entidad) y lo que usa la pantalla (modelo)
private fun MaterialEntidad.aMaterial(): Material {
    return Material(
        id = id,
        codigo = codigo,
        descripcion = descripcion,
        unidad = unidad,
        precio = precio,
        fabricante = fabricante,
        stock = stock,
        categoria = categoria,
        utilidad = utilidad,
        claveImagen = claveImagen
    )
}

private fun Material.aEntidad(): MaterialEntidad {
    return MaterialEntidad(
        id = id,
        codigo = codigo,
        descripcion = descripcion,
        unidad = unidad,
        precio = precio,
        fabricante = fabricante,
        stock = stock,
        categoria = categoria,
        utilidad = utilidad,
        claveImagen = claveImagen
    )
}

private fun MovimientoEntidad.aMovimiento(): Movimiento {
    val tipoMovimiento = if (tipo == TipoMovimiento.SALIDA.name) {
        TipoMovimiento.SALIDA
    } else {
        TipoMovimiento.ENTRADA
    }

    return Movimiento(
        id = id,
        idMaterial = idMaterial,
        tipo = tipoMovimiento,
        cantidad = cantidad,
        fecha = fecha
    )
}