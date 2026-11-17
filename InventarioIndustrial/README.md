# Inventario Industrial

Estudiante: Santiago Mesa
Asignatura: Aplicativos Móviles (Kotlin + Jetpack Compose + Room)

## Negocio

Empresa que maneja un almacén de materiales industriales (cables, protecciones eléctricas, aislantes). Cada material tiene código, descripción, unidad, precio, fabricante, stock y utilidad.

## Objetivo

Consultar y administrar las existencias del almacén, solicitar materiales respetando el stock y llevar el historial de entradas y salidas de cada material. Los datos se guardan en el dispositivo con Room.

## Arquitectura

```
app/src/main/java/com/example/inventarioindustrial
├── model/           Material, Movimiento, estados y filtros de stock
├── data/
│   ├── local/       MaterialEntidad, MovimientoEntidad, DAOs e InventarioBaseDatos (Room)
│   ├── repository/  InventarioRepositorio (convierte entidades a modelos y aplica las reglas)
│   └── MaterialesIniciales.kt   datos de la primera ejecución
├── viewmodel/       InventarioViewModel (StateFlow) y su Factory
├── ui/
│   ├── screens/     PantallaInicio, PantallaAlmacen, PantallaSolicitud, PantallaDetalleMaterial
│   ├── components/  tarjetas, formulario, diálogos, filtros y estadísticas
│   └── theme/       Color, Theme y Type (estilo Facebook con Material 3)
└── MainActivity.kt  configura Room, el ViewModel y Navigation Compose
```

## Room

- **Entidades relacionadas:** `MaterialEntidad` (tabla `materiales`) y `MovimientoEntidad` (tabla `movimientos`). Cada movimiento pertenece a un material con clave foránea y se borra junto con él.
- **CRUD completo sobre `MaterialEntidad`:** crear, consultar, actualizar y eliminar.
- **Flow / StateFlow:** los DAOs devuelven `Flow` y el ViewModel los expone como `StateFlow` que la interfaz observa.
- **Persistencia:** los 8 materiales iniciales se cargan una sola vez al crear la base de datos; todo cambio posterior se conserva al cerrar y volver a abrir la app.

## Pantallas

| Pantalla | Propósito |
|---|---|
| **Inicio** | Estadísticas del almacén, botón "Solicitar al almacén", filtros por categoría y por estado de stock, lista de materiales y botón "Nuevo material". |
| **Detalle** | Datos completos del material, botones Editar y Eliminar, e historial de movimientos. |
| **Almacén** | Búsqueda, filtro por categoría, Agregar / - / + por material y "Gestionar stock". |
| **Solicitud** | Materiales elegidos y "Confirmar pedido". |

## Funcionalidades nuevas y cómo probarlas

1. **Registro.** Inicio -> "+ Nuevo material" -> completar el formulario (todos los campos son obligatorios, el precio debe ser mayor a 0 y el código no puede repetirse) -> "Guardar material". Aparece en la lista.
2. **Consulta.** Inicio -> tocar un material o "Ver detalles". Se abre su pantalla de detalle con la ficha completa y su historial de entradas y salidas.
3. **Edición.** Detalle -> "Editar" -> cambiar por ejemplo el precio o el stock -> "Guardar cambios". La ficha se actualiza al instante.
4. **Eliminación.** Detalle -> "Eliminar" -> confirmar. El material y su historial desaparecen.
5. **Filtro por estado de stock y estadísticas.** Inicio -> las tarjetas muestran total, stock bajo y sin stock calculados desde Room, y los botones Todos / Stock bajo / Sin stock filtran la lista.

Funciones que ya existían y ahora se guardan en Room:

- **Gestionar stock** (Almacén): registra el ingreso de un pedido, sube el stock y deja una entrada en el historial.
- **Confirmar pedido** (Solicitud): descuenta el stock y deja una salida en el historial.
- **Búsqueda, filtro por categoría y cantidades** con el stock real.

Para comprobar la persistencia: registrar un material o un ingreso de stock, cerrar la aplicación por completo y volver a abrirla; los cambios se conservan.

## Identidad visual

Estilo inspirado en Facebook con Material 3, definido en `Color.kt`, `Theme.kt` y `Type.kt`, e ícono de lanzamiento propio (estante de almacén con cajas sobre fondo azul).

## Cómo ejecutar

1. Abrir la carpeta `InventarioIndustrial` en Android Studio y esperar la sincronización de Gradle.
2. Seleccionar un emulador o dispositivo (minSdk 26) y pulsar Run.
3. APK de prueba: `InventarioIndustrial/apk/InventarioIndustrial-debug.apk` (se regenera con `./gradlew assembleDebug`).
