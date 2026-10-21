package com.example.inventarioindustrial.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = FbBlueDark,
    onPrimary = Color.White,
    primaryContainer = FbBlueTintDark,
    onPrimaryContainer = FbBlueDark,
    secondary = FbTextDark,
    onSecondary = FbBackgroundDark,
    secondaryContainer = FbPillDark,
    onSecondaryContainer = FbTextDark,
    tertiary = FbAmberDark,
    background = FbBackgroundDark,
    onBackground = FbTextDark,
    surface = FbSurfaceDark,
    onSurface = FbTextDark,
    surfaceVariant = FbPillDark,
    onSurfaceVariant = FbTextSecondaryDark,
    surfaceContainer = FbSurfaceDark,
    surfaceContainerHigh = FbSurfaceDark,
    surfaceContainerHighest = FbSurfaceDark,
    outline = FbDividerDark,
    outlineVariant = FbDividerDark,
    error = FbRedDark
)

private val LightColorScheme = lightColorScheme(
    primary = FbBlue,
    onPrimary = Color.White,
    primaryContainer = FbBlueLight,
    onPrimaryContainer = FbBlue,
    secondary = FbText,
    onSecondary = Color.White,
    secondaryContainer = FbPill,
    onSecondaryContainer = FbText,
    tertiary = FbAmber,
    background = FbBackground,
    onBackground = FbText,
    surface = FbSurface,
    onSurface = FbText,
    surfaceVariant = FbPill,
    onSurfaceVariant = FbTextSecondary,
    surfaceContainer = FbSurface,
    surfaceContainerHigh = FbSurface,
    surfaceContainerHighest = FbSurface,
    outline = FbDivider,
    outlineVariant = FbDivider,
    error = FbRed
)

// Esquinas poco redondeadas, como las tarjetas y botones de Facebook
private val AppShapes = Shapes(
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(12.dp)
)

val ButtonShape = RoundedCornerShape(6.dp)

// El color dinámico se desactiva para conservar la identidad visual del negocio
@Composable
fun InventarioIndustrialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
