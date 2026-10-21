package com.example.inventarioindustrial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inventarioindustrial.ui.theme.ButtonShape
import com.example.inventarioindustrial.ui.theme.FbGreen
import com.example.inventarioindustrial.ui.theme.InventarioIndustrialTheme
import java.text.NumberFormat
import java.util.Locale

// Representa la información de un material del almacén
data class Material(
    val id: Int,
    val code: String,
    val description: String,
    val unit: String,
    val price: Int,
    val manufacturer: String,
    val stock: Int,
    val category: String,
    val usage: String,
    val image: Int
)

data class Category(
    val name: String
)

object Routes {
    const val HOME = "inicio"
    const val WAREHOUSE = "almacen"
    const val REQUEST = "solicitud"
}

// Un material con existencias entre 1 y este valor se considera con stock bajo
const val LOW_STOCK_LIMIT = 500

fun formatPrice(value: Int): String =
    "$" + NumberFormat.getIntegerInstance(Locale.forLanguageTag("es-CO")).format(value)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            InventarioIndustrialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InventarioIndustrialApp()
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioIndustrialApp() {

    var searchQuery by remember {
        mutableStateOf("")
    }

    var homeCategory by remember {
        mutableStateOf<String?>(null)
    }

    var warehouseCategory by remember {
        mutableStateOf<String?>(null)
    }

    var requestQuantities by remember {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Materiales del almacén (el stock cambia al registrar ingresos)
    var materials by remember {
        mutableStateOf(
            listOf(
                Material(
                    id = 1,
                    code = "0023NE",
                    description = "Cable 1/0 AWG centeflex",
                    unit = "m",
                    price = 45522,
                    manufacturer = "ERP CLESUS",
                    stock = 1200,
                    category = "Cables",
                    usage = "Conductor flexible para alimentación de tableros y equipos industriales de alta corriente.",
                    image = R.drawable.cable_1
                ),
                Material(
                    id = 2,
                    code = "0024NE",
                    description = "Cable 2 AWG centeflex",
                    unit = "m",
                    price = 30659,
                    manufacturer = "ERP CLESUS",
                    stock = 950,
                    category = "Cables",
                    usage = "Conductor flexible para conexión de motores y circuitos de potencia.",
                    image = R.drawable.cable_2
                ),
                Material(
                    id = 3,
                    code = "0025NE",
                    description = "Cable 2/0 AWG centeflex",
                    unit = "m",
                    price = 54536,
                    manufacturer = "ERP CLESUS",
                    stock = 600,
                    category = "Cables",
                    usage = "Conductor flexible para acometidas y alimentación de maquinaria pesada.",
                    image = R.drawable.cable_3
                ),
                Material(
                    id = 4,
                    code = "0026NE",
                    description = "Cable 4 AWG centeflex",
                    unit = "m",
                    price = 16479,
                    manufacturer = "ERP CLESUS",
                    stock = 1500,
                    category = "Cables",
                    usage = "Conductor flexible para circuitos de distribución y conexión de equipos.",
                    image = R.drawable.cable_4
                ),
                Material(
                    id = 5,
                    code = "0027NE",
                    description = "Cable 4/0 AWG centeflex",
                    unit = "m",
                    price = 84994,
                    manufacturer = "ERP CLESUS",
                    stock = 400,
                    category = "Cables",
                    usage = "Conductor flexible de gran calibre para alimentadores principales.",
                    image = R.drawable.cable_5
                ),
                Material(
                    id = 6,
                    code = "0028NE",
                    description = "Cable Centeflex 6 AWG",
                    unit = "m",
                    price = 10625,
                    manufacturer = "ERP CLESUS",
                    stock = 846,
                    category = "Cables",
                    usage = "Conductor flexible para circuitos de control y cargas medias.",
                    image = R.drawable.cable_6
                ),
                Material(
                    id = 7,
                    code = "0042NE",
                    description = "Descargador sobretensiones PSM3-30/750 TNC",
                    unit = "und",
                    price = 525467,
                    manufacturer = "ERP CLESUS",
                    stock = 0,
                    category = "Protección",
                    usage = "Protege la instalación eléctrica y los equipos contra sobretensiones transitorias y descargas atmosféricas.",
                    image = R.drawable.descargador_psm3
                ),
                Material(
                    id = 8,
                    code = "0103IE",
                    description = "Nomex 0.13 mm",
                    unit = "kg",
                    price = 62893,
                    manufacturer = "ERP CLESUS",
                    stock = 768,
                    category = "Aislantes",
                    usage = "Papel aislante térmico y eléctrico para bobinados de motores y transformadores.",
                    image = R.drawable.nomex_013
                )
            )
        )
    }

    val categories = listOf(
        Category("Cables"),
        Category("Protección"),
        Category("Aislantes")
    )

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(
                navController.graph.findStartDestination().id
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // Sube la cantidad solicitada sin superar el stock disponible
    fun increaseQuantity(materialId: Int) {
        val material = materials.first { it.id == materialId }
        val currentlyQuantity = requestQuantities[materialId] ?: 0

        if (currentlyQuantity < material.stock) {
            requestQuantities = requestQuantities + (
                    materialId to (currentlyQuantity + 1)
                    )
        }
    }

    fun decreaseQuantity(materialId: Int) {
        val currentlyQuantity = requestQuantities[materialId] ?: 0

        if (currentlyQuantity > 1) {
            requestQuantities = requestQuantities + (
                    materialId to (currentlyQuantity - 1)
                    )
        } else {
            requestQuantities = requestQuantities - materialId
        }
    }

    // Registra el ingreso de un nuevo pedido al almacén
    fun addStock(materialId: Int, amount: Int) {
        materials = materials.map { material ->
            if (material.id == materialId) {
                material.copy(stock = material.stock + amount)
            } else {
                material
            }
        }
    }

    // Cantidad de materiales distintos en la solicitud
    val requestCount = requestQuantities.size

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
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val itemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.HOME,
                    onClick = {
                        navigateTo(Routes.HOME)
                    },
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("Inicio")
                    },
                    colors = itemColors
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.WAREHOUSE,
                    onClick = {
                        navigateTo(Routes.WAREHOUSE)
                    },
                    icon = {
                        Icon(Icons.Filled.Place, contentDescription = null)
                    },
                    label = {
                        Text("Almacén")
                    },
                    colors = itemColors
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.REQUEST,
                    onClick = {
                        navigateTo(Routes.REQUEST)
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (requestCount > 0) {
                                    Badge {
                                        Text(requestCount.toString())
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
                    colors = itemColors
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // VISTA DE HOME
            composable(Routes.HOME) {
                HomeScreen(
                    categories = categories,
                    materials = materials,
                    selectedCategory = homeCategory,
                    onCategoryChange = { newCategory ->
                        homeCategory = newCategory
                    },
                    onRequestClick = {
                        navigateTo(Routes.WAREHOUSE)
                    }
                )
            }

            // VISTA DE WAREHOUSE
            composable(Routes.WAREHOUSE) {
                WarehouseScreen(
                    searchQuery = searchQuery,
                    onSearchChange = { newText ->
                        searchQuery = newText
                    },
                    categories = categories,
                    selectedCategory = warehouseCategory,
                    onCategoryChange = { newCategory ->
                        warehouseCategory = newCategory
                    },
                    materials = materials,
                    requestQuantities = requestQuantities,
                    onIncrease = { materialId ->
                        increaseQuantity(materialId)
                    },
                    onDecrease = { materialId ->
                        decreaseQuantity(materialId)
                    },
                    onAddStock = { materialId, amount ->
                        addStock(materialId, amount)
                    }
                )
            }

            // VISTA DE REQUEST
            composable(Routes.REQUEST) {
                RequestScreen(
                    materials = materials,
                    requestQuantities = requestQuantities,
                    onIncrease = { materialId ->
                        increaseQuantity(materialId)
                    },
                    onDecrease = { materialId ->
                        decreaseQuantity(materialId)
                    },
                    onClear = {
                        requestQuantities = emptyMap()
                    },
                    onConfirm = {
                        requestQuantities = emptyMap()
                    },
                    onGoToWarehouse = {
                        navigateTo(Routes.WAREHOUSE)
                    }
                )
            }
        }
    }
}
@Composable
fun HomeScreen(
    categories: List<Category>,
    materials: List<Material>,
    selectedCategory: String?,
    onCategoryChange: (String?) -> Unit,
    onRequestClick: () -> Unit
) {
    val filteredMaterials = materials.filter { material ->
        selectedCategory == null || material.category == selectedCategory
    }

    val lowStockCount = materials.count { it.stock in 1..LOW_STOCK_LIMIT }
    val outOfStockCount = materials.count { it.stock == 0 }

    var detailMaterial by remember {
        mutableStateOf<Material?>(null)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ENCABEZADO Y TARJETAS
        item {
            Text(
                text = "Detalles de existencias en almacén",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    label = "Materiales",
                    value = materials.size.toString(),
                    valueColor = MaterialTheme.colorScheme.primary
                )
                StatCard(
                    label = "Stock bajo",
                    value = lowStockCount.toString(),
                    valueColor = MaterialTheme.colorScheme.tertiary
                )
                StatCard(
                    label = "Sin stock",
                    value = outOfStockCount.toString(),
                    valueColor = MaterialTheme.colorScheme.error
                )
            }
        }

        // SOLICITAR AL ALMACÉN
        item {
            Button(
                onClick = onRequestClick,
                shape = ButtonShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar al almacén")
            }
        }

        // CATEGORÍAS
        item {
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category.name

                    CategoryBadge(
                        name = category.name,
                        selected = isSelected,
                        onClick = {
                            // Tocar la categoría activa quita el filtro
                            onCategoryChange(if (isSelected) null else category.name)
                        }
                    )
                }
            }
        }

        // MATERIALES
        items(filteredMaterials, key = { it.id }) { material ->
            MaterialInfoCard(
                material = material,
                onDetailsClick = {
                    detailMaterial = material
                }
            )
        }
    }

    detailMaterial?.let { material ->
        MaterialDetailDialog(
            material = material,
            onDismiss = {
                detailMaterial = null
            }
        )
    }
}

@Composable
fun WarehouseScreen(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    categories: List<Category>,
    selectedCategory: String?,
    onCategoryChange: (String?) -> Unit,
    materials: List<Material>,
    requestQuantities: Map<Int, Int>,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    onAddStock: (Int, Int) -> Unit
) {
    // Búsqueda por descripción o código + filtro por categoría
    val filteredMaterials = materials.filter { material ->
        val matchesText = material.description.contains(searchQuery, ignoreCase = true) ||
                material.code.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == null || material.category == selectedCategory
        matchesText && matchesCategory
    }

    var showStockDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Almacén",
                style = MaterialTheme.typography.headlineSmall
            )

            FilledTonalButton(
                onClick = {
                    showStockDialog = true
                },
                shape = ButtonShape
            ) {
                Text("Gestionar stock")
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            label = {
                Text("Buscar materiales")
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                CategoryBadge(
                    name = "Todos",
                    selected = selectedCategory == null,
                    onClick = {
                        onCategoryChange(null)
                    }
                )
            }
            items(categories) { category ->
                val isSelected = selectedCategory == category.name

                CategoryBadge(
                    name = category.name,
                    selected = isSelected,
                    onClick = {
                        // Tocar la categoría activa quita el filtro
                        onCategoryChange(if (isSelected) null else category.name)
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (filteredMaterials.isEmpty()) {
                item {
                    Text(
                        text = "No se encontraron materiales",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                items(filteredMaterials, key = { it.id }) { material ->
                    val quantity = requestQuantities[material.id] ?: 0

                    MaterialCard(
                        material = material,
                        quantity = quantity,
                        onIncrease = {
                            onIncrease(material.id)
                        },
                        onDecrease = {
                            onDecrease(material.id)
                        }
                    )
                }
            }
        }
    }

    if (showStockDialog) {
        StockDialog(
            materials = materials,
            onDismiss = {
                showStockDialog = false
            },
            onConfirm = { materialId, amount ->
                onAddStock(materialId, amount)
                showStockDialog = false
            }
        )
    }
}

@Composable
fun RequestScreen(
    materials: List<Material>,
    requestQuantities: Map<Int, Int>,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    onClear: () -> Unit,
    onConfirm: () -> Unit,
    onGoToWarehouse: () -> Unit
) {
    val selectedMaterials = materials.filter { material ->
        (requestQuantities[material.id] ?: 0) > 0
    }

    var showConfirmation by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Mi solicitud",
            style = MaterialTheme.typography.headlineSmall
        )

        if (selectedMaterials.isEmpty()) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aún no has agregado materiales a tu solicitud",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = onGoToWarehouse,
                shape = ButtonShape
            ) {
                Text("Ir al almacén")
            }
        } else {
            Text(
                text = "${selectedMaterials.size} materiales seleccionados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(selectedMaterials, key = { it.id }) { material ->
                    RequestItemRow(
                        material = material,
                        quantity = requestQuantities[material.id] ?: 0,
                        onIncrease = {
                            onIncrease(material.id)
                        },
                        onDecrease = {
                            onDecrease(material.id)
                        }
                    )
                }
            }

            Button(
                onClick = {
                    onConfirm()
                    showConfirmation = true
                },
                shape = ButtonShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar pedido")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedButton(
                onClick = onClear,
                shape = ButtonShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vaciar solicitud")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }

    // Aviso que aparece después de confirmar el pedido
    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = {
                showConfirmation = false
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = FbGreen,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text("Solicitud realizada")
            },
            text = {
                Text("Se realizó su solicitud")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmation = false
                    },
                    shape = ButtonShape
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
// Color del stock según el nivel de existencias
@Composable
fun stockColor(stock: Int): Color = when {
    stock == 0 -> MaterialTheme.colorScheme.error
    stock <= LOW_STOCK_LIMIT -> MaterialTheme.colorScheme.tertiary
    else -> MaterialTheme.colorScheme.onSurfaceVariant
}

/*
* Si la cantidad es 0, muestra agregar
* Al presionar Agregar, la cantidad pasa a 1
* Cuando la cantidad es mayor que 0, aparece - y +
* Al presionar +, la cantidad aumenta (hasta el stock disponible)
* Al presionar -, la cantidad disminuye
* Cuando llega a 0, vuelve a aparecer agregar
* En la barra inferior se ve cuántos materiales hay en la solicitud
* */
@Composable
fun QuantitySelector(
    quantity: Int,
    maxQuantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    if (maxQuantity == 0) {
        Button(
            onClick = {},
            enabled = false,
            shape = ButtonShape
        ) {
            Text("Sin stock")
        }
    } else if (quantity == 0) {
        Button(
            onClick = onIncrease,
            shape = ButtonShape
        ) {
            Text("Agregar")
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onDecrease,
                shape = ButtonShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Text("-")
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedButton(
                onClick = onIncrease,
                enabled = quantity < maxQuantity,
                shape = ButtonShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Text("+")
            }
        }
    }
}

// Tarjeta del almacén: permite agregar el material a la solicitud
@Composable
fun MaterialCard(
    material: Material,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = material.image
                    ),
                    contentDescription = material.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Fit
                )

                if (material.stock == 0) {
                    SoldOutBadge(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Descripción
            Text(
                text = material.description,
                style = MaterialTheme.typography.titleMedium
            )

            // Código
            Text(
                text = "Código: ${material.code}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Stock: ${material.stock} ${material.unit}",
                style = MaterialTheme.typography.bodyMedium,
                color = stockColor(material.stock)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Precio y botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "${formatPrice(material.price)} / ${material.unit}",
                    style = MaterialTheme.typography.titleMedium
                )

                QuantitySelector(
                    quantity = quantity,
                    maxQuantity = material.stock,
                    onIncrease = onIncrease,
                    onDecrease = onDecrease
                )
            }
        }
    }
}

// Tarjeta de consulta de Inicio: solo muestra información, no permite solicitar
@Composable
fun MaterialInfoCard(
    material: Material,
    onDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = material.image
                ),
                contentDescription = material.description,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = material.description,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Código: ${material.code}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Stock: ${material.stock} ${material.unit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = stockColor(material.stock)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                FilledTonalButton(
                    onClick = onDetailsClick,
                    shape = ButtonShape
                ) {
                    Text("Ver detalles")
                }
            }
        }
    }
}

@Composable
fun RequestItemRow(
    material: Material,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = material.image
                ),
                contentDescription = material.description,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = material.description,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Código: ${material.code}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Cantidad: $quantity ${material.unit}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                QuantitySelector(
                    quantity = quantity,
                    maxQuantity = material.stock,
                    onIncrease = onIncrease,
                    onDecrease = onDecrease
                )
            }
        }
    }
}

@Composable
fun MaterialDetailDialog(
    material: Material,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Detalles del material")
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = material.image
                    ),
                    contentDescription = material.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Fit
                )

                DetailRow("Descripción", material.description)
                DetailRow("Código", material.code)
                DetailRow("Precio", "${formatPrice(material.price)} / ${material.unit}")
                DetailRow("Fabricante", material.manufacturer)
                DetailRow("Stock", "${material.stock} ${material.unit}")
                DetailRow("Utilidad", material.usage)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

// Formulario para registrar el ingreso de un nuevo pedido al almacén
@Composable
fun StockDialog(
    materials: List<Material>,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    var selectedId by remember {
        mutableStateOf(materials.first().id)
    }
    var amountText by remember {
        mutableStateOf("")
    }
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    var showError by remember {
        mutableStateOf(false)
    }

    val selectedMaterial = materials.first { it.id == selectedId }
    val amount = amountText.toIntOrNull() ?: 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Gestión de stock")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Registra el ingreso de un nuevo pedido al almacén",
                    style = MaterialTheme.typography.bodyMedium
                )

                Box {
                    OutlinedButton(
                        onClick = {
                            menuExpanded = true
                        },
                        shape = ButtonShape,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedMaterial.description,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        }
                    ) {
                        materials.forEach { material ->
                            DropdownMenuItem(
                                text = {
                                    Text("${material.code} - ${material.description}")
                                },
                                onClick = {
                                    selectedId = material.id
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Stock actual: ${selectedMaterial.stock} ${selectedMaterial.unit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = stockColor(selectedMaterial.stock)
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { newText ->
                        amountText = newText.filter { it.isDigit() }.take(7)
                        showError = false
                    },
                    label = {
                        Text("Cantidad que ingresa (${selectedMaterial.unit})")
                    },
                    singleLine = true,
                    isError = showError,
                    supportingText = {
                        if (showError) {
                            Text("Ingresa una cantidad mayor a 0")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (amount > 0) {
                        onConfirm(selectedId, amount)
                    } else {
                        showError = true
                    }
                },
                shape = ButtonShape
            ) {
                Text("Registrar ingreso")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SoldOutBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.error,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = "AGOTADO",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Composable
fun RowScope.StatCard(label: String, value: String, valueColor: Color) {
    Card(
        modifier = Modifier.weight(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = valueColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Botón tipo pill para filtrar por categoría
@Composable
fun CategoryBadge(name: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.primary
        ),
        border = null
    )
}

@Preview(showBackground = true)
@Composable
fun InventarioIndustrialAppPreview() {
    InventarioIndustrialTheme {
        InventarioIndustrialApp()
    }
}