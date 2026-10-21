# Inventario Industrial

Estudiante: Santiago Mesa
Asignatura: Aplicativos Móviles (Kotlin + Jetpack Compose)

## Negocio

Empresa que maneja un almacén de materiales industriales (cables, protecciones eléctricas, aislantes). Cada material tiene código, descripción, unidad de medida, precio, fabricante, stock y utilidad.

## Objetivo de la aplicación

Consultar las existencias del almacén, solicitar materiales respetando el stock disponible y registrar el ingreso de nuevos pedidos al almacén.

## Pantallas (Navigation Compose)

| Pantalla | Propósito |
|---|---|
| **Inicio** | Resumen de existencias (cantidad de materiales, stock bajo y sin stock), botón "Solicitar al almacén", categorías y lista de materiales con "Ver detalles" (descripción, código, precio, fabricante, stock y utilidad). Solo consulta. |
| **Almacén** | Búsqueda, filtro por categoría, botones Agregar / - / + por material y botón "Gestionar stock". |
| **Solicitud** | Materiales elegidos con sus cantidades, "Confirmar pedido" (vacía la solicitud y muestra un aviso) y "Vaciar solicitud". |

## Funcionalidades interactivas

1. **Cantidades y búsqueda (trabajada en clase).** En Almacén se busca por descripción o código y cada material tiene Agregar / - / + limitado por el stock. El contador de la barra inferior muestra cuántos materiales hay en la solicitud y la pantalla Solicitud muestra las cantidades elegidas.
2. **Filtro por categorías (nueva).** Botones tipo pill (Cables, Protección, Aislantes) que filtran la lista en Inicio y en Almacén (donde se combinan con la búsqueda).
3. **Gestión de stock (nueva).** En Almacén, "Gestionar stock" abre un formulario con validación para elegir un material y registrar la cantidad que ingresa con un nuevo pedido. El stock se actualiza y se reflejan los cambios en las tarjetas de Inicio (stock bajo y sin stock) y en los límites de la solicitud.

Además, "Ver detalles" abre un cuadro con la ficha completa del material y "Confirmar pedido" muestra el aviso de solicitud realizada.

## Identidad visual

Estilo inspirado en Facebook con Material 3: azul de acento, fondo gris claro, tarjetas blancas con esquinas suaves, botones secundarios grises, botones tipo pill, barra inferior con iconos azules e insignia roja, y tema oscuro. Definido en `Color.kt`, `Theme.kt` y `Type.kt`.

## Cómo ejecutar

1. Abrir la carpeta `InventarioIndustrial` en Android Studio.
2. Esperar a que Gradle sincronice.
3. Seleccionar un emulador o dispositivo (minSdk 24) y pulsar Run.
