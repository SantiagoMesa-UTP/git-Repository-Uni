package com.example.inventarioindustrial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventarioindustrial.data.repository.InventarioRepositorio
import com.example.inventarioindustrial.model.FiltroStock
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.Movimiento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// El ViewModel guarda el estado de las pantallas y le pide los datos al repositorio
class InventarioViewModel(
    private val repositorio: InventarioRepositorio
) : ViewModel() {

    // LISTA DE MATERIALES (viene de Room)
    val materiales = repositorio.materiales.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // BÚSQUEDA Y FILTROS
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda = _textoBusqueda.asStateFlow()

    private val _categoriaInicio = MutableStateFlow<String?>(null)
    val categoriaInicio = _categoriaInicio.asStateFlow()

    private val _categoriaAlmacen = MutableStateFlow<String?>(null)
    val categoriaAlmacen = _categoriaAlmacen.asStateFlow()

    private val _filtroStock = MutableStateFlow(FiltroStock.TODOS)
    val filtroStock = _filtroStock.asStateFlow()

    fun cambiarBusqueda(texto: String) {
        _textoBusqueda.value = texto
    }

    fun cambiarCategoriaInicio(categoria: String?) {
        _categoriaInicio.value = categoria
    }

    fun cambiarCategoriaAlmacen(categoria: String?) {
        _categoriaAlmacen.value = categoria
    }

    fun cambiarFiltroStock(filtro: FiltroStock) {
        _filtroStock.value = filtro
    }

    // SOLICITUD EN CURSO: id del material -> cantidad pedida
    private val _cantidadesSolicitud = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val cantidadesSolicitud = _cantidadesSolicitud.asStateFlow()

    // Sube la cantidad de 1 en 1 sin pasarse del stock
    fun aumentarCantidad(idMaterial: Int) {
        val material = materiales.value.firstOrNull { it.id == idMaterial }

        if (material == null) {
            return
        }

        val cantidadActual = _cantidadesSolicitud.value[idMaterial] ?: 0

        if (cantidadActual < material.stock) {
            _cantidadesSolicitud.value =
                _cantidadesSolicitud.value + (idMaterial to (cantidadActual + 1))
        }
    }

    // Baja la cantidad; si llega a 0 el material sale de la solicitud
    fun disminuirCantidad(idMaterial: Int) {
        val cantidadActual = _cantidadesSolicitud.value[idMaterial] ?: 0

        if (cantidadActual > 1) {
            _cantidadesSolicitud.value =
                _cantidadesSolicitud.value + (idMaterial to (cantidadActual - 1))
        } else {
            _cantidadesSolicitud.value = _cantidadesSolicitud.value - idMaterial
        }
    }

    fun vaciarSolicitud() {
        _cantidadesSolicitud.value = emptyMap()
    }

    // CONFIRMAR PEDIDO: descuenta el stock en Room y vacía la solicitud
    fun confirmarSolicitud() {
        val cantidades = _cantidadesSolicitud.value

        viewModelScope.launch {
            repositorio.confirmarSolicitud(cantidades)
        }
        vaciarSolicitud()
    }

    // CONSULTAS DE UN MATERIAL
    fun observarMaterial(idMaterial: Int): Flow<Material?> {
        return repositorio.observarMaterial(idMaterial)
    }

    fun observarMovimientos(idMaterial: Int): Flow<List<Movimiento>> {
        return repositorio.observarMovimientos(idMaterial)
    }

    // REGISTRAR MATERIAL: alTerminar recibe false si el código ya existe
    fun agregarMaterial(material: Material, alTerminar: (Boolean) -> Unit) {
        viewModelScope.launch {
            val guardado = repositorio.agregarMaterial(material)
            alTerminar(guardado)
        }
    }

    // EDITAR MATERIAL: alTerminar recibe false si el código ya existe
    fun actualizarMaterial(material: Material, alTerminar: (Boolean) -> Unit) {
        viewModelScope.launch {
            val guardado = repositorio.actualizarMaterial(material)
            alTerminar(guardado)
        }
    }

    // ELIMINAR MATERIAL (también lo quita de la solicitud)
    fun eliminarMaterial(idMaterial: Int) {
        _cantidadesSolicitud.value = _cantidadesSolicitud.value - idMaterial

        viewModelScope.launch {
            repositorio.eliminarMaterial(idMaterial)
        }
    }

    // INGRESO DE STOCK
    fun ingresarStock(idMaterial: Int, cantidad: Int) {
        viewModelScope.launch {
            repositorio.ingresarStock(idMaterial, cantidad)
        }
    }
}


// La Factory le entrega el repositorio al ViewModel cuando Android lo crea
class InventarioViewModelFactory(
    private val repositorio: InventarioRepositorio
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        require(
            modelClass.isAssignableFrom(
                InventarioViewModel::class.java
            )
        )

        return InventarioViewModel(repositorio) as T
    }
}