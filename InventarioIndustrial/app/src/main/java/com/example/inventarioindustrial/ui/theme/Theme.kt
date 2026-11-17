package com.example.inventarioindustrial.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val ColoresOscuros = darkColorScheme(
    primary = AzulPrincipalOscuro,
    onPrimary = Blanco,
    primaryContainer = AzulClaroOscuro,
    onPrimaryContainer = AzulPrincipalOscuro,
    secondary = TextoPrincipalOscuro,
    onSecondary = FondoOscuro,
    secondaryContainer = GrisBotonOscuro,
    onSecondaryContainer = TextoPrincipalOscuro,
    tertiary = AmbarOscuro,
    background = FondoOscuro,
    onBackground = TextoPrincipalOscuro,
    surface = SuperficieOscura,
    onSurface = TextoPrincipalOscuro,
    surfaceVariant = GrisBotonOscuro,
    onSurfaceVariant = TextoSecundarioOscuro,
    surfaceContainer = SuperficieOscura,
    surfaceContainerLow = SuperficieOscura,
    surfaceContainerHigh = SuperficieOscura,
    surfaceContainerHighest = SuperficieOscura,
    outline = DivisorOscuro,
    outlineVariant = DivisorOscuro,
    error = RojoOscuro
)

private val ColoresClaros = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = Blanco,
    primaryContainer = AzulClaro,
    onPrimaryContainer = AzulPrincipal,
    secondary = TextoPrincipal,
    onSecondary = Blanco,
    secondaryContainer = GrisBoton,
    onSecondaryContainer = TextoPrincipal,
    tertiary = Ambar,
    background = FondoGris,
    onBackground = TextoPrincipal,
    surface = Blanco,
    onSurface = TextoPrincipal,
    surfaceVariant = GrisBoton,
    onSurfaceVariant = TextoSecundario,
    surfaceContainer = Blanco,
    surfaceContainerLow = Blanco,
    surfaceContainerHigh = Blanco,
    surfaceContainerHighest = Blanco,
    outline = Divisor,
    outlineVariant = Divisor,
    error = Rojo
)

// Esquinas poco redondeadas, como las tarjetas y botones de Facebook
private val Formas = Shapes(
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(12.dp)
)

val FormaBoton = RoundedCornerShape(6.dp)

// No se usa el color dinámico para conservar la identidad visual del negocio
@Composable
fun InventarioIndustrialTema(
    content: @Composable () -> Unit
) {
    val colores = if (isSystemInDarkTheme()) ColoresOscuros else ColoresClaros

    MaterialTheme(
        colorScheme = colores,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}