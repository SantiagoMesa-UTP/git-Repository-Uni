package com.example.inventarioindustrial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventarioindustrial.data.local.InventarioBaseDatos
import com.example.inventarioindustrial.data.repository.InventarioRepositorio
import com.example.inventarioindustrial.model.Material
import com.example.inventarioindustrial.model.categoriasMaterial
import com.example.inventarioindustrial.ui.components.FormularioMaterial
import com.example.inventarioindustrial.ui.screens.PantallaAlmacen
import com.example.inventarioindustrial.ui.screens.PantallaDetalleMaterial
import com.example.inventarioindustrial.ui.screens.PantallaInicio
import com.example.inventarioindustrial.ui.screens.PantallaSolicitud
import com.example.inventarioindustrial.ui.theme.InventarioIndustrialTema
import com.example.inventarioindustrial.viewmodel.InventarioViewModel
import com.example.inventarioindustrial.viewmodel.InventarioViewModelFactory

// Nombres de las pantallas para la navegación
object Rutas {
    const val INICIO = "inicio"
    const val ALMACEN = "almacen"
    const val SOLICITUD = "solicitud"
    const val DETALLE = "detalle_material/{idMaterial}"

    fun detalle(idMaterial: Int): String {
        return "detalle_material/$idMaterial"
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Config ROOM
        val baseDatos = InventarioBaseDatos.obtenerBaseDatos(applicationContext)
        val repositorio = InventarioRepositorio(baseDatos)
        val factory = InventarioViewModelFactory(repositorio)

        setContent {
            val inventarioViewModel: InventarioViewModel = viewModel(factory = factory)

            InventarioIndustrialTema {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InventarioIndustrialApp(inventarioViewModel)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioIndustrialApp(
    viewModel: InventarioViewModel
) {
    // Datos que vienen del ViewModel
    val materiales by viewModel.materiales.collectAsStateWithLifecycle()
    val textoBusqueda by viewModel.textoBusqueda.collectAsStateWithLifecycle()
    val categoriaInicio by viewModel.categoriaInicio.collectAsStateWithLifecycle()
    val categoriaAlmacen by viewModel.categoriaAlmacen.collectAsStateWithLifecycle()
    val filtroStock by viewModel.filtroStock.collectAsStateWithLifecycle()
    val cantidadesSolicitud by viewModel.cantidadesSolicitud.collectAsStateWithLifecycle()

    // Hoja inferior con el formulario (nuevo material o editar uno existente)
    var mostrarFormulario by remember { mutableStateOf(false) }
    var materialEditado by remember { mutableStateOf<Material?>(null) }
    var codigoRepetido by remember { mutableStateOf(false) }

    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    fun navegarA(ruta: String) {
        navController.navigate(ruta) {
            popUpTo(
                navController.graph.findStartDestination().id
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // Número que se ve sobre el carrito: cuántos materiales distintos hay en la solicitud
    val cantidadEnSolicitud = cantidadesSolicitud.size

    // La barra inferior solo se ve en las tres pantallas principales
    val mostrarBarraInferior = rutaActual == Rutas.INICIO ||
            rutaActual == Rutas.ALMACEN ||
            rutaActual == Rutas.SOLICITUD

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Inventario Industrial",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            if (mostrarBarraInferior) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    val colores = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    NavigationBarItem(
                        selected = rutaActual == Rutas.INICIO,
                        onClick = {
                            navegarA(Rutas.INICIO)
                        },
                        icon = {
                            Icon(Icons.Filled.Home, contentDescription = null)
                        },
                        label = {
                            Text("Inicio")
                        },
                        colors = colores
                    )

                    NavigationBarItem(
                        selected = rutaActual == Rutas.ALMACEN,
                        onClick = {
                            navegarA(Rutas.ALMACEN)
                        },
                        icon = {
                            Icon(Icons.Filled.Place, contentDescription = null)
                        },
                        label = {
                            Text("Almacén")
                        },
                        colors = colores
                    )

                    NavigationBarItem(
                        selected = rutaActual == Rutas.SOLICITUD,
                        onClick = {
                            navegarA(Rutas.SOLICITUD)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (cantidadEnSolicitud > 0) {
                                        Badge {
                                            Text(cantidadEnSolicitud.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                            }
                        },
                        label = {
                            Text("Solicitud")
                        },
                        colors = colores
                    )
                }
            }
        }

    ) { espaciado ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO,
            modifier = Modifier
                .fillMaxSize()
                .padding(espaciado)
        ) {
            // PANTALLA DE INICIO
            composable(Rutas.INICIO) {
                PantallaInicio(
                    categorias = categoriasMaterial,
                    materiales = materiales,
                    categoriaSeleccionada = categoriaInicio,
                    alCambiarCategoria = viewModel::cambiarCategoriaInicio,
                    filtroStock = filtroStock,
                    alCambiarFiltro = viewModel::cambiarFiltroStock,
                    alSolicitar = {
                        navegarA(Rutas.ALMACEN)
                    },
                    alAbrirMaterial = { idMaterial ->
                        navController.navigate(Rutas.detalle(idMaterial))
                    },
                    alNuevoMaterial = {
                        materialEditado = null
                        codigoRepetido = false
                        mostrarFormulario = true
                    }
                )
            }

            // PANTALLA DEL ALMACÉN
            composable(Rutas.ALMACEN) {
                PantallaAlmacen(
                    textoBusqueda = textoBusqueda,
                    alCambiarBusqueda = viewModel::cambiarBusqueda,
                    categorias = categoriasMaterial,
                    categoriaSeleccionada = categoriaAlmacen,
                    alCambiarCategoria = viewModel::cambiarCategoriaAlmacen,
                    materiales = materiales,
                    cantidades = cantidadesSolicitud,
                    alAumentar = viewModel::aumentarCantidad,
                    alDisminuir = viewModel::disminuirCantidad,
                    alIngresarStock = viewModel::ingresarStock
                )
            }

            // PANTALLA DE LA SOLICITUD
            composable(Rutas.SOLICITUD) {
                PantallaSolicitud(
                    materiales = materiales,
                    cantidades = cantidadesSolicitud,
                    alAumentar = viewModel::aumentarCantidad,
                    alDisminuir = viewModel::disminuirCantidad,
                    alVaciar = viewModel::vaciarSolicitud,
                    alConfirmar = viewModel::confirmarSolicitud,
                    alIrAlAlmacen = {
                        navegarA(Rutas.ALMACEN)
                    }
                )
            }

            // PANTALLA DE DETALLE DE UN MATERIAL
            composable(
                route = Rutas.DETALLE,
                arguments = listOf(
                    navArgument("idMaterial") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->
                val idMaterial = entrada.arguments?.getInt("idMaterial") ?: 0

                // Se consulta solo este material y su historial
                val material by remember(idMaterial) {
                    viewModel.observarMaterial(idMaterial)
                }.collectAsStateWithLifecycle(initialValue = null)

                val movimientos by remember(idMaterial) {
                    viewModel.observarMovimientos(idMaterial)
                }.collectAsStateWithLifecycle(initialValue = emptyList())

                PantallaDetalleMaterial(
                    material = material,
                    movimientos = movimientos,
                    alVolver = {
                        navController.popBackStack()
                    },
                    alEditar = { materialSeleccionado ->
                        materialEditado = materialSeleccionado
                        codigoRepetido = false
                        mostrarFormulario = true
                    },
                    alEliminar = { id ->
                        viewModel.eliminarMaterial(id)
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    // FORMULARIO PARA REGISTRAR O EDITAR UN MATERIAL
    if (mostrarFormulario) {
        ModalBottomSheet(
            onDismissRequest = {
                mostrarFormulario = false
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            FormularioMaterial(
                materialInicial = materialEditado,
                codigoRepetido = codigoRepetido,
                alCambiarCodigo = {
                    codigoRepetido = false
                },
                alGuardar = { material ->
                    if (materialEditado == null) {
                        viewModel.agregarMaterial(material) { guardado ->
                            if (guardado) {
                                mostrarFormulario = false
                            } else {
                                codigoRepetido = true
                            }
                        }
                    } else {
                        viewModel.actualizarMaterial(material) { guardado ->
                            if (guardado) {
                                mostrarFormulario = false
                            } else {
                                codigoRepetido = true
                            }
                        }
                    }
                },
                alCancelar = {
                    mostrarFormulario = false
                }
            )
        }
    }
}