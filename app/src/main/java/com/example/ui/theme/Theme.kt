package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = HarborTeal,
    onPrimary = Color.White,
    primaryContainer = HarborTealSoft,
    onPrimaryContainer = HarborTeal,
    secondary = Terracotta,
    onSecondary = Color.White,
    secondaryContainer = TerracottaSoft,
    onSecondaryContainer = Color(0xFF5D210B),
    tertiary = AmberBrass,
    onTertiary = Color.White,
    tertiaryContainer = AmberSoft,
    onTertiaryContainer = Color(0xFF563400),
    background = WarmCream,
    onBackground = EspressoInk,
    surface = WarmPaper,
    onSurface = EspressoInk,
    surfaceVariant = SandSurface,
    onSurfaceVariant = WarmSlate,
    surfaceContainerLow = SandSurface,
    surfaceContainer = SandSurface,
    surfaceContainerHigh = SandVariant,
    outline = HairlineWarm,
    outlineVariant = Color(0xFFE7DFD4),
    error = Color(0xFFB3261E),
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = NightTeal,
    onPrimary = Color(0xFF0A2622),
    primaryContainer = Color(0xFF20453E),
    onPrimaryContainer = HarborTealSoft,
    secondary = NightTerracotta,
    onSecondary = Color(0xFF3E1506),
    secondaryContainer = Color(0xFF5A2715),
    onSecondaryContainer = TerracottaSoft,
    tertiary = NightAmber,
    onTertiary = Color(0xFF3A2300),
    tertiaryContainer = Color(0xFF4D3410),
    onTertiaryContainer = AmberSoft,
    background = NightHarbor,
    onBackground = NightText,
    surface = NightCard,
    onSurface = NightText,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightMuted,
    surfaceContainerLow = NightCard,
    surfaceContainer = NightCard,
    surfaceContainerHigh = NightSurfaceVariant,
    outline = Color(0xFF3D4744),
    outlineVariant = Color(0xFF2D3633),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)

val NewHavenShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun NewHavenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = NewHavenTypography,
        shapes = NewHavenShapes,
        content = content
    )
}
